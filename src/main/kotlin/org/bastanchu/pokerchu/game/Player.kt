package org.bastanchu.pokerchu.game

import org.bastanchu.pokerchu.core.Card
import java.math.BigDecimal

abstract class Player(var bag: BigDecimal, val id:String) {

    var card1: Card? = null;
    var card2: Card? = null;

    enum class Action{FOLD, // Retire
                      CHECK, // Keep on game, no changes in bet made
                      BET, // Make a bet or raise current bet
                      CALL, // Keep on game, equal current highest bet
                      ALL_IN // Put all bag in game
    };

    /**
     * Receive blind bet requirement from Croupier
     *
     * @param amount Amount required to start game as blind player
     * @return <code>true</code> if the amount is available on hand or <code>false</code>
     *         if not possible to sastify the bet.
     */
    public fun requireBlind(amount: BigDecimal):Boolean {
        if (bag.toDouble() >= amount.toDouble()) {
            bag = bag.plus(amount.negate());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Notify preflop round cards
     *
     * @param card1 First private card
     * @param card2 Second private card
     *
     */
    public fun receivePrivateCards(card1:Card, card2:Card) {
        this.card1 = card1;
        this.card2 = card2;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}