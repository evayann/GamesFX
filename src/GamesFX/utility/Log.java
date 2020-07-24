package GamesFX.utility;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project, Notification
 * @version 0.2019
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */
public class Log {

	private static Logger logger = Logger.getLogger(Log.class.toString()); 
	
	private static boolean m_display = true;
	
	private static void displayMessage(String _message, Level _level) {
		logger.log(_level, _message);
	}
	
	public static void setLogdisplay(boolean _bool) {
		m_display = _bool;
	}
	
	public static void printError(String _error) {
		if (m_display) {
			displayMessage(_error, Level.SEVERE);
		}
	}
	
	public static void printMessage(String _message) {
//		displayMessage(message, Level.INFO);
		if (m_display) {
			System.out.println(_message);
		}
	}
}
