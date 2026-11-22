package io.github.fable;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.Board;

/**
 * An AI written by (and named after) Claude Fable 5.
 *
 * <p>Minimax search with alpha-beta pruning over a plain int[][] copy of the
 * board. Moves are searched center-out, which both plays stronger openings
 * and improves pruning. Positions are scored by sliding a window of
 * {@code goal} cells across every row, column and diagonal: windows that
 * contain only one player's pieces score exponentially by piece count,
 * mixed windows score nothing.</p>
 */
public class FableAI implements ArtificialIntelligence {

    private static final int WIN_SCORE = 1_000_000;

    private final int color;
    private final int depth;
    private int opponent;

    public FableAI(int color) {
        this(color, 7);
    }

    public FableAI(int color, int depth) {
        this.color = color;
        this.depth = depth;
    }

    @Override
    public void initialize(int opponent) {
        this.opponent = opponent;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public String getName() {
        return "FableAI(depth=" + depth + ")";
    }

    @Override
    public int yourTurn(Board board) {
        int width = board.getWidth();
        int height = board.getHeight();
        int goal = board.getGoal();

        int[][] grid = new int[width][height];
        int[] drop = new int[width];
        for (int x = 0; x < width; x++) {
            drop[x] = -1;
            for (int y = 0; y < height; y++) {
                grid[x][y] = board.getPiece(x, y);
                if (grid[x][y] == 0) {
                    drop[x] = Math.max(drop[x], y);
                }
            }
        }

        if (opponent == 0) {
            opponent = findOpponent(grid, width, height);
        }

        int[] order = centerOutOrder(width);

        // Take an immediate win, then block an immediate loss, before searching.
        int move = findWinningMove(grid, drop, order, goal, color);
        if (move < 0) {
            move = findWinningMove(grid, drop, order, goal, opponent);
        }
        if (move >= 0) {
            return move;
        }

        int bestColumn = -1;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE + 1;
        for (int x : order) {
            if (drop[x] < 0) {
                continue;
            }
            int y = drop[x]--;
            grid[x][y] = color;
            int score = wins(grid, x, y, goal, color)
                    ? WIN_SCORE
                    : -negamax(grid, drop, order, goal, depth - 1, -(WIN_SCORE + depth), -alpha, opponent);
            grid[x][y] = 0;
            drop[x]++;

            if (score > bestScore) {
                bestScore = score;
                bestColumn = x;
                alpha = Math.max(alpha, score);
            }
        }

        return bestColumn;
    }

    /**
     * Negamax with alpha-beta pruning: returns the score of the position
     * from the point of view of {@code player}, who is next to move.
     */
    private int negamax(int[][] grid, int[] drop, int[] order, int goal,
                        int remaining, int alpha, int beta, int player) {
        boolean full = true;
        for (int x : order) {
            if (drop[x] >= 0) {
                full = false;
                break;
            }
        }
        if (full) {
            return 0;
        }
        if (remaining == 0) {
            return evaluate(grid, goal, player);
        }

        for (int x : order) {
            if (drop[x] < 0) {
                continue;
            }
            int y = drop[x]--;
            grid[x][y] = player;
            // Prefer faster wins and slower losses so forced lines stay forced.
            int score = wins(grid, x, y, goal, player)
                    ? WIN_SCORE + remaining
                    : -negamax(grid, drop, order, goal, remaining - 1, -beta, -alpha, other(player));
            grid[x][y] = 0;
            drop[x]++;

            if (score > alpha) {
                alpha = score;
                if (alpha >= beta) {
                    return alpha;
                }
            }
        }

        return alpha;
    }

    /**
     * Scores every goal-length window on the board for {@code player}.
     * A window counts only if it holds a single player's pieces; its value
     * grows exponentially with how full it is.
     */
    private int evaluate(int[][] grid, int goal, int player) {
        int width = grid.length;
        int height = grid[0].length;
        int score = 0;

        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] d : directions) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int endX = x + d[0] * (goal - 1);
                    int endY = y + d[1] * (goal - 1);
                    if (endX >= width || endY < 0 || endY >= height) {
                        continue;
                    }
                    int mine = 0, theirs = 0;
                    for (int i = 0; i < goal; i++) {
                        int piece = grid[x + d[0] * i][y + d[1] * i];
                        if (piece == player) {
                            mine++;
                        } else if (piece != 0) {
                            theirs++;
                        }
                    }
                    if (theirs == 0 && mine > 0) {
                        score += pow4(mine);
                    } else if (mine == 0 && theirs > 0) {
                        score -= pow4(theirs);
                    }
                }
            }
        }

        // Small bias toward the center column, where more windows overlap.
        int center = width / 2;
        for (int y = 0; y < height; y++) {
            if (grid[center][y] == player) {
                score += 3;
            } else if (grid[center][y] != 0) {
                score -= 3;
            }
        }

        return score;
    }

    private int pow4(int count) {
        int value = 1;
        for (int i = 1; i < count; i++) {
            value *= 4;
        }
        return value;
    }

    /**
     * Returns a column where {@code player} wins immediately, or -1.
     */
    private int findWinningMove(int[][] grid, int[] drop, int[] order, int goal, int player) {
        for (int x : order) {
            if (drop[x] < 0) {
                continue;
            }
            int y = drop[x];
            grid[x][y] = player;
            boolean win = wins(grid, x, y, goal, player);
            grid[x][y] = 0;
            if (win) {
                return x;
            }
        }
        return -1;
    }

    /**
     * True if the piece just placed at (x, y) completes a goal-length line.
     */
    private boolean wins(int[][] grid, int x, int y, int goal, int player) {
        int width = grid.length;
        int height = grid[0].length;
        int[][] directions = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
        for (int[] d : directions) {
            int count = 1;
            for (int sign = -1; sign <= 1; sign += 2) {
                for (int i = 1; i < goal; i++) {
                    int nx = x + d[0] * i * sign;
                    int ny = y + d[1] * i * sign;
                    if (nx < 0 || nx >= width || ny < 0 || ny >= height || grid[nx][ny] != player) {
                        break;
                    }
                    count++;
                }
            }
            if (count >= goal) {
                return true;
            }
        }
        return false;
    }

    private int other(int player) {
        return player == color ? opponent : color;
    }

    private int findOpponent(int[][] grid, int width, int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] != 0 && grid[x][y] != color) {
                    return grid[x][y];
                }
            }
        }
        return color == 1 ? 2 : 1;
    }

    private int[] centerOutOrder(int width) {
        int[] order = new int[width];
        int center = width / 2;
        for (int i = 0; i < width; i++) {
            int offset = (i + 1) / 2;
            order[i] = (i % 2 == 1) ? center - offset : center + offset;
        }
        return order;
    }
}
