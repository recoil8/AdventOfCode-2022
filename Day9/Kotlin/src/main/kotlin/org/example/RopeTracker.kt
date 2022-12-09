package org.example

import java.io.Reader
import kotlin.math.absoluteValue

class RopeTracker(private val reader: Reader, gridSize: Int, private val start: Pair<Int, Int>) {
    private val grid = (1..gridSize).map {
        (1..gridSize).map { Point() }
    }
    private var headLoc = MutablePair(start.first, start.second)
    private var tailLoc = MutablePair(start.first, start.second)

    init {
        recordTail(tailLoc)
    }

    fun trackRopeTail(): Int {
        trackRope()
        return grid
            .map { row -> row.count { it.sawTail } }
            .sum()
    }

    fun trackLongRopeTail(knotCount: Int): Int {
        val knots = (1..knotCount).map { MutablePair(start.first, start.second) }
        recordTail(knots.last())

        reader
            .useLines { lines ->
                lines
                    .forEach { line ->
                        val (command, length) = line.split(" ")
                        for (i in 1..length.toInt()) {
                            moveHead(knots.first(), command)
                            knots.windowed(2, 1) { knotPair ->
                                updateTail(knotPair[0], knotPair[1])
                            }
                            recordTail(knots.last())
                        }
                    }
            }
        return grid
            .map { row -> row.count { it.sawTail } }
            .sum()
    }

    private fun trackRope() {
        reader
            .useLines { lines ->
                lines
                    .forEach { line ->
                        val (command, length) = line.split(" ")
                        for (i in 1..length.toInt()) {
                            moveHead(headLoc, command)
                            updateTail(headLoc, tailLoc)
                            recordTail(tailLoc)
                        }
                    }
            }
    }

    private fun moveHead(knot: MutablePair, command: String) {
        when (command) {
            "U" -> knot.first -= 1
            "D" -> knot.first += 1
            "L" -> knot.second -= 1
            "R" -> knot.second += 1
        }
    }

    private fun updateTail(lead: MutablePair, follower: MutablePair) {
        val hDelta = lead.first - follower.first
        val vDelta = lead.second - follower.second

        if (hDelta > 1) {
            follower.first += 1
        }
        if (hDelta == 1 && vDelta.absoluteValue > 1) {
            follower.first += 1
        }
        if (hDelta < -1) {
            follower.first -= 1
        }
        if (hDelta == -1 && vDelta.absoluteValue > 1) {
            follower.first -= 1
        }

        if (vDelta > 1) {
            follower.second += 1
        }
        if (vDelta == 1 && hDelta.absoluteValue > 1) {
            follower.second += 1
        }
        if (vDelta < -1) {
            follower.second -= 1
        }
        if (vDelta == -1 && hDelta.absoluteValue > 1) {
            follower.second -= 1
        }
    }

    private fun recordTail(tail: MutablePair) {
        grid[tail.first][tail.second].sawTail = true
    }
}

data class Point(var sawTail: Boolean = false)

data class MutablePair(var first: Int, var second: Int) {
