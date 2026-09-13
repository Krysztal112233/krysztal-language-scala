# Krysztal 的 fabric-language-scala Fork

[![Modrinth 版本](https://img.shields.io/modrinth/v/Ptd0Ha1s?style=flat&logo=modrinth&labelColor=green)](https://modrinth.com/mod/Ptd0Ha1s)

面向 Scala 3 的 Fabric 语言适配器。当前发布：KLS `3.7.0`，仅支持 Scala `3.9.0`。

- 开发者：[FOR_DEVELOPER](./FOR_DEVELOPER.md)（英文）
- 玩家：[FOR_USER](./FOR_USER.md)（英文）

## 依赖

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

设置 `kls_version=3.7.0` 与 `scala_version=3.9.0`，并在 `fabric.mod.json` 中声明 `"krysztal-language-scala": ">=3.7.0"`。

## 用法

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

## 已知问题

`unknown invokedynamic bsm: scala/runtime*` 来自 Scala 类加载机制，可忽略。
