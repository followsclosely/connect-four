package io.github.followsclosley.competition;

public interface AbstractMatch {
    /**
     * Getter for the name of player #1, or the AI being tested.
     *
     * @return Human read-able name of this AI
     */
    String getName();

    /**
     * Getter for the number of games simulated.
     *
     * @return The number of games simulated
     */
    Number getNumberOfGames();

    /**
     * Getter for the number of games won.
     *
     * @return The number of games won
     */
    Integer getWins();

    /**
     * Getter for the number of games not lost.
     *
     * @return The number of games not lost
     */
    Integer getWinsOrTies();
}