package net.wilson.games.connect;

import net.wilson.games.connect.ai.MiniMaxAlgorithm;
import net.wilson.games.connect.ai.ScoreStrategy;
import net.wilson.games.connect.impl.ai.Dummy;

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
