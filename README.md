# Krysztal's fork of fabric-language-scala

[![Modrinth Version](https://img.shields.io/modrinth/v/Ptd0Ha1s?style=flat&logo=modrinth&labelColor=green)](https://modrinth.com/mod/Ptd0Ha1s)

<a href="./docs/README_zhcn.md">中文简体</a>

A maintained Fabric language adapter for Scala 3. Current release: KLS `3.7.0`, Scala `3.9.0` only.

- Developers: [FOR_DEVELOPER](./docs/FOR_DEVELOPER.md)
- Players: [FOR_USER](./docs/FOR_USER.md)

## Dependencies

```groovy
plugins {
	id 'scala'
}

repositories {
	maven { url "https://api.modrinth.com/maven" }
	mavenCentral()
}

dependencies {
	modImplementation("maven.modrinth:krysztal-language-scala:${property("kls_version")}")
	implementation("org.scala-lang:scala3-library_3:${property("scala_version")}")
	implementation("org.scala-lang:scala-library:${property("scala_version")}")
}
```

Set `kls_version=3.7.0` and `scala_version=3.9.0`. Also declare `"krysztal-language-scala": ">=3.7.0"` in `fabric.mod.json`.

## Usage

### `class`

```scala
import net.fabricmc.api.ModInitializer

class ExampleEntry extends ModInitializer:
  override def onInitialize(): Unit =
    println("Hi")
```

```json
"entrypoints": {
  "main": ["dev.example.ExampleEntry"]
}
```

A class is instantiated through its public no-arg constructor, so the `scala`
adapter accepts classes as well; the plain entrypoint just uses the default
adapter.

### `object`

```scala
import net.fabricmc.api.ModInitializer

object ExampleEntry extends ModInitializer:
  override def onInitialize(): Unit =
    println("Hi")
```

```json
"entrypoints": {
  "main": [
    {
      "adapter": "scala",
      "value": "dev.example.ExampleEntry"
    }
  ]
}
```

## Known issues

`unknown invokedynamic bsm: scala/runtime*` comes from Scala class loading and can be ignored.
