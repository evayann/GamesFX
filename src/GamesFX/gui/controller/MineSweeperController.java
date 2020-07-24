/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports

import GamesFX.games.Case;
import GamesFX.games.MineSweeper;
import GamesFX.games.Parameters;
import GamesFX.gui.UIManager;
import GamesFX.gui.utility.UtilityFX;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import GamesFX.user.User;

import java.util.ArrayList;
import java.util.List;
// endregion Imports

public class MineSweeperController implements Controller {

    // region Attributes
    @FXML
    private Label m_mines;

    @FXML
    private GridPane m_games;

    private MineSweeper m_ms;
    private Case[][] m_cases;
    private int m_nbFlag;
    private List<Case> m_flags;
    private UIManager m_uim;
    private Parameters m_parameters;
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        m_uim = UIManager.getInstance();
        m_flags = new ArrayList<>();
        reset();
    }

    @FXML
    void reset() {
        m_nbFlag = 0;
        User u = m_uim.getCurrentUser();
        if (u != null) {
            m_parameters = u.getParameters();
            m_mines.setText(Integer.toString(m_parameters.getMineSweeperBombs()));
            m_flags.clear();
            m_games.getChildren().clear();
            m_ms = new MineSweeper(this, m_parameters.getMineSweeperWidth(),
                    m_parameters.getMineSweeperHeight(), m_parameters.getMineSweeperBombs());
            draw();
        }
    }

    private void draw() {
        int width  = m_parameters.getMineSweeperWidth();
        int height = m_parameters.getMineSweeperHeight();

        UtilityFX.computeGrid(m_games, width, height);

        m_cases = new Case[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Case b = new Case(x, y);
                b.setOnMouseClicked(event -> {
                    if (m_ms.isDiscover(b.x, b.y))
                        return; // Don't update an already reveal case
                    if (event.getButton() == MouseButton.PRIMARY && !b.getStyleClass().contains("flag")) // Can't click on a flag
                        m_ms.update(b.x, b.y);
                    else if (event.getButton() == MouseButton.SECONDARY)
                        updateFlag(b);
                });
                m_cases[x][y] = b;
                m_games.add(b, x, y);
            }
        }
    }

    public void updateButton(int _x, int _y, int _value) {
        Case c = m_cases[_x][_y];
        c.setCaseStyle("discover");
        if (_value == MineSweeper.MINE)
            c.addStyle("bomb");
        else if (_value != 0)
            c.setText(Integer.toString(_value));
    }

    public void gameOver(boolean _win) {
        for (Node n : m_games.getChildren()) {
            if (n instanceof Case) {
                Case c = (Case) n;
                int val = m_ms.reveal(c.x, c.y);
                if (val == -1)
                    c.setCaseStyle("bomb");
                else
                    c.setText(val != 0 ? Integer.toString(val) : "");
                c.getStyleClass().add("discover");
                c.setDisable(true);
                c.setOpacity(1.0);
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String text = _win ? "Win" : "Lose";
        alert.setTitle(text);
        alert.setHeaderText(text);
        alert.getDialogPane().getStylesheets().clear();
        alert.getDialogPane().getStylesheets().add(UIManager.CSS);
        alert.show();
    }

    void updateFlag(Case _case) {
        int mines = m_ms.getNbMines();
        for (Case f : m_flags) {
            if (f.equals(_case)) {
                f.getStyleClass().remove("flag");
                m_flags.remove(f);
                m_nbFlag--;
                m_mines.setText(Integer.toString(mines - m_nbFlag));
                return;
            }
        }

        if (mines - m_nbFlag >= 0) { // If all flags not use
            m_flags.add(_case); // Add new flag
            m_nbFlag++;
            _case.setCaseStyle("flag");
            m_mines.setText(Integer.toString(mines - m_nbFlag));
            if (mines - m_nbFlag == 0 && m_ms.allMinesCover(m_flags))
                gameOver(true);
        }
    }

    @Override
    public void load() {
        reset();
    }
    // endregion Methods
}
