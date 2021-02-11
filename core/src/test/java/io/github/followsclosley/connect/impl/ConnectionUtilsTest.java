package io.github.followsclosley.connect.impl;

import io.github.followsclosley.connect.Coordinate;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConnectionUtilsTest {

    @Test
    public void yourTurnFindHorizontalWin() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(0, 7);
        board.dropPiece(1, 7);
        board.dropPiece(2, 7);
        board.dropPiece(3, 7);

        Map<String, List<Coordinate>> connectFours = ConnectionUtils.getWinningConnections(board);
        assertEquals(1, connectFours.size());
        assertFalse(connectFours.get("Horizontal").isEmpty());
    }

    @Test
    public void yourTurnFindVerticalWin() {

        MutableBoard board = new MutableBoard();
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);

        Map<String, List<Coordinate>> connectFours = ConnectionUtils.getWinningConnections(board);
        assertEquals(1, connectFours.size());
        assertFalse(connectFours.get("Vertical").isEmpty());
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

        Map<String, List<Coordinate>> connectFours = ConnectionUtils.getWinningConnections(board);
        assertEquals(1, connectFours.size());
        assertFalse(connectFours.get("ForwardSlash").isEmpty());
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

        Map<String, List<Coordinate>> connectFours = ConnectionUtils.getWinningConnections(board);
        assertEquals(1, connectFours.size());
        assertFalse(connectFours.get("BackSlash").isEmpty());
    }
}