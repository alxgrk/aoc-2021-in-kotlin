package day9

import readInput
import java.util.*

typealias Point = Pair<Int, Int>

fun main() {

    fun dfs(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int): Point {
        var cell = matrix[rowIndex][columnIndex]
        var nextCell = rowIndex to columnIndex

        // use greater or equal check to escape situations where all neighbours have the same value
        // top
        if (rowIndex > 0 && cell >= matrix[rowIndex - 1][columnIndex]) {
            cell = matrix[rowIndex - 1][columnIndex]
            nextCell = rowIndex - 1 to columnIndex
        }
        // left
        if (columnIndex > 0 && cell >= matrix[rowIndex][columnIndex - 1]) {
            cell = matrix[rowIndex][columnIndex - 1]
            nextCell = rowIndex to columnIndex - 1
        }
        // bottom
        if (rowIndex < matrix.size - 1 && cell >= matrix[rowIndex + 1][columnIndex]) {
            cell = matrix[rowIndex + 1][columnIndex]
            nextCell = rowIndex + 1 to columnIndex
        }
        // right
        if (columnIndex < matrix[0].size - 1 && cell >= matrix[rowIndex][columnIndex + 1]) {
            cell = matrix[rowIndex][columnIndex + 1]
            nextCell = rowIndex to columnIndex + 1
        }

        return nextCell
    }

    fun getLocalMinimum(matrix: List<List<Int>>, rowIndex: Int, columnIndex: Int): Point {
        val newCoordinates = dfs(matrix, rowIndex, columnIndex)
        return if (newCoordinates == rowIndex to columnIndex)
            newCoordinates
        else
            getLocalMinimum(matrix, newCoordinates.first, newCoordinates.second)
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { line -> line.map(Char::digitToInt) }
        val localMinima = mutableSetOf<Point>()

        matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, _ ->
                localMinima += getLocalMinimum(matrix, rowIndex, columnIndex)
            }
        }

        val riskLevels = localMinima.map { (row, column) -> matrix[row][column] + 1 }
        return riskLevels.sum()
    }

    fun getNeighbours(matrix: List<List<Int>>, row: Int, column: Int): List<Point> {
        val neighbours = mutableListOf<Point>()
        // top
        if (row > 0) {
            neighbours += row - 1 to column
        }
        // left
        if (column > 0) {
            neighbours += row to column - 1
        }
        // bottom
        if (row < matrix.size - 1) {
            neighbours += row + 1 to column
        }
        // right
        if (column < matrix[0].size - 1) {
            neighbours += row to column + 1
        }
        return neighbours
    }

    fun bfs(
        matrix: List<List<Int>>,
        point: Point,
        marked: MutableSet<Point> = mutableSetOf(point),
        queue: Queue<Point> = LinkedList(listOf(point))
    ): Set<Point> {
        val basin = mutableSetOf(point)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val row = next.first
            val column = next.second
            val value = matrix[row][column]
            getNeighbours(matrix, row, column).forEach { neighbour ->
                val neighbourValue = matrix[neighbour.first][neighbour.second]
                if (neighbour !in marked && neighbourValue != 9 && value < neighbourValue) {
                    basin += neighbour
                    marked += neighbour
                    queue += neighbour
                }
            }
        }
        return basin
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { line -> line.map(Char::digitToInt) }
        val localMinima = mutableSetOf<Point>()

        matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, _ ->
                localMinima += getLocalMinimum(matrix, rowIndex, columnIndex)
            }
        }

        return localMinima.map { point -> bfs(matrix, point).size }
            .sortedDescending()
            .take(3)
            .reduce(Int::times)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    val testResult = part1(testInput)
    check(testResult == 15)

    val testResult2 = part2(testInput)
    check(testResult2 == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

