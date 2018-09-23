package dev.druss.snake.worlds;

import java.awt.Color;
import java.awt.Graphics;

import dev.druss.snake.Handler;

public class World {

	private Handler handler;
	private int width, height;
	
	public World(Handler handler, String string) {
		this.handler = handler;
		loadWorld();
	}

	private void loadWorld() {
		
	}

	public void render(Graphics g) {
		width = handler.getWidth();
		height = handler.getHeight();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
	}

	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
