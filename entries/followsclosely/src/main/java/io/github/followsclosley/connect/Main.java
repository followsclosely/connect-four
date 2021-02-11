package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class Main {
    public static void main(String[] args) {
        new Simulation()
                .number(10)
                .addArtificialIntelligence(new ScoreStrategy(2, 1))
                //.addArtificialIntelligence(new MiniMaxAlgorithm(2, 1, 2))
                .addArtificialIntelligence(new Dummy(1))
                .run()
                .printSummary();
    }
}
