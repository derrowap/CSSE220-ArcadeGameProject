/*
 * Interface for any object that is collectible
 * Items include: coins, emeralds
 * Value is the change in score that will occur when collected
 */

public interface Collectable extends Drawable {

	int getValue();

	void setValue(int value);
}
