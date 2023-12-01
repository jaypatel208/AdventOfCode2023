import java.io.File

fun main() {
    val fileName = "src/main/kotlin/calibration.txt"
    val lines = mutableListOf<String>()

    File(fileName).forEachLine {
        lines.add(it)
    }

    val numberList = lines.map { extractNumbers(it) }


    val sum = numberList.sum()

    print(sum)
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


