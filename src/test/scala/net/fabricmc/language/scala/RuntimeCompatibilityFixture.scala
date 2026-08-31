package net.fabricmc.language.scala

import org.junit.jupiter.api.Test

class RuntimeCompatibilityFixtureTest:
    @Test
    def executesScalaCollections(): Unit =
        RuntimeCompatibilityFixture.verify()

object RuntimeCompatibilityFixture:
    def main(args: Array[String]): Unit =
        verify()

    def verify(): Unit =
        val result = List(1, 2, 3).map(_ + 1).sum
        if result != 9 then
            throw new IllegalStateException(
              s"Unexpected Scala runtime result: $result"
            )
