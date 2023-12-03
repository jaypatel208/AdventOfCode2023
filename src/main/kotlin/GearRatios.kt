import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    day3P1()
    day3P2()
}

fun day3P2() {
    val lines = getStringsFromFile()
    val gearCounts = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

    val partRegex = Regex("\\d+")
    val gearRegex = Regex("\\*")

    for ((i, line) in lines.withIndex()) {
        for (match in partRegex.findAll(line)) {
            val range = max(0, match.range.first - 1)..min(match.range.last + 1, line.length - 1)
            val partValue = match.value.toInt()

            (i - 1..i + 1)
                .filter { it in lines.indices }
                .forEach { j ->
                    val subStr = lines[j].substring(range)
                    gearRegex.findAll(lines[j].substring(range)).forEach { gear ->
                        val gearLocation = Pair(j, gear.range.first + range.first)
                        gearCounts.getOrPut(gearLocation) { mutableListOf() }.add(partValue)
                    }
                }
        }
    }

    val sum = gearCounts.values.filter { it.size == 2 }.sumBy { it[0] * it[1] }
    println("Day 3 puzzle 1 answer: $sum")
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