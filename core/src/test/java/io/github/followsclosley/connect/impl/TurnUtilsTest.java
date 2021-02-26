package io.github.followsclosley.connect.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TurnUtilsTest {

    @Test
    public void yourTurnFindHorizontalWin() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(0, 7);
        board.dropPiece(1, 7);
        board.dropPiece(2, 7);
        board.dropPiece(3, 7);

        Turn turn = TurnUtils.getWinningConnections(board);
        assertEquals(1, turn.getLines().size());
    }

    @Test
    public void yourTurnFindVerticalWin() {

        MutableBoard board = new MutableBoard();
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);

        Turn turn = TurnUtils.getWinningConnections(board);
        assertEquals(1, turn.getLines().size());
    }

    @Test
    public void yourTurnFindDiagonalForwardSlashWin() {

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
        board.dropPiece(3, 7);

        Turn turn = TurnUtils.getWinningConnections(board);
        assertEquals(1, turn.getLines().size());
    }

    @Test
    public void yourTurnFindDiagonalBackSlashWin() {

        MutableBoard board = new MutableBoard();
        board.dropPiece(3, 7);

        board.dropPiece(2, 1);
        board.dropPiece(2, 7);

        board.dropPiece(1, 1);
        board.dropPiece(1, 1);
        board.dropPiece(1, 7);

        board.dropPiece(0, 1);
        board.dropPiece(0, 1);
        board.dropPiece(0, 1);
        board.dropPiece(0, 7);

        Turn turn = TurnUtils.getWinningConnections(board);
        assertEquals(1, turn.getLines().size());
    }
}