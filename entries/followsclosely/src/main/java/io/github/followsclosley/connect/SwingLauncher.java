package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.MinMax;
import io.github.followsclosley.connect.ai.MiniMaxAlgorithm;
import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {

        MutableBoard board = new MutableBoard();

//        board.dropPiece(3,2);
//        board.dropPiece(3,1);
//        board.dropPiece(3,2);
//        board.dropPiece(3,1);
//        board.dropPiece(3,2);
//        board.dropPiece(2,1);
//        board.dropPiece(3,2);
//        board.dropPiece(2,1);
//        board.dropPiece(2,2);
//        board.dropPiece(5,1);
//        board.dropPiece(2,2);
//        board.dropPiece(5,1);
//        board.dropPiece(5,2);
//        board.dropPiece(2,1);
//        board.dropPiece(5,2);
//        board.dropPiece(2,1);
//        board.dropPiece(5,2);
//        board.dropPiece(5,1);
//        board.dropPiece(1,2);
//        board.dropPiece(1,1);
//        board.dropPiece(1,2);
//        board.dropPiece(6,1);
//        board.dropPiece(1,2);
//        board.dropPiece(1,1);
//        board.dropPiece(6,2);
//        board.dropPiece(1,1);
//        board.dropPiece(6,2);
//        board.dropPiece(6,1);
//        board.dropPiece(6,2);
//        board.dropPiece(4,1);
//        board.dropPiece(4,2);
//        board.dropPiece(4,1);

        new SwingSupport()
                .setBoard(board)
                //.setArtificialIntelligence(new Dummy(SwingSupport.COMPUTER_COLOR))
                //.setArtificialIntelligence(new ScoreStrategy(SwingSupport.COMPUTER_COLOR))
                .setArtificialIntelligence(new MinMax(SwingSupport.COMPUTER_COLOR, 7))
                .run();
    }
}