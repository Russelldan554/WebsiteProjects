package dev.druss.snake;

import dev.druss.snake.entities.player.Player;
import dev.druss.snake.entities.statics.Token;
import dev.druss.snake.input.KeyManager;
import dev.druss.snake.worlds.World;

public class Handler {
	
	private Game game;
	private World world;
	private Player player;
	private Token token;
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Handler(Game game) {
		this.game = game;
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}

	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void setToken(Token token) {
		this.token = token;		
	}
	
	public Token getToken() {
		return token;
	}
}
