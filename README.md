# Krysztal's fork of fabric-language-scala

[![Modrinth Version](https://img.shields.io/modrinth/v/Ptd0Ha1s?style=flat&logo=modrinth&labelColor=green)](https://modrinth.com/mod/Ptd0Ha1s)

<a href="./docs/README_zhcn.md">中文简体</a>

This is a fork of fabric-language-scala that provides support for the latest stable Scala 3 release.

## Why fork?

The number of people who use Scala is very small, but the power of Scala's expressiveness makes the language practically perfect for developing mods.

The original `fabric-language-scala` was unmaintained and the maintainers couldn't spare any more effort to maintain it, so it slowly became unmaintained and non-functional.

Support for Scala3 is, if anything, almost non-existent.

So I decided to fork it and maintain it myself and implement it to be compatible with the original `fabric-language-scala`,named `krysztal-language-scala`.

## NOTE

- This language adapter will synchronize content upstream as much as possible and will ensure availability as much as possible.
- If you are a **DEVELOPER**, read [FOR_DEVELOPER](./docs/FOR_DEVELOPER.md).
- If you are a **USER**, read [FOR_USER](./docs/FOR_USER.md) and install the latest KLS file. There are no Scala-specific variants to choose from.

KLS follows the latest stable Scala 3 release and bundles its matching runtime. See [FOR_USER](./docs/FOR_USER.md) for current player compatibility requirements and [FOR_DEVELOPER](./docs/FOR_DEVELOPER.md) for exact dependency coordinates.

## How to use?

### Add dependencies

Add those lines to your project's `build.gradle`

```groovy
plugins {
  ...
	id 'scala' // Add `scala` plugin for gradle
  ...
}

repositories {
  ...
	maven { url "https://api.modrinth.com/maven" }
	mavenCentral()
  ...
}

dependencies {
  ...
	modImplementation("maven.modrinth:krysztal-language-scala:${property("kls_version")}")

	// Required on the developer compile classpath. Modrinth's generated Maven POM does not
	// expose the Scala dependencies nested in the player JAR.
	implementation("org.scala-lang:scala3-library_3:${property("scala_version")}")
	implementation("org.scala-lang:scala-library:${property("scala_version")}")
  ...
}
```

Set `kls_version` and `scala_version` in `gradle.properties` from the latest KLS release, and keep the Scala compiler version aligned with the runtime bundled by that release. The downloaded player JAR already contains the runtime; the explicit Scala dependencies above provide the matching compiler and compile-time APIs while developing.

Your mod should also declare KLS in its `fabric.mod.json` `depends` object with an appropriate minimum release. Fabric ignores the `+scala.*` build metadata when evaluating version ranges. See [FOR_DEVELOPER](./docs/FOR_DEVELOPER.md) for the current coordinates and compatibility details.

### Usage: `class`

Suppose your entry name is `ExampleEntry.scala`

```scala
import net.fabricmc.api.ModInitializer;

class ExampleEntry extends ModInitializer {
   lazy val logger = LoggerFactory.getLogger("KMMO")
   override def onInitialize(): Unit = {
       logger.info("Hi")
   }
}
```

And in `fabric.mod.json`

```json
   ...
"entrypoints": {
   "main": [
     "dev.example.ExampleEntry"
   ],
 },
   ...
```

But thanks to Scala's excellent interoperability with Java, we can use this
library simply as a Java entry point :)

### Usage: `object`

Suppose your entry name is `ExampleEntry.scala`

```scala
import net.fabricmc.api.ModInitializer;

object ExampleEntry extends ModInitializer {
    lazy val logger = LoggerFactory.getLogger("KMMO")
    override def onInitialize(): Unit = {
        logger.info("Hi")
    }
}
```

And in `fabric.mod.json`

```json
   ...
"entrypoints": {
   "main": [
     {
       "adapter": "scala",
       "value": "dev.example.ExampleEntry"
     }
   ],
 },
   ...
```

## Known issues

### unknown invokedynamic bsm: scala/runtime\*

This issue is caused by Scala's class loading mechanism.

It won't affect almost anything. Ignore it.
