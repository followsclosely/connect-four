package net.wilson.games.connect.impl.ai.followsclosely.strategies;

import net.wilson.games.connect.impl.MutableBoard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WinIfICanStrategyTest {

    @Test
    void yourTurnFindHorizontalWin() {
        WinIfICanStrategy strategy = new WinIfICanStrategy(7);
        MutableBoard board = new MutableBoard();
        board.dropPiece(0, 7);
        board.dropPiece(2, 7);
        board.dropPiece(3, 7);
        int x = strategy.yourTurn(board);
        assertEquals(1, x);
    }

    @Test
    void yourTurnFindVerticalWin() {
        WinIfICanStrategy strategy = new WinIfICanStrategy(7);
        MutableBoard board = new MutableBoard();
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        int x = strategy.yourTurn(board);
        assertEquals(3, x);
    }

    @Test
    void yourTurnFindDiagonalBackSlashWin() {
        WinIfICanStrategy strategy = new WinIfICanStrategy(7);
        MutableBoard board = new MutableBoard();
        board.dropPiece(0, 7);

        board.dropPiece(1, 1);
        board.dropPiece(1, 7);

        board.dropPiece(2, 1);
        board.dropPiece(2, 1);
        board.dropPiece(2, 7);

        board.dropPiece(3, 1);
        board.dropPiece(3, 1);
        board.dropPiece(3, 1);

        int x = strategy.yourTurn(board);
        assertEquals(3, x);
    }
}