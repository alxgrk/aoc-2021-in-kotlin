package day7

import readInput
import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }

        val min = positions.minOf { it }
        val max = positions.maxOf { it }
        return (min..max)
            .map { align -> positions.sumOf { abs(align - it) } }
            .minOf { it }
    }

    fun part2(input: List<String>): Int {
        val positions = input.first().split(",").map { it.toInt() }

        fun Int.gaussianSums(): Int = (0.5 * this * (this + 1)).toInt()

        val min = positions.minOf { it }
        val max = positions.maxOf { it }
        return (min..max)
            .map { align ->
                positions.sumOf { abs(align - it).gaussianSums() }
            }
            .minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val testResult = part1(testInput)
    check(testResult == 37)

    val testResult2 = part2(testInput)
    check(testResult2 == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

