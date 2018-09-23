package dev.druss.snake.states;

import java.awt.Graphics;

import dev.druss.snake.Handler;
import dev.druss.snake.entities.player.Player;
import dev.druss.snake.entities.statics.Token;
import dev.druss.snake.worlds.World;

public class GameState extends State{

	private World world;
	private Player player;
	private Token token;
	
	public GameState(Handler handler) {
		super(handler);
		world = new World(handler, "");
		player = new Player(handler, 10, 10, 5, 5);
		token = new Token(handler, 50, 50, 5, 5);
		handler.setWorld(world);
		handler.setPlayer(player);
		handler.setToken(token);
	}

	@Override
	public void tick() {
		world.tick();
		player.tick();
		token.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
		
		player.render(g);
		
		token.render(g);
	}

}
