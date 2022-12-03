package org.example.day1

import java.io.BufferedReader

class CalorieCounter(private val reader: BufferedReader) {
    fun topCalories() =
        reader.useLines { lines ->
            lines.fold(mutableListOf<MutableList<String>>(mutableListOf())) { groups, line ->
                if (line.isEmpty()) {
                    groups.add(mutableListOf())
                } else {
                    groups.last().add(line)
                }
                groups
            }
            .map { it.map(String::toInt) }
            .maxOfOrNull { it.sum() }
        }

    fun top3Calories() =
        reader.useLines { lines ->
            lines
                .split("")
                .map { it.map(String::toInt) }
                .map { it.sum() }
                .sorted()
                .takeLast(3)
                .sum()
        }
}

private fun <T> Sequence<T>.split(delimiter: T) =
    this.fold(mutableListOf<MutableList<T>>(mutableListOf())) { groups, line ->
        if (line == delimiter) {
            groups.add(mutableListOf())
        } else {
            groups.last().add(line)
        }
        groups
    } as List<List<T>>
