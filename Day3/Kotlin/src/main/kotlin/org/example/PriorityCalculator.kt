package org.example

import java.io.BufferedReader

class PriorityCalculator(private val reader: BufferedReader) {
    fun calculate() =
        reader.useLines { lines ->
            lines
                .map { line ->
                    val (left, right) = Pair(line.take(line.length/2), line.takeLast(line.length/2))
                    val union = left.toCharArray().intersect(right.toCharArray().asIterable())
//                    println("union: $union")
//                    println("code: ${union.first().code}/${union.first().code.priority()}")
                    union.sumOf { it.code.priority() }
                }
                .sum()
        }

    fun calculateBadges() =
        reader.useLines { lines ->
            lines.fold(mutableListOf<MutableList<String>>(mutableListOf())) { groups, line ->
                if (groups.last().size >= 3) {
                    groups.add(mutableListOf())
                }
                groups.last().add(line)
                groups
            }
                .map { groupLines ->
                    groupLines.takeLast(2).fold(groupLines.first().toSet()) { sect, line ->
                        sect.intersect(line.toCharArray().asIterable())
                    }
                    .sumOf { it.code.priority() }
                }
                .sum()
        }
}

private fun Int.priority() =
    when (this) {
        in 65..90 -> this - 38
        in 97..122 -> this - 96
        else -> this
    }
