/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports

import GamesFX.games.Case;
import GamesFX.games.Nonogram;
import GamesFX.games.Parameters;
import GamesFX.gui.UIManager;
import GamesFX.gui.utility.UtilityFX;
import GamesFX.user.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
// endregion Imports

enum NonoState {
    Colored("colored"),
    Cross("cross"),
    Nothings("");

    private String m_state;

    NonoState(String _state) {
        m_state = _state;
    }

    @Override
    public String toString() {
        return m_state;
    }
}

public class NonogramController implements Controller {

    // region Attributes
    @FXML
    private GridPane m_games;

    private Nonogram m_nono;
    private List<Case> m_colored;
    private UIManager m_uim;
    private Parameters m_parameters;
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        m_uim = UIManager.getInstance();
        m_colored = new ArrayList<>();
        reset();
    }

    @FXML
    void reset() {
        m_colored.clear();
        m_games.getChildren().clear();
        User u = m_uim.getCurrentUser();
        if (u != null) {
            m_parameters = u.getParameters();
            m_nono = new Nonogram(m_parameters.getNonogramWidth(), m_parameters.getNonogramHeight());
            draw();
        }
    }

    private void draw() {
        int width  = m_parameters.getNonogramWidth();
        int height = m_parameters.getNonogramHeight();
        UtilityFX.computeGrid(m_games, width + 1, height + 1);

        // Label for indication
        makeLabelIndices();

        // Button to play
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Case c = new Case(x, y, NonoState.Nothings);
                c.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY)
                        colorButton(c);
                    else if (event.getButton() == MouseButton.SECONDARY)
                        updateCross(c);
                });
                m_games.add(c, x + 1, y + 1);
            }
        }
    }

    private void makeLabelIndices() {
        int i = 0;
        for (String str : m_nono.getColsIndices()) {
            m_games.add(createLabel(str.equals("") ? "0" : str), i + 1, 0);
            i++;
        }
        i = 0;
        for (String str : m_nono.getRowsIndices()) {
            m_games.add(createLabel(str.equals("") ? "0" : str), 0, i + 1);
            i++;
        }
    }

    private Label createLabel(String _str) {
        Label l = new Label(_str);
        l.getStyleClass().add("case");
        l.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        l.setAlignment(Pos.CENTER);
        return l;
    }

    void updateCross(Case _case) {
        NonoState state = (NonoState) _case.getData();
        if (state == NonoState.Nothings)
            _case.setData(NonoState.Cross);
        else if (state == NonoState.Cross)
            _case.setData(NonoState.Nothings);
        _case.setCaseStyle(_case.getData().toString());
    }

    void gameOver() {
        for (Node n : m_games.getChildren()) {
            n.setDisable(true);
            n.setOpacity(1);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String text = "Win";
        alert.setTitle(text);
        alert.setHeaderText(text);
        alert.getDialogPane().getStylesheets().clear();
        alert.getDialogPane().getStylesheets().add(UIManager.CSS);
        alert.show();
    }

    void colorButton(Case _case) {
        NonoState state = (NonoState) _case.getData();
        if (state == NonoState.Colored) {
            _case.setData(NonoState.Nothings);
            _case.setCaseStyle(NonoState.Nothings.toString());
            for (Case f : m_colored) {
                if (f.equals(_case)) {
                    m_colored.remove(f);
                    return;
                }
            }
        }
        else if (state == NonoState.Nothings) {
            m_colored.add(_case); // Add new colored tile
            _case.setData(NonoState.Colored);
            _case.setCaseStyle(NonoState.Colored.toString());
            if (m_nono.isDiscover(m_colored))
                gameOver();
        }
    }

    // region Override
    @Override
    public void load() {
        reset();
    }
    // endregion Override
    // endregion Methods
}
