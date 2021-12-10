package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.ai.score.grader.BoardGrader;
import io.github.followsclosley.connect.impl.MutableBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardGraderTest {

    @Test
    public void testThreePiecesOnBottomRow() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(2, 2);
        board.dropPiece(3, 2);
        board.dropPiece(4, 2);

        int score = new BoardGrader().grade(board, 2, 7);
        System.out.println(score);

        assertTrue(score > 0);
    }

    @Test
    public void testThreePiecesOnBottomRowWithOppositeColorOnTop() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(2, 2);
        board.dropPiece(3, 2);
        board.dropPiece(4, 2);

        board.dropPiece(2, 1);
        board.dropPiece(3, 1);
        board.dropPiece(4, 1);

        int score = new BoardGrader().grade(board, 1, 2);


        assertTrue(score > 0);
    }

    @Test
    public void testThreePiecesOnBottomRowWithTwoRowsOfOppositeColorOnTop() {
        MutableBoard board = new MutableBoard();
        board.dropPiece(2, 2);
        board.dropPiece(3, 2);
        board.dropPiece(4, 2);

        board.dropPiece(2, 1);
        board.dropPiece(3, 1);
        board.dropPiece(4, 1);

        board.dropPiece(2, 1);
        board.dropPiece(3, 1);
        board.dropPiece(4, 1);

        int score = new BoardGrader().grade(board, 1, 2);

        assertTrue(score > 0);
    }

    @Test
    public void testWorthlessPieces() {
        MutableBoard board = new MutableBoard();

        for (int i = 0; i < 6; i++) {
            board.dropPiece(3, 2);
        }

        for (int i = 0; i < 3; i++) {
            board.dropPiece(1, 1);
            board.dropPiece(2, 1);
        }

        board.dropPiece(1, 2);
        board.dropPiece(2, 2);

        int score = new BoardGrader().grade(board, 1, 7);

        assertEquals(0, score);
    }
}