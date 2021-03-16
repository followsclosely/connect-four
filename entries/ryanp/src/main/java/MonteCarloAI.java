import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A totally random impl of AI.
 */
public class MonteCarloAI implements ArtificialIntelligence {

    private Random random = new Random();

    private int color;
    private int otherColor = -1;

    public MonteCarloAI(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int yourTurn(Board board) {

        MutableBoard mutableBoard = new MutableBoard(board);

        //get possible places to play
        List<Integer> playableSpots = getPlayableSpots(mutableBoard);

        Map<Integer, Integer> winCounter = winTrackerMap();

        int obviousChoice = getObviousChoice(playableSpots, mutableBoard);

        if(obviousChoice != -1){
            return obviousChoice;
        }

        //if no obvious choice then run simulated games to find answer
        for(Integer playableSpot : playableSpots){
            for(int i = 0; i < 100; i++){
                winCounter.put(playableSpot, winCounter.get(playableSpot) + simulateGame(playableSpot, new MutableBoard(mutableBoard)));
            }
        }

        int max = Integer.MIN_VALUE;
        int decision = -1;

        for(Integer play : winCounter.keySet()){
            if(winCounter.get(play) >= max){
                decision = play;
            }
            max = Math.max(max, winCounter.get(play));
        }

        return decision;
    }

    //top level call, simulating a game if the AI played at x
    private int simulateGame(int x, MutableBoard board){
        board.dropPiece(x, color);

        //if I just won, then return 1
        if(TurnUtils.getConnections(board).hasWinningLine(board.getGoal())){
            return 1;
        }else{
            //randomly play out a game, starting with my opponent playing
            //this will return 1 if I win, -1 if I lose, and 0 if a draw
            return simulateGameRecursive(otherColor(color, board), board);
        }
    }

    private int simulateGameRecursive(int turnColor, MutableBoard board){
        int randomMove = getRandomPlay(board);
        if(randomMove == -1) {
            return 0;
        }else{
            board.dropPiece(randomMove, turnColor);
            if(TurnUtils.getConnections(board).hasWinningLine(board.getGoal())){
                return turnColor == color ? 1 : -1;
            }else{
                return simulateGameRecursive(otherColor(turnColor, board), board);
            }
        }
    }


    private Map<Integer, Integer> winTrackerMap(){
        Map<Integer, Integer> winTrackerMap = new HashMap<>();
        winTrackerMap.put(0, 0);
        winTrackerMap.put(1, 0);
        winTrackerMap.put(2, 0);
        winTrackerMap.put(3, 0);
        winTrackerMap.put(4, 0);
        winTrackerMap.put(5, 0);
        winTrackerMap.put(6, 0);
        return winTrackerMap;
    }

    private int getRandomPlay(MutableBoard mutableBoard){
        int x = random.nextInt(mutableBoard.getWidth());
        //Keep adding one to the random spot while canDropPiece is false or we get to the width of the board
        for (int i = 0, width = mutableBoard.getWidth(); i < width; i++, x = (x + 1) % mutableBoard.getWidth()) {
            if (mutableBoard.canDropPiece(x)) {
                return x;
            }
        }

        return -1;
    }

    private List<Integer> getPlayableSpots(MutableBoard mutableBoard){
        return Stream.of(0, 1, 2, 3, 4, 5, 6).filter(mutableBoard::canDropPiece)
                .collect(Collectors.toList());
    }

    private int getObviousChoice(List<Integer> playableSpots, MutableBoard board){
        for(Integer playableSpot : playableSpots){
            if(TurnUtils.getConnections(board, getCoordinateIfPlayed(playableSpot, board), color)
                    .hasWinningLine(board.getGoal())){
                return playableSpot;
            }
        }

        for(Integer playableSpot : playableSpots){
            if(TurnUtils.getConnections(board, getCoordinateIfPlayed(playableSpot, board), otherColor(color, board))
                    .hasWinningLine(board.getGoal())){
                return playableSpot;
            }
        }

        return -1;
    }

    //assumes spot is playable
    private Coordinate getCoordinateIfPlayed(int playableSpot, MutableBoard mutableBoard){
        for(int y = 0; y <= mutableBoard.getHeight(); y++){
            if(y == mutableBoard.getHeight() - 1){
                return new Coordinate(playableSpot, y);
            }
            if(mutableBoard.getPiece(playableSpot, y) != 0){
                return new Coordinate(playableSpot, y - 1);
            }
        }
        //should never happen
        return null;
    }

    //kind of annoying way to find out the other color playing
    private int otherColor(int firstColor, MutableBoard mutableBoard){

        //if I don't know the other color I will pretend it is -1 for the simulations
        //I will check to see what the other color is by looking at the bottom two rows for something that
        //is not 0 and is not my color.
        // once I find the other color I will set it internally and not have to pretend again
        if(otherColor == -1){
            for(int y = mutableBoard.getHeight() - 1; y <= mutableBoard.getHeight(); y++){
                for(int x = 0; x < mutableBoard.getWidth(); x++){
                    if(mutableBoard.getPiece(x, y) != color && mutableBoard.getPiece(x, y) != 0){
                        otherColor = mutableBoard.getPiece(x, y);
                        return otherColor;
                    }
                }
            }
        }
        return color == firstColor ? otherColor : color;
    }

}