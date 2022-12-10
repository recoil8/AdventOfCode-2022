package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SignalSimulatorTest {
    private val exampleInput = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent()
    private val exampleDisplay = """
        ##..##..##..##..##..##..##..##..##..##..
        ###...###...###...###...###...###...###.
        ####....####....####....####....####....
        #####.....#####.....#####.....#####.....
        ######......######......######......####
        #######.......#######.......#######.....
    """.trimIndent()

    @Test
    fun sampledSignalExample() {
        val sampledSignal = SignalSimulator(exampleInput.reader())
            .sampledSignal()

        assertThat(sampledSignal).isEqualTo(13140)
    }

    @Test
    fun sampledSignal() {
        val sampledSignal = SignalSimulator(javaClass.getResourceAsReader("/Program.txt")!!)
            .sampledSignal()

        assertThat(sampledSignal).isEqualTo(13060)
    }

    @Test
    fun renderSignalExample() {
        val renderedSignal = SignalSimulator(exampleInput.reader())
            .renderSignal()

        println(renderedSignal)
        assertThat(renderedSignal).isEqualTo(exampleDisplay)
    }

    @Test
    fun renderSignal() {
        val renderedSignal = SignalSimulator(javaClass.getResourceAsReader("/Program.txt")!!)
            .renderSignal()

        println(renderedSignal)
        assertThat(renderedSignal).isEqualTo("""
            ####...##.#..#.###..#..#.#....###..####.
            #.......#.#..#.#..#.#..#.#....#..#....#.
            ###.....#.#..#.###..#..#.#....#..#...#..
            #.......#.#..#.#..#.#..#.#....###...#...
            #....#..#.#..#.#..#.#..#.#....#.#..#....
            #.....##...##..###...##..####.#..#.####.
        """.trimIndent())
    }
}

private fun <T> Class<T>.getResourceAsReader(name: String) =
    this.getResourceAsStream(name)?.reader()
