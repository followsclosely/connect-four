package io.github.followsclosley.competition;

public interface AbstractMatch {
    String getName();

    Float getNumberOfGames();

    Integer getWins();

    Integer getWinsOrTies();
}