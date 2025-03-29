package SenArchPackage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Ultimate1v1 implements IGameObject1v1, MouseListener{
	
	private ArrayList<UltimateGrid1v1> grids = new ArrayList<UltimateGrid1v1>(Main1v1.SIZE);
	private UltimateGrid1v1 mainGrid;
	
	private Rectangle restartButton;
	private Rectangle welcomeButton;
	
	private Rectangle restartButtonMask;
	private Rectangle welcomeButtonMask;
	
	private Connection con;
	private PreparedStatement pre;
	
	private int markerIndex = 0;
	
	
	
	public Ultimate1v1() {
		
		restartButton = new Rectangle(150, 260, 200, 50);
		welcomeButton = new Rectangle(150, 320, 200, 50);
		
		restartButtonMask = new Rectangle(150, 290, 200, 50);
		welcomeButtonMask = new Rectangle(150, 350, 200, 50);
		
		for (int i = 0; i < Main1v1.SIZE; i++) {
			int x = i % Main1v1.ROWS;
			int y = i / Main1v1.ROWS;
			grids.add(new UltimateGrid1v1(x, y, Main1v1.WIDTH / Main1v1.ROWS));
		}
		
		mainGrid = new UltimateGrid1v1(0, 0, Main1v1.WIDTH, false, false);
		
		 try {
	            // Get connection from the DatabaseConnection class
	            con = DatabaseConnection.getConnection();
	            System.out.println("Connection created");
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
	            e.printStackTrace();
	            System.exit(1); // Exit if we can't connect to database
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
		for (UltimateGrid1v1 grid : grids) {
			grid.render(graphicsRender);
		}
		
		mainGrid.render(graphicsRender);
		
		if(mainGrid.isGameEnd()) {
			drawEndGameOverlay(graphicsRender);
		}
	}
	


	private void drawEndGameOverlay(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0, 0, 0, (int)(225 * 0.7f)));
		
		graphicsRender.fillRect(0, 0, Main1v1.WIDTH, Main1v1.HEIGHT);
		graphicsRender.setColor(Color.white);
		
		int winType = mainGrid.getWinner();
		if(winType == -1) {
			// tie!
			graphicsRender.drawString("It's a tie!",  195, 235);
		} else {
			// won!
			graphicsRender.drawString((winType == 0 ? "m" : "z") + " has won!", 175, 245);
		}
		
		graphicsRender.setColor(new Color(0, 102, 102, (int)(255 * 0.9f)));  
		graphicsRender.fillRect(restartButton.x, restartButton.y, restartButton.width, restartButton.height);
		graphicsRender.setColor(new Color(128, 67, 20, (int)(255 * 0.9f)));
		graphicsRender.fillRect(welcomeButton.x, welcomeButton.y, welcomeButton.width, welcomeButton.height);
		
		graphicsRender.setColor(Color.white);
		graphicsRender.drawString("Restart Game", 170, 295);
		graphicsRender.drawString("Menu", 210, 350);
		
	}

	public void mouseReleased(MouseEvent e) {
		if(mainGrid.isGameEnd()) {
			updateDatabase();
			
	 Point2D clickPoint = e.getPoint();
			 
			 if (restartButtonMask.contains(clickPoint)) {
		            // Restart the game
		            reset();
		        } else if (welcomeButtonMask.contains(clickPoint)) {
		            // Go to Welcome screen
		        	Main1v1.frame.dispose();;
		            goToWelcomeScreen();		            		            
		        }
			
			return;
		}
		int activeX = -1;
		int activeY = -1;
		for (UltimateGrid1v1 grid : grids) {
			UltimatePlacement1v1 selectedPlacement = grid.mouseReleased(e, markerIndex);
			if(selectedPlacement != null) {
				activeX = selectedPlacement.getxIndex();
				activeY = selectedPlacement.getyIndex();
				markerIndex ++;
				
				if(grid.getWinner() >= 0) {
					mainGrid.placeMarker(grid.getX(), grid.getY(), grid.getWinner());
				}
			}
		}
		
		boolean multipleGridsActive = setActiveGrid(activeX, activeY);
	}
	
	public boolean setActiveGrid(int activeX, int activeY) {
		if(activeX >= 0 && activeY >= 0) {
			for (UltimateGrid1v1 grid : grids) {
				if(grid.getX() == activeX && grid.getY() == activeY) {
					if(grid.isGameEnd()) {
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
		if(mainGrid.isGameEnd()) {
			return;
		}
		for (UltimateGrid1v1 grid : grids) {
			grid.mouseMoved(e);
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
			
		    Welcome w = new Welcome();
		    }


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void updateDatabase() {
		  String name = "";
		  if (mainGrid.getWinner() == 0) {
			   name = "m";
		  }
		  else if(mainGrid.getWinner() == 1){
			  name = "z";
		  }
		  else {
			  name = "tie";
		  }
	            try {
	               
	                int scoreIncrement = 1; // Increment score by 1

	                // Update the score in the database
	                String updateSql = "UPDATE name_score SET score = score + ? WHERE name = ?";
	                pre = con.prepareStatement(updateSql);
	                pre.setInt(1, scoreIncrement);
	                pre.setString(2, name);
	              //  scoreIncrement++;
	                int rowsAffected = pre.executeUpdate();
	                
	                if (rowsAffected > 0) {
	                    System.out.println("Score updated successfully for player: " + name );
	                } else {
	                    System.out.println("Player not found: " + name);
	                }

	            } catch (SQLException e1) {
	                e1.printStackTrace();
	            }
	        }
		

}
