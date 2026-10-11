# recruits
Recruit and command villagers, manage armies through custom GUIs, enjoy PvP and team compatibility, and fully configurable features.

https://modrinth.com/mod/villager-recruits

All Rights Reserved unless otherwise explicitly stated.

## Development builds

Use `gradlew.bat` on Windows. Gradle's daemon runs on Java 21, selected by
`gradle/gradle-daemon-jvm.properties`; Minecraft 1.20.1 compilation and runs use
a Java 17 toolchain, while 1.21.1 uses Java 21. Do not change `JAVA_HOME` between
targets. Missing toolchains can be provisioned through Foojay.

```powershell
.\gradlew.bat :forge:1.20.1:build :neoforge:1.21.1:build :fabric:1.20.1:build :fabric:1.21.1:build
.\gradlew.bat :fabric:1.21.1:runClient
.\gradlew.bat :neoforge:1.21.1:runServer
```

Shared sources live in `src/main`; loader code lives in `forge/src`,
`neoforge/src` and `fabric/src`. Edit these sources, not the generated files
under `build/generated/stonecutter`. The common module is compiled for each
Minecraft version and included in the loader JAR. `checkCommonBoundary`
rejects loader-specific APIs in shared code.

Build targets are Forge 1.20.1, NeoForge 1.21.1 and Fabric 1.20.1/1.21.1.
Optional integrations are enabled only on supported targets.
