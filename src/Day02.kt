@file:Suppress("KotlinConstantConditions")

fun main() {
    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0

        input.map { it.split(' ') }
            .forEach { (first, second) ->
                val value = second.toInt()
                when (first) {
                    "forward" -> horizontal += value
                    "down" -> depth += value
                    "up" -> depth -= value
                }
            }
        println("Final horizontal position: $horizontal")
        println("Final depth position: $depth")
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0

        input.map { it.split(' ') }
            .forEach { (first, second) ->
                val value = second.toInt()
                when (first) {
                    "forward" -> {
                        horizontal += value
                        depth += aim * value
                    }
                    "down" -> aim += value
                    "up" -> aim -= value
                }
            }
        println("Final horizontal position: $horizontal")
        println("Final depth position: $depth")
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val testResult = part1(testInput)
    check(testResult == 150)

    val testResult2 = part2(testInput)
    check(testResult2 == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
