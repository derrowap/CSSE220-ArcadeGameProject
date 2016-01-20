import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Object Emerald is currently a green square that does nothing.
 * Will disappear when hero moves over it, and the score will increase.
 */
public class Emerald extends Pickups {
	private static final Color Emerald_Color = Color.GREEN;	
	private BufferedImage image;
	public Emerald(World world, Point2D point) {
		super(world, point);
		this.setColor(Emerald_Color);
		this.setValue(25);
		this.image = null;
		try{
			image = ImageIO.read(new File("Emrald.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void timePassed() {
		// If the hero collects the emerald, it disappears
		// and adds score to the Hero
		super.timePassed();
		if (encounteredWithHero()) {
			this.die();
			this.getWorld().getHero().addScore(getValue());
		} else {
			if (encounteredWithMonster() != null) {
				this.die();
			}
		}
	}

	@Override
	public void drawOn(Graphics2D g) {
		super.drawOn(g);
		g.translate(this.getPosition().getX() + 36,
				this.getPosition().getY() + 36);
		g.drawImage(image, -36, -36, 36, 36, 0, 0, image.getWidth(),
				image.getHeight(), null);
		g.translate(-this.getPosition().getX() - 36,
				-this.getPosition().getY() - 36);
	}
	
}
