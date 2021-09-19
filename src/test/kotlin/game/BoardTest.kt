package game

import org.bastanchu.pokerchu.core.Card
import org.bastanchu.pokerchu.game.Board
import org.bastanchu.pokerchu.game.Player
import org.bastanchu.pokerchu.poker.PokerHand
import org.junit.Test
import java.math.BigDecimal

import org.junit.Assert.*;
import java.lang.RuntimeException

class BoardTest {

    class BoardTestPlayer(bag: BigDecimal, id: String) : Player(bag, id) {

    }

    @Test
    fun shouldInitBoardWithTwoPlayersCorrectly() {
        val player1 = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        val player2 = BoardTestPlayer(BigDecimal(20), "PLAYER2");
        var board = Board();
        board.initBoard((arrayOf(player1, player2)));
        val playersInBoard = board.players?.asList() ?: ArrayList<Player>();
        assertTrue(playersInBoard.contains(player1));
        assertTrue(playersInBoard.contains(player2));
        assertEquals(1, board.pots.size);
        val pot = board.pots.get(0);
        assertEquals(BigDecimal.ZERO, pot.getHighestBet());
    }

    @Test
    fun shouldResetBoardCorrectly() {
        val player1 = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        val player2 = BoardTestPlayer(BigDecimal(20), "PLAYER2");
        var board = Board();
        board.initBoard((arrayOf(player1, player2)));
        board.resetBoard();
        assertEquals(0, board.pots.size);
        assertNull(board.players);
        assertEquals(0, board.commonCards.size);
    }

    // Best player poker hand

    @Test
    fun shouldReturnEmptyBestHandBeforeGivingCards() {
        val player = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        var board = Board();
        board.initBoard(arrayOf(player));
        assertNull(board.getBestHand(player));
    }

    @Test
    fun shouldReturnEmptyBestHandOnPreflopRound() {
        val player = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        var board = Board();
        player.card1 = Card(Card.Suit.DIAMONDS, Card.Rank.N2);
        player.card2 = Card(Card.Suit.DIAMONDS, Card.Rank.N3);
        board.initBoard(arrayOf(player));
        assertNull(board.getBestHand(player));
    }

    @Test
    fun shouldIdentifyADoublePairOnFlopRound() {
        val player = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        var board = Board();
        player.card1 = Card(Card.Suit.DIAMONDS, Card.Rank.N2);
        player.card2 = Card(Card.Suit.DIAMONDS, Card.Rank.N3);
        board.initBoard(arrayOf(player));
        board.commonCards.add(Card(Card.Suit.CLUBS, Card.Rank.N3));
        board.commonCards.add(Card(Card.Suit.SPADES, Card.Rank.ACE));
        board.commonCards.add(Card(Card.Suit.SPADES, Card.Rank.N2));
        val pokerBestHand = board.getBestHand(player);
        val pokerBestHandRank = pokerBestHand?.getRank();
        if (pokerBestHandRank != null) {
            assertEquals(PokerHand.Rank.DOUBLE_PAIR, pokerBestHandRank.first);
            assertEquals(Card.Rank.N3, pokerBestHandRank.second.get(0).rank);
            assertEquals(Card.Rank.N2, pokerBestHandRank.second.get(2).rank);
        } else {
            throw RuntimeException("Null hand");
        }
    }

    @Test
    fun shouldIdentifyATripleOn4thLaneRound() {
        val player = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        var board = Board();
        player.card1 = Card(Card.Suit.DIAMONDS, Card.Rank.N2);
        player.card2 = Card(Card.Suit.DIAMONDS, Card.Rank.N3);
        board.initBoard(arrayOf(player));
        board.commonCards.add(Card(Card.Suit.CLUBS, Card.Rank.N3));
        board.commonCards.add(Card(Card.Suit.SPADES, Card.Rank.ACE));
        board.commonCards.add(Card(Card.Suit.SPADES, Card.Rank.KING));
        board.commonCards.add(Card(Card.Suit.HEARTS, Card.Rank.N3));
        val pokerBestHand = board.getBestHand(player);
        val pokerBestHandRank = pokerBestHand?.getRank();
        if (pokerBestHandRank != null) {
            assertEquals(PokerHand.Rank.TRIPLE, pokerBestHandRank.first);
            assertEquals(Card.Rank.N3, pokerBestHandRank.second.get(0).rank);
        } else {
            throw RuntimeException("Null hand");
        }
    }

    @Test
    fun shouldIdentifyAColorOn5thLaneRound() {
        val player = BoardTestPlayer(BigDecimal(20), "PLAYER1");
        var board = Board();
        player.card1 = Card(Card.Suit.DIAMONDS, Card.Rank.N2);
        player.card2 = Card(Card.Suit.DIAMONDS, Card.Rank.N3);
        board.initBoard(arrayOf(player));
        board.commonCards.add(Card(Card.Suit.CLUBS, Card.Rank.N3));
        board.commonCards.add(Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        board.commonCards.add(Card(Card.Suit.CLUBS, Card.Rank.N4));
        board.commonCards.add(Card(Card.Suit.DIAMONDS, Card.Rank.N10));
        board.commonCards.add(Card(Card.Suit.DIAMONDS, Card.Rank.N8));
        val pokerBestHand = board.getBestHand(player);
        val pokerBestHandRank = pokerBestHand?.getRank();
        if (pokerBestHandRank != null) {
            assertEquals(PokerHand.Rank.COLOR, pokerBestHandRank.first);
            assertEquals(Card.Rank.ACE, pokerBestHandRank.second.get(0).rank);
        } else {
            throw RuntimeException("Null hand");
        }
    }
}