import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

/*
 * Interface for anything that needs to be drawn on the GUI
 * Such objects include: Dirt, Collectibles, and Movables
 */
public interface Drawable {
	public void setShape(Shape shape);

	public void setColor(Color color);

	public Shape getShape();

	public Color getColor();

	public void drawOn(Graphics2D g);
}
