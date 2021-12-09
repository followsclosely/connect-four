package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.impl.MutableBoard;
import org.junit.jupiter.api.Test;

import static io.github.followsclosley.connect.impl.MutableBoard.initialize;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardEvaluatorTest {

    @Test
    public void testScoreVertical() {

        MutableBoard board = initialize(new MutableBoard(5, 7, 4), "" +
                "-----" +
                "-----" +
                "-37--" +
                "-773-" +
                "-377-" +
                "-737-" +
                "33733");

        int score = new BoardEvaluator().scoreVertical(board, 7);
        assertEquals(7, score);
    }

    @Test
    public void testScoreHorizontalPotentialWinRight() {

        MutableBoard board = initialize(new MutableBoard(7, 5, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "37377--");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(5, score);
    }

    @Test
    public void testScoreHorizontalPotentialWinLeft() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "3--773-");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(5, score);
    }

    @Test
    public void testScoreHorizontalPotentialWinLeftAndRight() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "3--77-3" +
                "3--7733");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(24, score);
    }

    @Test
    public void testScoreHorizontalPotentialOnTwoRows() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "3--77-3");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(19, score);
    }

    @Test
    public void testScoreForwardDiagonal() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "----7--" +
                "---73--" +
                "---33--");

        int score = new BoardEvaluator().scoreForwardsDiagonal(board, 7);
        assertEquals(19, score);
    }

    @Test
    public void testScoreBackwardsDiagonal() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "---7---" +
                "---37--" +
                "---33--");

        int score = new BoardEvaluator().scoreBackwardsDiagonal(board, 7);
        assertEquals(19, score);
    }

    @Test
    public void testScoreBackwardsDiagonal2() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "--7----" +
                "--77---" +
                "7-337--");

        int score = new BoardEvaluator().scoreBackwardsDiagonal(board, 7);
        assertEquals(7, score);
    }

    @Test
    public void testScoreBackwardsDiagonal3() {

        MutableBoard board = initialize("" +
                "-------" +
                "-------" +
                "-------" +
                "--7----" +
                "--37---" +
                "--37---" +
                "7737-7");

        int score = new BoardEvaluator().scoreBackwardsDiagonal(board, 7);
        assertEquals(111, score);
    }

}