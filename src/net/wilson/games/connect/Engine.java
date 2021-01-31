package net.wilson.games.connect;

import net.wilson.games.common.Coordinate;
import net.wilson.games.connect.impl.ImmutableBoard;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class runs a game using ArtificialIntelligence to play the pieces.
 */
public class Engine {

    // The number of players. This is set in teh constuctor.
    int playerCount;

    // A List of players.
    private List<ArtificialIntelligence> players = new ArrayList<>();

    // The state of the game is held in the MutableBoard.
    private MutableBoard board = new MutableBoard(7,6, 4);

    /** Constructs and new Engine with a default board.
     * @param ais The players in the game. Can be 2-N.
     */
    public Engine(ArtificialIntelligence... ais){
        for(ArtificialIntelligence ai : ais){
            players.add(ai);
        }
        playerCount = players.size();
    }

    /**
     * Runs a simulation of one game.
     */
    public void startGame()
    {
        System.out.println(board.toMatrixString());

        int total = board.getWidth() * board.getHeight();
        for(int turn = 0; turn<total; turn++){
            ArtificialIntelligence player = players.get(turn%playerCount);
            int x = player.yourTurn(new ImmutableBoard(board));
            if( x >= 0 && x < board.getWidth() ) {
                int y = board.dropPiece(x, player.getColor());
                System.out.println(board);
                if ( board.getWinner(new Coordinate(x,y)) >= 0 )
                {
                    return;
                }
            }
        }
    }
}