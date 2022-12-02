package org.example.day1

import java.net.URL

class CalorieCounter(private val url: URL) {
    fun topCalories() =
        url.readText()
            .lines()
            .fold(mutableListOf<MutableList<String>>(mutableListOf())) { groups, line ->
                if (line.isEmpty()) {
                    groups.add(mutableListOf())
                } else {
                    groups.last().add(line)
                }
                groups
            }
            .map { it.map(String::toInt) }
            .maxOfOrNull { it.sum() }

    fun top3Calories() =
        url.readText()
            .lines()
            .fold(mutableListOf<MutableList<String>>(mutableListOf())) { groups, line ->
                if (line.isEmpty()) {
                    groups.add(mutableListOf())
                } else {
                    groups.last().add(line)
                }
                groups
            }
            .map { it.map(String::toInt) }
            .map { it.sum() }
            .sorted()
            .takeLast(3)
            .sum()
}
