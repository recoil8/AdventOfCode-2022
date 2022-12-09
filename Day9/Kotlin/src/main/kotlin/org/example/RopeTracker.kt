package org.example

import java.io.Reader
import kotlin.math.absoluteValue
import kotlin.math.sign

class RopeTracker(private val reader: Reader) {

    fun trackRopeTail(knotCount: Int): Int {
        val knots = (1..knotCount).map { KnotPosition(0, 0) }
        val tailLocations = mutableSetOf<KnotPosition>()
        tailLocations.add(knots.last().copy())

        reader.forEachLine { line ->
            val (command, length) = line.split(" ")
            for (i in 1..length.toInt()) {
                moveHead(knots.first(), command)
                knots.windowed(2, 1) { (leadKnot, followingKnot) ->
                    updateFollower(leadKnot, followingKnot)
                }
                tailLocations.add(knots.last().copy())
            }
        }
        return tailLocations.size
    }

    private fun moveHead(knot: KnotPosition, command: String) {
        when (command) {
            "U" -> knot.moveUpDown(-1)
            "D" -> knot.moveUpDown(1)
            "L" -> knot.moveLeftRight(-1)
            "R" -> knot.moveLeftRight(1)
        }
    }

    private fun updateFollower(lead: KnotPosition, follower: KnotPosition) {
        val hDelta = lead.row - follower.row
        val vDelta = lead.column - follower.column

        if (hDelta.absoluteValue > 1) {
            follower.moveUpDown(hDelta)
        }
        else if (hDelta.absoluteValue == 1 && vDelta.absoluteValue > 1) {
            follower.moveUpDown(hDelta)
        }

        if (vDelta.absoluteValue > 1) {
            follower.moveLeftRight(vDelta)
        }
        else if (vDelta.absoluteValue == 1 && hDelta.absoluteValue > 1) {
            follower.moveLeftRight(vDelta)
        }
    }
}

data class KnotPosition(var row: Int, var column: Int) {
    fun moveUpDown(direction: Int) {
        this.row += direction.sign
    }
    fun moveLeftRight(direction: Int) {
        this.column += direction.sign
    }
}
