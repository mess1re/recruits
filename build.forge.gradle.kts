import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.tasks.Jar
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    `java-library`
    id("net.neoforged.moddev.legacyforge") version "2.0.140"
}

val modId = project.property("mod.id") as String
val modName = project.property("mod.name") as String
val modVersion = project.property("mod.version") as String
val modGroup = project.property("mod.group") as String
val modDescription = project.property("mod.description") as String
val modAuthors = project.property("mod.authors") as String
val modCredits = project.property("mod.credits") as String
val modLicense = project.property("mod.license") as String
val modUpdateUrl = project.property("mod.update_url") as String
val modDisplayUrl = project.property("mod.display_url") as String
val minecraftRange = project.property("mod.minecraft_range") as String
val forgeRange = project.property("mod.forge_range") as String
val loaderRange = project.property("mod.loader_range") as String
val packFormat = project.property("mod.pack_format") as String
val architecturyVersion = project.property("deps.architectury") as String
val corelibVersion = project.property("deps.corelib") as String

group = modGroup
version = "$modVersion+${sc.current.version}"

base {
    archivesName.set("$modId-forge")
}

repositories {
    mavenCentral()
    maven("https://maven.architectury.dev/") { name = "Architectury" }
    maven("https://maven.maxhenkel.de/repository/public") { name = "henkelmax.public" }
    maven("https://www.cursemaven.com") {
        name = "CurseMaven"
        content { includeGroup("curse.maven") }
    }
}

dependencies {
    implementation("dev.architectury:architectury-forge:$architecturyVersion")

    // Temporary bridge until the CoreLib networking and menu helpers have local,
    // loader-neutral replacements. Jar-in-Jar preserves the current standalone
    // distribution model without leaking CoreLib onto consumers' compile classpaths.
    implementation("de.maxhenkel.corelib:corelib:$corelibVersion:api")
    runtimeOnly("de.maxhenkel.corelib:corelib:$corelibVersion")
    jarJar("de.maxhenkel.corelib:corelib:[$corelibVersion]")

    implementation("curse.maven:corpse-316582:5157034")
    implementation("curse.maven:epic-knights-armor-and-weapons-509041:5254836")
    implementation("curse.maven:cloth-config-348521:4973441")
    implementation("curse.maven:ewewukeks-musket-mod-354562:5779561")
    implementation("curse.maven:worldedit-225608:4586218")
    implementation("curse.maven:small-ships-450659:5566900")
    implementation("curse.maven:siegeweapons-1259343:7906096")

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

configurations.matching { it.name == "reobfRuntimeElements" }.configureEach {
    extendsFrom(configurations.implementation.get())
}

legacyForge {
    version = project.property("deps.forge") as String
    validateAccessTransformers = true

    runs {
        configureEach {
            gameDirectory = rootProject.file("run/${sc.current.version}-forge")
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("forge.enabledGameTestNamespaces", modId)
        }
        register("client") {
            client()
            programArguments.addAll("--username", "talhanation")
        }
        register("server") {
            server()
            programArgument("--nogui")
        }
        register("data") {
            data()
            gameDirectory = rootProject.file("run-data/${sc.current.version}-forge")
            programArguments.addAll(
                "--mod", modId,
                "--all",
                "--output", rootProject.file("src/generated/resources").absolutePath,
                "--existing", rootProject.file("src/main/resources").absolutePath
            )
        }
    }

    mods {
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}

mixin {
    add(sourceSets.main.get(), "mixins.$modId.refmap.json")
    config("mixins.$modId.json")
}

sourceSets.main {
    resources.srcDir(rootProject.file("src/generated/resources"))
}

java {
    withSourcesJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(17)
}

tasks.named<ProcessResources>("processResources") {
    val props = mapOf(
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_version" to modVersion,
        "mod_description" to modDescription,
        "mod_authors" to modAuthors,
        "mod_credits" to modCredits,
        "mod_license" to modLicense,
        "mod_update_url" to modUpdateUrl,
        "mod_display_url" to modDisplayUrl,
        "minecraft_range" to minecraftRange,
        "forge_range" to forgeRange,
        "loader_range" to loaderRange,
        "architectury_version" to architecturyVersion,
        "pack_format" to packFormat,
        "java_compatibility" to "JAVA_17"
    )
    inputs.properties(props)
    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "mixins.recruits.json")) {
        expand(props)
    }
    exclude("META-INF/neoforge.mods.toml")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "Specification-Title" to modId,
            "Specification-Vendor" to modAuthors,
            "Specification-Version" to "1",
            "Implementation-Title" to modName,
            "Implementation-Version" to archiveVersion.get(),
            "Implementation-Vendor" to modAuthors,
            "MixinConfigs" to "mixins.$modId.json"
        )
    }
}

tasks.named("createMinecraftArtifacts") {
    dependsOn("stonecutterGenerate")
}

tasks.register<Copy>("buildAndCollect") {
    dependsOn("build")
    from(tasks.named<Jar>("jar"), tasks.named<Jar>("sourcesJar"))
    into(rootProject.layout.buildDirectory.dir("libs/$modVersion"))
}
