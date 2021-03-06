import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComponent;

/*
 * Used to handle how the world is updated.
 * The functionality of the world itself, such as level and size.
 */
public class WorldComponent extends JComponent implements Temporal {
	private static final long serialVersionUID = 1L;
	private static final int Min_Level = 1;
	private static final int Max_Level = 3;
	private static final Dimension SIZE = new Dimension(1080, 72 * 10);
	private static final Shape background = new Rectangle2D.Double(0, 0,
			SIZE.getWidth(), SIZE.getHeight());
	private static final Color bgColor = Color.WHITE;
	private World world;
	private int currentLevel;
	private static final int FRAMES_PER_SECOND = 30;
	private static final long REPAINT_INTERVAL_MS = 1000 / FRAMES_PER_SECOND;
	private boolean isPaused;
	private int timeSinceLastBullet = 100;
	private JButton restartButton;

	public WorldComponent() {
		WorldKeyHandler keyListener = new WorldKeyHandler();
		addKeyListener(keyListener);
		setFocusable(true);
		this.currentLevel = 1;
		this.world = new World(this.currentLevel, 0, 3);

		Runnable repainter = new Runnable() {
			public void run() {
				// Periodically asks Java to repaint this component
				try {
					while (true) {
						Thread.sleep(REPAINT_INTERVAL_MS);
						timePassed();
					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}
			}
		};
		new Thread(repainter).start();

		// Background Music
		try {
			File file = new File("shrekMusic.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();

			// sleep to allow enough time for the clip to play
			Thread.sleep(500);

			stream.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		// Button that appears if you lose the game
		this.restartButton = new JButton("Restart Game");
		// this.restartButton.setLocation(200, 200);
		this.restartButton.setSize(200, 50);
		ActionListener restarter = new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent e) {
				loseGame();
			}
		};
		this.restartButton.addActionListener(restarter);
		this.add(this.restartButton);
		this.restartButton.setVisible(false);
	}

	/**
	 * draw the background of the world
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(bgColor);
		g2.fill(background);
		this.world.drawOn(g2);
		this.world.timePassed();
	}

	/**
	 * get the size of the WorldComponent
	 */
	@Override
	public Dimension getPreferredSize() {

		return SIZE;
	}

	/*
	 * This KeyListener handles when to change levels and pause/restart the
	 * game, also to move the hero
	 */
	public class WorldKeyHandler implements KeyListener {
		// Pauses and resumes the game when P is pressed
		public void keyPressed(KeyEvent e) {
			if (WorldComponent.this.isPaused) {
				if (e.getKeyChar() == 'p') {
					WorldComponent.this
							.setIsPause(!WorldComponent.this.isPaused);
					WorldComponent.this.world
							.setIsPause(!WorldComponent.this.world.isPaused());
				}
			} else {

				// Pauses the game, not allowing any movement from any object in
				// World
				if (e.getKeyChar() == 'p') {
					WorldComponent.this
							.setIsPause(!WorldComponent.this.isPaused);
					WorldComponent.this.world
							.setIsPause(!WorldComponent.this.world.isPaused());

					// changes the level up or down when u and d are pressed
				} else if (e.getKeyChar() == 'u') {
					upLevel();
				} else if (e.getKeyChar() == 'd') {
					downLevel();

					// Sends the KeyCode to handleHero() to change direction of
					// hero
					// KeyCode 32 is space bar, which adds a bullet to the World
				} else if (e.getKeyCode() == 37 || e.getKeyCode() == 38
						|| e.getKeyCode() == 39 || e.getKeyCode() == 40
						|| e.getKeyCode() == 32) {
					handleHero(e.getKeyCode());
				} else {

					// Prints out to console any unknown key presses
					System.out.println("Unknown key code: " + e.getKeyCode());
				}
			}
		}

		public void keyTyped(KeyEvent e) {
			// not needed
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 37) {
				WorldComponent.this.world.keymanager.releaseDirection('u');
			} else if (e.getKeyCode() == 39) {
				WorldComponent.this.world.keymanager.releaseDirection('d');
			} else if (e.getKeyCode() == 38) {
				WorldComponent.this.world.keymanager.releaseDirection('l');
			} else if (e.getKeyCode() == 40) {
				WorldComponent.this.world.keymanager.releaseDirection('r');
			}
		}
	}

	/**
	 * this is what moves the hero when pressing the arrow keys.
	 * 
	 * @keyCode is given from the key listener
	 */
	public void handleHero(int keyCode) {
		// Does not move anything if game is paused
		if (WorldComponent.this.isPaused) {
			return;
		}
		Hero hero = WorldComponent.this.world.getHero();
		if (!hero.getIsAlive()) {
			return;
		}
		// Creates a bullet object (Hero's weapon) and
		// adds it to the objects to get repainted in the World
		if (keyCode == 32) {
			if (this.timeSinceLastBullet >= 100) {
				hero.fire();
				this.timeSinceLastBullet = 0;
			}
			return;
		}
		// Sets direction of the Hero
		if (keyCode == 37) {
			this.world.keymanager.pressDirection('u');
		} else if (keyCode == 39) {
			this.world.keymanager.pressDirection('d');
		} else if (keyCode == 38) {
			this.world.keymanager.pressDirection('l');
		} else if (keyCode == 40) {
			this.world.keymanager.pressDirection('r');
		}
	}

	/**
	 * 
	 * Changes the level up one. Called when the u button is pressed
	 */
	public void upLevel() {
		if (this.getWorld().getHero().getLives() != 0) {
			this.currentLevel += 1;
			if (this.currentLevel > WorldComponent.Max_Level) {
				this.currentLevel -= 1;
				return;
			}
			this.world = new World(this.currentLevel, this.world.getHero()
					.getScore(), this.getWorld().getHero().getLives());
		}
	}

	/**
	 * Changes the level down one. Called when the d button is pressed
	 * 
	 */
	public void downLevel() {
		this.currentLevel -= 1;
		if (this.currentLevel < WorldComponent.Min_Level) {
			this.currentLevel += 1;
			return;
		}
		this.world = new World(this.currentLevel, this.world.getHero()
				.getScore(), this.world.getHero().getLives());
	}

	/**
	 * check whether or no the game is currently paused
	 */
	public boolean isPaused() {
		return this.isPaused;
	}

	/**
	 * set the game to be paused
	 */
	public void setIsPause(boolean isPaused) {
		this.isPaused = isPaused;
	}

	/**
	 * repaint the world if it is not paused
	 */
	public void timePassed() {
		if (!this.isPaused) {
			repaint();
			this.timeSinceLastBullet++;
		}
		if (!this.world.getHero().getIsAlive()) {
			// do something when the hero is died
			this.world.setIsPause(true);
			this.restartButton.setVisible(true);
		}
		// check to see if any emeralds are in the blocks to move up a level
		for (ObjectInWorld block : this.world.getBlocks()) {
			if (block.getColor() == Color.GREEN) {
				return;
			}
		}
		if (this.getLevel() == WorldComponent.Max_Level) {
			this.world.getHero().setLives(-1);
			this.world.setIsPause(true);
			this.restartButton.setVisible(true);
		} else
			upLevel();
	}

	// sets the object to null and gets rid of it from the game.
	public void die() {
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return this.world;
	}

	/**
	 * 
	 * @return the current level
	 */
	public int getLevel() {
		return this.currentLevel;
	}

	/*
	 * Sets the current level to this level
	 */
	public void setLevel(int level) {
		this.currentLevel = level;
	}

	/*
	 * Displays a button giving the option to restart the game when you lose
	 */
	public void loseGame() {
		this.world.getHero().setScore(0);
		this.world.getHero().setLives(3);
		this.world.getHero().setDeathTime(0);
		this.world.getHero().setIsAlive(true);
		this.currentLevel = 1;
		this.world.setLevel(1);
		this.world = new World(this.currentLevel, world.getHero().getScore(),
				world.getHero().getLives());
		this.restartButton.setVisible(false);
		this.setIsPause(false);
		this.world.setIsPause(false);
		this.currentLevel = 1;
	}

}
