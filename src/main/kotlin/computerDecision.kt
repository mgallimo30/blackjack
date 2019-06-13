/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

//A large portion of this code block is ported from a java project of mine
fun computerDecision(player: Players.Player, dealer: Players.Player): String {
    var output = "Invalid Output"
    player.apply {
        if (getPN() != 6) {

            val pCard1Value = firstCard.getNumV()
            val pCard2Value = secondCard.getNumV()
            val dCardValue = dealer.firstCard.getNumV()
            val cardSum = pCard1Value + pCard2Value

            val bs = arrayOf(
                arrayOf("H", "H", "H", "H", "H", "H", "H", "H", "H", "H"),
                arrayOf("H", "D", "D", "D", "D", "H", "H", "H", "H", "H"),
                arrayOf("D", "D", "D", "D", "D", "D", "D", "D", "H", "H"),
                arrayOf("D", "D", "D", "D", "D", "D", "D", "D", "D", "H"),
                arrayOf("H", "H", "S", "S", "S", "H", "H", "H", "H", "H"),
                arrayOf("S", "S", "S", "S", "S", "H", "H", "H", "H", "H"),
                arrayOf("S", "S", "S", "S", "S", "H", "H", "H", "H", "H"),
                arrayOf("S", "S", "S", "S", "S", "H", "H", "H", "U", "H"),
                arrayOf("S", "S", "S", "S", "S", "H", "H", "U", "U", "U"),
                arrayOf("S", "S", "S", "S", "S", "S", "S", "S", "S", "S"),
                arrayOf("S", "S", "S", "S", "S", "S", "S", "S", "S", "S"),
                arrayOf("S", "D", "D", "D", "D", "S", "S", "H", "H", "H"),
                arrayOf("H", "D", "D", "D", "D", "H", "H", "H", "H", "H"),
                arrayOf("H", "H", "D", "D", "D", "H", "H", "H", "H", "H"),
                arrayOf("H", "H", "D", "D", "D", "H", "H", "H", "H", "H"),
                arrayOf("H", "H", "H", "D", "D", "H", "H", "H", "H", "H"),
                arrayOf("H", "H", "H", "D", "D", "H", "H", "H", "H", "H"),
                arrayOf("P", "P", "P", "P", "P", "P", "P", "P", "P", "P"),
                arrayOf("S", "S", "S", "S", "S", "S", "S", "S", "S", "S"),
                arrayOf("P", "P", "P", "P", "P", "S", "P", "P", "S", "S"),
                arrayOf("P", "P", "P", "P", "P", "P", "H", "H", "H", "H"),
                arrayOf("H", "P", "P", "P", "P", "H", "H", "H", "H", "H"),
                arrayOf("D", "D", "D", "D", "D", "D", "D", "D", "H", "H"),
                arrayOf("H", "H", "H", "H", "H", "H", "H", "H", "H", "H"),
                arrayOf("H", "H", "P", "P", "P", "P", "H", "H", "H", "H")
            )
            /* being left in for debugging
            println("pCard1Value = $pCard1Value")
            println("pCard2Value = $pCard2Value")
            println("dCardValue = $dCardValue")
            println("cardSum = $cardSum")
            */

            if (pCard1Value == pCard2Value) {
                if (pCard1Value == 2 || pCard1Value == 3) {
                    output = bs[24][dCardValue - 2]
                } else if (pCard1Value == 4) {
                    output = bs[23][dCardValue - 2]
                } else if (pCard1Value == 5) {
                    output = bs[22][dCardValue - 2]
                } else if (pCard1Value == 6) {
                    output = bs[21][dCardValue - 2]
                } else if (pCard1Value == 7) {
                    output = bs[20][dCardValue - 2]
                } else if (pCard1Value == 9) {
                    output = bs[19][dCardValue - 2]
                } else if (pCard1Value == 10) {
                    output = bs[18][dCardValue - 2]
                } else if (pCard1Value == 11 || pCard1Value == 8) {
                    output = bs[17][dCardValue - 2]
                }
            } else if (pCard1Value == 11 || pCard2Value == 11) {
                if (pCard1Value == 2 || pCard2Value == 2) {
                    output = bs[16][dCardValue - 2]
                } else if (pCard1Value == 3 || pCard2Value == 3) {
                    output = bs[15][dCardValue - 2]
                } else if (pCard1Value == 4 || pCard2Value == 4) {
                    output = bs[14][dCardValue - 2]
                } else if (pCard1Value == 5 || pCard2Value == 5) {
                    output = bs[13][dCardValue - 2]
                } else if (pCard1Value == 6 || pCard2Value == 6) {
                    output = bs[12][dCardValue - 2]
                } else if (pCard1Value == 7 || pCard2Value == 7) {
                    output = bs[11][dCardValue - 2]
                } else if (7 < pCard1Value shl 11 || 7 < pCard2Value shl 11) {
                    output = bs[10][dCardValue - 2]
                }
            } else if (cardSum >= 17) {
                output = bs[9][dCardValue - 2]
            } else if (cardSum in 9..16) {
                output = bs[cardSum - 8][dCardValue - 2]
            } else if (cardSum <= 8) {
                output = bs[0][dCardValue - 2]
            }
            // OK, we're all done now we should know our move and can print it!

            when {
                output.compareTo("h", ignoreCase = true) == 0 -> output = "Hit"
                output.compareTo("s", ignoreCase = true) == 0 -> output = "Stand"
                output.compareTo("d", ignoreCase = true) == 0 -> output = "Double Down"
                output.compareTo("p", ignoreCase = true) == 0 -> output = "Split"
                output.compareTo("u", ignoreCase = true) == 0 -> output = "Surrender"
            }

            //println("Your move: $output")
        } else if (getPN() == 6) {
            output = when {
                total < 17 -> "Hit"
                else -> "Stand"
            }
        }
    }
    return output
}