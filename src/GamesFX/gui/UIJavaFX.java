/**
 * Project, Notification
 * @version 0.2019
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui;

import GamesFX.EventManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import GamesFX.utility.Log;

import java.util.Locale;

public class UIJavaFX extends Application {

	public static void launchJavaFX() {
		Log.printMessage("Launch JavaFX application");
		launch();
	}

	// region Override
	@Override
	public void start(Stage _primaryStage) {
		Log.printMessage("Set stage on UIManager");

		Locale.setDefault(Locale.FRANCE);
		Platform.setImplicitExit(true);

		UIManager.getInstance().initializeStage(_primaryStage);
	}

	@Override
	public void stop() {
		EventManager.getInstance().close();
	}
	// endregion Override
}
