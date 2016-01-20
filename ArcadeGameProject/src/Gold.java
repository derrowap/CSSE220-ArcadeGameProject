import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Gold object that is constructed when a bag falls more than one block
 * When collected by hero, will increase the score
 * Currently not implemented
 */
public class Gold extends Pickups {
	private final Color Gold_Color = new Color(255, 215, 0);
	private BufferedImage image;
	
	public Gold(World world, Point2D centerPoint) {
		super(world, centerPoint);
		this.setColor(this.Gold_Color);
		this.setValue(500);
		this.image = null;
		try{
			image = ImageIO.read(new File("Gold.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void timePassed() {
		super.timePassed();

		// Does the Hero collect it?
		if (encounteredWithHero()) {
			this.die();
			this.getWorld().getHero().addScore(getValue());
		} else

		// Does a monster break it?
		if (encounteredWithMonster() != null) {
			this.die();
		}
		if (this.getWorld().getTunnel()
				.isPositionEmptySpaceUnder(this.getPosition())) {
			this.moveTo(new Point2D.Double(this.getPosition().getX(), this
					.getPosition().getY() + 3));
		}
	}

	private void moveTo(Point2D point) {
		this.setPosition(point);
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
