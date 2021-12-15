package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.swing.SwingSupport;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {

        new SwingSupport()
                .setArtificialIntelligence(new MiniMaxWithAlphaBeta(SwingSupport.COMPUTER_COLOR, 7))
                .run();
    }
}