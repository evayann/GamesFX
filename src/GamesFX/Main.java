package GamesFX; /**
 * Project, GamesFX
 * @version 0.2020
 * @author Yann Zavattero
 * http://yann.fzcommunication.fr/index.php/
 */

import GamesFX.utility.Log;

public class Main {

    public static void main(String[] args) {
    	Log.setLogdisplay(true);
		EventManager em = EventManager.getInstance();
		em.initialize();
		em.start();
    }
}