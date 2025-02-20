package com.hsteffensen.y2024

import com.hsteffensen.PuzzlePart
import com.hsteffensen.solution

fun main() {
    solution(
        2024,
        1,
        PuzzlePart(::part1, "ABBAC" to 5),
        PuzzlePart(::part2, "AxBCDDCAxD" to 28),
        PuzzlePart(::part3, "xBxAAABCDxCC" to 30),
    )
}

fun part1(input: String): Int = input.sumOf(::potionsNeeded)

fun potionsNeeded(char: Char): Int =
    when (char) {
        'A' -> 0
        'B' -> 1
        'C' -> 3
        'D' -> 5
        else -> throw IllegalArgumentException("$char")
    }

fun part2and3(
    input: String,
    size: Int,
): Int =
    input.chunked(size).sumOf { chunk ->
        val chars = chunk.toList().filter { it != 'x' }
        chars.sumOf { potionsNeeded(it) } + ((chars.size - 1) * chars.size)
    }

fun part2(input: String): Int = part2and3(input, 2)

fun part3(input: String): Int = part2and3(input, 3)
