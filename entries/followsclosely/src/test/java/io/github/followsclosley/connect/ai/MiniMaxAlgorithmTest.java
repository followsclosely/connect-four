package io.github.followsclosley.connect.ai;

import io.github.followsclosley.connect.impl.MutableBoard;
import junit.framework.TestCase;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MiniMaxAlgorithmTest extends TestCase {

    public void testEvaluate() {

//        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(), "" +
//                "2000002" +
//                "2000001" +
//                "2020112" +
//                "1211122" +
//                "1221211" +
//                "1112221");

        MutableBoard board = ConnectTestUtils.initialize(new MutableBoard(5, 4, 4), "" +
                "00000" +
                "00100" +
                "00200" +
                "00100");

        System.out.println(board);
        MiniMaxAlgorithm algorithm = new MiniMaxAlgorithm(1, 0);

//        MiniMaxAlgorithm.Node root = algorithm.evaluate(board);
//
//        //Sort by score.
//        List<MiniMaxAlgorithm.Node> sortedList = root.getChildren().stream()
//                .sorted(Comparator.comparing(MiniMaxAlgorithm.Node::getScore).reversed())
//                .collect(Collectors.toList());
//
//        MiniMaxAlgorithm.Node best = sortedList.get(0);

        //assertEquals(4, best.getColumn());
    }
}