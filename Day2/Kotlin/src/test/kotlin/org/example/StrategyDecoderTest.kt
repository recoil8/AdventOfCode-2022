package org.example

import org.junit.jupiter.api.Test

internal class StrategyDecoderTest {
    @Test
    fun sumScoresFirstDecode() {
        StrategyDecoder(javaClass.getResource("/Strategy.txt")!!)
            .firstDecode()
            .let { println("Total score (1st decode): $it") }
    }

    @Test
    fun sumScoresSecondDecode() {
        StrategyDecoder(javaClass.getResource("/Strategy.txt")!!)
            .secondDecode()
            .let { println("Total score (2nd decode): $it") }
    }
}
