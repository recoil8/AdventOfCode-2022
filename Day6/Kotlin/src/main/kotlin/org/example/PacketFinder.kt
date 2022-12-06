package org.example

class PacketFinder(private val dataStream: String) {
    fun findStartPacket() = findDatagram(4)
    fun findMessagePacket() = findDatagram(14)

    private fun findDatagram(gramLength: Int): Int {
        for (index in 1..dataStream.length) {
            if ((index + gramLength) > dataStream.length) break
            if (dataStream.substring(index, index + gramLength).toSet().count() == gramLength) return index + gramLength
        }
        return -1
    }
}
