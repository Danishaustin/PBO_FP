package main;

import java.awt.Graphics;

import levels.LevelManager;

public class Game implements Runnable {

	// Game Running
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private Player player;
	private LevelManager levelManager;

	// Game Tiles
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {
		initClasses();

		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);

		startGameLoop();
	}

	private void initClasses() {
		levelManager = new LevelManager(this);
		player = new Player(200, 200, (int) (64 * SCALE), (int) (64 * SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());

	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}
	
	// Update Position & Repaint Panel

	public void update() {
		levelManager.update();
		player.update();
	}

	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
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


}