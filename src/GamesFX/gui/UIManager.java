/**
 * Project, Notification
 * @version 0.2019
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui;

// region Imports

import GamesFX.EventManager;
import GamesFX.gui.controller.Controller;
import GamesFX.gui.controller.SideMenuController;
import GamesFX.gui.utility.UtilityFX;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import GamesFX.user.User;
import GamesFX.utility.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
// endregion Imports

public class UIManager {

	// region Singleton
	private static UIManager m_uim;
	public static UIManager getInstance() {
		if (m_uim == null) {
			m_uim = new UIManager();
		}
		return m_uim;
	}

	private UIManager() {} // Make private constructor to don't have 2 instance of singleton

	// endregion Singleton

	// region Constant
	public static final String CREATE_USER_ID = "createUser";
	public static final String CSS = "GamesFX/gui/style.css";

	private final String ICON_PATH = "file:src/ressources/logo/logo.png";
	private final String PATH      = "GamesFX/gui/fxml/";

	private final String MENU        = "menu";
	private final String WELCOME     = "welcomePage";
	private final String LOGIN       = "login";
	private final String MINES       = "mines";
	private final String NONOGRAM    = "nonogram";
	private final String TIC_TAC_TOE = "tictactoe";
	private final String PARAMETERS  = "parameters";

	private int m_currentID;
	private final int WELCOME_ID     = 0;
	private final int LOGIN_ID       = 1;
	private final int MINESWEEPER_ID = 2;
	private final int NONOGRAM_ID    = 3;
	private final int TIC_TAC_TOE_ID = 4;
	private final int PARAMETERS_ID  = 5;

	private final String EXTENSION = ".fxml";
	// endregion Constant

	private Stage m_stage;

	private Pane               m_sideMenu;
	private SideMenuController m_sideMenuController;

	private Pane             m_mainPane;
	private Pane             m_otherSideMenuPane;
	private EventManager     m_em;
	private List<Region>     m_regions;
	private List<Controller> m_controllers;
	private Image            m_softwareIcon;

	public void initialize() {
		Log.printMessage("Initialize UI Manager");
		// --- Initialize attributes
		m_em = EventManager.getInstance();
		m_regions = new ArrayList<>();
		m_controllers = new ArrayList<>();
	}

	public void start() {
		// --- Launch JavaFX
		Log.printMessage("UI Start");
		UIJavaFX.launchJavaFX();
	}

	public void initializeStage(Stage _stage) {
		m_stage = _stage;
		m_softwareIcon = new Image(ICON_PATH);

		// Create & Set MainPane on Scene
		m_mainPane = new Pane();

		Scene scene = new Scene(m_mainPane, 800, 800);
		scene.getStylesheets().add(CSS);
		UtilityFX.setCSS(CSS);

		// Set minimal size and scene
		m_stage.setMinWidth(800);
		m_stage.setMinHeight(600);
		m_stage.setScene(scene);

		Log.printMessage("Load Scenes");

		try {
			// Create Side Menu
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(PATH + MENU + EXTENSION));
			m_sideMenu = fxmlLoader.load();
			m_sideMenuController = fxmlLoader.getController();
			m_sideMenu.prefWidthProperty().bind(scene.widthProperty());
			m_sideMenu.prefHeightProperty().bind(scene.heightProperty());
			m_otherSideMenuPane = m_sideMenuController.getMainPane();
		}
		catch (IOException e) {
			Log.printError("Error during fxml loading, more information : \n" + e.toString());
		}

		// Load others FXML
		loadFXML(WELCOME, WELCOME_ID);
		loadFXML(LOGIN, LOGIN_ID);
		loadFXML(MINES, MINESWEEPER_ID);
		loadFXML(NONOGRAM, NONOGRAM_ID);
		loadFXML(TIC_TAC_TOE, TIC_TAC_TOE_ID);
		loadFXML(PARAMETERS, PARAMETERS_ID);

		// --- Set Name and icon
		m_stage.setTitle("GamesFX");
		m_stage.getIcons().add(m_softwareIcon);

		// --- Switch to page and display it
		switchToLogIn();
		Log.printMessage("Display JavaFX Stage");
        m_stage.show();
		Log.printMessage("End Initialization Stage");
	}

	private void loadFXML(String _name, int _id) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(PATH + _name + EXTENSION));
			m_regions.add(_id, fxmlLoader.load());
			m_controllers.add(_id, fxmlLoader.getController());
		} catch (IOException e) {
			Log.printError("Error during fxml loading " + _name + ", more information : \n" + e.toString());
			e.printStackTrace();
		}
	}

	// region Switch
	private void switchScene(Pane _parent, int _sceneID) {
		m_currentID = _sceneID;
		_parent.getChildren().clear();
		Region r = m_regions.get(_sceneID);
		r.prefWidthProperty().bind(_parent.widthProperty());
		r.prefHeightProperty().bind(_parent.heightProperty());
		_parent.getChildren().add(r);
		m_controllers.get(m_currentID).load(); // To load necessary element for this controller
	}

	/**
	 * Switch to sceneID with SideMenu
	 * @param _sceneID
	 */
	private void switchTo(int _sceneID) {
		switchScene(m_otherSideMenuPane, _sceneID);
	}

	/**
	 * Switch to login page without SideMenu
	 */
	public void switchToLogIn() {
		switchScene(m_mainPane, LOGIN_ID);
	}

	private void exitLogIn() {
		m_mainPane.getChildren().clear();
		m_mainPane.getChildren().add(m_sideMenu);
	}

	public void loadWelcome() {
		exitLogIn();
		m_sideMenuController.setName(m_em.getCurrentUser().getName());
		m_sideMenuController.setImage("file:" + m_em.getCurrentUser().getPicturePath());
		switchToWelcome();
	}

	public void switchToWelcome() {
		switchTo(WELCOME_ID);
	}

	public void switchToMS() {
		switchTo(MINESWEEPER_ID);
	}

	public void switchToNono() {
		switchTo(NONOGRAM_ID);
	}

	public void switchToTicTacToe() {
		switchTo(TIC_TAC_TOE_ID);
	}

	public void switchToParameters() {
		switchTo(PARAMETERS_ID);
	}
	// endregion Switch

	// region Getters
	public List<User> getUsers() {
		return m_em.getUsers();
	}

	public User getCurrentUser() {
		return m_em.getCurrentUser();
	}
	// endregion Getters

	// region Welcome Connection User
	public void createNewUser(String _userName, String _imagePath) {
		m_em.createNewUser(_userName, _imagePath);
	}

	public void connectUser(User _user) {
		m_em.connectUser(_user);
	}

	public void disconnectCurrentUser() {
		m_em.disconnectCurrentUser();
	}
	// endregion Welcome Connection User

	// region Reload
	public void reload() {
		m_controllers.get(m_currentID).load();
	}

	public void resetMineSweeper() {
		m_controllers.get(MINESWEEPER_ID).load();
	}

	public void resetNonogram() {
		m_controllers.get(NONOGRAM_ID).load();
	}

	public void resetLogin() {
		m_controllers.get(LOGIN_ID).load();
	}

	public void resetMenu() {
		m_sideMenuController.load();
	}
	// endregion Reload

	// region Misc
	public void deleteCurrentUser() {
		m_em.deleteCurrentUser();
	}
	// endregion Misc
}