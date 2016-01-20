import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/*
 * Top panel of the Frame that displays the hero's score
 */
public class StatusComponent extends JComponent implements Temporal {
	private static final long serialVersionUID = 1L;
	private static final int FRAMES_PER_SECOND = 30;
	private static final long REPAINT_INTERVAL_MS = 1000 / FRAMES_PER_SECOND;
	private static final Dimension SIZE = new Dimension(1080, 100);
	private Shape background = new Rectangle2D.Double(0, 0, SIZE.getWidth(),
			SIZE.getHeight());
	private Color bgColor = Color.black;
	private WorldComponent wc;
	private int score = 0;
	private JLabel scoreLabel;
	private JLabel levelLabel;
	private JLabel lifeLabel;
	private JLabel winLabel;
	private int FONT_SIZE = 70;
	
	

	public StatusComponent(WorldComponent worldComponent) {
		this.wc = worldComponent;

		// Label that displays the score
		this.scoreLabel = new JLabel();
		this.scoreLabel.setSize((int) SIZE.getWidth() / 2,
				(int) SIZE.getHeight() - 20);
		this.scoreLabel.setFont(new Font("Arial", 0, this.FONT_SIZE));
		this.scoreLabel.setForeground(Color.GREEN);
		this.add(this.scoreLabel, BorderLayout.WEST);

		// Label that displays the current Level
		this.levelLabel = new JLabel();
		this.levelLabel.setSize((int) SIZE.getWidth() / 4,
				(int) SIZE.getHeight() - 20);
		this.levelLabel.setText("Level: " + this.wc.getLevel());
		this.levelLabel.setLocation((int) SIZE.getWidth()
				- this.levelLabel.getText().length() * (this.FONT_SIZE / 2), 0);
		this.levelLabel.setFont(new Font("Arial", 0, this.FONT_SIZE));
		this.levelLabel.setForeground(Color.GREEN);
		this.add(this.levelLabel, BorderLayout.EAST);

		// Label that displays the Hero's lives
		this.lifeLabel = new JLabel();
		this.lifeLabel.setSize((int) SIZE.getWidth() / 4,
				(int) SIZE.getHeight() - 20);
		this.lifeLabel.setText("Lives: "
				+ this.wc.getWorld().getHero().getLives() + ",");
		this.lifeLabel.setLocation((int) this.scoreLabel.getWidth(), 0);
		this.lifeLabel.setFont(new Font("Arial", 0, this.FONT_SIZE));
		this.lifeLabel.setForeground(Color.GREEN);
		this.add(this.lifeLabel, BorderLayout.CENTER);
		
		// Label if you win
		this.winLabel = new JLabel("WINNER!");
		this.winLabel.setSize((int) SIZE.getWidth() / 2,
				(int) SIZE.getHeight() - 20);
		this.winLabel.setLocation((int) this.scoreLabel.getWidth(), 0);
		this.winLabel.setFont(new Font("Arial", 0, this.FONT_SIZE));
		this.winLabel.setForeground(Color.GREEN);
		this.add(this.winLabel, BorderLayout.EAST);

		// Thread Updates the Status Component
		Runnable repainter = new Runnable() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(REPAINT_INTERVAL_MS);
						timePassed();
					}
				} catch (InterruptedException exception) {
				}
			}
		};
		new Thread(repainter).start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(bgColor);
		g2.fill(background);
		this.score = this.wc.getWorld().getHero().getScore();
		this.scoreLabel.setText("Score: " + this.score);
		this.lifeLabel.setText("Lives: "
				+ this.wc.getWorld().getHero().getLives() + ",");
		if (this.wc.getWorld().getHero().getLives() == -1) {
			winGame();
		} else {
			this.winLabel.setVisible(false);
			this.levelLabel.setVisible(true);
			this.lifeLabel.setVisible(true);
			this.levelLabel.setText("Level: " + this.wc.getLevel());
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return SIZE;
	}

	/**
	 * call repaint to update everything in the game
	 */
	public void timePassed() {
		repaint();
	}

	// OverRide
	public boolean isPaused() {
		// Status Component is never paused, no need to be
		return false;
	}

	// OverRide
	public void setIsPause(boolean isPaused) {
		// not needed
	}

	// Override
	public void die() {
		// not needed
	}

	/*
	 * Runs when the game is won, notifies player that he is a winner
	 */
	public void winGame() {
		this.lifeLabel.setVisible(false);
		this.levelLabel.setVisible(false);
		this.winLabel.setVisible(true);
	}
}
