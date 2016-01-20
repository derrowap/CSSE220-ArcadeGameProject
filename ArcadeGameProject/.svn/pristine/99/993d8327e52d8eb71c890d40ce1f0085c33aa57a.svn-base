import java.awt.geom.Point2D;

/*
 * Super Class of hero and Monsters, moves the object in specific directions
 * keeps track of the basic things all characters need
 */
public abstract class Character extends ObjectInWorld implements Movable {
	private double speed;
	protected char direction;
	protected boolean canDig;
	private boolean isAlive;
	private char headingDirection;

	public Character(World world, Point2D upLeftPoint) {
		super(world, upLeftPoint);
		isAlive = true;
		direction = 'u';
		headingDirection = 'u';
	}

	/**
	 * move the character to a certain point
	 * 
	 * @param point
	 */
	public void moveTo(Point2D Point) {
		this.setPosition(Point);
	}

	/**
	 * 
	 * set character's speed
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * 
	 * get the speed of the character
	 * 
	 * @return speed
	 */
	public double getSpeed() {
		return this.speed;
	}

	/**
	 * Move to the next position according to the direction
	 * 
	 * @param direction
	 */
	public void move(char direction) {
		Point2D newPoint = this.getPosition();
		if (direction == 'u' || direction == 'd') {
			if (this.getPosition().getY() % 72 != 0) {
				direction = headingDirection;
			} else {
				if (direction != 's')
					headingDirection = direction;
			}
		}
		if (direction == 'l' || direction == 'r') {
			if (this.getPosition().getX() % 72 != 0) {
				direction = headingDirection;
			} else {
				if (direction != 's')
					headingDirection = direction;
			}
		}
		if (direction == 'u') {
			newPoint = new Point2D.Double(this.getPosition().getX()
					- this.speed, this.getPosition().getY());
		}
		if (direction == 'd') {
			newPoint = new Point2D.Double(this.getPosition().getX()
					+ this.speed, this.getPosition().getY());
		}

		if (direction == 'l') {
			newPoint = new Point2D.Double(this.getPosition().getX(), this
					.getPosition().getY() - this.speed);
		}
		if (direction == 'r') {
			newPoint = new Point2D.Double(this.getPosition().getX(), this
					.getPosition().getY() + this.speed);
		}
		if (direction == 's') {
			//
		}

		if (isAvailable(newPoint, this.getWorld())) {
			this.moveTo(newPoint);
		}

	}

	/**
	 * return the direction that the character is facing
	 * 
	 * @return direction that the character is facing
	 */
	public char getDirection() {
		return this.direction;
	}

	/**
	 * @return the heading direction
	 * 
	 */
	public char getHeadingDirection() {
		return this.headingDirection;
	}

	/**
	 * 
	 * Set the direction that the character is facing
	 * 
	 * @param c
	 */
	public void setDirection(char c) {
		if (this.direction != c) {
			if (this.direction != 's')
				this.headingDirection = this.direction;
			this.direction = c;
		}
	}

	/**
	 * check whether the block(represent by its upperleft point) is inside the
	 * world
	 * 
	 * @param upperLeft
	 * @param world
	 * @return
	 */
	public boolean isAvailable(Point2D upperLeft, World world) {
		Point2D upperRight = new Point2D.Double(upperLeft.getX(),
				upperLeft.getY() + 72);
		Point2D lowerLeft = new Point2D.Double(upperLeft.getX() + 72,
				upperLeft.getY());
		Point2D lowerRight = new Point2D.Double(upperLeft.getX() + 72,
				upperLeft.getY() + 72);
		return (world.includePoint(upperRight) && world.includePoint(upperLeft)
				&& world.includePoint(lowerLeft) && world
					.includePoint(lowerRight));
	}

	@Override
	public Point2D getGridPosition() {
		int thisx = (int) this.getPosition().getX();
		int thisy = (int) this.getPosition().getY();
		if (thisx % 72 == 0 && thisy % 72 == 0) {
			return new Point2D.Double(thisx / 72, thisy / 72);
		} else {
			if (thisx % 72 != 0) {
				if (this.getDirection() == 'u') {
					return new Point2D.Double((thisx / 72) + 1, thisy / 72);
				} else {
					return new Point2D.Double(thisx / 72, thisy / 72);
				}
			} else {
				if (this.getDirection() == 'l') {
					return new Point2D.Double(thisx / 72, (thisy / 72) + 1);
				} else {
					return new Point2D.Double(thisx / 72, thisy / 72);
				}
			}
		}
	}

	/**
	 * get the character is alive or not
	 * 
	 * @return
	 */
	public boolean getIsAlive() {
		return this.isAlive;
	}

	/**
	 * set the character is still alive or not
	 * 
	 * @param isAlive
	 */
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * 
	 * check if the character encounters a bag object
	 * 
	 * @return
	 */
	public Bag characterEncounteredWithBag() {
		for (Bag bag : this.getWorld().getBags()) {
			/*
			 * Is the bag's left border left of the hero's left border && the
			 * bag's right border right of the hero's left border? || Is the
			 * bag's left border left of the hero's right border && the bag's
			 * right border right of the hero's right border?
			 */
			if ((bag.getPosition().getX() <= this.getPosition().getX() && bag
					.getPosition().getX() + 71 >= this.getPosition().getX())
					|| (bag.getPosition().getX() <= this.getPosition().getX() + 71 && bag
							.getPosition().getX() + 71 >= this.getPosition()
							.getX() + 71)) {

				// If the direction is moving up
				if (this.getDirection() == 'l') {
					// Is there a bag above the hero anywhere? &&
					// Is the bag directly above the hero?
					if (bag.getPosition().getY() <= this.getPosition().getY()
							&& bag.getPosition().getY() + 72 >= this
									.getPosition().getY()) {
						return bag;
					}

					// If the direction is moving down
				} else if (this.getDirection() == 'r') {
					// Is there a bag below the hero anywhere? &&
					// Is the bag directly below the hero?
					if (bag.getPosition().getY() >= this.getPosition().getY()
							&& this.getPosition().getY() + 72 >= bag
									.getPosition().getY()) {
						return bag;
					}
				}
			}

			/*
			 * Is the bag's upper border above the hero upper border && the
			 * bag's lower border is below the hero upper border? || Is the
			 * bag's upper border above the hero lower border && the bag's lower
			 * border is below the hero lower border?
			 */
			if ((bag.getPosition().getY() <= this.getPosition().getY() && bag
					.getPosition().getY() + 71 >= this.getPosition().getY())
					|| (bag.getPosition().getY() <= this.getPosition().getY() + 71 && bag
							.getPosition().getY() + 71 >= this.getPosition()
							.getY() + 71)) {
				// If the direction is moving left
				if (this.getDirection() == 'u') {
					// Is there a bag to the left of the hero anywhere? &&
					// Is the bag directly to the left of the hero?
					if (bag.getPosition().getX() <= this.getPosition().getX()
							&& bag.getPosition().getX() + 72 >= this
									.getPosition().getX()) {

						return bag;
					}

					// If the direction is moving right
				} else if (this.getDirection() == 'd') {
					if (bag.getPosition().getX() >= this.getPosition().getX()) {
						if (this.getPosition().getX() + 72 >= bag.getPosition()
								.getX()
								&& this.getPosition().getX() <= bag
										.getPosition().getX()) {
							return bag;
						}
					}
				}
			}
		}
		return null;
	}
}
