/**
 * Project, Notification
 * @version 0.2019
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.user;

import GamesFX.saver.Saver;

import java.util.ArrayList;
import java.util.List;

public class UsersManager {

	// region Singleton
	private static UsersManager m_um;

	public static UsersManager getInstance() {
		if (m_um == null) {
			m_um = new UsersManager();
		}
		return m_um;
	}

	private UsersManager() { // Make private constructor to don't have 2 instance of singleton
		m_users = Saver.loadUser();
	}
	// endregion Singleton

	// region Attributes
	private List<User> m_users;
	private User m_currentUser;
	// endregion Attributes

	// region Methods
	public void initialize() {}

	public boolean addUser(String _name, String _imagePath) {
		boolean b = m_users.add(new User(_name, _imagePath));
		Saver.save(m_users);
		return b;
	}

	public void setCurrentUser(User _user) {
		m_currentUser = _user;
	}

	public void disconnectCurrentUser() {
		setCurrentUser(null);
	}

	public void deleteCurrentUser() {
		Saver.deleteUser(m_currentUser);
		disconnectCurrentUser();
		m_users = Saver.loadUser();
	}

	public void close() {
		Saver.save(m_users);
	}

	// region Getters
	public List<String> getUsersName() {
		List<String> usersName = new ArrayList<>();
		for (User u : m_users) {
			usersName.add(u.getName());
		}
		return usersName;
	}

	public List<User> getUsers() {
		return m_users;
	}

	public User getCurrentUser() {
		return m_currentUser;
	}
	// endregion Getters
	// endregion Methods
}