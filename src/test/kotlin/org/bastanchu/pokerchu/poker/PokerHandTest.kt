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

    // Generic comparator test

    @Test
    fun shouldCompareDifferentRankCardsHands() {
        val colorStraightHand = PokerHand(
            arrayOf<Card>(
                Card(Card.Suit.DIAMONDS, Card.Rank.N3),
                Card(Card.Suit.DIAMONDS, Card.Rank.N4),
                Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                Card(Card.Suit.DIAMONDS, Card.Rank.N2)
            )
        );
        val pokerHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.DIAMONDS, Card.Rank.N4)) );
        val comparation = pokerHand.compareTo(colorStraightHand);
        assertTrue(comparation  < 0);
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

    @Test
    fun shouldCompareColorStraightCardsHandsCorrectly() {
        val higherColorStraightHand = PokerHand(
            arrayOf<Card>(
                Card(Card.Suit.CLUBS, Card.Rank.N4),
                Card(Card.Suit.CLUBS, Card.Rank.N3),
                Card(Card.Suit.CLUBS, Card.Rank.N2),
                Card(Card.Suit.CLUBS, Card.Rank.N6),
                Card(Card.Suit.CLUBS, Card.Rank.N5)
            )
        );
        val lowerColorStraightHand = PokerHand(
            arrayOf<Card>(
                Card(Card.Suit.DIAMONDS, Card.Rank.N3),
                Card(Card.Suit.DIAMONDS, Card.Rank.N4),
                Card(Card.Suit.DIAMONDS, Card.Rank.KING),
                Card(Card.Suit.DIAMONDS, Card.Rank.ACE),
                Card(Card.Suit.DIAMONDS, Card.Rank.N2)
            )
        );
        assertTrue(lowerColorStraightHand.compareTo(higherColorStraightHand) < 0);
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

    @Test
    fun shouldComparePokertCardsHandsCorrectly() {
        val pokerHandN4 = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.DIAMONDS, Card.Rank.N4)) );
        val pokerHandAce = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.ACE),
            Card(Card.Suit.DIAMONDS, Card.Rank.ACE)) );
        assertTrue(pokerHandN4.compareTo(pokerHandAce) < 0);
    }

    @Test
    fun shouldComparePokertCardsFifthCardCorrectly() {
        val pokerHandN4Upper = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.DIAMONDS, Card.Rank.N4)) );
        val pokerHandN4Lower = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.JACK),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.DIAMONDS, Card.Rank.N4)) );
        assertTrue(pokerHandN4Lower.compareTo(pokerHandN4Upper) < 0);
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

    @Test
    fun shouldCompareFullCardsHandWithHigherTripleCorrectly() {
        val upperFullHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.ACE)) );
        val lowerFullHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N2),
            Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.SPADES, Card.Rank.ACE)) );
        assertTrue(lowerFullHand.compareTo(upperFullHand) < 1);
    }

    @Test
    fun shouldCompareFullCardsHandWithHigherPairCorrectly() {
        val upperFullHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.ACE)) );
        val lowerFullHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.N7),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N7)) );
        assertTrue(lowerFullHand.compareTo(upperFullHand) < 1);
    }

    @Test
    fun shouldCompareFullCardsHandsWhenEqualsCorrectly() {
        val upperFullHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.ACE)) );
        val equalFullHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.DIAMONDS, Card.Rank.ACE)) );
        assertTrue(upperFullHand.compareTo(equalFullHand) == 0);
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

    @Test
    fun shouldCompareColorCardsHandCorrectly() {
        val upperColorHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.JACK),
            Card(Card.Suit.CLUBS, Card.Rank.N3),
            Card(Card.Suit.CLUBS, Card.Rank.N2),
            Card(Card.Suit.CLUBS, Card.Rank.N6)) );
        val lowerColorHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.JACK),
            Card(Card.Suit.CLUBS, Card.Rank.N3),
            Card(Card.Suit.CLUBS, Card.Rank.N2),
            Card(Card.Suit.CLUBS, Card.Rank.N5)) );
        assertTrue(lowerColorHand.compareTo(upperColorHand) < 0);
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

    @Test
    fun shouldCompareStraightHandCardsCorrectly() {
        val upperStraightHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.JACK),
            Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            Card(Card.Suit.HEARTS, Card.Rank.N9),
            Card(Card.Suit.SPADES, Card.Rank.N10),
            Card(Card.Suit.SPADES, Card.Rank.N8)) );
        val lowerStraightHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.JACK),
            Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.SPADES, Card.Rank.KING)) );
        assertTrue(lowerStraightHand.compareTo(upperStraightHand) < 0);
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

    @Test
    fun shouldCompareTripleHandsCardsWithDifferentTriplesCorrectly() {
        val upperTripleHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val lowerTripleHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N2),
            Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        assertTrue(lowerTripleHand.compareTo(upperTripleHand) < 0);
    }

    @Test
    fun shouldCompareTripleHandsCardsWithEqualsTriplesCorrectly() {
        val upperTripleHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val lowerTripleHand = PokerHand(arrayOf<Card>(Card(Card.Suit.CLUBS, Card.Rank.N4),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5)) );
        assertTrue(lowerTripleHand.compareTo(upperTripleHand) < 0);
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

    @Test
    fun shouldCompareDoublePairHandsCardsByHighestPair() {
        val upperDoublePairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.N2)) );
        val lowerDoublePairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N3),
            Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.CLUBS, Card.Rank.N2)) );
        assertTrue(lowerDoublePairHand.compareTo(upperDoublePairHand) <0);
    }

    @Test
    fun shouldCompareDoublePairHandsCardsBySecondHighestPair() {
        val upperDoublePairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.N3)) );
        val lowerDoublePairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.N2)) );
        assertTrue(lowerDoublePairHand.compareTo(upperDoublePairHand) <0);
    }

    @Test
    fun shouldCompareDoublePairHandsCardsByFifthCard() {
        val upperDoublePairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.N3)) );
        val lowerDoublePairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.N10),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.CLUBS, Card.Rank.N3)) );
        assertTrue(lowerDoublePairHand.compareTo(upperDoublePairHand) <0);
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

    @Test
    fun shouldComparePairHandsCardsByPair() {
        val upperPairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.ACE),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val lowerPairHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        assertTrue(lowerPairHand.compareTo(upperPairHand) <0);
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

    @Test
    fun shouldCompareHighestCardCorrectly() {
        val upperHighestCardHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val lowerHighestCardHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.QUEEN),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        assertTrue(lowerHighestCardHand.compareTo(upperHighestCardHand) < 0);
    }

    @Test
    fun shouldCompareHighestCardCorrectlyByAnotherCards() {
        val upperHighestCardHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val lowerHighestCardHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N2),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        assertTrue(lowerHighestCardHand.compareTo(upperHighestCardHand) < 0);
    }

    @Test
    fun shouldCompareHighestCardCorrectlyWithEqualValueCards() {
        val upperHighestCardHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        val equalsHighestCardHand = PokerHand(arrayOf<Card>(Card(Card.Suit.SPADES, Card.Rank.N3),
            Card(Card.Suit.HEARTS, Card.Rank.ACE),
            Card(Card.Suit.HEARTS, Card.Rank.N4),
            Card(Card.Suit.SPADES, Card.Rank.N5),
            Card(Card.Suit.SPADES, Card.Rank.N6)) );
        assertTrue(upperHighestCardHand.compareTo(equalsHighestCardHand) == 0);
    }
}