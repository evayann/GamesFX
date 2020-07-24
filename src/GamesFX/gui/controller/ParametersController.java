/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.gui.controller;

// region Imports

import GamesFX.games.Parameters;
import GamesFX.gui.UIManager;
import GamesFX.gui.utility.UtilityFX;
import GamesFX.saver.Saver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import GamesFX.user.User;

import java.io.File;
// endregion Imports

interface Update {
    void compute();
}

class SpinnerUpdate extends SpinnerValueFactory.IntegerSpinnerValueFactory {

    Update m_func;

    public SpinnerUpdate(int min, int max, int initialValue, Update _func) {
        super(min, max, initialValue);
        m_func = _func;
    }

    @Override
    public void increment(int _step) {
        super.increment(_step);
        if (m_func != null)
            m_func.compute();
    }

    @Override
    public void decrement(int _step) {
        super.decrement(_step);
        if (m_func != null)
            m_func.compute();
    }
}

public class ParametersController implements Controller {

    // region Attributes
    private UIManager  m_uiManager;
    private Parameters m_parameters;
    private String     m_imgPath;

    // region FXML
    @FXML
    private Spinner<Integer> m_msWidth;
    @FXML
    private Spinner<Integer> m_msHeight;
    @FXML
    private Spinner<Integer> m_msBombs;
    @FXML
    private Spinner<Integer> m_nnWidth;
    @FXML
    private Spinner<Integer> m_nnHeight;
    @FXML
    private ImageView m_userPicture;
    @FXML
    private TextField m_userName;
    // endregion FXML
    // endregion Attributes

    // region Methods
    @FXML
    void initialize() {
        m_uiManager = UIManager.getInstance();
    }

    @FXML
    void save() {
        if (m_parameters.getNonogramWidth() != m_msWidth.getValue() ||
                m_parameters.getNonogramHeight() != m_msHeight.getValue() ||
                m_parameters.getMineSweeperBombs() != m_msBombs.getValue()) {
            m_parameters.setMineSweeperWidth(m_msWidth.getValue());
            m_parameters.setMineSweeperHeight(m_msHeight.getValue());
            m_parameters.setMineSweeperBombs(m_msBombs.getValue());
            m_uiManager.resetMineSweeper();
        }
        if (m_parameters.getNonogramWidth() != m_nnWidth.getValue() ||
                m_parameters.getNonogramHeight() != m_nnHeight.getValue()) {
            m_parameters.setNonogramWidth(m_nnWidth.getValue());
            m_parameters.setNonogramHeight(m_nnHeight.getValue());
            m_uiManager.resetNonogram();
        }

        User u = m_uiManager.getCurrentUser();
        u.setName(m_userName.getText());
        if (m_imgPath != null) {
            u.setPicturePath(m_imgPath);
            m_imgPath = null;
        }
        m_uiManager.resetMenu();
        Saver.save(m_uiManager.getUsers());
    }

    @FXML
    void updatePicture() {
        File f = UtilityFX.fileChooser("Your image", "Images", "*.png", "*.jpg", "*.gif");
        if (f == null)
            return;
        m_userPicture.setImage(new Image("file:" + f.getAbsolutePath()));
        m_imgPath = f.getAbsolutePath();
    }

    @FXML
    void deleteAccount() {
        String text = "Delete your account ?";
        ButtonType bt = UtilityFX.alert(Alert.AlertType.WARNING, text, text,
                "Are you sure to delete the account " + m_uiManager.getCurrentUser().getName() + " definitively ?",
                ButtonType.YES, ButtonType.NO);

        if (bt.equals(ButtonType.NO))
            return;

        m_uiManager.deleteCurrentUser();
    }

    // region Override
    @Override
    public void load() {
        User u = m_uiManager.getCurrentUser();
        if (u == null)
            return;

        m_userPicture.setImage(new Image("file:" + u.getPicturePath()));
        m_userName.setText(u.getName());

        m_parameters = u.getParameters();
        Update update = () -> {
            SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                    1, m_msWidth.getValue() * m_msHeight.getValue(),
                    Math.min(m_msBombs.getValue(), m_msWidth.getValue() * m_msHeight.getValue()));
            m_msBombs.setValueFactory(svf);
        };

        // MineSweeper
        SpinnerValueFactory<Integer> msWidthFact = new SpinnerUpdate(1, 20, m_parameters.getMineSweeperWidth(), update);
        m_msWidth.setValueFactory(msWidthFact);

        SpinnerValueFactory<Integer> msHeightFact = new SpinnerUpdate(1, 20, m_parameters.getMineSweeperHeight(), update);
        m_msHeight.setValueFactory(msHeightFact);

        // Set Initial value of bombs
        SpinnerValueFactory<Integer> msBombsFact = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, m_msWidth.getValue() * m_msWidth.getValue(), m_parameters.getMineSweeperBombs());
        m_msBombs.setValueFactory(msBombsFact);
        update.compute();

        // Nonogram
        SpinnerValueFactory<Integer> nnWidthFact = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        nnWidthFact.setValue(m_parameters.getNonogramWidth());
        m_nnWidth.setValueFactory(nnWidthFact);

        SpinnerValueFactory<Integer> nnHeightFact = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        nnHeightFact.setValue(m_parameters.getNonogramHeight());
        m_nnHeight.setValueFactory(nnHeightFact);
    }
    // endregion Override
    // endregion Methods
}
