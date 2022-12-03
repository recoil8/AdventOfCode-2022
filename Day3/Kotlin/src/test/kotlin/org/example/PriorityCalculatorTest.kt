package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class PriorityCalculatorTest {
    private val sackContents = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    @Test
    fun calculatePriorityExample() {
        val priority = PriorityCalculator(sackContents.reader().buffered())
            .calculate()

        assertThat(priority).isEqualTo(157)
    }

    @Test
    fun calculatePriority() {
        val priority = PriorityCalculator(javaClass.getResourceAsBufferedReader("/Rucksack.txt")!!)
            .calculate()

        assertThat(priority).isEqualTo(8233)
    }

    @Test
    fun calculateBadgePriorityExample() {
        val priority = PriorityCalculator(sackContents.reader().buffered())
            .calculateBadges()

        assertThat(priority).isEqualTo(70)
    }

    @Test
    fun calculateBadgePriority() {
        val priority = PriorityCalculator(javaClass.getResourceAsBufferedReader("/Rucksack.txt")!!)
            .calculateBadges()

        assertThat(priority).isEqualTo(2821)
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
