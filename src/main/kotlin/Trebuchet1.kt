import java.io.File

fun main() {
    part1()
    part2()
}

fun part2() {
    val lines = getStringsFromFile()

    val numberList = lines.map { extractNumbersFromWordToo(it) }

    val sum = numberList.sum()

    println("Day 2 puzzle 2 answer: $sum")
}

fun extractNumbersFromWordToo(it: String): Int {
    var number = ""

    val mapping = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "zero" to 0
    )

    var value = 0
    val length = it.length

    while (value < it.length) {
        for (c in it) {
            if (c.isDigit()) {
                number += c
            } else {
                for (i in 0..length) {
                    if (value < i) {
                        val subString = it.substring(value, i)
                        mapping[subString.lowercase()]?.let {
                            number += it
                        }
                    }
                }
            }
            value++
        }
    }

    return when (number.length) {
        0 -> 0
        1 -> "${number[0]}${number[0]}".toInt()
        2 -> "${number[0]}${number[number.length - 1]}".toInt()
        else -> "${number.first()}${number.last()}".toInt()
    }
}

private fun part1() {
    val lines = getStringsFromFile()

    val numberList = lines.map { extractNumbers(it) }

    val sum = numberList.sum()

    println("Day 1 puzzle 1 answer: $sum")
}

private fun getStringsFromFile(): MutableList<String> {
    val fileName = "src/main/kotlin/calibration.txt"
    val lines = mutableListOf<String>()

    File(fileName).forEachLine {
        lines.add(it)
    }
    return lines
}

fun extractNumbers(it: String): Int {
    var number = ""
    for (i in it) {
        if (i.isDigit()) {
            number += i
        }
    }
    return when (number.length) {
        1 -> "${number[0]}${number[0]}".toInt()
        2 -> "${number[0]}${number[number.length - 1]}".toInt()
        else -> "${number.first()}${number.last()}".toInt()
    }
}