package GamesFX.gui.utility;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

import java.io.File;

public class UtilityFX {

    private static String CSS;
    public static void setCSS(String css) {
        CSS = css;
    }

    /**
     * Alert constructor
     * @param type
     * @param title
     * @param header
     * @param content
     * @return
     */
    private static Alert alertConstructor(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (CSS != null) {
            alert.getDialogPane().getStylesheets().clear();
            alert.getDialogPane().getStylesheets().add(CSS);
        }

        return alert;
    }

    /**
     * Alert constructor
     * @param type
     * @param title
     * @param header
     * @param content
     * @param buttonTypes
     * @return
     */
    private static Alert alertConstructor(AlertType type, String title, String header, String content, ButtonType... buttonTypes) {
        Alert alert = alertConstructor(type, title, header, content);

        alert.getDialogPane().getButtonTypes().clear();
        alert.getDialogPane().getButtonTypes().addAll(buttonTypes);
        alert.getDialogPane().getStyleClass().clear();
        alert.getDialogPane().getStyleClass().add("alertPane");

        // Add custom id
        for (ButtonType bt: buttonTypes) {
            Button button = (Button) alert.getDialogPane().lookupButton(bt);
            if (bt == ButtonType.YES) {
                button.getStyleClass().clear();
                button.getStyleClass().add("alertValid");
                button.setAlignment(Pos.CENTER);
            }
            else if (bt == ButtonType.NO) {
                button.getStyleClass().clear();
                button.getStyleClass().add("alertCancel");
                button.setAlignment(Pos.CENTER);
            }
            else if (bt == ButtonType.OK) {
                button.getStyleClass().clear();
                button.getStyleClass().add("alertOk");
                button.setAlignment(Pos.CENTER);
            }
        }
        return alert;
    }

    /**
     * Create a new alert
     * @param type the type of alert
     * @param title title of alert
     * @param header header information about alert
     * @param content content of alert
     */
    public static void alert(AlertType type, String title, String header, String content) {
        alertConstructor(type, title, header, content, ButtonType.OK).show();
    }

    /**
     * Create a new alert and wait an action
     * @param type the type of alert
     * @param title title of alert
     * @param header header information about alert
     * @param content content of alert
     */
    public static ButtonType alert(AlertType type, String title, String header, String content, ButtonType... buttonTypes) {
        return alertConstructor(type, title, header, content, buttonTypes).showAndWait().orElse(null);
    }

    public static File fileChooser(String title, String extName, String... extensions) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        FileChooser.ExtensionFilter exts = new FileChooser.ExtensionFilter(extName, extensions);
        fc.getExtensionFilters().add (exts);
        return fc.showOpenDialog(null); // To create a new window
    }

    public static void computeGrid(GridPane _gp, int _width, int _height) {
        _gp.getColumnConstraints().clear();
        _gp.getRowConstraints().clear();

        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(100 / (float) _width);
        for (int x = 0; x < _width; x++)
            _gp.getColumnConstraints().add(cc);


        RowConstraints rc = new RowConstraints();
        rc.setPercentHeight(100 / (float) _height);
        for (int y = 0; y < _height ; y++)
            _gp.getRowConstraints().add(rc);
    }

}
