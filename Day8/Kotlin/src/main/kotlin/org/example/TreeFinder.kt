package org.example

import java.io.BufferedReader

class TreeFinder(reader: BufferedReader) {
    private val treeGrid: List<List<Tree>>

    init {
        treeGrid = reader.useLines { lines ->
            lines
                .map(String::toCharArray)
                .map { charArray -> charArray.map { Tree(it.digitToInt()) }}
                .toList()
        }
    }

    fun countVisibleTrees() =
        treeGrid
            .also { markVisible(it) }
            .fold(0) { sum, treeRow ->
                sum + treeRow.fold(0) { rowSum, tree -> rowSum + if (tree.visible) 1 else 0 }
            }

    private fun markVisible(treeGrid: List<List<Tree>>) {
        // Count from left
        treeGrid
            .forEach { treeRow ->
                var height = treeRow[0].height
                treeRow[0].visible = true
                for (index in 1 until treeRow.size) {
                    if (treeRow[index].height > height) {
                        treeRow[index].visible = true
                        height = treeRow[index].height
                    }
                }
            }
        // Count from right
        treeGrid
            .map(List<Tree>::reversed)
            .forEach { treeRow ->
                var height = treeRow[0].height
                treeRow[0].visible = true
                for (index in 1 until treeRow.size) {
                    if (treeRow[index].height > height) {
                        treeRow[index].visible = true
                        height = treeRow[index].height
                    }
                }
            }
        // Count from top
        for (colIndex in 0 until treeGrid[0].size) {
            var height = treeGrid[0][colIndex].height
            treeGrid[0][colIndex].visible = true
            for (index in 1 until treeGrid.size) {
                val tree = treeGrid[index][colIndex]
                if (tree.height > height) {
                    tree.visible = true
                    height = tree.height
                }
            }
        }
        // Count from bottom
        for (colIndex in 0 until treeGrid[0].size) {
            var height = treeGrid.last()[colIndex].height
            treeGrid.last()[colIndex].visible = true
            for (index in (0 until treeGrid.size-1).reversed()) {
                val tree = treeGrid[index][colIndex]
                if (tree.height > height) {
                    tree.visible = true
                    height = tree.height
                }
            }
        }
    }

    fun bestScenicScore(): Int =
        treeGrid
            .also { trees ->
                trees.forEachIndexed { rowIndex, treeRow ->
                    treeRow.forEachIndexed { colIndex, tree -> calcScenicScore(tree, rowIndex, colIndex) }
                }
            }
            .map { row -> row.sortedByDescending { it.score } }
            .map(List<Tree>::first)
            .sortedByDescending { it.score }
            .first()
            .score

    private fun calcScenicScore(tree: Tree, rowIndex: Int, colIndex: Int) {
        tree.score = (
            topVisible(rowIndex, colIndex, tree.height)
                * rightVisible(rowIndex, colIndex, tree.height)
                * bottomVisible(rowIndex, colIndex, tree.height)
                * leftVisible(rowIndex, colIndex, tree.height)
            )
    }

    private fun topVisible(row: Int, column: Int, maxHeight: Int): Int {
        var lastHeight = -1
        var visibleCount = 0
        for (rowIndex in (0 until row).reversed()) {
            val tree = treeGrid[rowIndex][column]
            if (lastHeight < maxHeight) {
                visibleCount++
                lastHeight = tree.height
            }
        }
        return visibleCount
    }

    private fun rightVisible(row: Int, column: Int, maxHeight: Int): Int {
        var lastHeight = -1
        var visibleCount = 0
        treeGrid[row]
            .subList(column+1, treeGrid[row].size)
            .forEach { tree ->
                if (lastHeight < maxHeight) {
                    visibleCount++
                    lastHeight = tree.height
                }
            }
        return visibleCount
    }

    private fun bottomVisible(row: Int, column: Int, maxHeight: Int): Int {
        var lastHeight = -1
        var visibleCount = 0
        for (rowIndex in row+1 until treeGrid.size) {
            val tree = treeGrid[rowIndex][column]
            if (lastHeight < maxHeight) {
                visibleCount++
                lastHeight = tree.height
            }
        }
        return visibleCount
    }

    private fun leftVisible(row: Int, column: Int, maxHeight: Int): Int {
        if (column-1 < 0) { return 0 }

        var lastHeight = -1
        var visibleCount = 0
        treeGrid[row]
            .subList(0, column)
            .reversed()
            .forEach { tree ->
                if (lastHeight < maxHeight) {
                    visibleCount++
                    lastHeight = tree.height
                }
            }
        return visibleCount
    }
}

data class Tree(val height: Int, var visible: Boolean = false, var score: Int = 0)
