package org.example.day1

import org.junit.jupiter.api.Test

internal class CalorieCounterTest {
    @Test
    fun countCalories() {
        CalorieCounter(javaClass.getResource("/Calories.txt")!!)
            .topCalories()
            .let { println("Most calories: $it") }
    }

    @Test
    fun countTop3Calories() {
        CalorieCounter(javaClass.getResource("/Calories.txt")!!)
            .top3Calories()
            .let { println("Top 3 calories: $it") }
    }
}
