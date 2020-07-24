/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.games;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class Case extends Button {

    public int x, y;
    private Object m_data;

    public Case(int _x, int _y) {
        super();
        x = _x;
        y = _y;
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setAlignment(Pos.CENTER);
        setCaseStyle();
    }

    public Case(int _x, int _y, Object _data) {
        this(_x, _y);
        setData(_data);
    }

    public void addStyle(String... _styles) {
        getStyleClass().addAll(_styles);
    }

    public void setData(Object _data) {
        m_data = _data;
    }

    public void setCaseStyle(String... _styles) {
        getStyleClass().clear();
        getStyleClass().add("case");
        getStyleClass().addAll(_styles);
    }

    public Object getData() {
        return m_data;
    }

    @Override
    public boolean equals(Object _object) {
        // Compare to himself
        if (_object == this) {
            return true;
        }

        // Check type of _object
        if (!(_object instanceof Case)) {
            return false;
        }

        Case other = (Case) _object;
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "Case :" + x + ", " + y;
    }
}
