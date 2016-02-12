import java.awt.geom.Point2D;

/*
 * Interface for any object that is movable
 * Method is supposed to enable them to change their position
 */
public interface Movable extends Drawable {
	void moveTo(Point2D point);

}
