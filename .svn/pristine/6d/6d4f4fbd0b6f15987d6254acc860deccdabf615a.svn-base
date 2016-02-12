import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * Represents any object in the world to prevent copying of code
 * Allows other classes to get information about the object
 * Can also change their fields through the implemented methods
 */
public abstract class ObjectInWorld implements Drawable, Temporal {
	private static final int Block_Size = 72;
	private Shape shape;
	private Color color;
	private World world;
	private boolean isPaused;

	public ObjectInWorld(World world, Point2D upLeftPoint) {
		this.shape = new Rectangle2D.Double(upLeftPoint.getX(),
				upLeftPoint.getY(), Block_Size, Block_Size);
		this.world = world;
	}

	/**
	 * return the shape of the object
	 * 
	 * @return shape of the object
	 */
	public Shape getShape() {
		return this.shape;
	}

	/**
	 * get the color of the object
	 * 
	 * @return the color of the object
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * set the shpae of the object
	 * 
	 * @param shape
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**
	 * set the color of the object
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * 
	 * set the World that the object is in
	 * 
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * 
	 * get the object's world
	 * 
	 * @return world
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * update the position of the object by its upper left corner's position
	 * 
	 * @param upLeftPoint
	 */
	public void setPosition(Point2D upLeftPoint) {
		this.shape = new Rectangle2D.Double(upLeftPoint.getX(),
				upLeftPoint.getY(), Block_Size, Block_Size);
	}

	/**
	 * 
	 * get the position of the object
	 * 
	 * @return position of the object
	 */
	public Point2D getPosition() {
		return (new Point2D.Double(this.shape.getBounds2D().getX(), this.shape
				.getBounds2D().getY()));
	}

	/**
	 * 
	 * get the position of the object in the grid
	 * 
	 * @return
	 */
	public Point2D getGridPosition() {
		return new Point2D.Double(this.getPosition().getY() / 72, this
				.getPosition().getX() / 72);
	}

	/**
	 * remove the object from the game
	 */
	public void die() {
		this.world.getBlocksToClear().add(this);
	}

	/**
	 * return if the game is paused
	 */
	public boolean isPaused() {
		return this.isPaused;
	}

	/**
	 * set the object to be paused or un-paused
	 * 
	 */
	public void setIsPause(boolean isPaused) {
		this.isPaused = isPaused;
	}

	// Default thing to do if the specific object doesn't have this method
	// implemented
	public void timePassed() {

	}

	/**
	 * @return whether this object encounters with the hero
	 */
	public boolean encounteredWithHero() {
		Hero hero = this.getWorld().getHero();
		if (hero.getIsAlive() == false) {
			return false;
		}
		return hero.getShape().intersects((Rectangle2D) this.getShape());
	}

	/**
	 * 
	 * check if a Monster encounter something
	 * 
	 * @return monster
	 */
	public Monster encounteredWithMonster() {
		for (Monster m : this.getWorld().getEnemies()) {
			if (m.getShape().intersects((Rectangle2D) this.getShape())) {
				return m;
			}
		}
		return null;
	}

	public void drawOn(Graphics2D g) {
		g.setColor(this.getColor());
		g.fill(this.getShape());
	}
	
	
}
