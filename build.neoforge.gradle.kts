import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.Sync
import org.gradle.jvm.tasks.Jar
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    `java-library`
    id("net.neoforged.moddev") version "2.0.140"
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
val loaderRange = project.property("mod.loader_range") as String
val packFormat = project.property("mod.pack_format") as String
val architecturyVersion = project.property("deps.architectury") as String

group = modGroup
version = "$modVersion+${sc.current.version}"

base {
    archivesName.set("$modId-neoforge")
}

repositories {
    mavenCentral()
    maven("https://maven.architectury.dev/") { name = "Architectury" }
}

dependencies {
    implementation("dev.architectury:architectury-neoforge:$architecturyVersion")
}

// Forge renamed its public packages when NeoForge split off. Keeping 500+ import-only
// Stonecutter branches in the source would hide the meaningful version differences, so
// this target performs that namespace-only relocation in generated sources. Actual API
// changes still live explicitly in the shared source as Stonecutter branches.
val relocateLoaderNamespace = tasks.register<Sync>("relocateLoaderNamespace") {
    dependsOn("stonecutterGenerate")
    from(layout.buildDirectory.dir("generated/stonecutter/main/java"))
    into(layout.buildDirectory.dir("generated/neoforge/main/java"))
    filter { line: String ->
        line
            .replace("net.minecraftforge.api.distmarker", "net.neoforged.api.distmarker")
            .replace("net.minecraftforge.eventbus.api", "net.neoforged.bus.api")
            .replace("net.minecraftforge.fml", "net.neoforged.fml")
            .replace("net.minecraftforge", "net.neoforged.neoforge")
    }
}

neoForge {
    version = project.property("deps.neoforge") as String

    runs {
        register("client") {
            client()
            gameDirectory = rootProject.file("run/${sc.current.version}-neoforge")
            programArguments.addAll("--username", "talhanation")
        }
        register("server") {
            server()
            gameDirectory = rootProject.file("run/${sc.current.version}-neoforge")
            programArgument("--nogui")
        }
        register("data") {
            data()
            gameDirectory = rootProject.file("run-data/${sc.current.version}-neoforge")
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

sourceSets.main {
    java.setSrcDirs(listOf(layout.buildDirectory.dir("generated/neoforge/main/java")))
    resources.srcDir(rootProject.file("src/generated/resources"))
}

java {
    withSourcesJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<JavaCompile>().configureEach {
    dependsOn(relocateLoaderNamespace)
    options.encoding = "UTF-8"
    options.release.set(21)
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
        "loader_range" to loaderRange,
        "architectury_version" to architecturyVersion,
        "pack_format" to packFormat,
        "java_compatibility" to "JAVA_21"
    )
    inputs.properties(props)
    filesMatching(listOf("META-INF/neoforge.mods.toml", "pack.mcmeta", "mixins.recruits.json")) {
        expand(props)
    }
    exclude("META-INF/mods.toml")
    includeEmptyDirs = false
    eachFile {
        path = path
            .replace("/recipes/", "/recipe/")
            .replace("/loot_tables/", "/loot_table/")
            .replace("/structures/", "/structure/")
            .replace("/tags/blocks/", "/tags/block/")
            .replace("/tags/items/", "/tags/item/")
    }
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
