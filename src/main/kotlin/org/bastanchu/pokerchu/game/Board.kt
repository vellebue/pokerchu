package org.bastanchu.pokerchu.game

import org.bastanchu.pokerchu.core.Card
import org.bastanchu.pokerchu.poker.PokerHand
import java.lang.RuntimeException

class Board {

    var pots: MutableList<Pot>;
    var commonCards: MutableList<Card>;
    var players: Array<Player>?;

    init {
        pots = ArrayList<Pot>();
        commonCards = ArrayList<Card>();
        players = null;
    }

    public fun initBoard(players: Array<Player>) {
        pots.add(Pot(players));
        this.players = players;
    }

    public fun resetBoard() {
        pots = ArrayList<Pot>();
        commonCards = ArrayList<Card>();
        players = null;
    }

    /**
     * Gets the best hand playable given player cards and common cards
     * @param player A player to figure out which is her best poker hand.
     * @return The best poker hand available or null if there are no
     *         playable hands for now (player at pre-flop round or before).
     */
    public fun getBestHand(player:Player): PokerHand? {
        val card1 = player.card1;
        val card2 = player.card2;
        if ((card1 != null) && (card2 != null)) {
            if (commonCards.size < 3) {
                return null;
            } else if (commonCards.size == 3) {
                var pokerHandCards = ArrayList<Card>();
                pokerHandCards.add(card1);
                pokerHandCards.add(card2);
                pokerHandCards.addAll(commonCards);
                return PokerHand(pokerHandCards.toTypedArray());
            } else if (commonCards.size == 4) {
                return getBest4thLaneHand(card1, card2);
            } else if (commonCards.size == 5) {
                return getBest5thLaneHand(card1, card2);
            } else {
                throw RuntimeException("Invalid common cards number " +  commonCards.size);
            }
        } else {
            return null;
        }
    }

    // 6 combinations
    private fun getBest4thLaneHand(card1:Card, card2:Card):PokerHand {
        var pokerHandList = ArrayList<PokerHand>();
        // Two player cards and each three of four common cards (4 combinations)
        for (i in 0..3) {
            var currentPokerHandList = ArrayList<Card>();
            val cardToExclude = commonCards.get(i);
            for (j in 0..3) {
                val cardToInclude = commonCards.get(j);
                if (!cardToInclude.equals(cardToExclude)) {
                    currentPokerHandList.add(cardToInclude);
                }
            }
            currentPokerHandList.add(card1);
            currentPokerHandList.add(card2);
            pokerHandList.add(PokerHand(currentPokerHandList.toTypedArray()));
        }
        // Four common cards with first player card
        var currentPokerHandList1 = ArrayList<Card>();
        currentPokerHandList1.addAll(commonCards);
        currentPokerHandList1.add(card1);
        pokerHandList.add(PokerHand(currentPokerHandList1.toTypedArray()));
        // Four common cards with second player card
        var currentPokerHandList2 = ArrayList<Card>();
        currentPokerHandList2.addAll(commonCards);
        currentPokerHandList2.add(card2);
        pokerHandList.add(PokerHand(currentPokerHandList2.toTypedArray()));
        // Sort poker hands and return the best one
        pokerHandList.sort();
        return pokerHandList.get(pokerHandList.lastIndex);
    }

    // 21 combinations
    private fun getBest5thLaneHand(card1:Card, card2:Card):PokerHand {
        var pokerHandList = ArrayList<PokerHand>();
        // Two player cards and 3 of 5 common cards (10 combinations)
        var partialHandList = ArrayList<MutableList<Card>>();
        for (i in 0..4) {
            val cardToExclude1 = commonCards.get(i);
            for (j in i..4) {
                val cardToExclude2 = commonCards.get(j);
                if (!cardToExclude1.equals(cardToExclude2)) {
                    var currentPokerHandList = ArrayList<Card>();
                    for (k in 0..4) {
                        val cardToInclude = commonCards.get(k);
                        if (!cardToInclude.equals(cardToExclude1) &&
                            !cardToInclude.equals(cardToExclude2)) {
                            currentPokerHandList.add(cardToInclude);
                        }
                    }
                    partialHandList.add(currentPokerHandList);
                }
            }
        }
        partialHandList.forEach( {
            it.add(card1);
            it.add(card2);
            pokerHandList.add(PokerHand(it.toTypedArray()));
        });
        // Either first player card or second player card and 4 of 5 common cards (10 combinations)
        for (i in 0..4) {
            var currentPokerHandList1 = ArrayList<Card>();
            var currentPokerHandList2 = ArrayList<Card>();
            val cardToExclude = commonCards.get(i);
            for (j in 0..4) {
                val cardToInclude = commonCards.get(j);
                if (!cardToInclude.equals(cardToExclude)) {
                    currentPokerHandList1.add(cardToInclude);
                    currentPokerHandList2.add(cardToInclude);
                }
            }
            currentPokerHandList1.add(card1);
            currentPokerHandList2.add(card2);
            pokerHandList.add(PokerHand(currentPokerHandList1.toTypedArray()));
            pokerHandList.add(PokerHand(currentPokerHandList2.toTypedArray()));
        }
        // Combination of five common cards
        var fiveCommonCardsPokerHandList = ArrayList<Card>();
        fiveCommonCardsPokerHandList.addAll(commonCards);
        pokerHandList.add(PokerHand(fiveCommonCardsPokerHandList.toTypedArray()));
        // Sort poker hands and return the best one
        pokerHandList.sort();
        return pokerHandList.get(pokerHandList.lastIndex);
    }

}