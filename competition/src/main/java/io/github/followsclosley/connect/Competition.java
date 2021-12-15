package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta;
import io.github.followsclosley.connect.ai.score.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.jaron.connect.JaronBot;
import io.github.lane.LaneAI;
import io.github.ryanp102694.connect.MonteCarloAI;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Competition {

    int numberOfSimulations = 1000;
    private final List<ArtificialIntelligence> ais = new ArrayList<>();

    public static void main(String[] args) {
        new Competition()
                .add(new Dummy(1))
                .add(new JaronBot(2))
                .add(new ScoreStrategy(3))
                .add(new LaneAI(4, 5))
                .add(new MonteCarloAI(5))
                .add(new MiniMaxWithAlphaBeta(6, 7)
                        .setTimeout(1, TimeUnit.SECONDS)
                )
                .run();
    }

    public Competition add(ArtificialIntelligence ai) {
        ais.add(new ArtificialIntelligenceDecorator(ai));
        return this;
    }

    public void run() {
        int size = ais.size();
        Match[][] matches = new Match[ais.size()][ais.size()];

        for (int x = 0; x < size; x++) {
            ArtificialIntelligence player1 = ais.get(x);
            for (int y = 0; y < size; y++) {

                ArtificialIntelligence player2 = ais.get(y);


                if (x != y) {
                    matches[x][y] = new Match(player1, player2);
                    System.out.println(player1 + " vs. " + player2);
                    matches[x][y].run(numberOfSimulations);
                }
            }
        }

        printWithVelocity(matches);
    }

    private void printWithVelocity(Match[][] matches) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        VelocityContext context = new VelocityContext();
        context.put("matches", matches);
        context.put("ais", ais);

        Template t = velocityEngine.getTemplate("./competition/src/main/java/index.vm");

        StringWriter writer = new StringWriter();
        t.merge(context, writer);

        System.out.println(writer);
    }
}