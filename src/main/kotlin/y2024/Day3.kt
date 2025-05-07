package com.hsteffensen.y2024

import com.hsteffensen.PuzzlePart
import com.hsteffensen.neighbors4
import com.hsteffensen.neighbors8
import com.hsteffensen.solution

fun main() {
    solution(
        2024,
        3,
        PuzzlePart(
            Day3::countDiggableVolume,
            """..........
..###.##..
...####...
..######..
..######..
...####...
..........""" to 35,
        ),
        PuzzlePart(
            Day3::countDiggableVolume,
            """..........
..###.##..
...####...
..######..
..######..
...####...
..........""" to 35,
        ),
        PuzzlePart(
            Day3::countDiggableVolumeDiagonal,
            """..........
..###.##..
...####...
..######..
..######..
...####...
..........""" to 29,
        ),
    )
}

object Day3 {
    fun countDiggableVolume(input: String): Int {
        var grid = readGrid(input)
        while (true) {
            val newGrid = digGrid(grid)
            if (newGrid == grid) {
                return newGrid.values.sum()
            }
            grid = newGrid
        }
    }

    fun countDiggableVolumeDiagonal(input: String): Int {
        var grid = readGrid(input)
        while (true) {
            val newGrid = digGridDiagonal(grid)
            if (newGrid == grid) {
                return newGrid.values.sum()
            }
            grid = newGrid
        }
    }

    fun readGrid(input: String): Map<Pair<Int, Int>, Int> =
        input
            .lines()
            .flatMapIndexed { y, line -> line.mapIndexedNotNull { x, c -> if (c == '#') x to y else null } }
            .associateWith { 1 }

    fun digGrid(grid: Map<Pair<Int, Int>, Int>): Map<Pair<Int, Int>, Int> =
        grid.mapValues { (pos, _) ->
            pos.neighbors4().minOf { grid.getOrDefault(it, 0) } + 1
        }

    fun digGridDiagonal(grid: Map<Pair<Int, Int>, Int>): Map<Pair<Int, Int>, Int> =
        grid.mapValues { (pos, _) ->
            pos.neighbors8().minOf { grid.getOrDefault(it, 0) } + 1
        }
}
