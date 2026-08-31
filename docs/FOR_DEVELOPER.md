# FOR DEVELOPER

KLS `3.5.0+scala.3.8.4` provides one bundled runtime: Scala 3.8.4. Use that one KLS file for Minecraft 1.18 and later, and recommend the same file to players rather than asking them to select a Scala variant.

## Scala compiler and runtime compatibility

Compile mods with Scala 3.8.4 when possible. Scala 3 compatibility is intended to let code built by an older stable Scala 3 compiler run on a newer Scala 3 runtime, but the reverse direction is not guaranteed. Matching the KLS 3.8.4 runtime avoids relying on that compatibility direction and is the configuration maintained by this project. CI compiles representative fixtures with Scala 3.3.8, 3.7.4, and 3.8.4 and runs all three against the bundled 3.8.4 runtime on Java 17.

Do not bundle another Scala runtime in your mod. KLS puts `scala3-library_3` and `scala-library` 3.8.4 inside the player JAR and loads them at runtime.

## Gradle dependency

The Modrinth Maven endpoint serves the KLS artifact, but its generated POM does not expose the Scala dependencies nested in the player JAR. Declare those dependencies explicitly so Gradle's Scala plugin can select the matching compiler and your source has the Scala APIs on its compile classpath:

```groovy
plugins {
	id 'scala'
}

repositories {
	maven { url "https://api.modrinth.com/maven" }
	mavenCentral()
}

dependencies {
	modImplementation("maven.modrinth:krysztal-language-scala:3.5.0+scala.3.8.4")
	implementation("org.scala-lang:scala3-library_3:3.8.4")
	implementation("org.scala-lang:scala-library:3.8.4")
}
```

Declare KLS as a runtime dependency in your mod metadata as well:

```json
{
  "depends": {
    "krysztal-language-scala": ">=3.5.0"
  }
}
```

Fabric ignores the `+scala.3.8.4` build metadata when evaluating version ranges, so the dependency uses the KLS release version rather than trying to constrain the bundled runtime suffix.

This repository also defines the standard Maven coordinate `io.github.krysztal112233:krysztal-language-scala:3.5.0+scala.3.8.4` for local publication generation. No remote repository for that coordinate is configured here. Unlike Modrinth's generated POM, this standard publication exposes both Scala runtime dependencies to compile consumers.

## Java and Minecraft compatibility

KLS 3.5.0 and later targets Java 17 bytecode and declares Java 17 as its runtime floor. It supports Minecraft 1.18 and later. A newer Minecraft version can impose a newer Java requirement even though KLS itself remains Java 17 compatible.

Building KLS itself currently requires JDK 21 or later because Fabric Loom 1.17 requires it. This build-tool requirement is separate from the Java 17 runtime floor of the published mod; CI smoke-tests the bundled Scala runtime on Java 17.
