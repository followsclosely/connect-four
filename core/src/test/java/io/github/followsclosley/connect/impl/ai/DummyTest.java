package io.github.followsclosley.connect.impl.ai;

import io.github.followsclosley.connect.impl.MutableBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DummyTest {

    @Test
    public void yourTurnHasNoSpacesLeft() {
        Dummy dummy = new Dummy(7);
        MutableBoard board = new MutableBoard(3, 3, 3);

        //Fill the board.
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                board.dropPiece(x, 1);
            }
        }

        int x = dummy.yourTurn(board);
        assertEquals(-1, x);
    }

    @Test
    public void alwaysInBounds() {
        Dummy dummy = new Dummy(7);
        MutableBoard board = new MutableBoard(10, 10, 11);

        //Fill the board.
        for (int i = 0; i < 100; i++) {
            int x = dummy.yourTurn(board);
            assertTrue(x >= 0, "Value can not be less than zero.");
            assertTrue(x < 10, "Value can not be greater than ten.");
        }
    }
}