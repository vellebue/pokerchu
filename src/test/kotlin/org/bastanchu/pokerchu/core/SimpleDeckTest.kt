package org.bastanchu.pokerchu.core

import org.junit.Test
import org.junit.Assert.*;

internal class SimpleDeckTest {

    @Test
    fun anInitializedDeckShouldHave52Cards() {
        var simpleDeck = SimpleDeck();
        assertEquals(52, simpleDeck.deckSize());
    }
}