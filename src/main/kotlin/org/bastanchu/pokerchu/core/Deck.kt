package org.bastanchu.pokerchu.core

import java.util.*

abstract class Deck() {

    var deck = Stack<Card>();

    init {
        for (suit in Card.Suit.values()) {
            for (rank in Card.Rank.values()) {
                val card = Card(suit, rank);
                deck.push(card);
            }
        }
    }

    abstract fun shuffle()

    fun isEmptyDeck():Boolean {
        return deck.isEmpty();
    }

    fun deckSize():Int {
        return deck.size;
    }

    fun getCard():Card {
        return deck.pop();
    }
}