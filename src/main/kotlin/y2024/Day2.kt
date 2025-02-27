package com.hsteffensen.y2024

import com.hsteffensen.PuzzlePart
import com.hsteffensen.solution

fun main() {
    solution(
        2024,
        2,
        PuzzlePart(
            Day2::countWordsInText,
            """WORDS:THE,OWE,MES,ROD,HER

AWAKEN THE POWER ADORNED WITH THE FLAMES BRIGHT IRE""" to 4,
            """WORDS:THE,OWE,MES,ROD,HER

THE FLAME SHIELDED THE HEART OF THE KINGS""" to 3,
            """WORDS:THE,OWE,MES,ROD,HER

POWE PO WER P OWE R""" to 2,
            """WORDS:THE,OWE,MES,ROD,HER

THERE IS THE END""" to 3,
        ),
        PuzzlePart(
            Day2::countSymbolsInTexts,
            """WORDS:THE,OWE,MES,ROD,HER,QAQ

AWAKEN THE POWE ADORNED WITH THE FLAMES BRIGHT IRE
THE FLAME SHIELDED THE HEART OF THE KINGS
POWE PO WER P OWE R
THERE IS THE END
QAQAQ""" to 42,
        ),
        PuzzlePart(
            Day2::countSymbolsInTextGrid,
            """WORDS:THE,OWE,MES,ROD,RODEO

HELWORLT
ENIGWDXL
TRODEOAL""" to 10,
            """WORDS:Q,QAQ

QDDQDDD
QDDADDD
DQAQAQD
DDDADDD
DDDQDDD""" to 11,
        ),
    )
}

object Day2 {
    fun getWords(input: String): Set<String> =
        input
            .lines()
            .first()
            .removePrefix("WORDS:")
            .split(",")
            .toSet()

    fun getText(input: String): String = input.lines().last()

    fun countWordsInText(input: String): Int {
        val words = getWords(input)
        val text = getText(input)
        return words.sumOf {
            it.toRegex().findAll(text).count()
        }
    }

    fun getTexts(input: String): List<String> = input.lines().drop(2)

    fun countSymbolsInTexts(input: String): Int {
        val words = getWords(input)
        val texts = getTexts(input)
        return texts.sumOf { countSymbolsInText(words, it) }
    }

    fun countSymbolsInText(
        words: Collection<String>,
        text: String,
    ): Int =
        getSymbolIndexesInText(words, text)
            .size

    tailrec fun Regex.findAllOverlapping(
        text: String,
        startIndex: Int = 0,
        foundSoFar: List<MatchResult> = emptyList(),
    ): List<MatchResult> {
        val match = find(text, startIndex)
        return if (match == null) {
            foundSoFar
        } else {
            findAllOverlapping(text, match.range.start + 1, foundSoFar + match)
        }
    }

    fun countSymbolsInTextGrid(input: String): Int {
        val words = getWords(input)
        val texts = getTexts(input)
        val verticalTexts =
            texts
                .map { it.split("") }
                .reduce { a, b ->
                    val x = a.zip(b).map { (c, d) -> "$c$d" }
                    x
                }.filter { it.isNotEmpty() }
        val horizontalMatches =
            texts
                .mapIndexed { y, text -> getSymbolIndexesInTextWrapping(words, text).map { it to y } }
                .flatten()
                .toSet()
        val verticalMatches =
            verticalTexts
                .mapIndexed { x, text -> getSymbolIndexesInText(words, text).map { x to it } }
                .flatten()
                .toSet()
        return (horizontalMatches + verticalMatches)
            .size
    }

    fun getSymbolIndexesInTextWrapping(
        words: Collection<String>,
        text: String,
    ): Set<Int> =
        words
            .flatMap {
                it.toRegex().findAllOverlapping(text + text) + it.reversed().toRegex().findAllOverlapping(text + text)
            }.flatMap { it.range }
            .map { it % text.length }
            .toSet()

    fun getSymbolIndexesInText(
        words: Collection<String>,
        text: String,
    ): Set<Int> =
        words
            .flatMap { it.toRegex().findAllOverlapping(text) + it.reversed().toRegex().findAllOverlapping(text) }
            .flatMap { it.range }
            .toSet()
}
