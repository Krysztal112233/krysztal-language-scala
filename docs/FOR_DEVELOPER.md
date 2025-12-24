# FOR DEVELOPER

This project provided kinds of Scala3 version bundled jars.

I suggest that you always download the highest version of Scala for your target Minecraft version.

For example, you can see below KLS version:

- 3.3.1+scala.3.4.1
- 3.3.1+scala.3.4.0
- 3.3.1+scala.3.3.7
- 3.3.1+scala.3.3.6
- 3.3.1+scala.3.3.5
- 3.3.1+scala.3.3.4
- 3.3.1+scala.3.3.3
- 3.3.1+scala.3.3.1

And you should choose `3.3.1+scala.3.4.1`. User will choose the higher version or same version with you.

## [Scala 3 compatibility](https://docs.scala-lang.org/overviews/jdk-compatibility/overview.html#scala-3-compatibility)

To 2025.12.24, we got this table for JDK compatibility

|   JDK    |  3.8\*  | 3.4+  |  Minecraft  |
| :------: | :-----: | :---: | :---------: |
| 25 (LTS) | 3.8.0\* | 3.7.1 |    26.1     |
| 21 (LTS) | 3.8.0\* | 3.4.0 |    1.21     |
| 17 (LTS) | 3.8.0\* | 3.4.0 |    1.18     |
| 11 (LTS) |         | 3.4.0 | UNSUPPORTED |
| 8 (LTS)  |         | 3.4.0 | UNSUPPORTED |

So we can get those decision:

- Use 3.4.0 if your Minecraft build requires JDK 17.
- Use 3.4.0 if your Minecraft build requires JDK 21.
- Use 3.7.1 if your Minecraft build requires JDK 25.

KLS will target JDK17 as minium support until Scala3 dropped JDK17 support.

But I still provided higher version for higher JDK.
