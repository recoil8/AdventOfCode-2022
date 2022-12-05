package org.example

import java.io.BufferedReader

class CrateTracker(reader: BufferedReader) {
    private val stacks: List<MutableList<String>>
    private val moves: List<Move>

    init {
        val supplies = reader.useLines { lines ->
            lines
                .fold(SupplyParser()) { parser, line ->
                    when {
                        line.isBlank() -> parser.endCrates()
                        line.startsWith(" 1") -> {}
                        else -> parser.parseLine(line)
                    }
                    parser
                }
        }
        stacks = supplies.stacks
        moves = supplies.moves
    }

    fun trackCM9000(): String {
        moves.forEach { it.applyCM9000(stacks) }
        return stacks
            .fold("") { tops, stack -> tops.plus(stack.last())}
    }

    fun trackCM9001(): String {
        moves.forEach { it.applyCM9001(stacks) }
        return stacks
            .fold("") { tops, stack -> tops.plus(stack.last())}
    }
}

class SupplyParser {
    val moves: MutableList<Move> = mutableListOf()
    val stacks: MutableList<MutableList<String>> = mutableListOf()
    private var parserState: ParserState = ParserState.CRATES

    fun endCrates() {
        parserState = ParserState.MOVES
    }

    fun parseLine(line: String) {
        when (parserState) {
            ParserState.CRATES -> parseCrates(line)
            ParserState.MOVES -> parseMoves(line)
        }
    }

    private fun parseCrates(line: String) {
        line
            .asSequence()
            .windowed(3,4) { it[1].toString() }
            .forEachIndexed { index, crate ->
                if (stacks.size == index) {
                    stacks.add(mutableListOf())
                }
                if (crate.isNotBlank()) {
                    stacks[index].add(0, crate)
                }
            }
    }

    private fun parseMoves(line: String) {
        val move = line
            .split(" ")
            .let { listOf(it[1], it[3], it[5]) }
            .map { it.toInt() }
            .let { Move(it[0], it[1], it[2]) }
        moves.add(move)
    }
}

enum class ParserState {
    CRATES, MOVES
}

data class Move(val count: Int, val from: Int, val to: Int) {
    fun applyCM9000(stacks: List<MutableList<String>>) {
        stacks[to-1].addAll(stacks[from-1].removeLast(count))
    }

    fun applyCM9001(stacks: List<MutableList<String>>) {
        stacks[to-1].addAll(stacks[from-1].removeLast(count).reversed())
    }
}

private fun <E> MutableList<E>.removeLast(count: Int): List<E> =
    (1..count)
        .map { this.removeLast() }
        .fold(mutableListOf()) { list, item ->
            list.add(item)
            list
        }
