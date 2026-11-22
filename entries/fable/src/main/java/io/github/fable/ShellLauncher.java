package io.github.fable;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(200)
                .addArtificialIntelligence(new Dummy(1))
                .addArtificialIntelligence(new FableAI(2))
                .run()
                .printSummary();
    }
}
