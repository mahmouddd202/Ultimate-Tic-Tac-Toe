package SenArchPackage;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public class GamePanel1v1 extends Panel1v1 implements MouseMotionListener, MouseInputListener {

private Ultimate1v1 ultimate1v1;

public GamePanel1v1(Color color, String playerX, String playerO) {
    super(color);

    ultimate1v1 = new Ultimate1v1(playerX, playerO);

    // Register a popup observer
    ultimate1v1.addObserver(new GameEndPopup());
}


	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		ultimate1v1.update(deltaTime);
		
	
	}
	
	@Override
	public void render() {
		super.render();
		 
		ultimate1v1.render(graphicsRender);
		
		super.clear();
		
	}
	
	
	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		ultimate1v1.mouseReleased(e);
				
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		
	}

	@Override
	public void mouseExited(MouseEvent e) {

		
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		ultimate1v1.mouseMoved(e);
		
	}

	
}
