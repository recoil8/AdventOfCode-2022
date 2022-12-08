package org.example

import java.io.BufferedReader

class Finder(reader: BufferedReader) {
    private val fs = DirectoryEntry("/")
    init {
        var currentDir = fs
        reader.useLines { lines ->
            lines
                .forEach { line ->
                    when {
                        line == "\$ ls" -> {} // do nothing
                        line == "\$ cd /" -> {} // do nothing
                        line == "\$ cd .." -> {
                            currentDir = currentDir.parent ?: currentDir
                        }
                        line.startsWith("\$ cd ") -> {
                            val newDir = currentDir.files[line.removePrefix("\$ cd ")]
                            if (newDir != null && newDir is DirectoryEntry) {
                                currentDir = newDir
                            }
                        }
                        line.startsWith("dir ") -> currentDir.createDir(line.removePrefix("dir "))
                        line.matches(Regex("\\d+.*")) -> {
                            val (size, name) = line.split(" ")
                            currentDir.createFile(name, size.toInt())
                        }
                    }
                }
        }
//        printDirTree(fs)
    }

    fun sumSmallDirectories() =
        mutableListOf<Pair<Int,DirectoryEntry>>()
            .also { calcDirectorySizes(fs, it) }
            .filter { it.first < 100000 }
            .map(Pair<Int,DirectoryEntry>::first)
            .reduce { sum, size -> sum + size }

    fun smallestDeletableDir(): Int =
        mutableListOf<Pair<Int,DirectoryEntry>>()
            .also { calcDirectorySizes(fs, it) }
            .sortedBy { it.first }
            .let { list ->
                list.find { (size, _) ->
                    (70000000 - list.last().first + size) > 30000000
                }
            }
            .let { it?.first ?: 0 }

    private fun calcDirectorySizes(directory: DirectoryEntry, found: MutableList<Pair<Int,DirectoryEntry>>): Int =
        directory
            .files
            .values
            .fold(0) { sum, entry ->
                sum + when (entry) {
                    is FileEntry -> entry.size
                    is DirectoryEntry -> calcDirectorySizes(entry, found)
                    else -> 0
                }
            }
            .also { found.add(Pair(it, directory)) }

    @Suppress("unused")
    private fun printDirTree(directory: DirectoryEntry, prefix: String = "") {
        println("$prefix- ${directory.name} (dir)")
        val newPrefix = prefix.plus("  ")
        directory.files.forEach { (name, entry) ->
            when (entry) {
                is FileEntry -> println("$newPrefix - $name (file, size=${entry.size})")
                is DirectoryEntry -> printDirTree(entry, newPrefix)
            }
        }
    }
}

interface Entry {
    val name: String
    val size: Int
}

data class DirectoryEntry(
    override val name: String,
    val parent: DirectoryEntry? = null,
    val files: MutableMap<String,Entry> = mutableMapOf(),
): Entry {
    override fun toString() = "$name (dir)"
    override val size: Int = 0

    fun createDir(name: String) {
        this.files[name] = DirectoryEntry(name, this)
    }

    fun createFile(name: String, size: Int) {
        this.files[name] = FileEntry(name, size)
    }
}

data class FileEntry(override val name: String, override val size: Int) : Entry
