import java.awt.Color;
import java.awt.geom.Point2D;

/*
 * A class that the different types of enemies extend to prevent copying code
 * Currently not implemented
 */
public class Monster extends Character {


	public Monster(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
		this.setColor(Color.black);
	}

	@Override
	public void die() {
		this.getWorld().getEnemiesToClear().add(this);
	}
}
