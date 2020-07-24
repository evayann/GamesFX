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

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
// endregion Imports

public class WelcomeController implements Controller {

    // region Attributes
	// region FXML
	@FXML
	private Label m_userName;

	@FXML
	private Button m_helpMe;
	// endregion FXML
	private UIManager m_uim;
	// endregion Attributes

	// region Methods
    @FXML
    void initialize() {
    	m_uim = UIManager.getInstance();

		m_helpMe.setOnAction(event -> {
			boolean succeed = openInBrowser("http://yann.fzcommunication.fr/index.php/");
			if (! succeed) {
				UtilityFX.alert(Alert.AlertType.ERROR, "Problem to open website", "Problem to open website",
						"A problem was occured during the openning of website. You can check it at url : http://yann.fzcommunication.fr/index.php/");
			}
		});
    }

	private boolean openInBrowser(String _url) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			try {
				Desktop.getDesktop().browse(new URI(_url));
				return true;
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
				return false;
			}
		}
		else {
			return false;
		}
	}

	// region Override
	@Override
	public void load() {
		m_userName.setText("Welcome back " + m_uim.getCurrentUser().getName());
	}
	// endregion Override
	// endregion Methods
}
