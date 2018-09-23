package dev.druss.snake;

public class Launcher {

	public static void main(String[] args) {
		Game game = new Game("Snake", 300, 300);
		game.start();
	}
}
