/**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

package GamesFX.games;

import java.util.List;
import java.util.Random;

public class Nonogram {


    private int m_width;
    private int m_height;
    private boolean[][] m_nonogram;
    private int m_nbColoredZone;

    private Random rdm;

    public Nonogram(int _width, int _height) {
        m_width = _width;
        m_height = _height;
        create();
    }

    void create() {
        rdm = new Random();

        m_nbColoredZone = 0;
        m_nonogram = new boolean[m_width][m_height];

        int curr, maxIteration, iter;
        for (int x = 0; x < m_width; x++) {
            curr = 0;
            iter = 0;
            maxIteration = rdm.nextInt(m_height / 2);
            while (curr < m_height && iter < maxIteration) {
                int rdmNb = rdm.nextInt(m_height - curr);
                for (int j = 0; j < rdmNb; j++) {
                    m_nonogram[x][curr + j] = true;
                }
                iter++;
                m_nbColoredZone += rdmNb;
                curr += ++rdmNb; // Add one before add to current to have the space between 2 series
            }
        }
    }

    private String[] getIndices(boolean _width) {
        int first, second;
        if (_width) {
            first = m_width;
            second = m_height;
        }
        else {
            first = m_height;
            second = m_width;
        }

        String[] indices = new String[first];
        for (int i = 0; i < first; i++) {
            String str = "";
            int count = 0;
            for (int j = 0; j < second; j++) {
                boolean color = _width ? m_nonogram[i][j] : m_nonogram[j][i];
                if (color) {
                    count++;
                }
                else if (count != 0) {
                    str += count + " ";
                    count = 0;
                }
            }
            // Add last one (if we finish by a true)
            if (count != 0)
                str += count + " ";

            indices[i] = str;
        }
        return indices;
    }

    public String[] getColsIndices() {
        return getIndices(true);
    }

    public String[] getRowsIndices() {
        return getIndices(false);
    }

    public boolean isDiscover(List<Case> _casesColored) {
        int counter = 0;
        for (Case c : _casesColored) {
            if (m_nonogram[c.x][c.y])
                counter++;
            else
                return false;
        }
        return counter == m_nbColoredZone;
    }

    public boolean isColored(int _x, int _y) {
        return m_nonogram[_x][_y];
    }
}

