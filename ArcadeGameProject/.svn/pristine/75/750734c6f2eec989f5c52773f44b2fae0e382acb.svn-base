import java.awt.geom.Point2D;

/*
 * Used to set the value of any collectible object
 * Such items include emeralds and coins
 */
public abstract class Pickups extends ObjectInWorld implements Collectable {
	private int value;

	public Pickups(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
	}

	/**
	 * get the value of the score of this collectible object
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * set the value of the score for this collectible object
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
