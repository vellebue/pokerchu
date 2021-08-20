package org.bastanchu.pokerchu.core

data class Card(val suit:Suit, val rank:Rank) {

    enum class Suit{CLUBS, DIAMONDS, HEARTS, SPADES};
    enum class Rank{ACE, N2, N3, N4, N5, N6, N7, N8, N9, N10, JACK, QUEEN, KING}

}