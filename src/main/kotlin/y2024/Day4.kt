package com.hsteffensen.y2024

import com.hsteffensen.PuzzlePart
import com.hsteffensen.solution
import kotlin.math.absoluteValue

fun main() {
    solution(
        2024,
        4,
        PuzzlePart(
            Day4::countHammerStrokesToLowest,
            """3
4
7
8""" to 10,
        ),
        PuzzlePart(
            Day4::countHammerStrokesToLowest,
            """3
4
7
8""" to 10,
        ),
        PuzzlePart(
            Day4::countHammerStrokesMin,
            """2
4
5
6
8""" to 8,
        ),
    )
}

object Day4 {
    fun readNails(input: String): List<Int> =
        input
            .lines()
            .map { it.toInt() }

    fun countHammerStrokesToLowest(input: String): Int {
        val nails = readNails(input)
        return countHammerStrokesToLevel(nails, nails.min())
    }

    fun countHammerStrokesMin(input: String): Int {
        val nails = readNails(input)
        val smallest = nails.min()
        val biggest = nails.max()
        val middle = (smallest + biggest) / 2
        return countHammerStrokesMinHelper(
            nails = nails,
            lowerTarget = smallest,
            lowerStrokes = countHammerStrokesToLevel(nails, smallest),
            middleTarget = middle,
            middleStrokes = countHammerStrokesToLevel(nails, middle),
            upperTarget = biggest,
            upperStrokes = countHammerStrokesToLevel(nails, biggest),
        )
    }

    fun countHammerStrokesMinHelper(
        nails: List<Int>,
        lowerTarget: Int,
        lowerStrokes: Int,
        middleTarget: Int,
        middleStrokes: Int,
        upperTarget: Int,
        upperStrokes: Int,
    ): Int {
        if (lowerTarget == upperTarget || lowerTarget == middleTarget || middleTarget == upperTarget) {
            return middleStrokes
        }
        val lowerMiddleTarget = (lowerTarget + middleTarget) / 2
        val lowerMiddleStrokes = countHammerStrokesToLevel(nails, lowerMiddleTarget)
        val upperMiddleTarget = (middleTarget + upperTarget) / 2
        val upperMiddleStrokes = countHammerStrokesToLevel(nails, upperMiddleTarget)
        return if (lowerMiddleStrokes < upperMiddleStrokes) {
            countHammerStrokesMinHelper(
                nails = nails,
                lowerTarget = lowerTarget,
                lowerStrokes = lowerStrokes,
                middleTarget = lowerMiddleTarget,
                middleStrokes = lowerMiddleStrokes,
                upperTarget = middleTarget,
                upperStrokes = middleStrokes,
            )
        } else {
            countHammerStrokesMinHelper(
                nails = nails,
                lowerTarget = middleTarget,
                lowerStrokes = middleStrokes,
                middleTarget = upperMiddleTarget,
                middleStrokes = upperMiddleStrokes,
                upperTarget = upperTarget,
                upperStrokes = upperStrokes,
            )
        }
    }

    fun countHammerStrokesToLevel(
        nails: List<Int>,
        target: Int,
    ): Int = nails.sumOf { (it - target).absoluteValue }
}
