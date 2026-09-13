# FOR DEVELOPER

KLS `3.7.0` bundles Scala `3.9.0` only. Compile with that version and do not ship another Scala runtime.

```groovy
plugins {
	id 'scala'
}

repositories {
	maven { url "https://api.modrinth.com/maven" }
	mavenCentral()
}

dependencies {
	modImplementation("maven.modrinth:krysztal-language-scala:3.7.0")
	implementation("org.scala-lang:scala3-library_3:3.9.0")
	implementation("org.scala-lang:scala-library:3.9.0")
}
```

```json
{
  "depends": {
    "krysztal-language-scala": ">=3.7.0"
  }
}
```

Requires Minecraft `>=1.18` and Java `>=17`.
