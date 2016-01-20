import java.awt.geom.Point2D;
import java.util.ArrayList;

/*
 * Keeps track of any space that has been dug through or was initially
 * an empty space.
 */

public class Tunnel {
	private ArrayList<EmptySpace> emptySpaces;
	private boolean[][] map;
	private boolean[][] pixels;

	public Tunnel() {
		this.emptySpaces = new ArrayList<EmptySpace>();
		this.map = new boolean[15][15];
		this.pixels = new boolean[2000][2000];
	}

	/**
	 * Add empty space to the tunnel
	 * 
	 * @param o
	 */
	public void addEmptySpaces(EmptySpace o) {
		this.emptySpaces.add(o);
		this.map[(int) (o.getPosition().getX() / 72)][(int) (o.getPosition()
				.getY() / 72)] = true;
		for(int i = 0; i < 72; i++){
			for(int j = 0; j < 72; j++ ){
				if(i+o.getPosition().getX() < 0 || j+o.getPosition().getY() < 0) continue;
				this.pixels[(int) (i + o.getPosition().getX())][(int) (j + o.getPosition().getY())] = true;
			}
		}
	}

	/**
	 * 
	 * check if an object is inside of an empty space
	 * 
	 * @param o
	 * @return true if an object is inside an empty space
	 */
	public boolean isInEmptySpace(ObjectInWorld o) {
		int thisX = (int) o.getPosition().getX();
		int thisY = (int) o.getPosition().getY();
		if (thisX % 72 == 0) {
			if (thisY % 72 == 0) {
				return this.map[thisX / 72][thisY / 72];
			} else {
				return this.map[thisX / 72][thisY / 72]
						&& this.map[thisX / 72][(thisY / 72) + 1];
			}
		}
		if (thisY % 72 == 0) {
			if (thisX % 72 == 0) {
				return this.map[thisX / 72][thisY / 72];
			} else {
				return this.map[thisX / 72][thisY / 72]
						&& this.map[(thisX / 72) + 1][thisY / 72];
			}
		}
		return false;
	}

	/**
	 * 
	 * check if the point in an empty space block
	 * 
	 * @param point
	 * @return true if the point is in an empty space block
	 */
	public boolean isPositionEmptySpaceUnder(Point2D point) { 
		for(int i = 0; i < 72; i++){
			if(!this.pixels[(int) point.getX() + i][(int) (point.getY() + 72)]){
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * get direction of the shortest path from the monster to hero only through
	 * the tunnel not the dirt
	 * 
	 * @param hero
	 * @param monster
	 * @return direction
	 */
	public char getShortestWay(Hero hero, Monster monster) {
		if (hero == null) {
			return 's';
		}
		char direction = 'u';
		int heroX = (int) hero.getGridPosition().getX();
		int heroY = (int) hero.getGridPosition().getY();
		int monsterX = (int) monster.getGridPosition().getX();
		int monsterY = (int) monster.getGridPosition().getY();
		int monsterIndex = 0;
		int total = 0;
		class Node {
			int x;
			int y;
			int index;

			Node(int x, int y, int index) {
				this.x = x;
				this.y = y;
				this.index = index;
			}
		}
		ArrayList<Node> unVisited = new ArrayList<Node>();
		ArrayList<Node> graph = new ArrayList<Node>();
		Node source = new Node(heroX, heroY, 0);
		graph.add(source);
		unVisited.add(source);
		total = 1;
		int[] dist = new int[300];
		int[] prev = new int[300];
		dist[0] = 0;
		prev[0] = -1;
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[i].length; j++) {
				if (this.map[i][j] == true) {
					if (i != heroX || j != heroY) {
						Node newNode = new Node(i, j, total);
						if (i == monsterX && j == monsterY) {
							monsterIndex = total;
						}
						dist[total] = 100000;
						prev[total] = -1;
						unVisited.add(newNode);
						graph.add(newNode);
						total++;
					}
				}
			}
		}
		while (!unVisited.isEmpty()) {
			Node u = null;
			int mindist = 1000000;
			for (Node n : unVisited) {
				if (mindist > dist[n.index]) {
					mindist = dist[n.index];
					u = n;
				}
			}
			unVisited.remove(u);
			for (Node n : unVisited) {
				if ((u.x == n.x && Math.abs(u.y - n.y) == 1)
						|| (Math.abs(u.x - n.x) == 1 && u.y == n.y)) {
					int alt = dist[u.index] + 1;
					if (alt < dist[n.index]) {
						dist[n.index] = alt;
						prev[n.index] = u.index;
					}
				}
			}
		}
		if (prev[monsterIndex] == -1) {
			return 's';
		}
		Node nextNode = graph.get(prev[monsterIndex]);
		Node monsterNode = graph.get(monsterIndex);
		if (nextNode.x == monsterNode.x) {
			if (nextNode.y > monsterNode.y) {
				direction = 'r';
			} else {
				direction = 'l';
			}
		} else {
			if (nextNode.x > monsterNode.x) {
				direction = 'd';
			} else {
				direction = 'u';
			}
		}
		return direction;
	}
}
