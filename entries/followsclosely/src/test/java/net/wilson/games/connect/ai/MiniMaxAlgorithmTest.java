package net.wilson.games.connect.ai;

import junit.framework.TestCase;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MiniMaxAlgorithmTest extends TestCase {

    public void testEvaluate() {

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(), "" +
                "0002000" +
                "0001000" +
                "0002000" +
                "0001000" +
                "0002000" +
                "0001210");

        MiniMaxAlgorithm algorithm = new MiniMaxAlgorithm(board, 1, 1, 2);

        MiniMaxAlgorithm.Node root = algorithm.evaluate();

        //Sort by score.
        List<MiniMaxAlgorithm.Node> sortedList = root.getChildren().stream()
                .sorted(Comparator.comparing(MiniMaxAlgorithm.Node::getScore).reversed())
                .collect(Collectors.toList());

        MiniMaxAlgorithm.Node best = sortedList.get(0);

        assertEquals(4, best.getColumn());
    }
}