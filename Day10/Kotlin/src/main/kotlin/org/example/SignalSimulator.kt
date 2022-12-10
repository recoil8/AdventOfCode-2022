package org.example

import java.io.Reader

class SignalSimulator(private val reader: Reader) {
    fun sampledSignal() =
        simulateSignal()
            .let {(
                it[19] * 20
                + it[59] * 60
                + it[99] * 100
                + it[139] * 140
                + it[179] * 180
                + it[219] * 220
            )}

    fun renderSignal() =
        simulateSignal()
            .take(240)
            .foldIndexed(StringBuilder()) { index, display, signal ->
                (if (index % 40 + 1 in signal..signal + 2) "#" else ".")
                    .let { display.append(it) }
            }
            .chunked(40)
            .joinToString("\n")

    private fun simulateSignal() =
        reader.foldLines(mutableListOf(1)) { values, line ->
            when {
                line == "noop" -> values.add(values.last())
                line.startsWith("addx") -> {
                    values.add(values.last())
                    values.add(values.last() + line.split(" ")[1].toInt())
                }
                else -> {}
            }
            values
        }
}

private fun <R> Reader.foldLines(initial: R, operation: (acc: R, String) -> R): R {
    var result: R = initial
    useLines { lines -> lines.fold(initial, operation).let { result = it } }
    return result
}
