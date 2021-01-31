package net.wilson.games.connect;

import java.util.ArrayList;
import java.util.List;

/**
 * This class runs a game using ArtificialIntelligence to play the pieces.
 */
public class Engine {

    // The number of "in a row" needed to win.
    int goal = 4;

    // The number of players. This is set in teh constuctor.
    int playerCount;

    // A List of players.
    private List<ArtificialIntelligence> players = new ArrayList<>();

    // The state of the game is held in the Board.
    private BoardBasic board = new BoardBasic(7,6);

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
            int x = player.yourTurn(board);
            if( x >= 0 && x < board.getWidth() ) {
                int color = (turn%playerCount) + 1;
                int y = board.dropPiece(x, color);
                System.out.println(board);
                if ( getWinner(x,y) >= 0 )
                {
                    return;
                }
            }
        }
    }

    /**
     * Determines if the piece at x,y just caused the game to end.
     *
     * todo: It would be nice if this method would return back a list of coordinates if there is a winner.
     *
     * @param x The x value of the piece in the board.
     * @param y The y value of the piece in the board.
     * @return -1 if no winner found, else returns the winning index/color
     */
    public int getWinner(int x, int y) {


        boolean winner = false;

        //int x = last[0], y = last[1];
        int color = board.getPiece(x,y);

        // Horizontal First
        int horizontalCount = 1;
        for (int i=1; i<goal && x-i >= 0 && board.getPiece(x-i,y) == color; i++, horizontalCount++);
        for (int i=1; i<goal && x+i < board.getWidth() && board.getPiece(x+i,y) == color; i++, horizontalCount++);
        if( horizontalCount >= goal) {
            winner = true;
            System.out.println("We have a horizontal winner ("+color+") ["+x+","+y+"]!");
        }

        // Vertical
        int verticalCount = 1;
        for (int i=1; i<goal && y-i >= 0 && board.getPiece(x,y-i) == color; i++, verticalCount++);
        for (int i=1; i<goal && y+i < board.getHeight() && board.getPiece(x,y+i) == color; i++, verticalCount++);
        if( verticalCount >= goal) {
            winner = true;
            System.out.println("We have a vertical winner ("+color+") ["+x+","+y+"]!");
        }

        // Forward Slash Diagonal /
        int forwardSlashCount = 1;
        for (int i=1; i<goal && y+i < board.getHeight() && x-i >= 0         && board.getPiece(x-i,y+i) == color; i++, forwardSlashCount++);
        for (int i=1; i<goal && y-i >=0           && x+i < board.getWidth() && board.getPiece(x+i,y-i) == color; i++, forwardSlashCount++);
        if( forwardSlashCount >= goal) {
            winner = true;
            System.out.println("We have a / winner ("+color+") ["+x+","+y+"]!");
        }

        //Back Slash Diagonal \
        int backSlashCount = 1;
        for (int i=1; i<goal && y-i >= 0          && x-i >= 0         && board.getPiece(x-i,y-i) == color; i++, backSlashCount++);
        for (int i=1; i<goal && y+i < board.getHeight() && x+i < board.getWidth() && board.getPiece(x+i,y+i) == color; i++, backSlashCount++);
        if( backSlashCount >= goal) {
            winner = true;
            System.out.println("We have a \\ winner ("+color+") ["+x+","+y+"]!");
        }

        return (winner) ? color : -1;
    }
}