package SenArchPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;

public class Panel1v1 extends JPanel implements Runnable{

	private Thread thread;
	protected Graphics2D graphicsRender; 
	private Image img;	
	private Color backgroundColor;
	
	public Panel1v1(Color color) {
		this.backgroundColor = color;
		
		setPreferredSize(new Dimension(Main1v1.WIDTH, Main1v1.HEIGHT));
		setFocusable(false);
		requestFocus();
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		init(); 
		
		long lastTime = System.nanoTime();
		double nanoSecondPerUpdate = 1000000000D / 30;		
		float deltaTime = 0;
		
		while(true){

			long now = System.nanoTime();
			deltaTime += (now - lastTime) / nanoSecondPerUpdate;
			lastTime = now;
			
			if(deltaTime >= 1){

				update(deltaTime);
				
				render();

				deltaTime--;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void init() {

		img = createImage(Main1v1.WIDTH, Main1v1.HEIGHT);
		graphicsRender = (Graphics2D) img.getGraphics();
	}
	
	public void update(float deltaTime) {

	}

	public void render() {

		 if (graphicsRender != null) {
	            graphicsRender.clearRect(0, 0, Main1v1.WIDTH, Main1v1.HEIGHT);
	            graphicsRender.setFont(new Font("Arial", Font.CENTER_BASELINE, 25));
	            graphicsRender.setColor(backgroundColor);
	            graphicsRender.fillRect(0, 0, Main1v1.WIDTH, Main1v1.HEIGHT);
	            graphicsRender.setColor(Color.white);
	            
	        } else {
	            System.err.println("graphicsRender is null in render()");
	        }
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, 0, 0, null);
        }
    }

    public void clear() {
        // Schedule a repaint
        repaint();
    }
	
}
