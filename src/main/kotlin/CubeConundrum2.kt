import java.io.File

fun main() {
    day2P1()
    day2P2()
}

fun day2P2() {
    val lines = getStringsFromFile()

    var initial = 0

    val listEachGame = mutableListOf<List<String>>()

    for (i in lines.indices) {
        val string = lines[i]

        val pattern = Regex("(Game (\\d+):)(.*?)(?=(Game \\d+:|$))", RegexOption.DOT_MATCHES_ALL)
        val matches = pattern.findAll(string)

        for (match in matches) {
            val gameData = match.groups[3]?.value?.trim()

            val individualGameData = gameData?.split(";")?.map { it.trim() }
            if (individualGameData != null) {
                listEachGame.add(individualGameData)
            }
        }
    }

    val listToSum = mutableListOf<MutableList<Int>>()

    while (initial < listEachGame.size) {
        val toProcess = listEachGame[initial]

        var maxRedNumber: Int? = null
        var maxGreenNumber: Int? = null
        var maxBlueNumber: Int? = null

        for (element in toProcess) {
            val stringList = element.split(",")
            val regex = Regex("\\d+")

            for (s in stringList) {
                if (s.contains("red")) {
                    val matchResult = regex.find(s)
                    val extractedNumber = matchResult?.value

                    if (extractedNumber != null) {
                        val number = extractedNumber.toInt()

                        if (maxRedNumber == null || number > maxRedNumber) {
                            maxRedNumber = number
                        }
                    }
                } else if (s.contains("green")) {
                    val matchResult = regex.find(s)
                    val extractedNumber = matchResult?.value

                    if (extractedNumber != null) {
                        val number = extractedNumber.toInt()

                        if (maxGreenNumber == null || number > maxGreenNumber) {
                            maxGreenNumber = number
                        }
                    }
                } else if (s.contains("blue")) {
                    val matchResult = regex.find(s)
                    val extractedNumber = matchResult?.value

                    if (extractedNumber != null) {
                        val number = extractedNumber.toInt()

                        if (maxBlueNumber == null || number > maxBlueNumber) {
                            maxBlueNumber = number
                        }
                    }
                }
            }
        }

        val maxValuesList = mutableListOf<Int>()

        if (maxRedNumber != null) {
            maxValuesList.add(maxRedNumber)
        }
        if (maxGreenNumber != null) {
            maxValuesList.add(maxGreenNumber)
        }
        if (maxBlueNumber != null) {
            maxValuesList.add(maxBlueNumber)
        }

        listToSum.add(maxValuesList)

        initial++
    }

    val productListForEachList = listToSum.map { valuesList ->
        valuesList.reduceOrNull { acc, value -> acc * value } ?: 1
    }

    val finalSum = productListForEachList.sum()
    println("Day 2 puzzle 2 answer: $finalSum")
}

fun day2P1() {
    val lines = getStringsFromFile()
    var red = ""
    var green = ""
    var blue = ""

    val valid = "Valid"
    val inValid = "Invalid"

    val count = "Count"
    val notCount = "Not Count"

    val validString = mutableListOf<String>()
    val gameStatus = mutableListOf<String>()
    val sumList = mutableListOf<Int>()

    for (i in lines.indices) {
        val string = lines[i]

        val pattern = Regex("(Game (\\d+):)(.*?)(?=(Game \\d+:|$))", RegexOption.DOT_MATCHES_ALL)
        val matches = pattern.findAll(string)

        for (match in matches) {
            val gameData = match.groups[3]?.value?.trim()

            val individualGameData = gameData?.split(";")?.map { it.trim() }

            individualGameData?.forEach { set ->
                val rgbValues = extractRGBValues(set)
                for (i in rgbValues.indices) {
                    val single = rgbValues[i]
                    if (single.contains("red")) {
                        for (c in single) {
                            if (c.isDigit()) {
                                red += c
                            }
                        }
                        if (red.toInt() <= 12) {
                            validString.add(valid)
                        } else {
                            validString.add(inValid)
                        }
                    } else if (single.contains("green")) {
                        for (c in single) {
                            if (c.isDigit()) {
                                green += c
                            }
                        }
                        if (green.toInt() <= 13) {
                            validString.add(valid)
                        } else {
                            validString.add(inValid)
                        }
                    } else if (single.contains("blue")) {
                        for (c in single) {
                            if (c.isDigit()) {
                                blue += c
                            }
                        }
                        if (blue.toInt() <= 14) {
                            validString.add(valid)
                        } else {
                            validString.add(inValid)
                        }
                    }
                }
                if (validString.contains(inValid)) {
                    gameStatus.add(notCount)
                } else {
                    gameStatus.add(count)
                }
                validString.clear()
                red = ""
                green = ""
                blue = ""
            }
            if (!gameStatus.contains(notCount)) {
                match.groups[2]?.value?.let { sumList.add(it.toInt()) }
            }
        }
        gameStatus.clear()
    }
    val answer = sumList.sum()
    println("Day 2 puzzle 1 answer: $answer")
}

private fun getStringsFromFile(): MutableList<String> {
    val fileName = "src/main/kotlin/cubeconundrum.txt"
    val lines = mutableListOf<String>()

    File(fileName).forEachLine {
        lines.add(it.trimIndent())
    }
    return lines
}

fun extractRGBValues(set: String): List<String> {
    val rgbPattern = Regex("(\\d+) (green|blue|red)")
    val rgbMatches = rgbPattern.findAll(set)
    val rgbValues = mutableListOf<String>()

    for (rgbMatch in rgbMatches) {
        val value = rgbMatch.groups[1]?.value
        val color = rgbMatch.groups[2]?.value
        rgbValues.add("$color: $value")
    }

    return rgbValues
}