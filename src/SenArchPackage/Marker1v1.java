package SenArchPackage;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Marker1v1 implements IGameObject1v1 {
	
	private BufferedImage marker;
	
	private int x;
	private int y;
	private int startX;
	private int startY;
	private int size;
	private int type;
	
	
	private boolean won = false;
	private float alpha = 1;
	private float fadeSpeed = 0.05f;
	
	public Marker1v1(int x, int y, int startX, int startY, int size, int type) {

		this.x = x;
		this.y = y;
		this.startX = startX;
		this.startY = startY;
		this.size = size;

		this.type = type % 2;
		String markerType = this.type == 0 ? "x" : "o";

		try {
			// Load from resources using classloader
			InputStream imgStream = getClass().getResourceAsStream("/resources/assets/" + markerType + ".png");
			if (imgStream != null) {
				marker = ImageIO.read(imgStream);
			} else {
				throw new IOException("Image resource not found: assets/" + markerType + ".png");
			}
		} catch (IOException e) {
			System.err.println("Failed to load marker image: " + e.getMessage());
			// Create blank image as fallback
			marker = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		}

	}

	public Marker1v1(int type) {

		this.type = type % 2;
	}

	@Override
	public void update(float deltaTime) {

		if(won) {
			alpha += fadeSpeed;
			if(alpha >= 1) {
				alpha = 1;
				fadeSpeed *= -1;
				
				return;
			}
			else if(alpha <= 0.5f) {
				alpha = 0.5f;
				fadeSpeed *= -1;
				
				return;
			}
		}
		
	}

	@Override
	public void render(Graphics2D graphicsRender) {
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphicsRender.setComposite(ac);
		
		int size = this.size / Main1v1.ROWS;
		graphicsRender.drawImage(marker, startX + x * size, startY + y * size, size, size, null);
		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		graphicsRender.setComposite(ac);
	}

	public int getType() {
		
		return type;
	}
	
	public void setWon(boolean won) {
		this.won = won;
	}

}
