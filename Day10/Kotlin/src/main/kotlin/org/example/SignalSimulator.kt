package org.example

import java.io.Reader

class SignalSimulator(private val reader: Reader) {
    fun sampledSignal(): Int {
        val xRegister = simulateSignal()
        return (
            xRegister[19] * 20
            + xRegister[59] * 60
            + xRegister[99] * 100
            + xRegister[139] * 140
            + xRegister[179] * 180
            + xRegister[219] * 220
        )
    }

    fun renderSignal(): String {
        val xRegister = simulateSignal().take(240)

        val displayBuffer = StringBuilder()
        xRegister.forEachIndexed { index, signal ->
            val column = index % 40
            if (column+1 >= signal && column+1 <= signal+2) {
                displayBuffer.append("#")
            }
            else {
                displayBuffer.append(".")
            }
            if (column == 39) {
                displayBuffer.append("\n")
            }
        }
        return displayBuffer.toString()
    }

    private fun simulateSignal(): List<Int> {
        val xRegister = mutableListOf(1)

        reader.forEachLine { line ->
            when {
                line == "noop" -> {
                    xRegister.add(xRegister.last())
                }
                line.startsWith("addx") -> {
                    val (_, addend) = line.split(" ")
                    xRegister.add(xRegister.last())
                    xRegister.add(xRegister.last() + addend.toInt())
                }
            }
        }
        return xRegister
    }
}
