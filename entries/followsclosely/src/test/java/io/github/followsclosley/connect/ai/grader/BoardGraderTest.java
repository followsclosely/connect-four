package io.github.followsclosley.connect.ai.grader;

import io.github.followsclosley.connect.ai.score.grader.BoardGrader;
import io.github.followsclosley.connect.impl.MutableBoard;
import junit.framework.TestCase;
import org.junit.Assert;

public class BoardGraderTest extends TestCase {

    public void testThreePiecesOnBottomRow(){
        MutableBoard board = new MutableBoard();
        board.dropPiece(2,2);
        board.dropPiece(3,2);
        board.dropPiece(4,2);

        int score = new BoardGrader().grade(board, 2, 7);
        System.out.println(score);

        Assert.assertTrue(score > 0);
    }

    public void testThreePiecesOnBottomRowWithOppositeColorOnTop(){
        MutableBoard board = new MutableBoard();
        board.dropPiece(2,2);
        board.dropPiece(3,2);
        board.dropPiece(4,2);

        board.dropPiece(2,1);
        board.dropPiece(3,1);
        board.dropPiece(4,1);

        int score = new BoardGrader().grade(board, 1, 2);


        Assert.assertTrue(score > 0);
    }

    public void testThreePiecesOnBottomRowWithTwoRowsOfOppositeColorOnTop(){
        MutableBoard board = new MutableBoard();
        board.dropPiece(2,2);
        board.dropPiece(3,2);
        board.dropPiece(4,2);

        board.dropPiece(2,1);
        board.dropPiece(3,1);
        board.dropPiece(4,1);

        board.dropPiece(2,1);
        board.dropPiece(3,1);
        board.dropPiece(4,1);

        int score = new BoardGrader().grade(board, 1, 2);

        Assert.assertTrue(score > 0);
    }

    public void testWorthlessPieces(){
        MutableBoard board = new MutableBoard();

        for(int i=0; i<6; i++) {
            board.dropPiece(3, 2);
        }

        for(int i=0; i<3; i++){
            board.dropPiece(1,1);
            board.dropPiece(2,1);
        }

        board.dropPiece(1,2);
        board.dropPiece(2,2);

        int score = new BoardGrader().grade(board, 1, 7);

        Assert.assertEquals(0, score);
    }
}