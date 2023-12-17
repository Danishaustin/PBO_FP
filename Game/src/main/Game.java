package main;

import java.awt.Graphics;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {

	// Game Running
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	
	// Game States
	private Menu menu;
	private Playing playing;

	// Game Tiles
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {
		menu = new Menu(this);
		playing = new Playing(this);

		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);

		startGameLoop();
	}

	// Game Looping
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;

		int frames = 0;
		double deltaF = 0;

		long lastCheck = System.currentTimeMillis();
		long previousTime = System.nanoTime();

		while (true) {
			long currentTime = System.nanoTime();
	
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaF >= 1) {
				update();
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;

			}
		}
	}
	
	// Update & Repaint Panel

	public void update() {
		switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case EXIT:
		default:
			System.exit(0);
			break;
		}
	}

	public void render(Graphics g) {
		switch(Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING, PAUSED, GAMEOVER:
			playing.draw(g);
			break;
		default:
			break;
		}
	}
	
	// Additional Methods
	
	public void windowFocusLost() {
		if(Gamestate.state == Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
	}

	public Menu getMenu() {
		return this.menu;
	}
	
	public Playing getPlaying() {
		return this.playing;
	}

}