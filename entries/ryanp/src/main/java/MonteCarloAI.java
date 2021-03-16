import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
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

    public MonteCarloAI(int color) {
        this.color = color;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public int yourTurn(Board board) {

        /**
         * A Turn
         *
         * The Coordinate object is the x/y pair that is the origin of the Line objects.
         *
         * The List of lines is showing open spaces from the
         * coordinate.
         *
         * Lines(0) is vertical
         * Lines(1) is diagonal increasing
         * Lines(2) is horizontal
         * Lines(3) is diagonal decreasing
         */

        MutableBoard mutableBoard = new MutableBoard(board);

        //get possible places to play
        List<Integer> playableSpots = getPlayableSpots(mutableBoard);

        Map<Integer, Integer> winCounter = winTrackerMap();

        for(Integer playableSpot : playableSpots){
            for(int i = 0; i < 10000; i++){
                winCounter.put(playableSpot, winCounter.get(playableSpot) + simulateGame(playableSpot, new MutableBoard(mutableBoard)));
            }
        }

        System.out.println(winCounter);

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
            return simulateGameRecursive(otherColor(color), board);
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
                return simulateGameRecursive(otherColor(turnColor), board);
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

    private int otherColor(int color){
        return color == 1 ? 2 : 1;
    }

}