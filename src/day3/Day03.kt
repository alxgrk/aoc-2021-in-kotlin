@file:Suppress("NonAsciiCharacters")

package day3

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val rows = input.map { it.toCharArray() }

        var gammaBitString = ""
        var epsilonBitString = ""

        rows.first().indices.forEach { index ->
            val (zeros, ones) = rows.partition { it[index] == '0' }
            gammaBitString += if (zeros.size > ones.size) 0 else 1
            epsilonBitString += if (zeros.size > ones.size) 1 else 0
        }

        val γ = gammaBitString.toInt(2)
        val ε = epsilonBitString.toInt(2)

        return γ.times(ε)
    }

    fun part2(input: List<String>): Int {
        val rows = input.map { it.toCharArray() }

        fun rec(
            remaining: List<CharArray>,
            index: Int,
            selector: (List<CharArray>, List<CharArray>) -> List<CharArray>
        ): String =
            if (remaining.size == 1)
                String(remaining.first())
            else {
                val (zeros, ones) = remaining.partition { it[index] == '0' }
                rec(selector(zeros, ones), index + 1, selector)
            }

        val oxygenRating = rec(rows, 0) { zeros, ones ->
            if (zeros.size < ones.size || zeros.size == ones.size) ones else zeros
        }
        val co2Rating = rec(rows, 0) { zeros, ones ->
            if (zeros.size < ones.size || zeros.size == ones.size) zeros else ones
        }

        val γ = oxygenRating.toInt(2)
        val ε = co2Rating.toInt(2)

        return γ.times(ε)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    val testResult = part1(testInput)
    check(testResult == 198)

    val testResult2 = part2(testInput)
    check(testResult2 == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
