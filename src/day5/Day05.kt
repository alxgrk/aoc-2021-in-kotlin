package day5

import readInput
import kotlin.math.abs

data class Point(
    val x: Int,
    val y: Int
)

private infix fun Int.to(other: Int) = Point(this, other)

fun main() {

    fun Pair<Point, Point>.toRange(selector: Point.() -> Int) =
        if (first.selector() < second.selector())
            first.selector()..second.selector()
        else
            first.selector()downTo second.selector()

    fun Pair<Point, Point>.toRangeList(selector: Point.() -> Int): List<Int> =
        toRange(selector).toList()


    fun findAllPoints(pair: Pair<Point, Point>): List<Point> =
        if (pair.first.x == pair.second.x) {
            val commonX = pair.first.x

            val range = pair.toRange { y }
            range.map { Point(commonX, it) }
        } else if (pair.first.y == pair.second.y) {
            val commonY = pair.first.y
            val range = pair.toRange { x }
            range.map { Point(it, commonY) }
        } else {
            // in this case the line is diagonal
            val steps = abs(pair.first.x - pair.second.x)
            val xRange = pair.toRangeList { x }
            val yRange = pair.toRangeList { y }
            (0..steps).map {
                Point(xRange[it], yRange[it])
            }
        }

    fun part1(input: List<String>): Int {
        val lines = input
            .map { line ->
                val (start, end) = line.split("->")
                    .map { it.trim() }
                    .map {
                        val (x, y) = it.split(",")
                        x.toInt() to y.toInt()
                    }
                start to end
            }
            .filter { (start, end) -> start.x == end.x || start.y == end.y }

        val allPoints = lines.flatMap { findAllPoints(it) }

        val pointFrequency = mutableMapOf<Point, Int>()
        allPoints.forEach {
            pointFrequency[it] = (pointFrequency[it] ?: 0) + 1
        }

        return pointFrequency.filter { it.value >= 2 }.size
    }

    fun part2(input: List<String>): Int {
        val lines = input
            .map { line ->
                val (start, end) = line.split("->")
                    .map { it.trim() }
                    .map {
                        val (x, y) = it.split(",")
                        x.toInt() to y.toInt()
                    }
                start to end
            }
            .filter { (start, end) ->
                start.x == end.x
                        || start.y == end.y
                        || (abs(start.x - end.x) == abs(start.y - end.y))
            }

        val allPoints = lines.flatMap { findAllPoints(it) }

        val pointFrequency = mutableMapOf<Point, Int>()
        allPoints.forEach {
            pointFrequency[it] = (pointFrequency[it] ?: 0) + 1
        }

        return pointFrequency.filter { it.value >= 2 }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    val testResult = part1(testInput)
    check(testResult == 5)

    val testResult2 = part2(testInput)
    check(testResult2 == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
