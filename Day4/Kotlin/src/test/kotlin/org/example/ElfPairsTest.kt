package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class ElfPairsTest {
    private val pairsExample = """
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
    """.trimIndent()

    @Test
    fun findContainedPairsExample() {
        val numContained = ElfPairs(pairsExample.reader().buffered())
            .containedPairs()

        assertThat(numContained).isEqualTo(2)
    }

    @Test
    fun findContainedPairs() {
        val numContained = ElfPairs(javaClass.getResourceAsBufferedReader("/Pairs.txt")!!)
            .containedPairs()

        assertThat(numContained).isEqualTo(550)
    }

    @Test
    fun findOverlapPairsExample() {
        val numContained = ElfPairs(pairsExample.reader().buffered())
            .overlappingPairs()

        assertThat(numContained).isEqualTo(4)
    }

    @Test
    fun findOverlapPairs() {
        val numContained = ElfPairs(javaClass.getResourceAsBufferedReader("/Pairs.txt")!!)
            .overlappingPairs()

        assertThat(numContained).isEqualTo(931)
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
