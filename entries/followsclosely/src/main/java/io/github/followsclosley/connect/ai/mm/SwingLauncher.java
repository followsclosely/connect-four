package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {

        MutableBoard board = new MutableBoard();

        new SwingSupport()
                .setBoard(board)
                //.setArtificialIntelligence(new Dummy(SwingSupport.COMPUTER_COLOR))
                //.setArtificialIntelligence(new ScoreStrategy(SwingSupport.COMPUTER_COLOR))
                .setArtificialIntelligence(new MiniMaxWithAlphaBeta(7, 10))
                .run();
    }
}