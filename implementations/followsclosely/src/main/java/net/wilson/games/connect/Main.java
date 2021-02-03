package net.wilson.games.connect;

import net.wilson.games.connect.ai.ScoreStrategy;

public class Main {
    public static void main(String[] args){
        new Simulation().number(2000).addArtificialIntelligence(new ScoreStrategy(7)).run().printSummary();
    }
}
