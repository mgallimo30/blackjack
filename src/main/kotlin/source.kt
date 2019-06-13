import java.lang.Thread.sleep
import kotlin.math.round

/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

//set to true to show debugging output
const val debug = false
//set to true to show computer player output
const val showAll = false //TODO finish adding computer player output

fun main() {

    val deck = Deck()
    var numberOfRounds = 0

    println("Welcome Black Jack\n"); sleep(1000)
    println(
        "Table rules:\n" +
                "\tThere may be 1 to 5 players\n" +
                "\tThere may be 0 to 5 live players\n" +
                "\tThere may be 0 to 5 computer players\n" +
                "\tAll players start with $1000\n" +
                "\tYou may play with 1 to 8 decks of cards\n" +
                "\tBets are between $5 and $20\n" +
                "\tNo more then 100 hands are allowed\n" +
                "\tNo buy back allowed\n"
    )
    sleep(500)

    val players = Players()

    players.apply {
        for (i in 1..numberOfPlayers) {
            when (i) {
                1 -> player1.initPlayer()
                2 -> player2.initPlayer()
                3 -> player3.initPlayer()
                4 -> player4.initPlayer()
                5 -> player5.initPlayer()
            }
        }
    }

    deck.apply {
        initDeck()
        print("\nStarting Game in")
        print(" 3"); sleep(1000)
        print(" 2"); sleep(1000)
        println(" 1"); sleep(1000)
        println()
        do {
            numberOfRounds++
            if (debug) {
                println("thereIsALivePlayer")
            }
            if (players.thereIsALivePlayer() || showAll) {
                println("Round $numberOfRounds:\n")
            }

            if (debug) {
                println("placeBets")
            }
            placeBets(players)

            if (debug) {
                println("dealCards")
            }
            if (players.thereIsAPlayerPlayingThisHand())
                dealCards(players, this)

            if (debug) {
                println("playerMoves")
            }
            if (players.thereIsAPlayerPlayingThisHand())
                playerMoves(players, this)

            if (debug) {
                println("endRound")
            }
            if (players.thereIsAPlayerPlayingThisHand())
                endRound(players)

            if (players.thereIsALivePlayer() || showAll) {
                print("Press enter to start next round")
                readLine()
                println()
            }
        } while (players.numberOfPlayersLeft() > 0 && numberOfRounds < 100)
    }
    endOfGame(players, numberOfRounds)
}

fun endOfGame(player: Players, numberOfRounds: Int) {
    println("Game Over")
    sleep(1000)
    println()
    (1..player.numberOfPlayers).forEach {
        player.getPlayer(it).apply {
            if (played) {
                if (wasLive) {
                    println(
                        "Player $it \"$name\" has $$money left\n" +
                                "Your win ratio is %${round((win / handsPlayed) * 100)}"
                    )
                } else {
                    println(
                        "Computer player $it has $$money left\n" +
                                "The computers win ratio is %${round((win / handsPlayed) * 100)}"
                    )
                }
                println()
            }

        }
        sleep(1000)
    }
    println("The dealers win ratio is %${round((player.dealer.win / player.dealer.handsPlayed) * 100)}")
    sleep(1000)
    println("")
    println("$numberOfRounds rounds were played")
    sleep(1000)
    println("Good Bye")
}