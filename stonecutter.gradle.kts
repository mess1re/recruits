plugins {
    id("dev.kikugie.stonecutter")
    id("dev.architectury.loom") version "1.17-SNAPSHOT" apply false
    id("architectury-plugin") version "3.5-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "9.4.3" apply false
}

stonecutter active "1.20.1"

subprojects {
    val platform = path.split(':').getOrNull(1)
    if (platform in listOf("forge", "neoforge", "fabric")) extra["loom.platform"] = platform
}

stonecutter parameters {
    val version = current.version
    val module = branch.id.ifEmpty { "common" }

    properties {
        tags(version, module)
    }

    replacements {
        string(current.parsed >= "1.21") {
            replace(
                "protected void defineSynchedData()",
                "protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder)"
            )
            replace("super.defineSynchedData();", "super.defineSynchedData(builder);")
            replace("this.entityData.define(", "builder.define(")
            replace(
                "populateDefaultEquipmentEnchantments(randomsource, difficultyInstance)",
                "populateDefaultEquipmentEnchantments(world, randomsource, difficultyInstance)"
            )
            replace(
                "populateDefaultEquipmentEnchantments(random, difficultyInstance)",
                "populateDefaultEquipmentEnchantments(world, random, difficultyInstance)"
            )
        }
        regex(current.parsed >= "1.21") {
            replace(
                """import net\.minecraftforge\.common\.(?:Forge|Mod)ConfigSpec;""",
                "import net.neoforged.neoforge.common.ModConfigSpec;",
                """import net\.neoforged\.neoforge\.common\.(?:Forge|Mod)ConfigSpec;""",
                "import net.minecraftforge.common.ForgeConfigSpec;"
            )
            replace("""\bForgeConfigSpec\b""", "ModConfigSpec", """\bModConfigSpec\b""", "ForgeConfigSpec")
            replace("""\bBlockPathTypes\b""", "PathType", """\bPathType\b""", "BlockPathTypes")
            replace(
                """new ResourceLocation\(([^,()\r\n]+),\s*([^()\r\n]+)\)""",
                "ResourceLocation.fromNamespaceAndPath(\$1, \$2)",
                """ResourceLocation\.fromNamespaceAndPath\(([^,()\r\n]+),\s*([^()\r\n]+)\)""",
                "new ResourceLocation(\$1, \$2)"
            )
            replace(
                """new ResourceLocation\(([^,()\r\n]+)\)""",
                "ResourceLocation.parse(\$1)",
                """ResourceLocation\.parse\(([^,()\r\n]+)\)""",
                "new ResourceLocation(\$1)"
            )
            replace(
                """(finalizeSpawn\([^,\r\n]+,[^,\r\n]+,[^,\r\n]+,[^,\r\n]+), null\);""",
                "\$1);",
                """(finalizeSpawn\([^,\r\n]+,[^,\r\n]+,[^,\r\n]+, null)\);""",
                "\$1, null);"
            )
        }
    }

    swaps["mod_id"] = "\"${properties.get<String>("mod.id")}\";"
    swaps["mod_version"] = "\"${properties.get<String>("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
}
