package io.github.followsclosley.connect;

import io.github.followsclosley.competition.AbstractMatch;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Match implements AbstractMatch {

    private final ArtificialIntelligence[] ais;
    private float numberOfGames;
    private Map<Integer, AtomicInteger> counts;

    public Match(ArtificialIntelligence ai1, ArtificialIntelligence ai2) {
        ais = new ArtificialIntelligence[]{ai1, ai2};
    }

    public Match run(int numberOfGames) {
        this.numberOfGames = numberOfGames;
        this.counts = new Simulation()
                .number(numberOfGames)
                .addArtificialIntelligence(ais[0])
                .addArtificialIntelligence(ais[1])
                .run()
                .getCounts();
        return this;
    }

    @Override
    public Number getNumberOfGames() {
        return numberOfGames;
    }

    @Override
    public String getName() {
        return ais[0].getName();
    }

    @Override
    public Integer getWins() {
        try {
            return getWins(ais[0].getColor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getWinsOrTies() {
        try {
            Integer wins = getWins(ais[0].getColor());
            Integer ties = getWins(-1);
            return ((wins == null) ? 0 : wins) + ((ties == null) ? 0 : ties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getWins(Integer color) {
        AtomicInteger atomicInteger = (counts == null) ? null : counts.get(color);
        return (atomicInteger == null) ? 0 : atomicInteger.get();
    }
}