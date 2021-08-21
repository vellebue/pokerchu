package org.bastanchu.pokerchu.poker

import org.junit.Test;
import org.junit.Assert.*;

import org.bastanchu.pokerchu.core.Card
import java.lang.RuntimeException

class PokerHandTest {

    @Test(expected = RuntimeException::class)
    fun shouldThrowAnExceptionWhenGivenANon5CardsHand() {
        val pokerHand = PokerHand({ Card(Card.Suit.HEARTS, Card.Rank.ACE) } as Array<Card>);
    }

    @Test(expected = RuntimeException::class)
    fun shouldThrowAnExceptionWhenGivenARepeatingCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
                                                Card(Card.Suit.HEARTS, Card.Rank.ACE),
                                                Card(Card.Suit.HEARTS, Card.Rank.ACE),
                                                Card(Card.Suit.SPADES, Card.Rank.N4),
                                                Card(Card.Suit.SPADES, Card.Rank.N6)));
    }

    // TRIPLE Cards tests

    @Test
    fun shouldRecognizeATripleCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.TRIPLE, pair.first);
        assertEquals(Card(Card.Suit.CLUBS, Card.Rank.N4), pair.second);
    }

    // PAIR Cards tests

    @Test
    fun shouldRecognizeAPairCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
                                                Card(Card.Suit.HEARTS, Card.Rank.ACE),
                                                Card(Card.Suit.HEARTS, Card.Rank.N4),
                                                Card(Card.Suit.SPADES, Card.Rank.N4),
                                                Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.PAIR, pair.first);
        assertEquals(Card(Card.Suit.HEARTS, Card.Rank.N4), pair.second);
    }

    //HIGHEST Card tests

    @Test
    fun shouldRecognizeAHighestCardHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
                                                Card(Card.Suit.HEARTS, Card.Rank.ACE),
                                                Card(Card.Suit.HEARTS, Card.Rank.N4),
                                                Card(Card.Suit.SPADES, Card.Rank.N5),
                                                Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.HIGHEST, pair.first);
        assertEquals(Card(Card.Suit.HEARTS, Card.Rank.ACE), pair.second);
    }
}