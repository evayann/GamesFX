/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports

import GamesFX.gui.UIManager;
import GamesFX.gui.utility.UtilityFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import GamesFX.user.User;
// endregion Imports

public class SideMenuController implements Controller {

    // region Attributes
    @FXML
    private ImageView m_userPicture;

    @FXML
    private Label m_userName;

    @FXML
    private Pane m_mainPane;

    @FXML
    private Button m_welcome;

    private UIManager m_uim;
    private Button m_currButton;
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        m_uim = UIManager.getInstance();
        selected(m_welcome);
    }

    @FXML
    void switchToTicaTaToe(ActionEvent _event) {
        m_uim.switchToTicTacToe();
        selected((Button) _event.getSource());
    }

    @FXML
    void switchToParameters(ActionEvent _event) {
        m_uim.switchToParameters();
        selected((Button) _event.getSource());
    }

    @FXML
    void switchToNono(ActionEvent _event) {
        m_uim.switchToNono();
        selected((Button) _event.getSource());
    }

    @FXML
    void switchToMS(ActionEvent _event) {
        m_uim.switchToMS();
        selected((Button) _event.getSource());
    }

    @FXML
    void switchToWelcome(ActionEvent _event) {
        m_uim.switchToWelcome();
        selected((Button) _event.getSource());
    }

    private void selected(Button _button) {
        _button.getStyleClass().add("selected");
        if (m_currButton != null)
            m_currButton.getStyleClass().remove("selected");
        m_currButton = _button;
    }

    public void setName(String _name) {
        m_userName.setText(_name);
    }

    public void setImage(String _imgPath) {
        m_userPicture.setImage(new Image(_imgPath));
        m_userPicture.setFitHeight(80);
        m_userPicture.setFitWidth(80);
    }

    public void logOut() {
        ButtonType bt = UtilityFX.alert(Alert.AlertType.CONFIRMATION,
                "Disconnection", "Disconnection", "Do you want to log out  ?",
                ButtonType.NO, ButtonType.YES);
        if (bt.equals(ButtonType.YES)) {
            m_uim.disconnectCurrentUser();
            selected(m_welcome);
        }
    }

    public Pane getMainPane() {
        return m_mainPane;
    }

    @Override
    public void load() {
        User u = m_uim.getCurrentUser();
        if (u != null) {
            setName(u.getName());
            setImage("file:" + u.getPicturePath());
        }
    }
    // endregion Methods
}
