import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;

/*
 * World that all objects are drawn on
 */
public class World implements Temporal {
	private final static int Block_Size = 72;
	private char[][] info;
	private int level;
	private Hero hero;
	private boolean isPaused = false;
	private ArrayList<Dirt> dirts;
	private ArrayList<Dirt> dirtsToClear;
	private ArrayList<FootPrint> footprints;
	private ArrayList<FootPrint> footprintsToAdd;
	private ArrayList<Monster> enemies;
	private ArrayList<Monster> enemiesToClear;
	private ArrayList<ObjectInWorld> blocks;
	private ArrayList<ObjectInWorld> blocksToClear;
	private ArrayList<ObjectInWorld> blocksToAdd;
	private Tunnel tunnel;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> bulletsToClear;
	private ArrayList<Bag> bags;
	private ArrayList<Bag> bagsToClear;
	public KeyManager keymanager;
	private ArrayList<Monster> enemiesToAdd;

	public World(int level, int score, int lives) {
		this.level = level;
		LevelLoader ll = new LevelLoader(level);
		this.info = ll.getInfo();
		this.tunnel = new Tunnel();
		this.blocks = new ArrayList<ObjectInWorld>();
		this.enemies = new ArrayList<Monster>();
		this.keymanager = new KeyManager();
		this.dirts = new ArrayList<Dirt>();
		this.bags = new ArrayList<Bag>();
		this.bagsToClear = new ArrayList<Bag>();
		this.footprintsToAdd = new ArrayList<FootPrint>();
		this.enemiesToAdd = new ArrayList<Monster>();
		for (int i = 0; i < this.info.length; i++) {
			for (int j = 0; j < this.info[i].length; j++) {
				if (this.info[i][j] == 'D') {
					this.dirts.add(new Dirt(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));
				}
				if (this.info[i][j] == 'E') {
					this.dirts.add(new Dirt(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));
					this.blocks.add(new Emerald(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));

				}
				if (this.info[i][j] == 'B') {
					this.tunnel
							.addEmptySpaces(new EmptySpace(this,
									new Point2D.Double(j * Block_Size, i
											* Block_Size)));
					Bag bag = new Bag(this, new Point2D.Double(j * Block_Size,
							i * Block_Size));
					this.blocks.add(bag);
					this.bags.add(bag);
				}
				if (this.info[i][j] == 'G') {
					this.dirts.add(new Dirt(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));
					this.blocks.add(new Gold(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));

				}
				if (this.info[i][j] == 'H') {
					this.hero = new Hero(this, new Point2D.Double(j
							* Block_Size, i * Block_Size));
					this.hero.setScore(score);
					this.hero.setLives(lives);
					this.tunnel
							.addEmptySpaces(new EmptySpace(this,
									new Point2D.Double(j * Block_Size, i
											* Block_Size)));
				}
				if (this.info[i][j] == '*') {
					this.tunnel
							.addEmptySpaces(new EmptySpace(this,
									new Point2D.Double(j * Block_Size, i
											* Block_Size)));
				}
				if (this.info[i][j] == 'N') {
					this.enemies.add(new Nobbin(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));
					this.tunnel
							.addEmptySpaces(new EmptySpace(this,
									new Point2D.Double(j * Block_Size, i
											* Block_Size)));
				}
				if (this.info[i][j] == 'O') {
					this.enemies.add(new Hobbin(this, new Point2D.Double(j
							* Block_Size, i * Block_Size)));
				}
				if (this.info[i][j] == 'M') {
					this.blocks
							.add(new MonsterGenerator(this, new Point2D.Double(
									j * Block_Size, i * Block_Size)));
					this.tunnel
							.addEmptySpaces(new EmptySpace(this,
									new Point2D.Double(j * Block_Size, i
											* Block_Size)));
				}
			}
			this.footprints = new ArrayList<FootPrint>();
			this.blocksToClear = new ArrayList<ObjectInWorld>();
			this.blocksToAdd = new ArrayList<ObjectInWorld>();
			this.enemiesToClear = new ArrayList<Monster>();
			this.bullets = new ArrayList<Bullet>();
			this.bulletsToClear = new ArrayList<Bullet>();
			this.dirtsToClear = new ArrayList<Dirt>();
		}
	}

	/**
	 * 
	 * draw the blocks onto the GUI
	 * 
	 * @param g
	 */
	public void drawOn(Graphics2D g) {
		for (Dirt d : this.dirts) {
			drawDrawable(d, g);
		}
		for (FootPrint fp : this.footprints) {
			drawDrawable(fp, g);
		}
		for (ObjectInWorld o : this.blocks) {
			if (o != null) {
				drawDrawable(o, g);
			}
		}
		for (Bullet b : this.bullets) {
			drawDrawable(b, g);
		}
		for (Monster mn : this.enemies) {
			drawDrawable(mn, g);
		}

		if (this.hero.getIsAlive()) {
			drawDrawable(this.hero, g);
		}
	}

	/**
	 * 
	 * draw a specific Drawable type object
	 * 
	 * @param d
	 * @param g
	 */
	public void drawDrawable(Drawable d, Graphics2D g) {
		d.drawOn(g);
	}

	/**
	 * 
	 * get the current level of the game
	 * 
	 * @return current level of the game
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * 
	 * get the blocks in the world
	 * 
	 * @return ArrayList of the blocks in the world
	 */
	public ArrayList<ObjectInWorld> getBlocks() {
		return this.blocks;
	}

	/**
	 * return ArrayList used to clear any unspecific object in the World
	 * 
	 * @return ArrayList of type ObjectInWorld
	 */
	public ArrayList<ObjectInWorld> getBlocksToClear() {
		return this.blocksToClear;
	}

	/**
	 * return ArrayList used to clear the Enemies from the World
	 * 
	 * @return ArrayList of type Enemies
	 */
	public ArrayList<Monster> getEnemiesToClear() {
		return this.enemiesToClear;
	}

	public ArrayList<Monster> getEnemiesToAdd(){
		return this.enemiesToAdd;
	}
	/**
	 * return ArrayList used to clear the bullet off of the World
	 * 
	 * @return ArrayList of type Bullet
	 */
	public ArrayList<Bullet> getBulletsToClear() {
		return this.bulletsToClear;
	}

	public ArrayList<Bag> getBagsToClear() {
		return this.bagsToClear;
	}

	/**
	 * return ArrayList is used to clear the Dirt objects off of World
	 * 
	 * @return ArrayList of Dirt objects
	 */
	public ArrayList<Dirt> getDirtsToClear() {
		return this.dirtsToClear;
	}

	/**
	 * return if the game is paused or not
	 */
	public boolean isPaused() {
		return this.isPaused;
	}

	/**
	 * 
	 * get the hero of the current game
	 * 
	 * @return
	 */
	public Hero getHero() {
		return this.hero;
	}

	/**
	 * 
	 * @return tunnel object in the game currently
	 */
	public Tunnel getTunnel() {
		return this.tunnel;
	}

	/**
	 * 
	 * get all the bags in the game
	 * 
	 * @return
	 */
	public ArrayList<Bag> getBags() {
		return this.bags;
	}

	/**
	 * 
	 * check if the point is in the window
	 * 
	 * @param point
	 * @return true if the point is in the window
	 */
	public boolean includePoint(Point2D point) {
		return (point.getX() >= 0 && point.getY() >= 0 && point.getX() <= 1080 && point
				.getY() <= 720);
	}

	/**
	 * 
	 * @return footprints in the world
	 */
	public ArrayList<FootPrint> getFootprints() {
		return this.footprints;
	}

	public ArrayList<FootPrint> getFootprintsToAdd(){
		return this.footprintsToAdd;
	}
	// Calls timePassed on every object that is on the GameComponent
	// Each object has its own implemented thing to do when called
	public synchronized void timePassed() {
		// Doesn't do anything if paused
		if (!this.isPaused) {

			// Dirt
			for (Dirt d : this.dirts) {
				if (d != null) {
					d.timePassed();
				}
			}

			// Any object not specific
			for (ObjectInWorld o : this.blocks) {
				if (o != null) {
					o.timePassed();
				}
			}

			// All Monsters
			for (Monster m : this.enemies) {
				if (m != null) {
					m.timePassed();
				}
			}

			// Any bullet
			for (Bullet b : this.bullets) {
				if (b != null) {
					b.timePassed();
				}
			}

			// Hero
			this.hero.timePassed();
		}
		this.blocks.removeAll(this.blocksToClear);
		this.blocks.addAll(this.blocksToAdd);
		this.blocksToClear.clear();
		this.blocksToAdd.clear();
		this.enemies.addAll(enemiesToAdd);
		this.enemies.removeAll(enemiesToClear);
		this.enemiesToAdd.clear();
		this.enemiesToClear.clear();
		this.bullets.removeAll(bulletsToClear);
		this.bulletsToClear.clear();
		this.dirts.removeAll(dirtsToClear);
		this.dirtsToClear.clear();
		this.bags.removeAll(this.bagsToClear);
		this.bagsToClear.clear();
		this.footprints.addAll(footprintsToAdd);
		this.footprintsToAdd.clear();
	}

	/**
	 * remove from the game acutally do nothing in this class
	 */
	public void die() {
		//
	}

	/**
	 * set the game to be paused or un-paused
	 */
	public void setIsPause(boolean isPaused) {
		this.isPaused = isPaused;
	}

	/**
	 * 
	 * get the info that stroes int he blocks in the level
	 * 
	 * @return info of current level
	 */
	public char[][] getInfo() {
		return this.getInfo();
	}

	/**
	 * add an object to the blocks
	 * 
	 * @param o
	 */
	public void addBlocks(ObjectInWorld o) {
		this.blocksToAdd.add(o);
	}

	/**
	 * 
	 * get all the enemies currently in the game
	 * 
	 * @return
	 */
	public ArrayList<Monster> getEnemies() {
		return this.enemies;
	}

	/**
	 * 
	 * add a bullet to the world to be drawn
	 * 
	 * @param bullet
	 */
	public void addBullet(Bullet bullet) {
		this.bullets.add(bullet);
	}
	
	/*
	 * Sets the level of this World to int level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
