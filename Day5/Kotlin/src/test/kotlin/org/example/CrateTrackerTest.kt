package org.example

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

internal class CrateTrackerTest {
    private val exampleInput = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
    """.trimIndent()

    @Test
    fun trackCM9000Example() {
        val topCrates = CrateTracker(exampleInput.reader().buffered())
            .trackCM9000()

        assertThat(topCrates).isEqualTo("CMZ")
    }

    @Test
    fun trackCM9000() {
        val numContained = CrateTracker(javaClass.getResourceAsBufferedReader("/Crates.txt")!!)
            .trackCM9000()

        assertThat(numContained).isEqualTo("BSDMQFLSP")
    }

    @Test
    fun trackCM9001Example() {
        val topCrates = CrateTracker(exampleInput.reader().buffered())
            .trackCM9001()

        assertThat(topCrates).isEqualTo("MCD")
    }

    @Test
    fun trackCM9001() {
        val topCrates = CrateTracker(javaClass.getResourceAsBufferedReader("/Crates.txt")!!)
            .trackCM9001()

        assertThat(topCrates).isEqualTo("PGSQBFLDP")
    }
}

private fun <T> Class<T>.getResourceAsBufferedReader(name: String, charset: Charset = Charsets.UTF_8) =
    this.getResourceAsStream(name)?.bufferedReader(charset)
