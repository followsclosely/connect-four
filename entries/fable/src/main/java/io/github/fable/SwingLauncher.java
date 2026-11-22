package io.github.fable;

import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.swing.SwingSupport;

public class SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                .setBoard(new MutableBoard())
                .setArtificialIntelligence(new FableAI(SwingSupport.COMPUTER_COLOR))
                .run();
    }
}
