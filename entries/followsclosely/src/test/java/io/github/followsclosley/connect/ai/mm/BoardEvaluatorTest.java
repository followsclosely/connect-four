package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.ai.ConnectTestUtils;
import io.github.followsclosley.connect.impl.MutableBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardEvaluatorTest {

    public void testScoreVertical() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(5, 7, 4), "" +
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

    public void testScoreHorizontalPotentialWinRight() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 5, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "37377--");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(5, score);
    }

    public void testScoreHorizontalPotentialWinLeft() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 5, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "3--773-");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(5, score);
    }

    public void testScoreHorizontalPotentialWinLeftAndRight() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 5, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "3--77-3" +
                "3--7733");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(14, score);
    }

    public void testScoreHorizontalPotentialOnTwoRows() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 5, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "3--77-3");

        int score = new BoardEvaluator().scoreHorizontal(board, 7);
        assertEquals(9, score);
    }

    public void testScoreForwardDiagonal() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 7, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "----7--" +
                "---77--" +
                "---33-7");

        int score = new BoardEvaluator().scoreForwardDiagonal(board, 7);
        assertEquals(19, score);
    }

    public void testScoreBackwardsDiagonal() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 7, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "-------" +
                "--7----" +
                "--77---" +
                "7-33---");

        int score = new BoardEvaluator().scoreBackwardsDiagonal(board, 7);
        assertEquals(5, score);
    }

    public void testScoreBackwardsDiagonal2() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 7, 4), "" +
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

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(7, 7, 4), "" +
                "-------" +
                "-------" +
                "-------" +
                "--7----" +
                "--37---" +
                "--37---" +
                "7737-7");

        int score = new BoardEvaluator().scoreBackwardsDiagonal(board, 7);
        assertEquals(7, score);
    }

}