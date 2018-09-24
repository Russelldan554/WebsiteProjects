package dev.druss.snake;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import dev.druss.snake.display.Display;
import dev.druss.snake.input.KeyManager;
import dev.druss.snake.states.GameState;
import dev.druss.snake.states.State;
/**
 * version 1.0
 * @author dan
 *
 */
public class Game implements Runnable{
	private final double VERSION = 1.0;
	
	//Class Variables
	private Display display;
	private int width, height;
	private String title;
	private JMenu menu;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	public State gameState, menuState;
	
	//input
	private KeyManager keyManager;
	
	//handler
	private Handler handler;
	
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		keyManager = new KeyManager();
	}

	private void init() {
		display = new Display(title, width, height);
		display.getJFrame().addKeyListener(keyManager);
		
		menu = new JMenu("File");
		JMenuItem retry = new JMenuItem("Restart");
		menu.add(retry);
		JMenuItem version = new JMenuItem("Version");
		menu.add(version);
		
		JMenuBar menubar = new JMenuBar();;
		menubar.add(menu);
		display.getJFrame().setJMenuBar(menubar);
		
		retry.addActionListener((ActionEvent e) -> {
			gameState = new GameState(handler);
			State.setState(gameState);
		});
		
		version.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(null, "Version: " + VERSION);
		});
		
		handler = new Handler(this);
		
		gameState = new GameState(handler);
		State.setState(gameState);
	}
	
	private void tick() {
		keyManager.tick();
		State.getState().tick();
	}
	
	private void render () {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;			
		}
		
		g = bs.getDrawGraphics();
		//clear Screen
		g.clearRect(0, 0, width, height);
		gameState.render(g);
		
		bs.show();
		g.dispose();
	}
	
	@Override
	public void run() {
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000){
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public int getWidth(){
		return width;
	}	
	
	public int getHeight() {
		return height;
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
}
