package day6

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var fishes = input.first().split(",").map { it.toInt() }

        repeat(80) {
            fishes = fishes.flatMap {
                val newTimer = it - 1
                if (newTimer < 0)
                    listOf(6, 8)
                else
                    listOf(newTimer)
            }
        }

        return fishes.size
    }

    fun part2(input: List<String>): Long {
        val fishes = input.first().split(",").map { it.toInt() }

        val counts = LongArray(9) { i ->
            fishes.count { i == it }.toLong()
        }

        repeat(256) {
            val toClone = counts[0]
            (0..8).forEach {
                counts[it] = when (it) {
                    6 -> counts[7] + toClone
                    8 -> toClone
                    else -> counts[it + 1]
                }
            }
        }

        return counts.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    val testResult = part1(testInput)
    check(testResult == 5934)

    val testResult2 = part2(testInput)
    check(testResult2 == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
