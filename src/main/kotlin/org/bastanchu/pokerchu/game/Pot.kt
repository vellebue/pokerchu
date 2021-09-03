package org.bastanchu.pokerchu.game

import java.lang.RuntimeException
import java.math.BigDecimal

class Pot(val players:Array<Player>) {

    var potBets:MutableMap<String, PlayerPotStatus> = HashMap<String, PlayerPotStatus>();

    init {
        for (i in 0..players.lastIndex) {
            val player = players[i];
            potBets.put(player.id, PlayerPotStatus());
        }
    }

    class PlayerPotStatus() {
        var alive: Boolean = true;
        var bets: MutableList<BigDecimal> = ArrayList<BigDecimal>();
    }

    /**
     * Make a bet in this pot for a registered player.
     * @param player The player who makes the bet.
     * @param amount The amount of the bet
     * @throws RuntimeException If the player is not registered or if there is not enough amount in player's bag to satisfy the bet.
     *
     */
    public fun makeBet(player: Player, amount: BigDecimal) {
        var playerStatus:PlayerPotStatus? = potBets.get(player.id);
        if (playerStatus == null) {
            throw RuntimeException("Player id: " + player.id + " not registered in pot.");
        } else {
            if (amount.toDouble() > player.bag.toDouble()) {
                throw RuntimeException("Player id: " + player.id + " amount: " + amount + " has exceeded current player's bag: " + player.bag);
            } else {
                playerStatus.bets.add(amount);
                player.bag = player.bag.minus(amount);
            }
        }
    }

    /**
     * Gets player's cumulate bet.
     * @param player Player whose bet is queried.
     * @return Player's current total bet.
     */
    public fun getPlayerBet(player: Player):BigDecimal {
        val playerPotStatus = potBets.get(player.id);
        if (playerPotStatus == null) {
            return BigDecimal.ZERO;
        } else {
            var amount = BigDecimal.ZERO;
            for (i in 0..playerPotStatus.bets.lastIndex) {
                val partialBet = playerPotStatus.bets.get(i);
                amount = amount.plus(partialBet);
            }
            return amount;
        }
    }

    /**
     * Gets current highest bet in the pot.
     * @return The currecnt highest bet or zero if there are no bets registered in the pot.
     */
    public fun getHighestBet():BigDecimal {
        var highestBet = BigDecimal.ZERO;
        for (playerId in potBets.keys) {
            val player = players.filter { it.id.equals(playerId) }.first();
            val playerBet = getPlayerBet(player);
            if (playerBet.toDouble() > highestBet.toDouble()) {
                highestBet = playerBet;
            }
        }
        return highestBet;
    }
}