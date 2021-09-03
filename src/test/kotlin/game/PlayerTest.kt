package game

import org.bastanchu.pokerchu.core.Card
import org.bastanchu.pokerchu.game.Player
import org.junit.Assert.*;
import org.junit.Test
import java.math.BigDecimal

class PlayerTest {

    class PlayerTestClass(bag: BigDecimal, id: String) : Player(bag, id) {

    }

    @Test
    fun shouldRequireBlindBetCorrectly() {
        var player = PlayerTestClass(BigDecimal(10.0), "PLAYER1");
        val status = player.requireBlind(BigDecimal(2.0));
        assertTrue(status);
        assertEquals(BigDecimal(8.0), player.bag);
    }

    @Test
    fun shouldRequireBlindBetWithoutEnoughBagCorrectly() {
        var player = PlayerTestClass(BigDecimal(1.0), "PLAYER1");
        val status = player.requireBlind(BigDecimal(2.0));
        assertFalse(status);
    }

    @Test
    fun shouldNotifyPrivateCardsCorrectly() {
        var player = PlayerTestClass(BigDecimal(1.0), "PLAYER1");
        player.receivePrivateCards(Card(Card.Suit.HEARTS, Card.Rank.ACE), Card(Card.Suit.CLUBS, Card.Rank.ACE));
        assertEquals(Card(Card.Suit.HEARTS, Card.Rank.ACE), player.card1);
        assertEquals(Card(Card.Suit.CLUBS, Card.Rank.ACE), player.card2);
    }

}