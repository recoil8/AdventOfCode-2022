package org.example

import java.io.BufferedReader

class PriorityCalculator(private val reader: BufferedReader) {
    fun calculate() =
        reader.useLines { lines: Sequence<String> ->
            lines
                .map(String::halvsies)
                .map(Pair<String,String>::mapToSet)
                .map(Pair<Set<Char>,Set<Char>>::reduceIntersect)
                .map(Set<Char>::first)
                .sumOf(Char::priority)
        }

    fun calculateBadges() =
        reader.useLines { lines ->
            lines
                .map(String::toSet)
                .windowed(3, 3) { groupLines ->
                    groupLines
                        .reduce { first, second -> first.intersect(second) }
                        .sumOf { it.priority() }
                }
                .sum()
        }
}

private fun String.halvsies(): Pair<String, String> =
    Pair(
        this.take(this.length / 2),
        this.takeLast(this.length / 2),
    )

private fun Pair<String, String>.mapToSet(): Pair<Set<Char>,Set<Char>> =
    Pair(this.first.toSet(), this.second.toSet())

private fun Pair<Set<Char>, Set<Char>>.reduceIntersect() =
    this.first.intersect(this.second)

private fun Char.priority() =
    when (this.code) {
        in 65..90 -> this.code - 38
        in 97..122 -> this.code - 96
        else -> 0
    }
