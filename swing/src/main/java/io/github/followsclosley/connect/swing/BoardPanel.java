package io.github.followsclosley.connect.swing;


import io.github.followsclosley.connect.Board;
import io.github.followsclosley.connect.Coordinate;
import io.github.followsclosley.connect.impl.Turn;
import io.github.followsclosley.connect.impl.TurnUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * This panel draws the connect four board given a Board
 *
 * @see Board
 */
public class BoardPanel extends JPanel {

    public static final int PIECE_SIZE = 75;
    private final Color[] COLORS = {Color.GRAY, Color.GRAY, Color.RED, Color.BLACK};
    private final Board board;
    protected Dimension defaultDimension;

    private Image[] images = null;

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * PIECE_SIZE, board.getHeight() * PIECE_SIZE);
    }

    /**
     * If this method is called then the Panel will use images as pieces instead of g.fillRoundRect
     */
    public void loadPrettyImages() {
        try {
            images = new Image[3];
            images[0] = ImageIO.read(ClassLoader.getSystemResource("black.png")).getScaledInstance(PIECE_SIZE - 3, PIECE_SIZE - 3, Image.SCALE_SMOOTH);
            images[1] = ImageIO.read(ClassLoader.getSystemResource("blue.png")).getScaledInstance(PIECE_SIZE - 3, PIECE_SIZE - 3, Image.SCALE_SMOOTH);
            images[2] = ImageIO.read(ClassLoader.getSystemResource("red.png")).getScaledInstance(PIECE_SIZE - 3, PIECE_SIZE - 3, Image.SCALE_SMOOTH);
        } catch (IOException oops) {
            oops.printStackTrace();
            images = null;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw the pieces on the board
        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                g.setColor(COLORS[board.getPiece(x, y) + 1]);
                g.fillRoundRect(x * PIECE_SIZE, y * PIECE_SIZE, PIECE_SIZE - 5, PIECE_SIZE - 5, PIECE_SIZE - 5, PIECE_SIZE - 5);
            }
        }

        //Draw board with images.
        if (images != null) {
            for (int x = 0, width = board.getWidth(); x < width; x++) {
                for (int y = 0, height = board.getHeight(); y < height; y++) {
                    int color = board.getPiece(x, y);
                    g.drawImage(images[color], x * PIECE_SIZE, y * PIECE_SIZE, this);
                }
            }
        }


        //Draw the winning lines
        if (board.getTurns().size() > 0) {
            Turn turn = TurnUtils.getConnections(board);
            if (turn.hasWinningLine(board.getGoal()))
                for (Turn.Line line : turn.getLines()) {
                    if (line.getPieceCount() >= board.getGoal()) {
                        List<Coordinate> coordinates = line.getConnected();
                        Coordinate start = coordinates.get(0);
                        Coordinate end = coordinates.get(coordinates.size() - 1);

                        ((Graphics2D) g).setStroke(new BasicStroke(10));
                        g.setColor(Color.YELLOW);
                        g.drawLine(start.getX() * PIECE_SIZE + (PIECE_SIZE / 2), start.getY() * PIECE_SIZE + (PIECE_SIZE / 2), end.getX() * PIECE_SIZE + (PIECE_SIZE / 2), end.getY() * PIECE_SIZE + (PIECE_SIZE / 2));
                    }
                }
        }
    }
}