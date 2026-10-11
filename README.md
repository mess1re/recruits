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
.\gradlew.bat :1.20.1-forge:build :1.21.1-neoforge:build
.\gradlew.bat :1.20.1-forge:runClient
.\gradlew.bat :1.21.1-neoforge:runServer
```

Stonecutter generates version-specific projects; do not edit `versions/` or
`common/versions/`. The Architectury common module lives in `common/src` and
compiles separately for each Minecraft version. Its transformed classes are
included in each loader's final JAR, so users do not install a separate common
JAR. `checkCommonBoundary` rejects loader-specific APIs in this module.

The multiloader migration is in progress: Forge 1.20.1 and NeoForge 1.21.1 are
the current build targets. Only the networking contracts and async helpers have
been extracted into common so far. The remaining shared Forge/NeoForge code is
still in `src/main`; Fabric targets are not yet available.
