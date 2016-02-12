import java.awt.BorderLayout;
import javax.swing.JPanel;

/*
 * GamePanel constructed by the GameFrame so that the objects can be attached to it
 */
public class GamePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GamePanel() {
		BorderLayout layout = new BorderLayout(0, 0);
		setLayout(layout);
		WorldComponent worldComponent = new WorldComponent();
		add(new StatusComponent(worldComponent), BorderLayout.NORTH);
		add(worldComponent, BorderLayout.SOUTH);
	}
}
