/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

fun getInt(output: String): Int {
    var num = -1
    var input: String?
    do {
        print(output)
        input = readLine()
        if (input == "-1") return -1
        try {
            if (input != null)
                num = input.toInt()

        } catch (e: NumberFormatException) {
            println("Please try again")
            num = 0
        }
    } while (num == -1)
    return num
}

fun getString(output: String): String {
    var str = ""
    do {
        print(output)
        val input = readLine()
        if (input != null)
            if (input == "") {
                println("please try again")
            } else {
                str = input
            }
    } while (str == "")
    return str
}

fun getBool(outPut: String): Boolean {
    var out = false
    do {
        var answer = getString(outPut)
        when (answer) {
            "y", "Y", "yes", "Yes" -> out = true
            "n", "N", "no", "No " -> out = false
            else -> {
                println("Please enter either y or n")
                answer = "-1"
            }
        }
    } while (answer == "-1")
    return out
}