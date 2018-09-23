package dev.druss.snake.entities.player;

import java.awt.Color;
import java.awt.Graphics;

import dev.druss.snake.Handler;
import dev.druss.snake.entities.Entity;

public class Player extends Entity {
	
	private Handler handler;
	private final int SPEED = 1;
	private int size;
	private int[][] snake;
	private float tx = 0, ty = 0;
	private boolean gameOver;
	private int score = 0;

	public Player(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		size = 25;
		this.snake = new int [10000][2];
		snake[0][0] = (int) this.x;
		snake[0][1] = (int) this.y;
		gameOver = false;
		tx = SPEED;
		ty = 0;
	}

	@Override
	public void tick() {	
		getMovement();
		
		collision();
		
		//set movement
		if (!((x + tx) < 0)) {
			if (!(x + tx > handler.getWidth())) {
				x += tx;
			} else
				gameOver = true;			
		} else 
			gameOver = true;
		
		if (!((y + ty) < 0)) {
			if (!((y + ty) > handler.getHeight())) {
				y += ty;
			} else
				gameOver = true;
		} else
			gameOver = true;
		

		//save coordinates of snake
		for (int i = size; i > 0; i--) {
			snake[i][0] = snake[i - 1][0];
			snake[i][1] = snake[i - 1][1];
		}
		snake[0][0] = (int) this.x;
		snake[0][1] = (int) this.y;
	}
	
	private void collision() {
		//collision detection
		//coordinates after move
		float rightX = this.x + tx + this.width;
		float leftX = this.x + tx;
		float topY = this.y + ty;
		float botY = this.y - this.height + ty;
		float entTopY = handler.getToken().getY();
		float entBotY = handler.getToken().getY() + handler.getToken().getHeight();
		float entLeftX = handler.getToken().getX();
		float entRightX = handler.getToken().getX() + handler.getToken().getWidth();

		if (tx > 0) { //moving right
			if (rightX == entLeftX) {
				if ((entTopY <= topY && topY < entBotY) ) {   
					handler.getToken().setCollision(true);
				}
			}
		} else if (tx < 0) { //moving left
			if (leftX == entRightX) {
				if (entTopY <= topY && topY < entBotY) {
					handler.getToken().setCollision(true);
				}
			}
		} else if (ty < 0) { //moving up
			if (topY == entBotY) {
				if (entLeftX <= leftX && leftX < entRightX) {
					handler.getToken().setCollision(true);
				}
			}
		} else if (ty > 0) { //moving down
			if (botY == entTopY) {
				if (entLeftX <= leftX && leftX < entRightX) {
					handler.getToken().setCollision(true);
				}
			}
		}
	}

	private void getMovement() {
		if (handler.getGame().getKeyManager().up) {
			if (ty <= 0 && this.x % 5 == 0) {
				ty = -SPEED;
				tx = 0;
			}
		}
		if (handler.getGame().getKeyManager().down) {
			if (ty >= 0 && this.x % 5 == 0) {
				ty = SPEED;
				tx = 0;
			}
		}
		if (handler.getGame().getKeyManager().left) {
			if (tx <= 0 && this.y % 5 == 0) {
				tx = -SPEED;
				ty = 0;
			}
		}
		if (handler.getGame().getKeyManager().right) {
			if (tx >= 0 && this.y % 5 == 0) {
				tx = SPEED;
				ty = 0;
			}
		}
	}

	@Override
	public void render(Graphics g) {		
		if (!gameOver) {
			for (int i = 0; i < size; i++) {
				g.setColor(Color.WHITE);
				g.fillRect(snake[i][0], snake[i][1], this.width, this.width);
			}	
		} else {
			g.setColor(Color.WHITE);
			g.drawString("Game Over Score: " + score, handler.getWidth()/2,handler.getHeight()/2);
		}
	}	
	
	public int getSize() {
		return size;
	}

	public void increaseSize() {
		score += 10;
		this.size += 10;
	}

	public int[][] getSnake() {
		return snake;
	}

}
