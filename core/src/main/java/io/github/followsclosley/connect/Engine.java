package io.github.followsclosley.connect;

import io.github.followsclosley.connect.impl.ImmutableBoard;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class runs a game using ArtificialIntelligence to play the pieces.
 */
public class Engine {

    // The number of players. This is set in the constuctor.
    int playerCount;

    // A List of players.
    private List<ArtificialIntelligence> players = new ArrayList<>();

    // The state of the game is held in the MutableBoard.
    private MutableBoard board = new MutableBoard(7, 6, 4);

    /**
     * Constructs and new Engine with a default board.
     *
     * @param ais The players in the game. Can be 2-N.
     */
    public Engine(ArtificialIntelligence... ais) {
        for (ArtificialIntelligence ai : ais) {
            players.add(ai);
        }
        playerCount = players.size();
    }

    /**
     * Runs a simulation of one game.
     */
    public int startGame(int firstIndex) {

        int winner = -1;
        //System.out.println(board.toMatrixString());

        //TODO: This is a hack that needs to be cleaned up...
        ArtificialIntelligence ai0 = players.get(0);
        ArtificialIntelligence ai1 = players.get(1);
        ai0.initialize(ai1.getColor());
        ai1.initialize(ai0.getColor());

        //The total number of turns before the board is full
        int total = board.getWidth() * board.getHeight();
        for (int turn = firstIndex; turn < total; turn++) {
            ArtificialIntelligence player = players.get(turn % playerCount);

            //Pass an immutable board down as to not mess with this standard
            int x = player.yourTurn(new ImmutableBoard(board));

            int y = board.dropPiece(x, player.getColor());
            //System.out.println(board);

            //Check for a winner, print if found
            Turn turnDetails = TurnUtils.getConnections(board, new Coordinate(x, y), board.getPiece(x, y));

            if (turnDetails.hasWinningLine(board.getGoal())) {
                StringBuffer b = new StringBuffer();
                for (Turn.Line line : turnDetails.getLines()) {
                    Coordinate winningCoordinate = line.getConnected().get(0);
                    winner = board.getPiece(winningCoordinate.getX(), winningCoordinate.getY());
                    b.append(winner).append(" : ");
                    for (Coordinate coordinate : line.getConnected()) {
                        b.append(coordinate).append(" ");
                    }
                }
                //System.out.println(b);
                return winner;
            }
        }

        return winner;
    }

    public Board getBoard() { return board; }
}