package org.bastanchu.pokerchu.poker

import org.bastanchu.pokerchu.core.Card
import kotlin.collections.ArrayList

class PokerHand @Throws(RuntimeException::class) constructor(val cards:Array<Card>) :Comparable<PokerHand> {

    enum class Rank{HIGHEST, PAIR, DOUBLE_PAIR, TRIPLE, STRAIGHT, COLOR, FULL, POKER, COLOR_STRAIGHT }

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
        val isColor = cardsInColor(finalArray);
        val finalStraightArray = cardsInStraight(finalArray);
        if (isColor && (finalStraightArray != null)) {
            return Pair(Rank.COLOR_STRAIGHT, finalStraightArray);
        }
        else if (firstArray.size == 4) {
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
            if (isColor) {
                return Pair(Rank.COLOR, finalArray);
            }
            else if (finalStraightArray != null) {
                return Pair(Rank.STRAIGHT, finalStraightArray);
            } else {
                return Pair(Rank.HIGHEST, finalArray);
            }
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

    private fun cardsInStraight(cards:Array<Card>):Array<Card>? {
        val decomposedArray = arraysEqualsRangeCard(cards);
        if (decomposedArray.size == 5) {
            // There should be five different cards to have a straight
            val sortedArray = decomposedArray.flatMap( { it -> it.asList() } ).toTypedArray();
            if (cardsInSimpleStraightSortedArray(cards)) {
                // Simple straight
                return cards;
            } else {
                // Maybe straight across ace
                return cardsInPartedStraightSortedArray(cards);
            }
        } else {
            return null;
        }
    }

    private fun cardsInSimpleStraightSortedArray(cards:Array<Card>):Boolean {
        if (cards.size > 1) {
            var isStraight = true;
            var i = 0;
            do {
                val currentCard = cards[i];
                val currentNextCard = cards[i + 1];
                isStraight = currentCard.rank.ordinal - currentNextCard.rank.ordinal == 1;
                i++;
            } while(isStraight && i < cards.size - 1);
            return isStraight;
        } else {
            return true;
        }
    }

    private fun cardsInPartedStraightSortedArray(cards:Array<Card>):Array<Card>? {
        var partitionIndex:Int? = null;
        var i = 0;
        do {
            val currentCard = cards[i];
            val currentNextCard = cards[i + 1];
            if (currentCard.rank.ordinal - currentNextCard.rank.ordinal != 1) {
                partitionIndex = i;
            }
            i++;
        } while (partitionIndex == null && i < cards.size - 1);
        if (partitionIndex == null) {
            return cards;
        } else {
            val upperArray = cards.toMutableList().subList(0, partitionIndex + 1).toTypedArray();
            val lowerArray = cards.toMutableList().subList(partitionIndex + 1, cards.size).toTypedArray();
            if (cardsInSimpleStraightSortedArray(upperArray) &&
                cardsInSimpleStraightSortedArray(lowerArray) &&
                upperArray[0].rank.equals(Card.Rank.ACE) &&
                lowerArray[lowerArray.lastIndex].rank.equals(Card.Rank.N2)) {
                var resultingList = ArrayList<Card>();
                resultingList.addAll(lowerArray);
                resultingList.addAll(upperArray);
                return resultingList.toTypedArray();
            } else {
                return null;
            }
        }
    }

    private fun cardsInColor(cards:Array<Card>):Boolean {
        var detectedSuit:Card.Suit? = null;
        var inColor = true;
        var i = 0;
        while(inColor && (i < cards.size)) {
            val currentCard = cards[i];
            if (detectedSuit == null) {
                detectedSuit = currentCard.suit;
            } else {
                inColor = detectedSuit.equals(currentCard.suit);
            }
            i++;
        }
        return inColor;
    }

    override fun compareTo(other: PokerHand): Int {
        val thisHandRank = getRank();
        val otherHandRank = other.getRank();
        if (!thisHandRank.first.equals(otherHandRank.first)) {
            return thisHandRank.first.compareTo(otherHandRank.first);
        } else {
            // Same rank hands
            return compareArrays(thisHandRank.second, otherHandRank.second);
        }
    }

    private fun compareArrays(first:Array<Card>, second:Array<Card>):Int {
        if ((first.size == 0) && (second.size == 0)) {
            return 0;
        } else {
            val comparation = first[0].compareTo(second[0]);
            if (comparation == 0) {
                return compareArrays(first.toMutableList().subList(1, first.size).toTypedArray(),
                                     second.toMutableList().subList(1, second.size).toTypedArray());
            } else {
                return comparation;
            }
        }
    }
}