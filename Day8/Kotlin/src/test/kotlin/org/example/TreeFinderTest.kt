package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class TreeFinderTest {
    private val exampleInput = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    @Test
    fun findVisibleTreesExample() {
        val visibleTrees = TreeFinder(exampleInput.reader().buffered())
            .countVisibleTrees()

        assertThat(visibleTrees).isEqualTo(21)
    }

    @Test
    fun findVisibleTrees() {
        val visibleTrees = TreeFinder(javaClass.getResourceAsBufferedReader("/Trees.txt")!!)
            .countVisibleTrees()

        assertThat(visibleTrees).isEqualTo(1700)
    }

    @Test
    fun findScenicScoreExample() {
        val bestScenicScore = TreeFinder(exampleInput.reader().buffered())
            .bestScenicScore()

        assertThat(bestScenicScore).isEqualTo(8)
    }

    @Test
    fun findScenicScore() {
        val bestScenicScore = TreeFinder(javaClass.getResourceAsBufferedReader("/Trees.txt")!!)
            .bestScenicScore()

        assertThat(bestScenicScore).isEqualTo(470596)
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
