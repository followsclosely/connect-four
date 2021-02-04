package net.wilson.games.connect.ai;

import net.wilson.games.connect.impl.MutableBoard;

public class ConnectTestUtils {

    public static MutableBoard initialize(MutableBoard board, String config){

        int index = config.length() -1;
        for(int y= board.getHeight()-1; y>=0 && index >=0; y--){
            for(int x= board.getWidth()-1; x>=0 && index >=0; x--, index--){
                char color = config.charAt(index);
                if( color != '0' && color != ' ' ) {
                    board.dropPiece(x, Character.getNumericValue(color));
                }
            }
        }

        return board;
    }
}
