/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.games;

import java.io.Serializable;

public class Parameters implements Serializable {

    private static final long serialVersionUID = 5740874292400933575L;

    // MineSweeper
    private int mineSweeperWidth;
    private int mineSweeperHeight;
    private int mineSweeperBombs;

    // Nonogram
    private int nonogramWidth;
    private int nonogramHeight;

    public Parameters() {
        // MineSweeper
        mineSweeperWidth  = 20;
        mineSweeperHeight = 20;
        mineSweeperBombs  = 20;

        // Nonogram
        nonogramWidth  = 6;
        nonogramHeight = 6;
    }

    public void setMineSweeperBombs(int _mineSweeperBombs) {
        mineSweeperBombs = _mineSweeperBombs;
    }

    public void setMineSweeperHeight(int _mineSweeperHeight) {
        mineSweeperHeight = _mineSweeperHeight;
    }

    public void setMineSweeperWidth(int _mineSweeperWidth) {
        mineSweeperWidth = _mineSweeperWidth;
    }

    public void setNonogramHeight(int _nonogramHeight) {
        nonogramHeight = _nonogramHeight;
    }

    public void setNonogramWidth(int _nonogramWidth) {
        nonogramWidth = _nonogramWidth;
    }

    // region Getters
    public int getMineSweeperBombs() {
        return mineSweeperBombs;
    }

    public int getMineSweeperHeight() {
        return mineSweeperHeight;
    }

    public int getMineSweeperWidth() {
        return mineSweeperWidth;
    }

    public int getNonogramHeight() {
        return nonogramHeight;
    }

    public int getNonogramWidth() {
        return nonogramWidth;
    }
    // endregion Getters
}
