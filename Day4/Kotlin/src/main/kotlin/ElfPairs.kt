import java.io.BufferedReader

class ElfPairs(private val reader: BufferedReader) {
    fun containedPairs(): Int =
        reader.useLines { lines ->
            lines
                .map { line -> line.split(",") }
                .map { stringPair: List<String> ->
                    stringPair
                        .map { it.split("-") }
                        .map { endPoints -> endPoints.map { it.toInt() }}
                        .map { endPoints -> endPoints.let { range -> IntRange(range[0], range[1]) }}
                        .let { ranges: List<IntRange> ->
                            val first: IntRange = ranges[0]
                            val second: IntRange = ranges[1]
                            if (first.containsRange(second) || second.containsRange(first)) {
                                1
                            }
                            else 0
                        }
                }
                .sum()
        }

    fun overlappingPairs() =
        reader.useLines { lines ->
            lines
                .map { line -> line.split(",") }
                .map { stringPair: List<String> ->
                    stringPair
                        .map { it.split("-") }
                        .map { endPoints -> endPoints.map { it.toInt() }}
                        .map { endPoints -> endPoints.let { range -> IntRange(range[0], range[1]) }}
                        .let { ranges: List<IntRange> ->
                            val first: IntRange = ranges[0]
                            val second: IntRange = ranges[1]
                            if (first.overlapsRange(second) || second.containsRange(first)) {
                                1
                            }
                            else 0
                        }
                }
                .sum()
        }

}

private fun IntRange.overlapsRange(range: IntRange) =
    this.last >= range.first && this.first <= range.last

private fun IntRange.containsRange(range: IntRange) =
    this.first <= range.first && this.last >= range.last
