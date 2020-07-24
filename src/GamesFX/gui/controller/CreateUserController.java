/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports
import GamesFX.gui.UIManager;
import GamesFX.gui.utility.PopWindow;
import GamesFX.gui.utility.UtilityFX;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import GamesFX.user.User;

import java.io.File;
// endregion Imports

public class CreateUserController {

    // region Attributes
    @FXML
    private TextField m_userName;

    @FXML
    private ImageView m_userPicture;

    private UIManager m_uiManager;
    private String m_imagePath;
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        m_uiManager = UIManager.getInstance();
        m_imagePath = User.DEFAULT_PICTURE;
    }

    @FXML
    void createUser() {
        if (m_userName.getText().length() > 3) {
            // Create User, clean text and hide popup
            m_uiManager.createNewUser(m_userName.getText(), m_imagePath);
            m_uiManager.resetLogin();
            m_userName.clear();
            close();
        }
        else {
            m_userName.clear();
            UtilityFX.alert(Alert.AlertType.WARNING, "Bad Name", "Bad Name", "Your name is too short !");
        }
    }

    @FXML
    void chooseImage() {
        File f = UtilityFX.fileChooser("Your image", "Images", "*.png", "*.jpg", "*.gif");
        m_imagePath = "file:" + f.getAbsolutePath();
        m_userPicture.setImage(new Image(m_imagePath));
    }

    @FXML
    void close() {
        PopWindow.close(UIManager.CREATE_USER_ID);
    }
    // endregion Methods

}
