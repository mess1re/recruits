plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

val minecraftVersion = stonecutter.current.version
val architecturyVersion = project.property("deps.architectury") as String
val fabricLoaderVersion = project.property("deps.fabric_loader") as String
val javaVersion = project.property("mod.java").toString().toInt()

group = "${project.property("mod.group")}.common"
version = "${project.property("mod.version")}+$minecraftVersion"
base { archivesName.set("${project.property("mod.id")}-common") }

architectury.common(listOf(if (minecraftVersion == "1.20.1") "forge" else "neoforge", "fabric"))

loom {
    silentMojangMappingsLicense()
    accessWidenerPath.set(rootProject.file("src/main/resources/recruits.accesswidener"))
}

repositories {
    mavenCentral()
    maven("https://maven.architectury.dev/")
    maven("https://maven.fabricmc.net/")
    maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs"
        content { includeGroup("fuzs.forgeconfigapiport") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(loom.officialMojangMappings())
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    modImplementation("dev.architectury:architectury:$architecturyVersion")
    modCompileOnly(project.property("deps.forge_config_api_port") as String)
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
    val props = mapOf("java_compatibility" to "JAVA_$javaVersion")
    inputs.properties(props)
    filesMatching("mixins.recruits.json") {
        expand(props)
    }
    if (stonecutter.current.parsed >= "1.21") {
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
}

val commonJava = sourceSets.main.get().allJava
val checkCommonBoundary = tasks.register("checkCommonBoundary") {
    group = "verification"
    description = "Rejects loader-specific APIs in the shared module."
    dependsOn("stonecutterGenerate")
    inputs.files(commonJava)
    doLast {
        val loaderApi = Regex("\\b(net\\.(minecraftforge|neoforged|fabricmc\\.loader)|dev\\.architectury\\.platform\\.(forge|neoforge|fabric))\\.")
        // Config specs come from Forge Config API Port on every loader.
        val configSpec = Regex("\\b(net\\.minecraftforge\\.common\\.ForgeConfigSpec|net\\.neoforged\\.neoforge\\.common\\.ModConfigSpec)\\b")
        val violations = commonJava.files.filter { loaderApi.containsMatchIn(it.readText().replace(configSpec, "")) }
        check(violations.isEmpty()) { "Loader APIs in common: ${violations.joinToString()}" }
    }
}

tasks.named("check") { dependsOn(checkCommonBoundary) }
tasks.withType<JavaCompile>().configureEach { dependsOn(checkCommonBoundary) }
