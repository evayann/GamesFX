/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports

import GamesFX.gui.UIManager;
import GamesFX.gui.utility.PopWindow;
import GamesFX.user.User;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;
// endregion Imports

public class LogInController implements Controller {

    // region Attributes
    @FXML
    private GridPane m_availableUsers;

    private UIManager m_uiManager;
    private List<User> m_users;
    private double m_profilePictureSize;

    private int m_nbMaxUser;
    private int m_row;
    private int m_column;
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        m_uiManager = UIManager.getInstance();
        m_profilePictureSize = 150;
        m_nbMaxUser = 6;
    }

    private void computeUsers() {
        m_users = m_uiManager.getUsers();
        m_availableUsers.getChildren().clear();

        // Initial GridPane, 1 x 1 (column x row)
        Button addButton = new Button("Add new player");
        addButton.setOnMouseClicked(event -> displayPopUpNewUser());
        ImageView im = createProfileImage("./ressources/login/addPerson.png");
        addButton.setGraphic(im);
        addButton.setContentDisplay(ContentDisplay.TOP);
        addButton.getStyleClass().add("UserButton");

        if (m_users.size() == 0) {
            m_column = 1;
            m_row = 1;

            m_availableUsers.add(addButton, 0, 0);
        }
        else if (m_users.size() == 1) {
            // Add to make 2 columns
            m_availableUsers.addColumn(1);
            m_column = 2;
            m_row = 2;

            displayUser(m_users.get(0), 0);
            m_availableUsers.add(addButton, 1, 0);
        }
        else {
            // Add to make 3 columns
            m_availableUsers.addColumn(1);
            m_availableUsers.addColumn(2);
            m_column = 3;
            m_row = 0; // Start at 0 to add first row bellow

            int i;
            for (i = 0; i < m_users.size(); i++) {
                displayUser(m_users.get(i), i);

                if ((i % 3) == 0) {
                    m_availableUsers.addRow(i / 3);
                    m_row++;
                }
            }


            // Add new GamesFX.user button only if we don't reach max GamesFX.user
            if (m_nbMaxUser > m_users.size()) {
                m_row = (i % 3) == 0 ? m_row + 1: m_row;  // Increment if we pass to next row for add button
                m_availableUsers.add(addButton, i % 3, i / 3);
            }
        }
        centerGridPane(addButton);
        percentSizeGridPane();
    }

    private void displayUser(User _user, int _userNumber) {
        try {
            Button b = new Button(_user.getName());
            b.setGraphic(createProfileImage("file:" + _user.getPicturePath()));
            b.setContentDisplay(ContentDisplay.TOP);
            b.setOnAction(event -> m_uiManager.connectUser(_user));
            b.getStyleClass().add("UserButton");
            m_availableUsers.add(b, _userNumber % 3, _userNumber / 3);
            centerGridPane(b);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ImageView createProfileImage(String _path) {
        ImageView im = new ImageView(new Image(_path));
        im.setFitWidth(m_profilePictureSize);
        im.setFitHeight(m_profilePictureSize);
        return im;
    }

    private void centerGridPane(Node... _nodesToCenter) {
        for (Node n: _nodesToCenter) {
            GridPane.setHalignment(n, HPos.CENTER);
            GridPane.setValignment(n, VPos.CENTER);
        }
    }

    private void percentSizeGridPane() {
        // Clear Constraints
        m_availableUsers.getRowConstraints().clear();
        m_availableUsers.getColumnConstraints().clear();

        // Set new constraints
        for (int i = 0; i < m_row; i++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight((float) 100 / m_row);
            m_availableUsers.getRowConstraints().add(rc);
        }
        for (int i = 0; i < m_column; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth((float) 100 / m_column);
            m_availableUsers.getColumnConstraints().add(cc);
        }
    }

    private void displayPopUpNewUser() {
        new PopWindow(UIManager.CREATE_USER_ID, "GamesFX/gui/fxml/createUser.fxml", UIManager.CSS, "Create new User").show();
    }

    // region Override
    @Override
    public void load() {
        computeUsers();
    }
    // endregion Override
    // endregion Methods
}
