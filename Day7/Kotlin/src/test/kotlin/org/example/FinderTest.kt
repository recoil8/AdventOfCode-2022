package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class FinderTest {
    private val exampleInput = """
        ${'$'} cd /
        ${'$'} ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        ${'$'} cd a
        ${'$'} ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        ${'$'} cd e
        ${'$'} ls
        584 i
        ${'$'} cd ..
        ${'$'} cd ..
        ${'$'} cd d
        ${'$'} ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    @Test
    fun sumSmallDirectoriesExample() {
        val smallestDirs = Finder(exampleInput.reader().buffered())
            .sumSmallDirectories()

        assertThat(smallestDirs).isEqualTo(95437)
    }

    @Test
    fun sumSmallDirectories() {
        val smallestDirs = Finder(javaClass.getResourceAsBufferedReader("/Terminal.txt")!!)
            .sumSmallDirectories()

        assertThat(smallestDirs).isEqualTo(1432936)
    }

    @Test
    fun smallestDeletableDirExample() {
        val smallestDeletableDir = Finder(exampleInput.reader().buffered())
            .smallestDeletableDir()

        assertThat(smallestDeletableDir).isEqualTo(24933642)
    }

    @Test
    fun smallestDeletableDir() {
        val smallestDeletableDir = Finder(javaClass.getResourceAsBufferedReader("/Terminal.txt")!!)
            .smallestDeletableDir()

        assertThat(smallestDeletableDir).isEqualTo(272298)
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
