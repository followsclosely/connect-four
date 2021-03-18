package io.github.ryanp102694.connect;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.TurnUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A totally mediocre impl of AI.
 */
public class MonteCarloAI implements ArtificialIntelligence {

    private final int SIMULATIONS_PER_TURN = 10000;
    private boolean DEBUG = false;
    private Random random = new Random();
    private ExecutorService executorService = Executors.newFixedThreadPool(7);
    private ConcurrentHashMap<Integer, Integer> winCounter = winTrackerMap();

    private int color;
    private int otherColor = -1;


    public MonteCarloAI(int color) {
        this.color = color;
    }

    @Override
    public void initialize(int opponent) {
        this.otherColor = opponent;
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

        //keep the simulations from the previous run that apply to the opponents last move
        if(mutableBoard.getTurns().size() > 0){
            int spotToKeep = mutableBoard.getTurns().get(mutableBoard.getTurns().size() - 1).getX();
            if(DEBUG){
                System.out.println(String.format("Preserving %s from play %s from previous simulations.",
                        winCounter.get(spotToKeep), spotToKeep));
            }
            Stream.of(0, 1, 2, 3, 4, 5, 6).filter(i -> i != spotToKeep).forEach( i -> winCounter.put(i, 0));
        }


        int obviousChoice = getObviousChoice(playableSpots, mutableBoard);

        if(obviousChoice != -1){
            return obviousChoice;
        }

        List<Future<Void>> futures = new ArrayList<>();
        long start = System.currentTimeMillis();
        //if no obvious choice then run simulated games to find answer
        for(Integer playableSpot : playableSpots){
            futures.add(executorService.submit(() -> {
                for (int i = 0; i < SIMULATIONS_PER_TURN; i++) {
                    winCounter.put(playableSpot, winCounter.get(playableSpot) + simulateGame(playableSpot, new MutableBoard(mutableBoard)));
                }
                return null;
            }));
        }

        for(Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.out.println("Something went wrong. This shouldn't happen.");
            }
        }

        if(DEBUG){
            System.out.println(String.format("Finished %s simulations in %s: %s", SIMULATIONS_PER_TURN,
                    System.currentTimeMillis() - start, winCounter));
        }
        int max = Integer.MIN_VALUE;
        int decision = -1;

        for(Integer play : winCounter.keySet()){
            if(winCounter.get(play) >= max){
                decision = play;
            }
            max = Math.max(max, winCounter.get(play));
        }

        if(DEBUG){
            System.out.println(decision);
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


    private ConcurrentHashMap<Integer, Integer> winTrackerMap(){
        ConcurrentHashMap<Integer, Integer> winTrackerMap = new ConcurrentHashMap<>();
        Stream.of(0, 1, 2, 3, 4, 5, 6).forEach( i -> winTrackerMap.put(i, 0));
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

        //if I can win play there
        for(Integer playableSpot : playableSpots){
            if(TurnUtils.getConnections(board, getCoordinateIfPlayed(playableSpot, board), color)
                    .hasWinningLine(board.getGoal())){
                return playableSpot;
            }
        }

        //if opponent can win play there
        for(Integer playableSpot : playableSpots){
            if(TurnUtils.getConnections(board, getCoordinateIfPlayed(playableSpot, board), otherColor)
                    .hasWinningLine(board.getGoal())){
                return playableSpot;
            }
        }

        //play in the middle first
        if(board.getTurns().size() < 5){
            return board.getWidth() / 2;
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

    private int otherColor(int colorToSwitch){
        return colorToSwitch == color ? otherColor : color;
    }
}