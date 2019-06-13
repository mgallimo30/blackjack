import kotlin.random.Random

/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

fun placeBets(players: Players) {
    val maxBet = 20
    val minBet = 5
    players.newRound()
    for (i in 1..players.numberOfPlayers) {
        var bet = 0
        players.getPlayer(i).apply {
            if (isLivePlayer) {
                println("Player $i \"$name\" has $$money:")
            }
            do {
                if (debug) {
                    println("placeBets(); player = $i; money = $money;")
                    println("isPlaying = $isPlaying; isLivePlayer = $isLivePlayer; isPlayingThisHand = $isPlayingThisHand\n")
                }
                if (isPlaying && isLivePlayer) {
                    isPlayingThisHand = getBool("Do you want to place a bet? (y/n) ")
                    if (isPlayingThisHand) {
                        bet = when {
                            money < maxBet -> getInt(
                                "$name enter a bet between \$$minBet" +
                                        " and \$$money: "
                            )
                            money >= maxBet -> getInt(
                                "$name enter a bet between \$$minBet" +
                                        " and \$$maxBet: "
                            )
                            else -> 0
                        }

                        when {
                            bet == 0 -> println("Please enter an even dollar amount between 5 and 20")
                            bet < 5 -> println("Bet to low")
                            bet > 20 -> println("Bet to high")
                            bet > money -> println("You don't have enough for that bet")
                        }
                        if (bet in 5..20 && bet == money) {
                            println("All in!")
                        }
                    } else {
                        if (getBool("Do you want to quit? (y/n) ")) {
                            isPlaying = false
                            isLivePlayer = false
                            println()
                        }
                    }
                } else if (isPlaying && !isLivePlayer) {
                    when {
                        money > maxBet -> bet = Random.nextInt(5, 20)
                        money == 5 -> bet = 5
                        money in 6..(maxBet) -> bet = Random.nextInt(5, money)

                    }
                    if (showAll){
                        println("Computer $name $i bet \$$bet")
                    }
                }
            } while (bet !in 5..20 && isPlayingThisHand)
            money -= bet
            amountBet = bet
            if (isLivePlayer) {
                println()
            }
        }
    }
}

fun dealCards(player: Players, deck: Deck) {
    var dealerBlackJack = false
    for (i in 1..6) {
        player.getPlayer(i).apply {
            if (isPlaying && isPlayingThisHand) {
                firstCard = deck.deck.last()
                deck.deck.removeAt(deck.deck.size - 1)
                if (deck.deck.isEmpty()) {
                    deck.rebuildDeck()
                }
            }
        }
    }

    for (i in 1..6) {
        player.getPlayer(i).apply {
            if (isPlaying && isPlayingThisHand) {
                secondCard = deck.deck.last()
                deck.deck.removeAt(deck.deck.size - 1)
                if (deck.deck.isEmpty()) {
                    deck.rebuildDeck()
                }
                total = firstCard.getNumV() + secondCard.getNumV()
                if (total == 22) { //player has two aces
                    total -= 10  //default to largest value without going over
                    subTen = true
                } else if (firstCard.getNumV() == 11 || secondCard.getNumV() == 11) {
                    subTen = true
                }

            }

        }
    }
    (6.downTo(1)).forEach {
        player.getPlayer(it).apply {
            if ((firstCard.getStrV() == "Ace" && secondCard.getStrV() == "Jack")
                || (firstCard.getStrV() == "Jack" && secondCard.getStrV() == "Ace")
            ) {
                if (it < 6 && !dealerBlackJack) {
                    if (isLivePlayer) {
                        println("You got Black Jack! You win!")
                    }
                    amountBet *= 3
                    money += amountBet
                    win++
                    handsPlayed++
                    player.dealer.handsPlayed++
                    isPlayingThisHand = false
                } else {
                    if (isLivePlayer) {
                        println("The dealer has Black Jack!")
                    }
                    win += player.numberOfPlayersLeft()
                    handsPlayed += player.numberOfPlayersLeft()
                    player.dealerBlackJack()
                    dealerBlackJack = true
                }

            }
        }
    }
}

fun playerMoves(players: Players, deck: Deck) {
    var bust: Boolean
    var stand: Boolean
    var decision: String
    for (i in 1..6) {
        bust = false
        stand = false
        decision = ""
        players.getPlayer(i).apply {
            if (debug) {
                println("thereIsALivePlayer 2")
            }

            if (isPlaying && isPlayingThisHand && isLivePlayer) {
                println(
                    "Dealer is showing a ${players.dealer.firstCard.getStrV()}" +
                            " of ${players.dealer.firstCard.getStrS()}\n"
                ); Thread.sleep(2500)

                println("Player $i \"$name\":")
                println("$name you have a ${firstCard.getStrV()} of ${firstCard.getStrS()}")
                println("and you have a ${secondCard.getStrV()} of ${secondCard.getStrS()}")
                do {
                    decision = getString("$name do you want to Stand or Hit? (s/h) ")
                    println(); Thread.sleep(500)
                    when (decision) {
                        "s", "S", "stand", "Stand" -> stand = true
                        "h", "H", "hit", "Hit" -> bust = playerHit(this, players.dealer, deck)
                    }
                } while (!bust && !stand)
                println()
            } else if (isPlaying && isPlayingThisHand && !isLivePlayer) {
                do {
                    decision = computerDecision(this, players.dealer)
                    if (i < 6) {
                        when (decision) {
                            "Stand" -> {
                                stand = true
                            }
                            else -> {
                                bust = playerHit(this, players.dealer, deck)
                                stand = true
                            }
                        }
                    } else {
                        when (decision) {
                            "Hit" -> {
                                bust = playerHit(this, players.dealer, deck)
                            }
                            else -> {
                                stand = true
                            }
                        }
                    }

                } while (!bust && !stand)
            }
        }
    }
}

fun playerHit(player: Players.Player, dealer: Players.Player, deck: Deck): Boolean {
    var bust = false
    if (player.isLivePlayer) {
        println(
            "Your card is a ${deck.deck.last().getStrV()}" +
                    " of ${deck.deck.last().getStrS()}"
        )
    }
    player.apply {
        total += deck.deck.last().getNumV()
        if (total > 21 && subTen) {
            total -= 10
            subTen = false
        } else if (deck.deck.last().getNumV() == 11 && (total - 10) <= 21) {
            total -= 10
        } else if (total > 21) {
            if (isLivePlayer) {
                println("You bust")
            }
            bust = true
            if (money < 5) {
                yourOut(this)
            }
            isPlayingThisHand = false
            handsPlayed++
            dealer.win++
            dealer.handsPlayed++
        }
    }
    deck.deck.removeAt(deck.deck.size - 1)
    if (deck.deck.isEmpty()) {
        deck.rebuildDeck()
    }
    return bust
}

fun endRound(player: Players) {
    val dealerBust = { player.dealer.total > 21 }()

    if (debug) {
        println("endRound / thereIsALivePlayer")
    }
    if (player.thereIsALivePlayer() && player.thereIsAPlayerPlayingThisHand()) {
        player.dealer.apply {
            println(
                "Dealer has a ${firstCard.getStrV()} of ${firstCard.getStrS()} " +
                        "and a ${secondCard.getStrV()} of ${secondCard.getStrS()}" +
                        "\nAfter game play the dealers total is $total"
            )
            Thread.sleep(1000)
            if (dealerBust) {
                println("Dealer Bust!")
                Thread.sleep(1000)
            }
            println()
        }
    }

    (1..player.numberOfPlayers).forEach {
        if (debug) {
            println("endRound / forEach / $it")
        }
        player.getPlayer(it).apply {
            if (dealerBust && isPlayingThisHand) {
                amountBet *= 2
                money += amountBet
                win++
                handsPlayed++
                player.dealer.handsPlayed++

            } else if (isPlayingThisHand && total < player.dealer.total) {
                if (isLivePlayer) {
                    println("Player $it \"$name\" you have $total. You lost!\n")
                    Thread.sleep(1000)
                }
                handsPlayed++
                player.dealer.win++
                player.dealer.handsPlayed++

                if (money < 5) {
                    yourOut(this)
                    isPlayingThisHand = false
                }
            } else if (isPlayingThisHand && total > player.dealer.total) {
                if (isLivePlayer) {
                    println("Player $it \"$name\" you have $total. You won!\n")
                    Thread.sleep(1000)
                }
                amountBet *= 2
                money += amountBet
                win++
                handsPlayed++
                player.dealer.handsPlayed++

            } else if (isPlayingThisHand && total == player.dealer.total) {
                if (isLivePlayer) {
                    println("Player $it \"$name\" you draw!\n")
                    Thread.sleep(1000)
                }
                if (money < 5) {
                    yourOut(this)
                }
                handsPlayed++
                player.dealer.win++
                player.dealer.handsPlayed++
            }
        }

    }
}

fun yourOut(player: Players.Player) {
    player.apply {
        if (isLivePlayer) {
            println("Your out")
            isLivePlayer = false
        } else if (!isLivePlayer) {
            println("Computer $name ${getPN()} is out")
        }
        isPlaying = false
    }
    Thread.sleep(1000)
    println()
}