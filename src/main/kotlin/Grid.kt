package com.hsteffensen

fun Pair<Int, Int>.neighbors4(): Collection<Pair<Int, Int>> =
    listOf(
        first + 1 to second,
        first to second + 1,
        first - 1 to second,
        first to second - 1,
    )

fun Pair<Int, Int>.neighbors8(): Collection<Pair<Int, Int>> =
    listOf(
        first + 1 to second,
        first + 1 to second + 1,
        first to second + 1,
        first - 1 to second + 1,
        first - 1 to second,
        first - 1 to second - 1,
        first to second - 1,
        first + 1 to second - 1,
    )
