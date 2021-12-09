package io.github.followsclosley.connect.ai.score;

import io.github.followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {

        new SwingSupport()
                .setBoard(new MutableBoard())
                .setArtificialIntelligence(new ScoreStrategy(SwingSupport.COMPUTER_COLOR))
                .run();
    }
}