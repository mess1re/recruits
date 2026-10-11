import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.tasks.Jar
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.gradleup.shadow")
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
val mcVersion = sc.current.version

val commonPath = ":$mcVersion"
evaluationDependsOn(commonPath)
val commonProject = project(commonPath)

group = modGroup
version = "$modVersion+$mcVersion"

base {
    archivesName.set("$modId-forge")
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    silentMojangMappingsLicense()
    accessWidenerPath.set(commonProject.extensions.getByType<net.fabricmc.loom.api.LoomGradleExtensionAPI>().accessWidenerPath)

    // Remap mixin targets directly in the jar, so neither common nor forge mixins need a refmap.
    mixin {
        useLegacyMixinAp.set(false)
    }

    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(accessWidenerPath.get().asFile.name)
        mixinConfig("mixins.$modId.json", "mixins.$modId.forge.json")
    }

    runs {
        named("client") {
            client()
            runDir(rootProject.file("run/$mcVersion-forge").relativeTo(projectDir).path)
            programArgs("--username", "talhanation")
        }
        named("server") {
            server()
            runDir(rootProject.file("run/$mcVersion-forge").relativeTo(projectDir).path)
            programArg("--nogui")
        }
    }
}

val common: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}
val shadowBundle: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}
configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    named("developmentForge").get().extendsFrom(common)
}

repositories {
    mavenCentral()
    maven("https://maven.architectury.dev/") { name = "Architectury" }
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://maven.minecraftforge.net/") { name = "Forge" }
    maven("https://www.cursemaven.com") {
        name = "CurseMaven"
        content { includeGroup("curse.maven") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings(loom.officialMojangMappings())
    forge("net.minecraftforge:forge:${project.property("deps.forge")}")

    modImplementation("dev.architectury:architectury-forge:$architecturyVersion")

    common(project(path = commonPath, configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = commonPath, configuration = "transformProductionForge"))

    modCompileOnly("curse.maven:corpse-316582:5157034")
    modCompileOnly("curse.maven:epic-knights-armor-and-weapons-509041:5254836")
    modCompileOnly("curse.maven:cloth-config-348521:4973441")
    modCompileOnly("curse.maven:ewewukeks-musket-mod-354562:5779561")
    modCompileOnly("curse.maven:worldedit-225608:4586218")
    modCompileOnly("curse.maven:small-ships-450659:5566900")
    modCompileOnly("curse.maven:siegeweapons-1259343:7906096")
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
    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta", "mixins.recruits.forge.json")) {
        expand(props)
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    configurations = listOf(shadowBundle)
    archiveClassifier.set("dev-shadow")
    exclude("architectury.common.json")
}

tasks.named<net.fabricmc.loom.task.RemapJarTask>("remapJar") {
    inputFile.set(tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar").flatMap { it.archiveFile })
    archiveClassifier.set(null as String?)
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Specification-Title" to modId,
            "Specification-Vendor" to modAuthors,
            "Specification-Version" to "1",
            "Implementation-Title" to modName,
            "Implementation-Version" to modVersion,
            "Implementation-Vendor" to modAuthors
        )
    }
}

tasks.named<JavaExec>("runServer") {
    standardInput = System.`in`
}

tasks.register<Copy>("buildAndCollect") {
    dependsOn("build")
    from(tasks.named("remapJar"), tasks.named("remapSourcesJar"))
    into(rootProject.layout.buildDirectory.dir("libs/$modVersion"))
}
