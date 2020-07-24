/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports

import GamesFX.games.Case;
import GamesFX.games.TTTState;
import GamesFX.games.TicTacToe;
import GamesFX.gui.utility.UtilityFX;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
// endregion Imports

public class TicTacToeController implements Controller {

    // region Attributes
    @FXML
    private GridPane m_games;
    @FXML
    private Label m_winner;

    private boolean m_aiOn;
    private TicTacToe m_ttt;
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        reset();
        m_aiOn = false;
    }

    @FXML
    void reset() {
        m_games.getChildren().clear();
        m_ttt = new TicTacToe();
        m_winner.setText("");
        draw();
    }

    @FXML
    void aiOn() {
        m_aiOn = true;
        if (m_ttt.getCurrentPlayer() == TTTState.P2)
            playAI();
    }

    @FXML
    void aiOff() {
        m_aiOn = false;
    }

    private void draw() {
        int width = m_ttt.getWidth(), height = m_ttt.getHeight();
        UtilityFX.computeGrid(m_games, width, height);

        // Button to play
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Case b = new Case(x, y);
                b.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY)
                        play(b);
                });
                m_games.add(b, x, y);
            }
        }
    }

    private void play(Case _case) {
        String player = m_ttt.getCurrentPlayer().toString();
        boolean playCase = m_ttt.play(_case.x, _case.y);
        if (playCase) {
            _case.setCaseStyle(player);
            TTTState winner = m_ttt.winnerIs();
            if (winner != null)
                gameOver(winner);
            if (m_aiOn) {
                playAI();
            }
        }
    }

    private void playAI() {
        Case c = m_ttt.playAI();
        // Search node to display it
        for (Node n : m_games.getChildren()) {
            if (n instanceof Case) {
                Case cs = (Case) n;
                if (cs.equals(c))
                    cs.setCaseStyle(TTTState.P2.toString());
            }
        }
        TTTState winner = m_ttt.winnerIs();
        if (winner != null)
            gameOver(winner);
    }

    public void gameOver(TTTState _winner) {
        for (Node n : m_games.getChildren()) {
            n.setDisable(true);
            n.setOpacity(1);
        }
        String header = _winner != TTTState.Tie ? "Congratulations : " + _winner : "It's a tie";
        String content = _winner != TTTState.Tie ?
                "You make a good game " + TTTState.oppositePlayer(_winner) + " but " + _winner + " is stronger ! " :
                "It's a tie, you need to make a new game to have a real winner !";
        UtilityFX.alert(Alert.AlertType.INFORMATION, "Game Over", header, content);
    }

    @Override
    public void load() {
        reset();
    }
    // endregion Methods
}
