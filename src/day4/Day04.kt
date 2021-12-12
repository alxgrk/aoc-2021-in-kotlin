@file:Suppress("NonAsciiCharacters")

package day4

import readInput

data class Board(
    val rows: List<List<Int>>,
    val markedFields: List<MutableList<Boolean>> = buildList {
        repeat(5) {
            this.add(buildList {
                repeat(5) {
                    this.add(false)
                }
            }.toMutableList())
        }
    }
) {
    fun markFieldWithNumber(number: Int): Boolean {
        rows.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { fieldIndex, field ->
                if (field == number) {
                    markedFields[rowIndex][fieldIndex] = true
                    return isBingo()
                }
            }
        }
        return false
    }

    private fun isBingo(): Boolean =
        markedFields.any { row -> row.all { it } } ||
                rows.first().indices.any { index ->
                    val (allTrue) = markedFields.partition { it[index] }
                    return@any allTrue.size == 5
                }

    fun sumOfUnmarked(): Int {
        var sum = 0
        markedFields.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { index, marked ->
                if (!marked) {
                    sum += rows[rowIndex][index]
                }
            }
        }
        return sum
    }
}

fun main() {
    fun createBoards(input: List<String>) = input.drop(1)
        .filter { it.isNotBlank() }
        .windowed(5, 5) { board ->
            val rows = board.map { row ->
                row.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
            }
            Board(rows)
        }

    fun createNumbersDrawn(input: List<String>) = input.first().split(",").map { it.toInt() }

    fun part1(input: List<String>): Int {
        val numbersDrawn = createNumbersDrawn(input)
        val boards = createBoards(input)

        var result = 0
        for (number in numbersDrawn) {
            if (result != 0) break

            for (board in boards) {
                val isBingo = board.markFieldWithNumber(number)
                if (isBingo) {
                    result = board.sumOfUnmarked() * number
                    break
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val numbersDrawn = createNumbersDrawn(input)
        var boards = createBoards(input)

        var result = 0
        for (number in numbersDrawn) {
            if (result != 0) break

            for (board in boards) {
                val isBingo = board.markFieldWithNumber(number)
                if (isBingo) {
                    if ( boards.size == 1) {
                        result = board.sumOfUnmarked() * number
                        break
                    } else {
                        boards = boards.filter { it != board }
                    }
                }
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val testResult = part1(testInput)
    check(testResult == 4512)

    val testResult2 = part2(testInput)
    check(testResult2 == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
