/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

data class Card(val suit: Int, val value: Int) {
    private val numSuit = suit
    private val numValue: Int
    private val strSuit: String
    private val strValue: String

    init {
        strSuit = setStrS()
        strValue = setStrV(value)
        numValue = when (value) {
            in 11..13 -> 10
            1 -> 11
            else -> value
        }
    }

    fun getNumV(): Int {
        return numValue
    }

    fun getStrS(): String {
        return strSuit
    }

    private fun setStrS(): String {
        when (numSuit) {
            0 -> return "Spades"
            1 -> return "Hearts"
            2 -> return "Diamonds"
            3 -> return " Clubs"
        }
        return "Error"

    }

    fun getStrV(): String {
        return strValue
    }

    private fun setStrV(num: Int): String {
        return when (num) {
            1 -> "Ace"
            11 -> "Jack"
            12 -> "Queen"
            13 -> "King"
            else -> num.toString()
        }
    }
}