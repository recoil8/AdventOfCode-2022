package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class StrategyDecoderTest {
    private val strategyExample = """
        A Y
        B X
        C Z
    """.trimIndent()

    @Test
    fun sumScoresFirstDecodeExample() {
        val score = StrategyDecoder(strategyExample.reader().buffered())
            .firstDecode()

        assertThat(score).isEqualTo(15)
    }

    @Test
    fun sumScoresFirstDecode() {
        val score = StrategyDecoder(javaClass.getResourceAsBufferedReader("/Strategy.txt")!!)
            .firstDecode()

        assertThat(score).isEqualTo(15523)
    }

    @Test
    fun sumScoresSecondDecodeExample() {
        val score = StrategyDecoder(strategyExample.reader().buffered())
            .secondDecode()

        assertThat(score).isEqualTo(12)
    }

    @Test
    fun sumScoresSecondDecode() {
        val score = StrategyDecoder(javaClass.getResourceAsBufferedReader("/Strategy.txt")!!)
            .secondDecode()

        assertThat(score).isEqualTo(15702)
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
