# Krysztal's fork of fabric-language-scala

![Modrinth Version](https://img.shields.io/modrinth/v/Ptd0Ha1s?style=flat&logo=modrinth&labelColor=green)

<a href="./docs/README_zhcn.md">中文简体</a>

This is a fork of fabric-language-scala, support the newest Scala3 version.

## Why fork?

The number of people who use Scala is very small, but the power of Scala's expressiveness makes the language practically perfect for developing mods.

The original `fabric-language-scala` was unmaintained and the maintainers couldn't spare any more effort to maintain it, so it slowly became unmaintained and non-functional.

Support for Scala3 is, if anything, almost non-existent.

So I decided to fork it and maintain it myself and implement it to be compatible with the original `fabric-language-scala`,named `krysztal-language-scala`.

## NOTE

- This language adapter will synchronize content upstream as much as possible and will ensure availability as much as possible.
- If you are **DEVELOPER**, please reading [FOR_DEVELOPER](./docs/FOR_DEVELOPER.md), and remind your user which Scala3 version you used
  - I suggested you should using the newest version as possible
- If you are **USER**, please reading [FOR_USER](./docs/FOR_USER.md)

From KLS `3.3.2`, I set the compile target of `javac` to Java17, it means you should using at least 1.18

## How to use?

### Add dependence

Add those lines to your project's `build.gradle`

```groovy
plugins {
  ...
	id 'scala' // Add `scala` plugin for gradle
  ...
}

repositories {
  ...
	maven { url "https://maven.krysztal.dev/releases" }
  ...
}

dependencies {
  ...
	modImplementation "dev.krysztal:krysztal-language-scala:${project.kls_version}+scala.${project.scala_version}"
  ...
}
```

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

This issues caused by scala's class loading mechanism.

It won't affect almost anything. Ignore it.
