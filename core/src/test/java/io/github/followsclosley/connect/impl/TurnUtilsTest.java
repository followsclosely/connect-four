package io.github.followsclosley.connect.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TurnUtilsTest {

    @Test
    public void yourTurnFindHorizontalWin() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(0, 7);
        board.dropPiece(1, 7);
        board.dropPiece(2, 7);
        board.dropPiece(3, 7);

        Turn turn = TurnUtils.getConnections(board);
        assertTrue(turn.hasWinningLine(4));
    }

    @Test
    public void yourTurnFindVerticalWin() {

        MutableBoard board = new MutableBoard();
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);

        Turn turn = TurnUtils.getConnections(board);
        assertTrue(turn.hasWinningLine(4));
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

        Turn turn = TurnUtils.getConnections(board);
        assertTrue(turn.hasWinningLine(4));
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

        Turn turn = TurnUtils.getConnections(board);
        assertTrue(turn.hasWinningLine(4));
    }
}