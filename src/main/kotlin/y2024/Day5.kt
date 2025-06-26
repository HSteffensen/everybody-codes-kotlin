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
5 2 3 4""" to 2323,
        ),
        PuzzlePart(
            Day5::clapDance,
            """2 3 4 5
6 7 8 9""" to 50877075,
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

    fun clapDance(input: String): Int {
        val lines = readLines(input)
        for (step in 0..<10) {
            clapDanceStep(lines, step)
        }
        return lines.map { it.first() }.joinToString("").toInt()
    }

    fun clapDanceStep(
        lines: MutableList<MutableList<Int>>,
        step: Int,
    ) {
        val firstLine = step % 4
        val activeLine = (firstLine + 1) % 4
        val clapper = lines[firstLine].removeFirst()
        val activeLineLength = lines[activeLine].size
        val newPosition =
            if (clapper <= activeLineLength) clapper - 1 else activeLineLength - (clapper - activeLineLength - 1)
        lines[activeLine].add(newPosition, clapper)
    }
}
