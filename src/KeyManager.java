/**
 * 
 * this class is used to make the hero to move smoothly
 * 
 * @author yuanx
 * 
 */
public class KeyManager {
	public int[] directionKeys;

	KeyManager() {
		this.directionKeys = new int[4];
	}

	public void pressDirection(char c) {
		int d = 0;
		switch (c) {
		case 'u':
			d = 0;
			break;
		case 'd':
			d = 1;
			break;
		case 'l':
			d = 2;
			break;
		case 'r':
			d = 3;
			break;
		}
		for (int i : this.directionKeys) {
			if (i > 0)
				i--;
		}
		this.directionKeys[d] = 3;
	}

	public void releaseDirection(char c) {
		int d = 0;
		switch (c) {
		case 'u':
			d = 0;
			break;
		case 'd':
			d = 1;
			break;
		case 'l':
			d = 2;
			break;
		case 'r':
			d = 3;
			break;
		}
		this.directionKeys[d] = 0;
	}

	/**
	 * 
	 * get the direction
	 * 
	 * @return
	 */
	public char getDirection() {
		int d = -1;
		for (int i = 0; i < 4; i++) {
			if (this.directionKeys[i] == 3) {
				d = i;
			}
		}
		switch (d) {
		case -1:
			return 's';
		case 0:
			return 'u';
		case 1:
			return 'd';
		case 2:
			return 'l';
		case 3:
			return 'r';
		}
		return ' ';
	}
}
