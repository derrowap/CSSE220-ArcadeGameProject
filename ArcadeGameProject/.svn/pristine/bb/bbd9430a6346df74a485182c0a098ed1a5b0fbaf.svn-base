import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Scans a text file and loads the level onto the GUI
 */
public class LevelLoader {
	private String fileName;
	private char[][] info;

	public LevelLoader(int level) {
		this.fileName = "level" + level + ".txt";
		Scanner scan = null;
		try {
			scan = new Scanner(new File(this.fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
			e.printStackTrace();
		}
		this.info = new char[10][15];
		int i = 0;
		while (scan.hasNext()) {
			this.info[i] = scan.next().toCharArray();
			i++;
		}
		scan.close();
	}

	/**
	 * Get the information of the level
	 * 
	 * @return a 2D array of char, 'D' stands for Dirt, 'E' stands for emerald,
	 *         'H' stand for Hero.
	 */
	public char[][] getInfo() {
		return this.info;
	}

	/**
	 * return a Stirng of the info
	 */
	public String toString() {
		String result = new String();
		for (char[] str : info) {
			for (char c : str) {
				result += c;
			}
			result += "\n";
		}
		return result;
	}
}
