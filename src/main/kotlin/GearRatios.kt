import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    day3P1()
    day3P2()
}

fun day3P2() {
    val lines = getStringsFromFile()
}

fun day3P1() {
    val lines = getStringsFromFile()
    var sum = 0

    val regex = Regex("\\d+")

    for (i in lines.indices) {
        for (match in regex.findAll(lines[i])) {
            val range = (max(0, match.range.first - 1)..min(match.range.last + 1, lines[i].length - 1))

            for (j in i - 1..i + 1) {
                if (j in lines.indices && lines[j].checkForSymbol(range)) {
                    sum += match.value.toInt()
                    break
                }
            }
        }
    }

    println("Day 3 puzzle 1 answer: $sum")
}

private fun String.checkForSymbol(range: IntRange): Boolean {
    val subStr = this.substring(range)
    val symbolRegex = Regex("[^\\d\\.]")

    return subStr.contains(symbolRegex)
}

private fun getStringsFromFile(): MutableList<String> {
    val fileName = "src/main/kotlin/gearratios.txt"
    val lines = mutableListOf<String>()

    File(fileName).forEachLine {
        lines.add(it.trimIndent())
    }
    return lines
}