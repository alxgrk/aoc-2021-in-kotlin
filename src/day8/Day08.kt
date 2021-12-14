package day8

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        val digits = input.flatMap { it.split("|")[1].trim().split(" ") }
        return digits.filter { it.length in listOf(2, 3, 4, 7) }.size
    }

    fun part2(input: List<String>): Int {
        val pairs = input.map { line ->
            val (signalPatterns, digits) = line.split("|").map { it.trim().split(" ") }
            signalPatterns to digits
        }

        return pairs.sumOf { (signalPatterns, digits) ->
            val numberToSegments = mutableMapOf<Int, CharArray>()
            signalPatterns.sortedBy { it.length }.forEach { pattern ->
                val chars = pattern.toCharArray()
                when (pattern.length) {
                    2 -> numberToSegments[1] = chars
                    3 -> numberToSegments[7] = chars
                    4 -> numberToSegments[4] = chars
                    7 -> numberToSegments[8] = chars
                    5 -> {
                        val is2 = chars.filter { numberToSegments[4]!!.contains(it) }.size == 2
                        if (is2) {
                            numberToSegments[2] = chars
                            return@forEach
                        }
                        val is3 = numberToSegments[1]!!.all { it in chars }
                        if (is3) {
                            numberToSegments[3] = chars
                            return@forEach
                        }
                        val is5 =
                            pattern.singleOrNull {
                                !numberToSegments[4]!!.contains(it) && !numberToSegments[7]!!.contains(
                                    it
                                )
                            }
                        if (is5 != null) {
                            numberToSegments[5] = chars
                            return@forEach
                        }
                    }
                    6 -> {
                        val is6 = !numberToSegments[1]!!.all { it in chars }
                        if (is6) {
                            numberToSegments[6] = chars
                            return@forEach
                        }
                        val is9 = numberToSegments[4]!!.all { it in chars }
                        if (is9) {
                            numberToSegments[9] = chars
                            return@forEach
                        }
                        val twoAndFive = numberToSegments[2]!! + numberToSegments[5]!!
                        val is0 = chars.all { it in twoAndFive }
                        if (is0) {
                            numberToSegments[0] = chars
                            return@forEach
                        }
                    }
                }
            }

            digits.joinToString("") { digit ->
                numberToSegments.entries.first { (_, v) ->
                    v.toList().containsAll(digit.toList())
                }.key.toString()
            }.toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    val testResult = part1(testInput)
    check(testResult == 26)

    val testResult2 = part2(testInput)
    check(testResult2 == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

