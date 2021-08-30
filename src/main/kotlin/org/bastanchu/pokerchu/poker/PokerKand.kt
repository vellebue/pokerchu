package org.bastanchu.pokerchu.poker

import org.bastanchu.pokerchu.core.Card
import kotlin.collections.ArrayList

class PokerHand @Throws(RuntimeException::class) constructor(val cards:Array<Card>)  {

    enum class Rank{HIGHEST, PAIR, DOUBLE_PAIR, TRIPLE, STAIR, COLOR, FULL, POKER, COLOR_STAIR }

    init {
        if (cards.size != 5) {
            throw RuntimeException("A poker hand must have 5 cards");
        }
        if (repeatingCards()) {
            throw RuntimeException("A poker hand must not repeat the same card");
        }
    }

    private  fun repeatingCards():Boolean {
        for(i in 0..4) {
            for(j in 1..4) {
                if (i != j && cards[i].equals(cards[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    fun getRank():Pair<Rank, Array<Card>> {
        val decomposedArray = arraysEqualsRangeCard(cards);
        val firstArray = decomposedArray[0];
        val finalArray = decomposedArray.flatMap( { it -> it.asList() } ).toTypedArray();
        if (firstArray.size == 4) {
            return Pair(Rank.POKER, finalArray);
        } else if (firstArray.size == 3) {
            val secondArray = decomposedArray[1];
            if (secondArray.size == 2) {
                return Pair(Rank.FULL, finalArray);
            } else {
                return Pair(Rank.TRIPLE, finalArray);
            }
        } else if (firstArray.size == 2) {
            val secondArray = decomposedArray[1];
            if (secondArray.size == 2) {
                return Pair(Rank.DOUBLE_PAIR, finalArray);
            } else {
                return Pair(Rank.PAIR, finalArray);
            }
        } else {
            return Pair(Rank.HIGHEST, finalArray);
        }
    }

    /**
     * Decompose an array of cards into subarrays of repeating range cards.
     * The array is sorted from highest subarray length to lowest and for the same size
     * from highest rank card to lowest
     * @param cards The array to decompose.
     * @return A sorted array of arrays of cards.
     */
    private fun arraysEqualsRangeCard(cards:Array<Card>):Array<Array<Card>> {
        if (cards.size == 0) {
            return ArrayList<Array<Card>>().toTypedArray();
        }
        else if (cards.size == 1) {
            return arrayOf(cards);
        } else {
            val firstCard = cards[0];
            var returningCards = ArrayList<Card>();
            var remainingCards = ArrayList<Card>();
            returningCards.add(firstCard);
            for (i in 1..cards.lastIndex) {
                val currentCard = cards[i];
                if (currentCard.rank.equals(firstCard.rank)) {
                    returningCards.add(currentCard);
                } else {
                    remainingCards.add(currentCard);
                }
            }
            var returningList = arraysEqualsRangeCard(remainingCards.toTypedArray()).toList();
            var sorteableList = ArrayList<Array<Card>>();
            sorteableList.addAll(returningList.toTypedArray());
            sorteableList.add(returningCards.toTypedArray());
            val comparator = {e1:Array<Card>, e2:Array<Card> ->
                if (e1.size != e2.size) {
                    - e1.size.compareTo(e2.size);
                } else {
                    - e1[0].rank.compareTo(e2[0].rank)
                }
            };
            val sortedList = sorteableList.sortedWith(comparator);
            return sortedList.toTypedArray();
        }
    }
}