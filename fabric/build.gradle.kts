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
val modLicense = project.property("mod.license") as String
val modDisplayUrl = project.property("mod.display_url") as String
val minecraftRange = project.property("mod.minecraft_range") as String
val fabricLoaderVersion = project.property("deps.fabric_loader") as String
val fabricApiVersion = project.property("deps.fabric_api") as String
val architecturyVersion = project.property("deps.architectury") as String
val javaVersion = project.property("mod.java").toString().toInt()
val mcVersion = sc.current.version

val commonPath = ":$mcVersion"
evaluationDependsOn(commonPath)
val commonProject = project(commonPath)

group = modGroup
version = "$modVersion+$mcVersion"

base {
    archivesName.set("$modId-fabric")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    silentMojangMappingsLicense()
    accessWidenerPath.set(commonProject.extensions.getByType<net.fabricmc.loom.api.LoomGradleExtensionAPI>().accessWidenerPath)

    // Remap mixin targets directly in the jar, so neither common nor fabric mixins need a refmap.
    mixin {
        useLegacyMixinAp.set(false)
    }

    // Lets Fabric Loader treat the common output as part of this mod in development, e.g. to find the access widener.
    mods {
        maybeCreate("main").apply {
            sourceSet(sourceSets.main.get())
            sourceSet(commonProject.sourceSets.main.get(), commonProject)
        }
    }

    runs {
        named("client") {
            client()
            runDir(rootProject.file("run/$mcVersion-fabric").relativeTo(projectDir).path)
            programArgs("--username", "talhanation")
        }
        named("server") {
            server()
            runDir(rootProject.file("run/$mcVersion-fabric").relativeTo(projectDir).path)
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
    named("developmentFabric").get().extendsFrom(common)
}

repositories {
    mavenCentral()
    maven("https://maven.architectury.dev/") { name = "Architectury" }
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
    maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs"
        content { includeGroup("fuzs.forgeconfigapiport") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApiVersion")

    modImplementation("dev.architectury:architectury-fabric:$architecturyVersion")
    modImplementation(project.property("deps.forge_config_api_port") as String)

    common(project(path = commonPath, configuration = "namedElements")) { isTransitive = false }
    shadowBundle(project(path = commonPath, configuration = "transformProductionFabric"))
}

java {
    withSourcesJar()
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.release.set(javaVersion)
}

tasks.named<ProcessResources>("processResources") {
    val props = mapOf(
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_version" to modVersion,
        "mod_description" to modDescription,
        "mod_authors" to modAuthors.split(",").joinToString(", ") { "\"${it.trim()}\"" },
        "mod_license" to modLicense,
        "mod_display_url" to modDisplayUrl,
        "minecraft_range" to minecraftRange,
        "fabric_loader_version" to fabricLoaderVersion,
        "fabric_api_version" to fabricApiVersion.substringBefore('+'),
        "architectury_version" to architecturyVersion,
        "java_version" to javaVersion,
        "java_compatibility" to "JAVA_$javaVersion"
    )
    inputs.properties(props)
    filesMatching(listOf("fabric.mod.json", "mixins.recruits.fabric.json")) {
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

tasks.named<JavaExec>("runServer") {
    standardInput = System.`in`
}

tasks.register<Copy>("buildAndCollect") {
    dependsOn("build")
    from(tasks.named("remapJar"), tasks.named("remapSourcesJar"))
    into(rootProject.layout.buildDirectory.dir("libs/$modVersion"))
}
