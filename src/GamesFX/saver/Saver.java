/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.saver;

import GamesFX.user.User;
import GamesFX.utility.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Saver {

	private static String OS = System.getProperty("os.name").toLowerCase();
	private static final String USER_FILE = "User_";
	private static final String EXTENSION = ".serialize";

	private static String getPath() {
		String path = System.getProperty("user.home");
		if (OS.contains("mac") || OS.contains("darwin") || OS.contains("nux")) {
			path += "/.config/GamesFX/";
		}
		else { // Window
			path += "\\GamesFX\\";
		}
		return path;
	}

	/**
	 * Copy a file on directory for GamesFX.
	 * @param _path The path of file to copy.
	 * @return The path of copy.
	 */
	public static String copy(String _path) {
		String copyString = getPath() + Paths.get(_path).getFileName().toString();
		Path copied = Paths.get(copyString);

		try {
			Files.copy(Paths.get(_path), copied, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyString;
	}

	public static boolean delete(String _path) {
		return new File(_path).delete();
	}

	/**
	 * Save all users on different files
	 * @param users The list of users to save.
	 * @return A boolean to indicate if all users is correctly save.
	 */
	public static boolean save(List<User> users) {
		try {
    		for (User u : users) {
    			File f = new File( Saver.getPath() + USER_FILE + u.getName() + EXTENSION);
    			f.createNewFile(); // If file not exist
				FileOutputStream file  = new FileOutputStream(f);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(u);
    			out.close();
                file.close();
    		}
            return true;
		}
        catch(IOException ex) {
            Log.printError("IOException is caught");
            ex.printStackTrace();
        }
		return false;
	}

	/**
	 * Load all user on files and return the list.
	 * @return List of user correctly loaded
	 */
	public static List<User> loadUser() {
		File pathFile = new File(Saver.getPath());
		List<User> users = new ArrayList<>();
		try {
			for (File f : pathFile.listFiles()) {
				// Don't load anything else of User
				if (!f.getName().contains("User"))
					continue;
				FileInputStream file = new FileInputStream(f);
		        ObjectInputStream in = new ObjectInputStream(file);
		        users.add((User) in.readObject());
		        in.close();
		        file.close();
			}
		}
		catch (IOException ex) {
			Log.printError("IOException is caught");
        }
        catch (ClassNotFoundException ex) {
        	Log.printError("ClassNotFoundException is caught");
        }
		catch (NullPointerException ex) {
			Log.printError("NullPointerException is caught");
		}
		return users;
	}

	/**
	 * Delete a GamesFX user.
	 * @param _user The user to delete.
	 * @return A boolean to indicate if user is really delete
	 */
	public static boolean deleteUser(User _user) {
		return Saver.delete(getPath() + USER_FILE + _user.getName() + EXTENSION) && Saver.delete(_user.getPicturePath());
	}
}
