package game

import org.bastanchu.pokerchu.game.Player
import org.bastanchu.pokerchu.game.Pot
import org.junit.Test
import java.math.BigDecimal

import org.junit.Assert.*;

class PotTest {

    class PotTestPlayer(bag: BigDecimal, id: String) : Player(bag, id) {

    }

    // makeBet tests

    @Test(expected = RuntimeException::class)
    fun shouldThrowAnExceptionWhenAddingABetForAnUnregisteredPlayer() {
        val registeredPlayer = PotTestPlayer(BigDecimal(10.0), "PLAYER1");
        val unregisteredPlayer = PotTestPlayer(BigDecimal(15.0),"PLAYER2");
        var pot = Pot(arrayOf(registeredPlayer));
        pot.makeBet(unregisteredPlayer, BigDecimal(2.0));
    }

    @Test(expected = RuntimeException::class)
    fun shouldThrowAnExceptionWhenAddingABetOverPlayersBagBudget() {
        val registeredPlayer = PotTestPlayer(BigDecimal(10.0), "PLAYER1");
        var pot = Pot(arrayOf(registeredPlayer));
        pot.makeBet(registeredPlayer, BigDecimal(12.0));
    }

    @Test
    fun shouldMakeABetCorrectlyAdjustingPlayersBag() {
        val registeredPlayer = PotTestPlayer(BigDecimal(10.0), "PLAYER1");
        var pot = Pot(arrayOf(registeredPlayer));
        pot.makeBet(registeredPlayer, BigDecimal(7.0));
        val playerPotStatus = pot.potBets.get(registeredPlayer.id);
        if (playerPotStatus != null) {
            assertEquals(BigDecimal(7.0), playerPotStatus.bets.get(0)); // Bet registered
            assertEquals(BigDecimal(3.0), registeredPlayer.bag);// Player's bet discounted from player's bag
        } else {
            throw RuntimeException("Player not registered " +registeredPlayer.id);
        }
    }

    // getPlayerBet tests

    @Test
    fun shouldGetZeroBetForUnregisteredPlayers() {
        val registeredPlayer = PotTestPlayer(BigDecimal(10.0), "PLAYER1");
        val unregisteredPlayer = PotTestPlayer(BigDecimal(15.0),"PLAYER2");
        var pot = Pot(arrayOf(registeredPlayer));
        val bet = pot.getPlayerBet(unregisteredPlayer);
        assertEquals(BigDecimal(0), bet);
    }

    @Test
    fun shouldGetZeroBetForAPlayerThatHasMadeNoBets() {
        val registeredPlayer = PotTestPlayer(BigDecimal(10.0), "PLAYER1");
        var pot = Pot(arrayOf(registeredPlayer));
        val bet = pot.getPlayerBet(registeredPlayer);
        assertEquals(BigDecimal(0), bet);
    }

    @Test
    fun shouldGetTotalBetForAPlayerThatHasMadeSeveralBets() {
        val registeredPlayer = PotTestPlayer(BigDecimal(10.0), "PLAYER1");
        var pot = Pot(arrayOf(registeredPlayer));
        pot.makeBet(registeredPlayer, BigDecimal(2.0));
        pot.makeBet(registeredPlayer, BigDecimal(3.0));
        assertEquals(BigDecimal(5), pot.getPlayerBet(registeredPlayer));
    }

    @Test
    fun shouldGetTheHighestBetCorrectly() {
        val registeredPlayer1 = PotTestPlayer(BigDecimal(20.0), "PLAYER1");
        val registeredPlayer2 = PotTestPlayer(BigDecimal(25.0), "PLAYER2");
        var pot = Pot(arrayOf(registeredPlayer1, registeredPlayer2));
        pot.makeBet(registeredPlayer1, BigDecimal(5));
        pot.makeBet(registeredPlayer2, BigDecimal(6));
        pot.makeBet(registeredPlayer1, BigDecimal(3));
        pot.makeBet(registeredPlayer2, BigDecimal(4));
        assertEquals(BigDecimal(10), pot.getHighestBet());
    }
}