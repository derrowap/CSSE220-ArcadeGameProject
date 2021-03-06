import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * 
 * Creates Hobbins in its space when there is less than 2 + whatever the current
 * level
 *
 * @author yorkww. Created Feb 17, 2015.
 */
public class MonsterGenerator extends ObjectInWorld {

	private int timeSinceLastMonster;

	public MonsterGenerator(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
		this.timeSinceLastMonster = 0;
	}

	public void timePassed() {
		// Timer that spawns a Hobbin every 200 other times this method is
		// called
		if (this.timeSinceLastMonster >= 200) {
			if (this.getWorld().getEnemies().size() < 2 + this.getWorld()
					.getLevel()) {
				if(Math.random() > 0.5){
				Nobbin newNobbin = new Nobbin(this.getWorld(),
						this.getPosition());
				this.getWorld().getEnemiesToAdd().add(newNobbin);
				this.timeSinceLastMonster = 0;
				} else {
					Hobbin newHobbin = new Hobbin(this.getWorld(),
							this.getPosition());
					this.getWorld().getEnemiesToAdd().add(newHobbin);
					this.timeSinceLastMonster = 0;
				}
				
			}
		}
		this.timeSinceLastMonster++;
	}

	@Override
	public void drawOn(Graphics2D g) {
	}
	
	
}
