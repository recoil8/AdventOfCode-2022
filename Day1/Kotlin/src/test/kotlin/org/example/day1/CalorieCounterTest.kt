package org.example.day1

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class CalorieCounterTest {
    private val caloriesExample = """
        1000
        2000
        3000

        4000

        5000
        6000

        7000
        8000
        9000

        10000
    """.trimIndent()

    @Test
    fun countCaloriesExample() {
        val calories = CalorieCounter(caloriesExample.reader().buffered())
            .topCalories()

        assertThat(calories).isEqualTo(24000)
    }

    @Test
    fun countCalories() {
        val calories = CalorieCounter(javaClass.getResourceAsBufferedReader("/Calories.txt")!!)
            .topCalories()

        assertThat(calories).isEqualTo(72017)
    }

    @Test
    fun countTop3CaloriesExample() {
        val calories = CalorieCounter(caloriesExample.reader().buffered())
            .top3Calories()

        assertThat(calories).isEqualTo(45000)
    }

    @Test
    fun countTop3Calories() {
        val calories = CalorieCounter(javaClass.getResourceAsBufferedReader("/Calories.txt")!!)
            .top3Calories()

        assertThat(calories).isEqualTo(212520)
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
