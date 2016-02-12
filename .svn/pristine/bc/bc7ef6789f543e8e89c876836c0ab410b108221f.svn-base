import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Enemy that kills the Hero when touches it
 */

//This is the dude that doesn't dig
public class Nobbin extends Monster {
	private BufferedImage image;
	
	public Nobbin(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
		this.setSpeed(3);
		this.setColor(Color.cyan);
		this.image = null;
		try{
			image = ImageIO.read(new File("Nobbin.jpg"));	
		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	@Override
	public void timePassed() {
		this.setDirection(this.getWorld().getTunnel()
				.getShortestWay(this.getWorld().getHero(), this));
		this.move(this.getDirection());
		if (this.encounteredWithHero()) {
			this.getWorld().getHero().die();
		}
		if(Math.random() < 0.1 && this.getPosition().getX() %72==0 && this.getPosition().getY()%72==0){
			Hobbin newHobbin = new Hobbin(this.getWorld(), this.getPosition());
			this.getWorld().getEnemiesToClear().add(this);
			this.getWorld().getEnemiesToAdd().add(newHobbin);
		}
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
