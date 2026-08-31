# Krysztal 的 fabric-language-scala Fork

[![Modrinth 版本](https://img.shields.io/modrinth/v/Ptd0Ha1s?style=flat&logo=modrinth&labelColor=green)](https://modrinth.com/mod/Ptd0Ha1s)

这是 fabric-language-scala 的 Fork，提供对最新稳定版 Scala 3 的支持。

## 为啥要 Fork?

使用 Scala 开发的人数特别少，但是 Scala 的表现十分适合用于开发 Mod。

最初的 `fabric-language-scala`
没有得到维护，维护者无法再花更多的精力来维护它，所以它慢慢地变得少人维护并且没有功能。

对于 Scala 3 的支持，即便有，也几乎等于不存在。

于是，我决定自己 Fork 这个项目并进行维护，同时将其实现为兼容原版
`fabric-language-scala` 的版本，命名为 `krysztal-language-scala`。

## 注意

- 该语言适配器将尽可能同步上游内容，并全力确保其可用性。
- 开发者请阅读 [FOR_DEVELOPER](./FOR_DEVELOPER.md)（英文）。
- 玩家请阅读 [FOR_USER](./FOR_USER.md)（英文），并安装适用于其 Minecraft 版本的最新 KLS 文件，无需选择不同的 Scala 版本。
- KLS 跟随最新稳定版 Scala 3，并内置与其匹配的运行时。当前玩家兼容要求请查看 [FOR_USER](./FOR_USER.md)（英文），准确依赖坐标请查看 [FOR_DEVELOPER](./FOR_DEVELOPER.md)（英文）。

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
	mavenCentral()
  ...
}

dependencies {
  ...
	modImplementation("maven.modrinth:krysztal-language-scala:${property("kls_version")}")

	// 开发时必须显式添加。Modrinth 生成的 Maven POM 不会公开玩家 JAR
	// 中嵌套的 Scala 依赖。
	implementation("org.scala-lang:scala3-library_3:${property("scala_version")}")
	implementation("org.scala-lang:scala-library:${property("scala_version")}")
  ...
}
```

请在 `gradle.properties` 中把 `kls_version` 和 `scala_version` 设置为最新 KLS 发布页列出的值，并让 Scala 编译器版本与该发布内置的运行时保持一致。下载的玩家 JAR 已包含运行时；上面显式声明的 Scala 依赖用于为开发环境提供匹配的编译器和编译期 API。

模组的 `fabric.mod.json` 还应在 `depends` 中声明 KLS，并根据模组需求设置合适的最低发布版本。Fabric 在判断版本范围时会忽略 `+scala.*` 构建元数据。当前坐标及兼容性细节请查看 [FOR_DEVELOPER](./FOR_DEVELOPER.md)（英文）。

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
