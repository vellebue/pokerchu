package org.bastanchu.pokerchu.poker

import org.bastanchu.pokerchu.core.Card

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

    fun getRank():Pair<Rank, Card> {
        val equalsRangeCards = numEqualsRangeCard();
        if (equalsRangeCards.second == 4) {
            return Pair(Rank.POKER, equalsRangeCards.first);
        } else if (equalsRangeCards.second == 3) {
            return Pair(Rank.TRIPLE, equalsRangeCards.first);
        } else if (equalsRangeCards.second == 2) {
            return Pair(Rank.PAIR, equalsRangeCards.first);
        } else {
            return Pair(Rank.HIGHEST, equalsRangeCards.first);
        }
    }

    private fun numEqualsRangeCard():Pair<Card, Int> {
        val seeker = {card:Card ->
            var num = 1;
            for (i in 0..4) {
                if (!card.equals(cards[i])) {
                    if (card.rank == cards[i].rank) {
                        num++;
                    }
                }
            }
            num;
        };
        var highestRank = 1;
        var highestPair:Pair<Card, Int>? = null;
        for(i in 0..4) {
            val currRank = seeker(cards[i]);
            if (currRank > highestRank) {
                highestRank = currRank;
                highestPair = Pair(cards[i], highestRank);
            }
        }
        if (highestPair != null) {
            return highestPair;
        } else {
            return Pair(highestCard(), 1);
        }
    }

    private fun highestCard():Card {
        var highestCard:Card = cards[0];
        for(i in 1..4) {
            if (highestCard.rank < cards[i].rank) {
                highestCard = cards[i];
            }
        }
        return highestCard;
    }
}