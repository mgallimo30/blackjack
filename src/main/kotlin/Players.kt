/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

//Dealer and players
class Players {
    val numberOfPlayers: Int
    val player1 = Player(1)
    val player2 = Player(2)
    val player3 = Player(3)
    val player4 = Player(4)
    val player5 = Player(5)
    val dealer = Player(6)

    init {
        dealer.isPlaying = true
        dealer.isPlayingThisHand = true
        numberOfPlayers = setNumberOfPlayers()
    }


    private fun setNumberOfPlayers(): Int {
        var num: Int
        do {
            num = getInt("How many players are there? (1-5) ")
            if (num !in 1..num) {
                println("Please enter a number between 1 - 5")
            }
        } while (num !in 1..num)
        println()
        return num
    }

    fun getPlayer(num: Int): Players.Player {
        return when (num) {
            1 -> player1
            2 -> player2
            3 -> player3
            4 -> player4
            5 -> player5
            else -> dealer
        }
    }

    fun thereIsALivePlayer(): Boolean {
        return when {
            player1.isLivePlayer -> true
            player2.isLivePlayer -> true
            player3.isLivePlayer -> true
            player4.isLivePlayer -> true
            player5.isLivePlayer -> true
            else -> false
        }
    }

    fun thereIsAPlayerPlayingThisHand(): Boolean {
        return when {
            player1.isPlayingThisHand -> true
            player2.isPlayingThisHand -> true
            player3.isPlayingThisHand -> true
            player4.isPlayingThisHand -> true
            player5.isPlayingThisHand -> true
            else -> false
        }
    }

    fun newRound() {
        for (i in 1..6) {
            getPlayer(i).apply {
                if (isPlaying) {
                    isPlayingThisHand = true
                    amountBet = 0
                    firstCard = Card(0, 0)
                    secondCard = Card(0, 0)
                    total = 0
                    subTen = false
                }
            }
        }
    }

    fun dealerBlackJack() {
        for (i in 1..numberOfPlayers) {
            getPlayer(i).apply {
                if (isPlaying) {
                    if (money < 5) {
                        yourOut(this)
                    }
                    isPlayingThisHand = false
                    handsPlayed++
                }
            }
        }
    }

    fun numberOfPlayersLeft(): Int {
        var num = 0
        when {
            player1.isPlaying -> num++
            player2.isPlaying -> num++
            player3.isPlaying -> num++
            player4.isPlaying -> num++
            player5.isPlaying -> num++
        }
        return num
    }

    class Player(num: Int) {
        private val playerNumber = num
        var played = false
        var wasLive = false
        var isPlaying = false
        var isPlayingThisHand = false
        var isLivePlayer = false
        var name = "Player"
        var money = 1000
        var amountBet = 0
        var firstCard = Card(0, 0)
        var secondCard = Card(0, 0)
        var total = 0
        var subTen = false
        var win = 0.0
        var handsPlayed = 0.0

        fun initPlayer() {
            played = true
            isPlaying = true
            setLivePlayer()
            if (isLivePlayer) {
                wasLive = true
                setName()
            }
        }

        fun getPN(): Int {
            return playerNumber
        }

        private fun setLivePlayer() {
            isLivePlayer = getBool("Is player $playerNumber going to be a live player? (y/n) ")
            if (!isLivePlayer) {
                println()
            }
        }

        private fun setName() {
            name = getString("What is the players name? ")
            println()
        }
    }
}