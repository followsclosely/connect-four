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
                board.dropPiece(x, color);
                System.out.println(board);
                if ( board.getWinner() )
                {
                    return;
                }
            }
        }
    }
}