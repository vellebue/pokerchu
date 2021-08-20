package org.bastanchu.pokerchu.core

import java.util.*
import kotlin.math.roundToInt

class SimpleDeck:Deck() {

    //private val SEED = 37L;

    override fun shuffle() {
        val rand = Random();
        var newDeck = Stack<Card>();
        for (i in 52 downTo 1) {
            val index = Math.floor(rand.nextDouble() * i).roundToInt();
            val card = deck.get(index);
            deck.removeAt(index);
            newDeck.push(card);
        }
        deck = newDeck;
    }
}