package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                //.setBoard(new MutableBoard(7,6,4))
                //.setArtificialIntelligence(new Dummy(SwingSupport.COMPUTER_COLOR))
                .setArtificialIntelligence(new ScoreStrategy(SwingSupport.COMPUTER_COLOR, SwingSupport.PLAYER_COLOR))
                //.setArtificialIntelligence(new MiniMaxAlgorithm(SwingSupport.COMPUTER_COLOR, SwingSupport.PLAYER_COLOR, SwingSupport.COMPUTER_COLOR))
                .run();
    }
}