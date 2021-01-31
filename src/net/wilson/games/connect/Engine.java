package net.wilson.games.connect;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    List<ArtificialIntelligence> players = new ArrayList<>();
    BoardBasic board = new BoardBasic();

    public Engine(ArtificialIntelligence ai1, ArtificialIntelligence ai2){
        players.add(ai1);
        players.add(ai2);
    }

    public void startGame()
    {
        System.out.println(board.toMatrixString());

        int total = board.getWidth() * board.getHeight();
        for(int turn = 0; turn<total; turn++){
            ArtificialIntelligence player = players.get(turn%2);
            int x = player.yourTurn(board);
            if( x >= 0 && x < board.getWidth() ) {
                int color = (turn%2) + 1;
                int y = board.dropPiece(x, color);
                System.out.println(board);
                if ( getWinner(x,y) )
                {
                    return;
                }
            }
        }
    }

    public boolean getWinner(int x, int y) {

        boolean winner = false;

        //int x = last[0], y = last[1];
        int color = board.getPiece(x,y);

        // Horizontal First
        int horizontalCount = 1;
        for (int i=1; i<4 && x-i >= 0 && board.getPiece(x-i,y) == color; i++, horizontalCount++);
        for (int i=1; i<4 && x+i < board.getWidth() && board.getPiece(x+i,y) == color; i++, horizontalCount++);
        if( horizontalCount >= 4) {
            winner = true;
            System.out.println("We have a horizontal winner ("+color+") ["+x+","+y+"]!");
        }

        // Vertical
        int verticalCount = 1;
        for (int i=1; i<4 && y-i >= 0 && board.getPiece(x,y-i) == color; i++, verticalCount++);
        for (int i=1; i<4 && y+i < board.getHeight() && board.getPiece(x,y+i) == color; i++, verticalCount++);
        if( verticalCount >= 4) {
            winner = true;
            System.out.println("We have a vertical winner ("+color+") ["+x+","+y+"]!");
        }

        // Forward Slash Diagonal /
        int forwardSlashCount = 1;
        for (int i=1; i<4 && y+i < board.getHeight() && x-i >= 0         && board.getPiece(x-i,y+i) == color; i++, forwardSlashCount++);
        for (int i=1; i<4 && y-i >=0           && x+i < board.getWidth() && board.getPiece(x+i,y-i) == color; i++, forwardSlashCount++);
        if( forwardSlashCount >= 4) {
            winner = true;
            System.out.println("We have a / winner ("+color+") ["+x+","+y+"]!");
        }

        //Back Slash Diagonal \
        int backSlashCount = 1;
        for (int i=1; i<4 && y-i >= 0          && x-i >= 0         && board.getPiece(x-i,y-i) == color; i++, backSlashCount++);
        for (int i=1; i<4 && y+i < board.getHeight() && x+i < board.getWidth() && board.getPiece(x+i,y+i) == color; i++, backSlashCount++);
        if( backSlashCount >= 4) {
            winner = true;
            System.out.println("We have a \\ winner ("+color+") ["+x+","+y+"]!");
        }

        return winner;
    }
}