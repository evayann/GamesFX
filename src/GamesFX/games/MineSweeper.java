/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.games;

import GamesFX.gui.controller.MineSweeperController;

import java.util.List;
import java.util.Random;

public class MineSweeper {

    private MineSweeperController m_msc;

    private int m_width;
    private int m_height;
    private int m_nbMines;

    public static int MINE = -1;
    private int[][] m_mines;
    private boolean[][] m_revealed;

    private Random rdm;

    public MineSweeper(MineSweeperController _msc, int _width, int _height, int _nbBombs) {
        m_msc = _msc;
        m_width = _width;
        m_height = _height;
        m_nbMines = _nbBombs;
        create();
    }

    public void create() {
        rdm = new Random();
        // Initalize 2D tab
        m_mines = new int[m_width][m_height];
        m_revealed = new boolean[m_width][m_height];
        computeMines();
        computeNeigboursMines();
    }

    private void computeMines() {
        int currNbMine = 0;
        while(currNbMine < m_nbMines){
            int x = rdm.nextInt(m_width);
            int y = rdm.nextInt(m_height);

            if (m_mines[x][y] == MINE)
                continue; // Already Mine here

            m_mines[x][y] = MINE;
            currNbMine++;
        }
    }

    private int neighboursMines(int _x, int _y) {
        if(outBounds(_x, _y))
            return 0;

        int i = 0;
        for (int x = _x - 1; x <= _x + 1; x++) {
            for (int y = _y - 1; y <= _y + 1; y++) {
                if (outBounds(x, y))
                    continue;

                if (m_mines[x][y] == MINE)
                    i += 1;
            }
        }
        return i;
    }

    private void computeNeigboursMines() {
        for (int x = 0; x < m_width; x++) {
            for (int y = 0; y < m_height; y++) {
                if (m_mines[x][y] != MINE)
                    m_mines[x][y] = neighboursMines(x, y);
            }
        }
    }


    public boolean isDiscover(int _x, int _y) {
        return m_revealed[_x][_y];
    }

    public boolean isMine(int _x, int _y) {
        return m_mines[_x][_y] == MINE;
    }

    public boolean allMinesCover(List<Case> _coverPos) {
        int bombCounter = 0;
        for (Case flag : _coverPos){
            if (m_mines[flag.x][flag.y] == MINE)
                bombCounter++;
        }
        return bombCounter == m_nbMines;
    }

    public int getNbMines() {
        return m_nbMines;
    }

    boolean outBounds(int _x,int _y){
        return _x < 0 || _y < 0 || _x >= m_width || _y >= m_height;
    }

    public void update(int _x, int _y){
        if (outBounds(_x,_y))
            return;
        if (m_revealed[_x][_y])
            return;

        m_revealed[_x][_y] = true;
        m_msc.updateButton(_x, _y, m_mines[_x][_y]);

        if(isMine(_x,_y)) {
            m_msc.gameOver(false);
            return;
        }

        else if (m_mines[_x][_y] != 0)
            return;

        for (int x=-1; x <= 1; x++)
            for (int y=-1; y <= 1; y++)
                update(_x + x, _y + y);
    }

    public int reveal(int _x, int _y) {
        return m_mines[_x][_y];
    }
}
