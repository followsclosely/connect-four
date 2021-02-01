package net.wilson.games.connect.impl;

import net.wilson.games.connect.Coordinate;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MutableBoardTest {

    @Test
    void yourTurnFindHorizontalWin() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(0, 7);
        board.dropPiece(1, 7);
        board.dropPiece(2, 7);
        board.dropPiece(3, 7);

        Map<String, List<Coordinate>> connectFours = board.getWinningConnections();
        assertEquals(1, connectFours.size());
        assertNotNull(connectFours.get("Horizontal").size());
    }

    @Test
    void yourTurnFindVerticalWin() {

        MutableBoard board = new MutableBoard();
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);
        board.dropPiece(3, 7);

        Map<String, List<Coordinate>> connectFours = board.getWinningConnections();
        assertEquals(1, connectFours.size());
        assertNotNull(connectFours.get("Vertical").size());
    }

    @Test
    void yourTurnFindDiagonalForwardSlashWin() {

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

        Map<String, List<Coordinate>> connectFours = board.getWinningConnections();
        assertEquals(1, connectFours.size());
        assertNotNull(connectFours.get("ForwardSlash"));
    }

    @Test
    void yourTurnFindDiagonalBackSlashWin() {

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

        Map<String, List<Coordinate>> connectFours = board.getWinningConnections();
        assertEquals(1, connectFours.size());
        assertNotNull(connectFours.get("BackSlash"));
    }
}