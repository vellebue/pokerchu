package org.bastanchu.pokerchu.core

import org.junit.Test
import org.junit.Assert.*;

internal class SimpleDeckTest {

    @Test
    fun anInitializedDeckShouldHave52Cards() {
        var simpleDeck = SimpleDeck();
        assertEquals(52, simpleDeck.deckSize());
    }

    @Test
    fun aShuffledDeckMustHave52Cards() {
        var simpleDeck = SimpleDeck();
        simpleDeck.shuffle();
        assertEquals(52, simpleDeck.deckSize());
    }

    @Test
    fun aShuffledDeckMustHaveTheSameCardsMaybeInDifferentOrder() {
        var simpleOriginalDeck = SimpleDeck();
        var shufflableDeck = SimpleDeck();
        shufflableDeck.shuffle();
        println(shufflableDeck);
        var i = 0;
        while(!shufflableDeck.isEmptyDeck()) {
            val card = shufflableDeck.getCard();
            assertTrue("At iteration $i", simpleOriginalDeck.containsCard(card));
            i++;
        }
    }

    @Test
    fun aShuffledDeckMustHaveNotRepeatingCards() {
        var shufflableDeck = SimpleDeck();
        shufflableDeck.shuffle();
        println(shufflableDeck);
        var extractedCards = ArrayList<Card>();
        var i = 0;
        do  {
            val card = shufflableDeck.getCard();
            assertTrue("At iteration $i", !extractedCards.contains(card));
            extractedCards.add(card);
            i++;
        } while (!shufflableDeck.isEmptyDeck());
    }
}