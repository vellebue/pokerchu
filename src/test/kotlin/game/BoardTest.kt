package game

import org.bastanchu.pokerchu.game.Board
import org.bastanchu.pokerchu.game.Player
import org.junit.Test
import java.math.BigDecimal

import org.junit.Assert.*;

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
}