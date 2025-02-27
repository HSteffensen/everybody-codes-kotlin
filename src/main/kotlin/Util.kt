package com.hsteffensen

import kotlin.io.path.Path
import kotlin.io.path.readText

data class PuzzlePart<T>(
    val solution: (String) -> T,
    val testCases: List<Pair<String, T>>,
) {
    constructor(solution: (String) -> T, vararg testCases: Pair<String, T>) : this(solution, testCases.toList())
}

fun <T> solution(
    year: Int,
    day: Int,
    vararg parts: PuzzlePart<T>,
) {
    for ((i, partData) in parts.withIndex()) {
        val (solution, testCases) = partData
        val name = "y$year/d${day.toString().padStart(2, '0')}/part${i + 1}"
        for ((case, answer) in testCases) {
            val result = solution(case)
            check(result == answer) { "FAILURE - $name: expected `$answer`, got `$result`" }
        }
        val input = readInput(name)
        val result = solution(input)
        println("$name: $result")
    }
}

fun readInput(name: String) = Path(".local/$name.txt").readText().trim()
