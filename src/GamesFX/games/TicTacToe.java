/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.games;

import java.util.HashMap;
import java.util.Map;

interface MinMax {
    int compute(int a, int b);
}

public class TicTacToe {

    private int m_width = 3;
    private int m_height = 3;
    private TTTState[][] m_board;
    private TTTState m_currentPlayer;

    private Map<TTTState, Integer> m_scores; // Score for AI minimax

    public TicTacToe() {
        // Add score for minimax algorithm
        m_scores = new HashMap<>();
        m_scores.put(TTTState.P1, -1);
        m_scores.put(TTTState.P2,  1);
        m_scores.put(TTTState.Tie, 0);
        create();
    }

    void create() {
        m_board = new TTTState[m_width][m_height];
        for (int x = 0; x < m_width; x++) {
            for (int y = 0; y < m_height; y++) {
                m_board[x][y] = TTTState.Empty;
            }
        }
        m_currentPlayer = TTTState.P1;
    }

    boolean equals3(TTTState _a, TTTState _b, TTTState _c) {
        return _a == _b && _b == _c && _a != TTTState.Empty;
    }

    public TTTState winnerIs() {
        TTTState winner = null;

        // horizontal
        for (int i = 0; i < 3; i++) {
            if (equals3(m_board[i][0], m_board[i][1], m_board[i][2])) {
                winner = m_board[i][0];
            }
        }

        // Vertical
        for (int i = 0; i < 3; i++) {
            if (equals3(m_board[0][i], m_board[1][i], m_board[2][i])) {
                winner = m_board[0][i];
            }
        }

        // Diagonal
        if (equals3(m_board[0][0], m_board[1][1], m_board[2][2])) {
            winner = m_board[0][0];
        }
        if (equals3(m_board[2][0], m_board[1][1], m_board[0][2])) {
            winner = m_board[2][0];
        }

        int empty = 0;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (m_board[x][y] == TTTState.Empty) {
                    empty++;
                }
            }
        }

        return winner == null && empty == 0 ? TTTState.Tie : winner;
    }

    public boolean play(int _x, int _y) {
        TTTState state = m_board[_x][_y];
        if (state != TTTState.Empty)
            return false; // Play only on empty cell
        m_board[_x][_y] = m_currentPlayer;
        m_currentPlayer = TTTState.oppositePlayer(m_currentPlayer);
        return true;
    }

    public Case playAI() {
        int bestScore = Integer.MIN_VALUE;
        Case move = null;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (m_board[x][y] == TTTState.Empty) {
                    m_board[x][y] = TTTState.P2; // Try this position for AI (always second player)
                    int score = minimax(0, false);
                    m_board[x][y] = TTTState.Empty; // And remove it after try
                    if (score > bestScore) {
                        bestScore = score;
                        move = new Case(x, y);
                    }
                }
            }
        }
        if (move != null) {
            play(move.x, move.y);
            return move;
        }
        return null;
    }

    private int minimax(int depth, boolean isMaximizing) {
        TTTState result = winnerIs();
        if (result != null) {
            return m_scores.get(result);
        }

        int bestScore;
        TTTState player;
        MinMax to;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            player = TTTState.P2;
            to = Integer::max;
        }
        else {
            bestScore = Integer.MAX_VALUE;
            player = TTTState.P1;
            to = Integer::min;
        }
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                // Is the spot available?
                if (m_board[x][y] == TTTState.Empty) {
                    m_board[x][y] = player;
                    int score = minimax(depth + 1, !isMaximizing);
                    m_board[x][y] = TTTState.Empty;
                    bestScore = to.compute(score, bestScore);
                }
            }
        }
        return bestScore;
    }

    public int getWidth() {
        return m_width;
    }

    public int getHeight() {
        return m_height;
    }

    public TTTState getCurrentPlayer() {
        return m_currentPlayer;
    }
}