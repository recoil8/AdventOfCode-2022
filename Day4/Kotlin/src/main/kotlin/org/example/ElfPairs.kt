package org.example

import java.io.BufferedReader

class ElfPairs(private val reader: BufferedReader) {
    fun containedPairs(): Int =
        reader.useLines { lines ->
            parseLines(lines)
                .map { ranges -> ranges[0].containsRange(ranges[1]) || ranges[1].containsRange(ranges[0]) }
                .map { if (it) 1 else 0 }
                .sum()
        }

    fun overlappingPairs() =
        reader.useLines { lines ->
            parseLines(lines)
                .map { ranges -> ranges[0].overlapsRange(ranges[1]) || ranges[1].containsRange(ranges[0]) }
                .map { if (it) 1 else 0 }
                .sum()
        }

    private fun parseLines(lines: Sequence<String>) =
        lines
            .map { line -> line.split(",") }
            .map { rangeStrings ->
                rangeStrings
                    .map { it.split("-") }
                    .map { it.map(String::toInt) }
                    .map { it.let { range -> IntRange(range[0], range[1]) } }
            }
}

private fun IntRange.overlapsRange(range: IntRange) =
    this.last >= range.first && this.first <= range.last

private fun IntRange.containsRange(range: IntRange) =
    this.first <= range.first && this.last >= range.last
