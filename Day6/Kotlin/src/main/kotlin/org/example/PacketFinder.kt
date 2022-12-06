package org.example

class PacketFinder(private val dataStream: String) {
    fun findStartPacket(): Int {
        var foundIndex = -1
        dataStream
            .asIterable()
            .forEachIndexed { index, _ ->
                if (foundIndex >= 0) return@forEachIndexed
                if ((index + 4) > dataStream.length) return@forEachIndexed
                if (dataStream.substring(index, index + 4).toSet().count() == 4) {
                    foundIndex = index
                }
            }
        return foundIndex + 4
    }

    fun findMessagePacket(): Int {
        var foundIndex = -1
        dataStream
            .asIterable()
            .forEachIndexed { index, _ ->
                if (foundIndex >= 0) return@forEachIndexed
                if ((index + 14) > dataStream.length) return@forEachIndexed
                if (dataStream.substring(index, index + 14).toSet().count() == 14) {
                    foundIndex = index
                }
            }
        return foundIndex + 14
    }
}
