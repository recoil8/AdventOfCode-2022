package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class PacketFinderTest {
    private val exampleInput = "nppdvjthqldpwncqszvftbrmjlhg"

    @Test
    fun packetStartExample() {
        val packetIndex = PacketFinder(exampleInput)
            .findStartPacket()

        assertThat(packetIndex).isEqualTo(6)
    }

    @Test
    fun packetStart() {
        val packetIndex = PacketFinder(javaClass.getResource("/Datastream.txt")!!.readText())
            .findStartPacket()

        assertThat(packetIndex).isEqualTo(1876)
    }

    @Test
    fun messageStartExample() {
        val messageIndex = PacketFinder(exampleInput)
            .findMessagePacket()

        assertThat(messageIndex).isEqualTo(23)
    }

    @Test
    fun messageStart() {
        val messageIndex = PacketFinder(javaClass.getResource("/Datastream.txt")!!.readText())
            .findMessagePacket()

        assertThat(messageIndex).isEqualTo(2202)
    }
}
