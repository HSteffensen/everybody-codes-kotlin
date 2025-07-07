package com.hsteffensen.y2024

import com.hsteffensen.PuzzlePart
import com.hsteffensen.solution

fun main() {
    solution(
        2024,
        5,
        PuzzlePart(
            Day5::clapDance,
            """2 3 4 5
3 4 5 2
4 5 2 3
5 2 3 4""" to 2323L,
            """2 3 4 5
6 7 8 9""" to 6254L,
        ),
        PuzzlePart(
            Day5::clapDanceUntilRepeat,
            """2 3 4 5
6 7 8 9""" to 50877075L,
        ),
        PuzzlePart(
            Day5::clapDanceHighestNumber,
            """2 3 4 5
6 7 8 9""" to 6584L,
        ),
    )
}

object Day5 {
    fun readLines(input: String): MutableList<MutableList<Int>> {
        val lines =
            input
                .lines()
                .map { line -> line.split(" ").map { it.toInt() } }
        return (0..<4)
            .map { column -> lines.mapNotNull { line -> line.getOrNull(column) }.toMutableList() }
            .toMutableList()
    }

    fun clapDance(input: String): Long {
        val lines = readLines(input)
        for (step in 0..<10) {
            clapDanceStep(lines, step)
        }
        return shoutNumber(lines)
    }

    private fun shoutNumber(lines: List<List<Int>>): Long = lines.map { it.first() }.joinToString("").toLong()

    fun clapDanceStep(
        lines: MutableList<MutableList<Int>>,
        step: Int,
    ) {
        val firstLine = step % 4
        val activeLine = (firstLine + 1) % 4
        val clapper = lines[firstLine].removeFirst()
        val activeLineLength = lines[activeLine].size
        val clapperFinalLoop = clapper % (activeLineLength * 2)
        if (clapperFinalLoop == 0) {
            lines[activeLine].add(1, clapper)
            return
        }
        val newPosition =
            if (clapperFinalLoop <= activeLineLength) clapperFinalLoop - 1 else activeLineLength - (clapperFinalLoop - activeLineLength - 1)
        lines[activeLine].add(newPosition, clapper)
    }

    fun clapDanceUntilRepeat(input: String): Long {
        val lines = readLines(input)
        var step = 0
        val shoutCounts = mutableMapOf<Long, Int>()
        while (true) {
            clapDanceStep(lines, step)
            val shout = shoutNumber(lines)
            shoutCounts.merge(shout, 1, Int::plus)
            if (shoutCounts[shout] == 2024) {
                break
            }
            step++
        }

        return shoutNumber(lines).toLong() * (step + 1).toLong()
    }

    fun clapDanceHighestNumber(input: String): Long {
        val lines = readLines(input)
        var step = 0
        val shoutCounts = mutableMapOf<Long, Int>()
        while (true) {
            clapDanceStep(lines, step)
            val shout = shoutNumber(lines)
            shoutCounts.merge(shout, 1, Int::plus)
            if (shoutCounts[shout] == 2024) {
                break
            }
            step++
        }

        return shoutCounts.keys.max()
    }
}
