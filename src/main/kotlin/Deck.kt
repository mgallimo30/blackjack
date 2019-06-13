/*
 * Name        : source.kt
 * Author      : Matthew Ryan Gallimo
 *
 * License     : Copyright (C) 2019 All rights reserved
 */

class Deck {
    val deck = mutableListOf<Card>()
    private var numberOfDecks = 0
    private var numberOfCards = 0

    fun initDeck() {
        setNumDecks()
        buildDeck()
        shuffleDeck()
    }

    fun rebuildDeck() {
        buildDeck()
        shuffleDeck()
    }

    private fun setNumDecks() { //Gets the number of decks to be used from the user
        do {
            numberOfDecks = getInt("How many decks of cards will you be playing with? (1-8) ")
            if (numberOfDecks < 1 || numberOfDecks > 8) {
                println("Please enter a number between 1 - 8")
                numberOfDecks = 0
            }
        } while (numberOfDecks == 0)

        numberOfCards = numberOfDecks * 52
    }

    private fun buildDeck() {
        for (deckNum in 1..numberOfDecks) {
            for (suit in 0..3) {
                for (value in 1..13) {
                    deck.add(Card(suit, value))
                }
            }
        }
    }

    private fun shuffleDeck() {
        deck.shuffle()
    }
}