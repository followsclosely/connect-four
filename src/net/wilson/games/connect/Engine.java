package net.wilson.games.connect;

import net.wilson.games.connect.impl.ImmutableBoard;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class runs a game using ArtificialIntelligence to play the pieces.
 */
public class Engine {

    // The number of players. This is set in teh constuctor.
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
    public int startGame() {

        int winner = -1;
        //System.out.println(board.toMatrixString());

        //The total number of turns before the board is full
        int total = board.getWidth() * board.getHeight();
        for (int turn = 0; turn < total; turn++) {
            ArtificialIntelligence player = players.get(turn % playerCount);

            //Pass an immutable board down as to not mess with this standard
            int x = player.yourTurn(new ImmutableBoard(board));

            int y = board.dropPiece(x, player.getColor());
            System.out.println(board);

            //Check for a winner, print if found
            Map<String, List<Coordinate>> connections = board.getWinner(new Coordinate(x, y));
            if (!connections.isEmpty()) {
                StringBuffer b = new StringBuffer();
                for (Map.Entry<String, List<Coordinate>> entry : connections.entrySet()) {
                    Coordinate winningCoordinate = entry.getValue().get(0);
                    winner = board.getPiece(winningCoordinate.getX(), winningCoordinate.getY());
                    b.append(winner).append(" ").append(entry.getKey()).append(": ");
                    for (Coordinate coordinate : entry.getValue()) {
                        b.append(coordinate).append(" ");
                    }
                }
                System.out.println(b);
                return winner;
            }
        }

        return winner;
    }
}