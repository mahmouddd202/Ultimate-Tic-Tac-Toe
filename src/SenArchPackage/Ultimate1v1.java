package SenArchPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Ultimate1v1 implements IGameObject1v1, MouseListener {

	private ArrayList<UltimateGrid1v1> grids = new ArrayList<UltimateGrid1v1>(Main1v1.SIZE);
	private UltimateGrid1v1 mainGrid;

	private Rectangle restartButton;
	private Rectangle welcomeButton;
	private Rectangle restartButtonMask;
	private Rectangle welcomeButtonMask;

	private String playerX;
	private String playerO;
	private Connection con;
	private int markerIndex = 0;
	private ArrayList<GameObserver> observers = new ArrayList<>();

	public Ultimate1v1() {
		initializeButtons();
		initializeGrids();
		initializeDatabaseConnection();
	}

	public Ultimate1v1(String playerX, String playerO) {
		this();
		this.playerX = playerX;
		this.playerO = playerO;
	}

	private void initializeButtons() {
		restartButton = new Rectangle(150, 260, 200, 50);
		welcomeButton = new Rectangle(150, 320, 200, 50);
		restartButtonMask = new Rectangle(150, 290, 200, 50);
		welcomeButtonMask = new Rectangle(150, 350, 200, 50);
	}

	private void initializeGrids() {
		for (int i = 0; i < Main1v1.SIZE; i++) {
			int x = i % Main1v1.ROWS;
			int y = i / Main1v1.ROWS;
			grids.add(new UltimateGrid1v1(x, y, Main1v1.WIDTH / Main1v1.ROWS));
		}
		mainGrid = new UltimateGrid1v1(0, 0, Main1v1.WIDTH, false, false);
	}

	private void initializeDatabaseConnection() {
		try {
			con = DatabaseConnection.getInstance().getConnection();
			System.out.println("Connection created");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void update(float deltaTime) {
		for (UltimateGrid1v1 grid : grids) {
			grid.update(deltaTime);
		}
		mainGrid.update(deltaTime);
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		renderGrids(graphicsRender);
		if (mainGrid.isGameEnd()) {
			drawEndGameOverlay(graphicsRender);
		}
	}

	private void renderGrids(Graphics2D graphicsRender) {
		for (UltimateGrid1v1 grid : grids) {
			grid.render(graphicsRender);
		}
		mainGrid.render(graphicsRender);
	}

	private void drawEndGameOverlay(Graphics2D graphicsRender) {
		// Draw semi-transparent overlay
		graphicsRender.setColor(new Color(0, 0, 0, (int)(225 * 0.7f)));
		graphicsRender.fillRect(0, 0, Main1v1.WIDTH, Main1v1.HEIGHT);

		// Draw winner text
		graphicsRender.setColor(Color.white);
		int winType = mainGrid.getWinner();
		if (winType == -1) {
			graphicsRender.drawString("It's a tie!", 195, 235);
		} else {
			String winnerName = (winType == 0) ? playerX : playerO;
			graphicsRender.drawString(winnerName + " has won!", 175, 245);
		}

		// Draw buttons
		drawButton(graphicsRender, restartButton, new Color(0, 102, 102), "Restart Game", 170, 295);
		drawButton(graphicsRender, welcomeButton, new Color(128, 67, 20), "Menu", 210, 350);
	}

	private void drawButton(Graphics2D g, Rectangle rect, Color color, String text, int textX, int textY) {
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255 * 0.9f)));
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.white);
		g.drawString(text, textX, textY);
	}

	public void mouseReleased(MouseEvent e) {
		if (mainGrid.isGameEnd()) {
			handleGameEndActions(e);
			return;
		}
		handleGameplayActions(e);
	}

	private void handleGameEndActions(MouseEvent e) {
		String winnerName = getWinnerName();
		notifyObservers(winnerName);

		Point2D clickPoint = e.getPoint();
		if (restartButtonMask.contains(clickPoint)) {
			reset();
		} else if (welcomeButtonMask.contains(clickPoint)) {
			Main1v1.frame.dispose();
			goToWelcomeScreen();
		}
	}

	private String getWinnerName() {
		int winner = mainGrid.getWinner();
		if (winner == 0) return playerX;
		if (winner == 1) return playerO;
		return "tie";
	}

	private void handleGameplayActions(MouseEvent e) {
		int activeX = -1;
		int activeY = -1;

		for (UltimateGrid1v1 grid : grids) {
			UltimatePlacement1v1 selectedPlacement = grid.mouseReleased(e, markerIndex);
			if (selectedPlacement != null) {
				activeX = selectedPlacement.getxIndex();
				activeY = selectedPlacement.getyIndex();
				markerIndex++;

				if (grid.getWinner() >= 0) {
					mainGrid.placeMarker(grid.getX(), grid.getY(), grid.getWinner());
				}
			}
		}
		setActiveGrid(activeX, activeY);
	}

	public boolean setActiveGrid(int activeX, int activeY) {
		if (activeX >= 0 && activeY >= 0) {
			for (UltimateGrid1v1 grid : grids) {
				if (grid.getX() == activeX && grid.getY() == activeY) {
					if (grid.isGameEnd()) {
						setGridsActive();
						return true;
					}
					grid.setActive(true);
				} else {
					grid.setActive(false);
				}
			}
		}
		return false;
	}

	private void setGridsActive() {
		for (UltimateGrid1v1 grid : grids) {
			grid.setActive(!grid.isGameEnd());
		}
	}

	public void mouseMoved(MouseEvent e) {
		if (!mainGrid.isGameEnd()) {
			for (UltimateGrid1v1 grid : grids) {
				grid.mouseMoved(e);
			}
		}
	}

	private void reset() {
		for (UltimateGrid1v1 grid : grids) {
			grid.reset();
		}
		mainGrid.reset();
		markerIndex = 0;
	}

	private void goToWelcomeScreen() {
		new Choose();
	}

	// Observer pattern methods
	public void addObserver(GameObserver observer) {
		observers.add(observer);
	}

	private void notifyObservers(String winnerName) {
		for (GameObserver obs : observers) {
			obs.onGameEnd(winnerName);
		}
	}

	// Required MouseListener methods (unused but required by interface)
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}