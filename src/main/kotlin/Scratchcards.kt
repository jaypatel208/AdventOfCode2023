import java.io.File
import kotlin.math.pow

fun main() {
    day4P1()
    day4P2()
}

fun day4P2() {
    val input = getStringsFromFile()
}

fun day4P1() {
    val input = getStringsFromFile()

    val commonCounts = input.map { string ->
        val (listBeforePipe, listAfterPipe) = string.split(":")[1].trim()
            .split(" | ").map { it.split(" ").filter { it.isNotBlank() }.map { it.toInt() } }

        listBeforePipe.intersect(listAfterPipe.toSet()).count()
    }

    val pointsList = commonCounts.map { 2.0.pow(it - 1).toInt() }
    val sum = pointsList.sum()

    println("Day 4 puzzle 1 answer: $sum")
}

private fun getStringsFromFile(): MutableList<String> {
    val fileName = "src/main/kotlin/scratchcards.txt"
    val lines = mutableListOf<String>()

    File(fileName).forEachLine {
        lines.add(it.trimIndent())
    }
    return lines
}