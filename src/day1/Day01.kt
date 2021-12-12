package day1

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        if (numbers.isEmpty()) return 0

        return numbers
            .fold(0 to numbers[0]) { (count, prev), next ->
                if (prev < next) count + 1 to next
                else count to next
            }.first
    }

    fun part2(input: List<String>): Int {
        val numbers = input.map { it.toInt() }
        if (numbers.size < 4) return 0

        val initial = numbers.take(3).sum()
        return numbers
            .windowed(3) { it.sum() }
            .fold(0 to initial) { (count, prev), next ->
                if (prev < next) count + 1 to next
                else count to next
            }.first
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    val testResult = part1(testInput)
    check(testResult == 7)

    val testResult2 = part2(testInput)
    check(testResult2 == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
