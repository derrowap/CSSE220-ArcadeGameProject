import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * Bag object that is currently a blue square on the GUI.
 * Can kill Monsters or Hero if falling on top of them
 * If it drops further than one block, it will break into Gold
 * Can be moved sideways by Hero
 * 
 */
public class Bag extends Pickups implements Movable {
	private static final Color Bag_Color = Color.BLUE;
	private World world = this.getWorld();
	private int emptySpacesUnder = 0;
	private int timeWaited = 0;
	private BufferedImage image;

	public Bag(World world, Point2D centerPoint) {
		super(world, centerPoint);
		this.setColor(Bag_Color);
		this.image = null;
		try{
			image = ImageIO.read(new File("Bag.jpg"));	
		}catch(IOException e){
			e.printStackTrace();
		}	
	}

	@Override
	public void timePassed() {
		// Moves if there is empty space underneath
		if (this.world.getTunnel()
				.isPositionEmptySpaceUnder(this.getPosition())) {
			this.timeWaited++;
			// Delays the falling so it won't kill you if you empty the space
			// right under it
			if (this.timeWaited > 80) {
				this.emptySpacesUnder++;
				this.moveTo(new Point2D.Double(this.getPosition().getX(), this
						.getPosition().getY() + 3));
				Monster m = this.encounteredWithMonster();
				if (m != null) {
					m.die();
				}
				// Kills the hero if it encounters it while falling
				if (this.encounteredWithHero()) {
					this.getWorld().getHero().die();
				}
				Bag bag = this.bagEncounteredWithBag();
				// If a bag falls on top of another bag, they both break
				// Value of gold is doubled since it was two bags
				if (bag != null) {
					Gold gold = new Gold(this.getWorld(), this.getPosition());
					bag.die();
					this.die();
					gold.setValue(gold.getValue() * 2);
					this.world.addBlocks(gold);
					return;
				}
			}
		} else {
			this.timeWaited = 0;
			// If it dropped more than one block, it will break into gold
			if (this.emptySpacesUnder > 47) {
				Bag bag = this.bagEncounteredWithBag();
				Gold gold = new Gold(this.getWorld(), this.getPosition());

				// If a bag falls on top of another bag, they both break
				// Value of gold is doubled since it was two bags
				if (bag != null) {
					bag.die();
					gold.setValue(gold.getValue() * 2);
				}
				this.die();
				this.world.addBlocks(gold);
			}
			this.emptySpacesUnder = 0;
		}

		// Moves the bag if the hero encounters it
		int deltaX = 0;
		int deltaY = 0;
		if (this.encounteredWithHero()) {
			Hero hero = this.getWorld().getHero();
			char direction = hero.getDirection();
			if (direction == 'u')
				if (hero.getPosition().getX() > this.getPosition().getX())
					deltaX -= 3;
			if (direction == 'd')
				if (hero.getPosition().getX() < this.getPosition().getX())
					deltaX += 3;
			this.moveTo(new Point2D.Double(this.getPosition().getX() + deltaX,
					this.getPosition().getY() + deltaY));
		}

		// Adds an empty space if there isn't one already where the bag abject
		// is
		if (!this.getWorld().getTunnel().isInEmptySpace(this)) {
			this.getWorld().getFootprintsToAdd()
					.add(new FootPrint(this.getWorld(), this.getPosition()));
		}
	}

	public void moveTo(Point2D point) {
		this.setPosition(point);
	}

	/**
	 * 
	 * check whether a bag encounters with another bag
	 * 
	 * @return true if so
	 */
	public Bag bagEncounteredWithBag() {
		for (Bag bag : this.getWorld().getBags()) {
			if (bag.equals(this))
				continue;
			if (bag.getPosition() == this.getPosition())
				return bag;
			/*
			 * (Is the bag upper border above this upper border && Is the bag
			 * lower border below this upper border?) || (Is the bag upper
			 * border above this lower border && Is the bag lower border below
			 * this lower border?)
			 */
			if ((bag.getPosition().getY() <= this.getPosition().getY() && bag
					.getPosition().getY() + 71 >= this.getPosition().getY())
					|| (bag.getPosition().getY() <= this.getPosition().getY() + 71 && bag
							.getPosition().getY() + 71 >= this.getPosition()
							.getY() + 71)) {
				/*
				 * (Is the bag left border left of this left border && Is the
				 * bag right border right of this left border?) || (Is the bag
				 * left border left of this right border && Is the bag right
				 * border right of this right border?)
				 */
				if ((bag.getPosition().getX() <= this.getPosition().getX() && bag
						.getPosition().getX() + 71 >= this.getPosition().getX())
						|| (bag.getPosition().getX() <= this.getPosition()
								.getX() + 71 && bag.getPosition().getX() + 71 >= this
								.getPosition().getX() + 71))
					return bag;
			}
		}
		return null;
	}

	@Override
	public void die() {
		super.die();
		this.getWorld().getBagsToClear().add(this);
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
