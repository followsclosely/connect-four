package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.MiniMaxAlgorithm;
import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.ryanp102694.connect.MonteCarloAI;

public class Main {
    public static void main(String[] args) {
        new Simulation()
                .number(100)
                .addArtificialIntelligence(new MiniMaxAlgorithm(1, 4))
                .addArtificialIntelligence(new Dummy(2))
                .run()
                .printSummary();
    }
}
