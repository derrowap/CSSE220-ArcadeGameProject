import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/*
 * Dirt Object that is currently a gray block and does nothing
 */
public class Dirt extends ObjectInWorld {
	private static final Color Dirt_Color = Color.darkGray;
	private BufferedImage image;
	
	public Dirt(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
		this.setColor(Dirt_Color);
		this.image = null;
		try{
			image = ImageIO.read(new File("Dirt.jpg"));	
		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	@Override
	public void timePassed() {
		ArrayList<FootPrint> fps = this.getWorld().getFootprints();
		double leftMost = -10000;
		double rightMost = 10000;
		double upMost = -10000;
		double downMost = 10000;
		double thisx = this.getPosition().getX();
		double thisy = this.getPosition().getY();
		for (FootPrint fp : fps) {
			double fpx = fp.getPosition().getX();
			double fpy = fp.getPosition().getY();
			if (fpy == thisy && fpx == thisx) {
				this.die();
				this.getWorld()
						.getTunnel()
						.addEmptySpaces(
								new EmptySpace(this.getWorld(), this
										.getPosition()));
				return;
			}
			if (fpy == thisy) {
				if (fpx < thisx) {
					if (fpx > leftMost) {
						leftMost = fpx;
					}
				}
				if (fpx > thisx) {
					if (fpx < rightMost) {
						rightMost = fpx;
					}
				}
			}
			if (fpx == thisx) {
				if (fpy < thisy) {
					if (fpy > upMost) {
						upMost = fpy;
					}
				}
				if (fpy > thisy) {
					if (fpy < downMost) {
						downMost = fpy;
					}
				}
			}
			if (leftMost + 72 > rightMost) {
				this.die();
				this.getWorld()
						.getTunnel()
						.addEmptySpaces(
								new EmptySpace(this.getWorld(), this
										.getPosition()));
				return;
			}
			if (upMost + 72 > downMost) {
				this.die();
				this.getWorld()
						.getTunnel()
						.addEmptySpaces(
								new EmptySpace(this.getWorld(), this
										.getPosition()));
				return;
			}
		}
	}

	@Override
	public void die() {
		this.getWorld().getDirtsToClear().add(this);
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
