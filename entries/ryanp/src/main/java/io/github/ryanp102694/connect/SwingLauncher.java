package io.github.ryanp102694.connect;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                .setBoard(new MutableBoard())
                .setArtificialIntelligence(new MonteCarloAI(SwingSupport.COMPUTER_COLOR))
                .run();
    }
}