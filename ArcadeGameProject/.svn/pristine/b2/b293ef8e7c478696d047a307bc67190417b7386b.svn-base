import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Enemy that chases after the hero trying to kill it by touching it
 */

// This is the dude that digs
public class Hobbin extends Monster {

	private int counter;
	private BufferedImage image;

	public Hobbin(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
		this.setSpeed(2);
		this.setColor(Color.MAGENTA);
		this.image = null;
		try {
			image = ImageIO.read(new File("Hobbin.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.counter = 0;
	}

	@Override
	public void timePassed() {
		if (this.counter >= 100 && this.getPosition().getX() % 72 ==0 && this.getPosition().getY() % 72 ==0) {
			if (this.getWorld().getTunnel().isInEmptySpace(this)
					&& ((this.getPosition().getX() / 72) == this
							.getGridPosition().getX())
					&& ((this.getPosition().getY() / 72) == this
							.getGridPosition().getY())) {
				Nobbin newNobbin = new Nobbin(this.getWorld(),
						this.getPosition());
				this.getWorld().getEnemiesToClear().add(this);
				this.getWorld().getEnemiesToAdd().add(newNobbin);
			}
		}
		this.counter++;
		this.setDirection(this.track());
		this.move(this.getDirection());
		if (this.encounteredWithHero())
			this.getWorld().getHero().die();
		if (!this.getWorld().getTunnel().isInEmptySpace(this)) {
			this.getWorld().getFootprintsToAdd()
					.add(new FootPrint(this.getWorld(), this.getPosition()));
		}
	}

	private char track() {
		Hero hero = this.getWorld().getHero();
		if (Math.abs(this.getPosition().getX() - hero.getPosition().getX()) < Math
				.abs(this.getPosition().getY() - hero.getPosition().getY())) {
			if (this.getPosition().getY() - hero.getPosition().getY() > 0)
				return 'l';
			return 'r';
		}
		if (this.getPosition().getX() - hero.getPosition().getX() > 0)
			return 'u';
		return 'd';
	}

	@Override
	public void drawOn(Graphics2D g) {
		super.drawOn(g);
		double rotateAngle = 0;
		switch (this.getHeadingDirection()) {
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
