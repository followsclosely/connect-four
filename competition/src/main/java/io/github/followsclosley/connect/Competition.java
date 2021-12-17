package io.github.followsclosley.connect;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import io.github.followsclosley.competition.AbstractCompetition;
import io.github.followsclosley.competition.AbstractMatch;
import io.github.followsclosley.connect.ai.mm.MiniMaxWithAlphaBeta;
import io.github.followsclosley.connect.ai.score.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.ArtificialIntelligenceDecorator;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.jaron.connect.JaronBot;
import io.github.lane.LaneAI;
import io.github.ryanp102694.connect.MonteCarloAI;

import java.util.concurrent.TimeUnit;

public class Competition extends AbstractCompetition<ArtificialIntelligence> {

    public static void main(String[] args) {
        new Competition()
                .simulations(1000)
                .add(new Dummy(1))
                .add(new JaronBot(2))
                .add(new ScoreStrategy(3))
                .add(new LaneAI(4, 5))
                .add(new MonteCarloAI(5))
                .add(new MiniMaxWithAlphaBeta(6, 5)
                        .setTimeout(1, TimeUnit.SECONDS)
                )
                .run()
                .printSummary()
                .printMetrics();
    }

    @Override
    public AbstractCompetition<ArtificialIntelligence> add(ArtificialIntelligence ai) {
        return super.add(new ArtificialIntelligenceTimer(ai));
    }

    @Override
    public AbstractMatch run(ArtificialIntelligence ai1, ArtificialIntelligence ai2, int numberOfSimulations) {
        return new Match(ai1, ai2).run(numberOfSimulations);
    }

    public class ArtificialIntelligenceTimer extends ArtificialIntelligenceDecorator {

        private final Timer timer;

        public ArtificialIntelligenceTimer(ArtificialIntelligence parent) {
            super(parent);
            this.timer = registry.timer(MetricRegistry.name(Competition.class, parent.getName()));
        }

        @Override
        public int yourTurn(Board board) {
            try (final Timer.Context c = timer.time()) {
                return parent.yourTurn(board);
            }
        }
    }
}
