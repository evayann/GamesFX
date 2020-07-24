/**
 * Project, Notification
 * @version 0.2019
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.user;

// region Imports

import GamesFX.games.Parameters;
import GamesFX.saver.Saver;

import java.io.File;
import java.io.Serializable;
// endregion Imports

public class User implements Serializable {

	// region Attributes
	private static final long serialVersionUID = 5740874292400933575L;

	private String m_name;
	private String m_profilPicturePath;
	private Parameters m_parameters;
	public static String DEFAULT_PICTURE = new File("src/ressources/login/profil.png").getAbsolutePath();
	// endregion Attributes

	// region Methods
	public User(String _name) {
		m_name = _name;

		m_profilPicturePath = DEFAULT_PICTURE;
		m_parameters = new Parameters();
	}

	public User(String _name, String _path) {
		this(_name);
		setPicturePath(_path);
	}

	// region Setters
	public void setPicturePath(String _path) {
		// Copy picture to always can display it
		if (!_path.equals(User.DEFAULT_PICTURE))
			_path = Saver.copy(_path);
		// Remove previous copy of picture to save storage
		if (!m_profilPicturePath.equals(User.DEFAULT_PICTURE))
			Saver.delete(m_profilPicturePath);

		m_profilPicturePath = _path;
	}

	public void setName(String _name) {
		m_name = _name;
	}
	// endregion Setters

	// region Getters
	public String getName() {
		return m_name;
	}

	public String getPicturePath() {
		return m_profilPicturePath;
	}

	public Parameters getParameters() {
		return m_parameters;
	}
    // endregion Getters
	// endregion Methods
}