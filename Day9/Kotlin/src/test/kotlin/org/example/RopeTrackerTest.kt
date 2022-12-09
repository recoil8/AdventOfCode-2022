package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class RopeTrackerTest {
    private val exampleInput = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent()
    private val largeExampleInput = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent()

    @Test
    fun trackRopeExample() {
        val tailPlaces = RopeTracker(exampleInput.reader())
            .trackRopeTail(2)

        assertThat(tailPlaces).isEqualTo(13)
    }

    @Test
    fun trackRope() {
        val tailPlaces = RopeTracker(javaClass.getResourceAsReader("/RopeMoves.txt")!!)
            .trackRopeTail(2)

        assertThat(tailPlaces).isEqualTo(5981)
    }

    @Test
    fun trackLongRopeExample() {
        val tailPlaces = RopeTracker(exampleInput.reader())
            .trackRopeTail(10)

        assertThat(tailPlaces).isEqualTo(1)
    }

    @Test
    fun trackLongRopeLargeExample() {
        val tailPlaces = RopeTracker(largeExampleInput.reader())
            .trackRopeTail(10)

        assertThat(tailPlaces).isEqualTo(36)
    }

    @Test
    fun trackLongRope() {
        val tailPlaces = RopeTracker(javaClass.getResourceAsReader("/RopeMoves.txt")!!)
            .trackRopeTail(10)

        assertThat(tailPlaces).isEqualTo(2352)
    }
}

private fun <T> Class<T>.getResourceAsReader(name: String) =
    this.getResourceAsStream(name)?.reader()
