package org.example

import java.io.BufferedReader

class StrategyDecoder(private val reader: BufferedReader) {
    fun firstDecode() =
        reader.useLines { lines ->
            lines.map { line ->
                when (line) {
                    "A X" -> 1 + 3 // Draw -- Rock/Rock
                    "A Y" -> 2 + 6 // Win  -- Rock/Paper
                    "A Z" -> 3 + 0 // Lose -- Rock/Scissors
                    "B X" -> 1 + 0 // Lose -- Paper/Rock
                    "B Y" -> 2 + 3 // Draw -- Paper/Paper
                    "B Z" -> 3 + 6 // Win  -- Paper/Scissors
                    "C X" -> 1 + 6 // Win  -- Scissors/Rock
                    "C Y" -> 2 + 0 // Lose -- Scissors/Paper
                    "C Z" -> 3 + 3 // Draw -- Scissors/Scissors
                    else -> 0
                }
            }
            .sum()
        }

    fun secondDecode() =
        reader.useLines { lines ->
            lines.map { line ->
                when (line) {
                    "A X" -> 3 + 0 // Lose -- Rock/Scissors
                    "A Y" -> 1 + 3 // Draw -- Rock/Rock
                    "A Z" -> 2 + 6 // Win  -- Rock/Paper
                    "B X" -> 1 + 0 // Lose -- Paper/Rock
                    "B Y" -> 2 + 3 // Draw -- Paper/Paper
                    "B Z" -> 3 + 6 // Win  -- Paper/Scissors
                    "C X" -> 2 + 0 // Lose -- Scissors/Paper
                    "C Y" -> 3 + 3 // Draw -- Scissors/Scissors
                    "C Z" -> 1 + 6 // Win  -- Scissors/Rock
                    else -> 0
                }
            }
            .sum()
        }
}
