import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Is created when the user presses the space bar
 * This is the Hero's weapon in the game and shoots directly forward
 * in the direction the Hero was facing at the time.
 * Kills any enemy that it comes in contact with
 * Gets destroyed when it hits an enemy or a wall
 */
public class Bullet extends Character {

	private Hero hero;
	private BufferedImage image;
	public Bullet(World world, Point2D upLeftPoint, Hero hero) {
		super(world, upLeftPoint);
		this.hero = hero;
		this.setColor(Color.RED);
		this.setDirection(this.hero.getHeadingDirection());
		// this.setShape(new Rectangle2D.Double(this.getPosition().getX(),
		// this.getPosition().getY(), 20, 20));
		this.setSpeed(6);
		this.image = null;
		try{
			image = ImageIO.read(new File("Bullet.jpg"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void timePassed() {
		Point2D previous = this.getPosition();
		this.move(getDirection());
		if (previous.equals(this.getPosition())) {
			this.die();
			return;
		}
		if (!this.getWorld().getTunnel().isInEmptySpace(this)) {
			this.die();
		}
		Monster m = this.bulletEncounteredWithMonster();
		if (m != null) {
			m.die();
			this.die();
		}
	}

	@Override
	public void die() {
		this.getWorld().getBulletsToClear().add(this);
	}

	public Monster bulletEncounteredWithMonster() {
		for (Monster m : this.getWorld().getEnemies()) {
			if (m.getShape().intersects((Rectangle2D) this.getShape())) {
				return m;
			}
		}
		return null;
	}
	@Override
	public void drawOn(Graphics2D g) {
		super.drawOn(g);
		double rotateAngle = 0;
		switch (this.getDirection()) {
		case 'l':
			break;
		case 'r':
			rotateAngle = 180;
			break;
		case 'd':
			rotateAngle = 90;
			break;
		case 'u':
			rotateAngle = 270;
			break;
		}
		g.translate(this.getPosition().getX() + 36,
				this.getPosition().getY() + 36);
		g.rotate(Math.toRadians(rotateAngle));
		g.drawImage(image, -36, -36, 36, 36, 0, 0, image.getWidth(),
				image.getHeight(), null);
		g.rotate(-Math.toRadians(rotateAngle));
		g.translate(-this.getPosition().getX() - 36,
				-this.getPosition().getY() - 36);
	}

}
