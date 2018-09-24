package dev.druss.snake.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import dev.druss.snake.Handler;
import dev.druss.snake.entities.Entity;

public class Token extends Entity{
	
	private long startTime, currentTime;
	private Random rand;
	private boolean collision;

	public Token(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		startTime = System.currentTimeMillis();
		rand = new Random();
		randomSpot();
	}

	@Override
	public void tick() {
		if (collision) {
			randomSpot();
			collision = false;
			handler.getPlayer().increaseSize();
		}
		
	}

	private void randomSpot() {
		int tx = (int) this.x;
		int ty = (int) this.y;
		tx =  rand.nextInt(handler.getWidth()-50);
		ty =  rand.nextInt(handler.getHeight()-50);
		tx = tx - (tx % 5);
		ty = ty - (ty % 5);
		
		this.x = tx;
		this.y = ty;
	}

	@Override
	public void render(Graphics g) {
		currentTime = System.currentTimeMillis();
		
		if ((currentTime - startTime) > 500 && (currentTime - startTime) < 1000 ) {
			g.fillRect((int) x,(int) y, 5, 5);
		} else if ((currentTime - startTime) > 1000) {
			g.setColor(Color.black);
			g.fillRect((int) x,(int) y, 5, 5);
			startTime = currentTime;
		}
		
	}
	
	public void setCollision(boolean col) {
		this.collision = col;
	}

}
