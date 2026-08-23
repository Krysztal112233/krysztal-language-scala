# Krysztal 的 fabric-language-scala Fork

![Modrinth 版本](https://img.shields.io/modrinth/v/Ptd0Ha1s?style=flat&logo=modrinth&labelColor=green)

这是 fabric-language-scala 的 Fork，支持了最新的 Scala3 版本。

## 为啥要 Fork?

使用 Scala 开发的人数特别少，但是 Scala 的表现十分适合用于开发 Mod。

最初的 `fabric-language-scala`
没有得到维护，维护者无法再花更多的精力来维护它，所以它慢慢地变得少人维护并且没有功能。

对于 Scala 3 的支持，即便有，也几乎等于不存在。

于是，我决定自己 Fork 这个项目并进行维护，同时将其实现为兼容原版
`fabric-language-scala` 的版本，命名为 `krysztal-language-scala`。

## 注意

- 该语言适配器将尽可能同步上游内容，并全力确保其可用性。

## 咋用啊?

### 添加依赖

添加这些到你项目的 `build.gradle` 文件。

```groovy
plugins {
  ...
	id 'scala' // 为 Gradle 添加 Scala 插件
  ...
}

repositories {
  ...
	maven { url "https://api.modrinth.com/maven" }
  ...
}

dependencies {
  ...
	// Scala 3 语言支持（Fabric 语言适配器及 Scala 运行时；运行时由此模组捆绑）。
	implementation("maven.modrinth:krysztal-language-scala:${property("krysztal_scala_version")}")
  ...
}
```

### 用途: `class`

假设你的入口文件名为 `ExampleEntry.scala`

```scala
import net.fabricmc.api.ModInitializer;

class ExampleEntry extends ModInitializer {
   lazy val logger = LoggerFactory.getLogger("KMMO")
   override def onInitialize(): Unit = {
       logger.info("你好我是丁真")
   }
}
```

在 `fabric.mod.json` 添加

```json
   ...
"entrypoints": {
   "main": [
     "dev.example.ExampleEntry"
   ],
 },
   ...
```

得益于 Scala 与 Java 出色的互操作性，我们可以直接将该库作为 Java 入口点来使用 :)

### 用途: `object`

假设你的入口文件名为 `ExampleEntry.scala`

```scala
import net.fabricmc.api.ModInitializer;

object ExampleEntry extends ModInitializer {
    lazy val logger = LoggerFactory.getLogger("KMMO")
    override def onInitialize(): Unit = {
        logger.info("Hi")
    }
}
```

在 `fabric.mod.json` 中添加

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

## 已知问题

### unknown invokedynamic bsm: scala/runtime\*

此问题是由 Scala 的类加载机制引起的。

这几乎不会造成任何影响，直接忽略即可。
