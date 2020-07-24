package GamesFX; /**
 * Project, Notification
 * @version 0.2019
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

// region Imports

import GamesFX.gui.UIManager;
import GamesFX.user.User;
import GamesFX.user.UsersManager;
import GamesFX.utility.Log;

import java.util.List;
// endregion Imports

public class EventManager {
	// region Singleton
	private static EventManager m_em;

	public static EventManager getInstance() {
		if (m_em == null) {
			m_em = new EventManager();
		}
		return m_em;
	}

	private EventManager() {} // Make private constructor to don't have 2 instance of singleton
	// endregion Singleton

	// region Attributes
	private UsersManager m_usersManager;
	private UIManager m_uiManager;
	// endregion Attributes

	public void initialize() {
		// --- Get every elements
		m_usersManager = UsersManager.getInstance(); // User Manager
		m_uiManager = UIManager.getInstance(); // UI Manager

		// --- Initialize every elements
		m_usersManager.initialize();
		m_uiManager.initialize();
	}

	public void start() {
		// --- Start UI
		m_uiManager.start();
	}

	// --- Start GETTERS
	public List<String> getUsersName() {
		if (m_usersManager != null) {
			return m_usersManager.getUsersName();
		}
		return null;
	}

	public List<User> getUsers() {
		if (m_usersManager != null) {
			return m_usersManager.getUsers();
		}
		return null;
	}

	public User getCurrentUser() {
		if (m_usersManager != null) {
			return m_usersManager.getCurrentUser();
		}
		return null;
	}
	// --- End GETTERS

    // region Misc
    public void close() {
		Log.printMessage("Close");
		m_usersManager.close();
    }

	public void createNewUser(String _userName, String _imagePath) {
		boolean succeeded = m_usersManager.addUser(_userName, _imagePath);
		if (succeeded) {
			m_uiManager.reload();
		}
	}

	public void connectUser(User _user) {
		if (m_usersManager != null) {
			m_usersManager.setCurrentUser(_user);
			m_uiManager.loadWelcome();
		}
	}

	public void disconnectCurrentUser() {
		 m_usersManager.disconnectCurrentUser();
		 m_uiManager.switchToLogIn();
	}

    public void deleteCurrentUser() {
		m_usersManager.deleteCurrentUser();
		m_uiManager.switchToLogIn();
    }

//	public void startUI() {
//		m_uiManager.open();
//	}
//
//	public void stopUI() {
//		m_uiManager.stop();
//	}
	// endregion Misc
}