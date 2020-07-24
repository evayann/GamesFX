package GamesFX.gui.utility;

// region Imports

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
// endregion Imports

public class PopWindow {

    // region Attributes
    private static Map<String, PopWindow> IDS_POPWINDOW = new HashMap();


    private Stage m_stage;
    private Region m_region;
    private Scene m_scene;
    private String m_id;
    // endregion Attributes

    // region Methods
    // region Statics
    public static boolean close(String _id) {
        for (Map.Entry<String, PopWindow> entry : IDS_POPWINDOW.entrySet()) {
            if (entry.getKey().equals(_id)) {
                entry.getValue().close();
                return true;
            }
        }
        return false;
    }
    // endregion Statics

    // region Constructors
    /**
     * Instantiate a PopWindow. Can be place on the full application if you give
     * the node add to your scene or place on an others node.
     */
    public PopWindow(String _id) {
        m_stage = new Stage();
        m_id = _id;
        IDS_POPWINDOW.put(_id, this);
    }

    /**
     * Instantiate a PopWindow. Can be place on the full application if you give
     * the node add to your scene or place on an others node.
     * @param _fxmlPath path of FXML file
     */
    public PopWindow(String _id, String _fxmlPath) {
        this(_id);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(_fxmlPath));
            m_region = fxmlLoader.load();
//            m_regionController = fxmlLoader.getController();
        }
        catch (IOException e) {
            System.err.println("Error during fxml loading, more information : \n" + e.toString());
        }

        m_scene = new Scene(m_region);
        m_stage.setScene(m_scene);
    }

    /**
     * Instantiate a PopWindow. Can be place on the full application if you give
     * the node add to your scene or place on an others node.
     * @param _fxmlPath
     * @param _title
     */
    public PopWindow(String _id, String _fxmlPath, String _title) {
        this(_id, _fxmlPath);
        m_stage.setTitle(_title);
    }

    /**
     * Instantiate a PopWindow. Can be place on the full application if you give
     * the node add to your scene or place on an others node.
     * @param _fxmlPath
     * @param _css
     * @param _title
     */
    public PopWindow(String _id, String _fxmlPath, String _css, String _title) {
        this(_id, _fxmlPath, _title);
        m_scene.getStylesheets().add(_css);
    }
    // endregion Constructors

    public void show() {
        m_stage.show();
    }

    private void close() {
        m_stage.close();
    }

    // endregion Methods

}
