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


    // COLOR STRAIGHT Card Tests

    @Test
    fun shouldRecognizeAColorStraightCardsHand() {
        val pokerHand = PokerHand(
            arrayOf<Card>(
                Card(Card.Suit.DIAMONDS, Card.Rank.N3),
                Card(Card.Suit.DIAMONDS, Card.Rank.N4),
                Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                Card(Card.Suit.DIAMONDS, Card.Rank.N2)
            )
        );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.COLOR_STRAIGHT, pair.first);
        assertEquals(Card.Rank.N4, pair.second[0].rank);
    }

    // POKER Card Tests

    @Test
    fun shouldRecognizeAPokerCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.DIAMONDS, Card.Rank.N4)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.POKER, pair.first);
        assertEquals(Card.Rank.N4, pair.second[0].rank);
    }

    // FULL Card Tests

    @Test
    fun shouldRecognizeAFullCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.ACE)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.FULL, pair.first);
        assertEquals(Card.Rank.N4, pair.second[0].rank);
        assertEquals(Card.Rank.ACE, pair.second[3].rank);
    }

    // COLOR Card Tests

    @Test
    fun shouldRecognizeASimpleColorCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.JACK),
            Card(Card.Suit.CLUBS, Card.Rank.N3),
            Card(Card.Suit.CLUBS, Card.Rank.N2),
            Card(Card.Suit.CLUBS, Card.Rank.N5)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.COLOR, pair.first);
        assertEquals(Card.Rank.JACK, pair.second[0].rank);
    }

    // STRAIGHT Cards tests
    @Test
    fun shouldRecognizeASimpleStraightCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.JACK),
            Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            Card(Card.Suit.HEARTS, Card.Rank.N9),
            Card(Card.Suit.SPADES, Card.Rank.N10),
            Card(Card.Suit.SPADES, Card.Rank.N8)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.STRAIGHT, pair.first);
        assertEquals(Card.Rank.QUEEN, pair.second[0].rank);
    }

    @Test
    fun shouldRecognizeAPartedStraightCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            Card(Card.Suit.SPADES, Card.Rank.KING),
            Card(Card.Suit.SPADES, Card.Rank.ACE)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.STRAIGHT, pair.first);
        assertEquals(Card.Rank.N3, pair.second[0].rank);
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
        assertEquals(Card.Rank.N4, pair.second[0].rank);
    }

    // DOUBLE PAIR Cards tests

    @Test
    fun shouldRecognizeADoublePairCardsHand() {
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.N2)) );
        val pair = pokerHand.getRank();
        assertEquals(PokerHand.Rank.DOUBLE_PAIR, pair.first);
        assertEquals(Card.Rank.N4, pair.second[0].rank);
        assertEquals(Card.Rank.N2, pair.second[2].rank);
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
        assertEquals(Card.Rank.N4, pair.second[0].rank);
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
        assertEquals(Card.Rank.ACE, pair.second[0].rank);
    }
}