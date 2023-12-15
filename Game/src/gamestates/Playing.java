package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import levels.LevelManager;
import main.Game;
import main.Player;
import utilz.LoadData;

public class Playing extends States {
	
	private Player player;
	private Pause pause;
	private LevelManager levelManager;
	BufferedImage background = LoadData.GetSpriteAtlas(LoadData.BACKGROUND);
	private boolean paused = false;
	
	private int xLvlOffset = 0, yLvlOffset = 0;
	
	private int leftBorderLineX = (int) (0.45 * Game.GAME_WIDTH);
	private int rightBorderLineX = (int) (0.55 * Game.GAME_WIDTH);
	private int lvlTilesWideX = LoadData.GetLevelData()[0].length;
	private int maxLvlOffsetX = (lvlTilesWideX - Game.TILES_IN_WIDTH) * Game.TILES_SIZE;
	
	private int upBorderLineY = (int) (0.45 * Game.GAME_HEIGHT);
	private int downBorderLineY = (int) (0.55 * Game.GAME_HEIGHT);
	private int lvlTilesWideY = LoadData.GetLevelData().length;
	private int maxLvlOffsetY = (lvlTilesWideY - Game.TILES_IN_HEIGHT) * Game.TILES_SIZE;
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(1000, 1000, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pause = new Pause(game);

	}

	@Override
	public void update() {
		if(!paused) {
			levelManager.update();
			player.update();
			cameraMovement();
		} else {
			pause.update();
		}
		
	}

	private void cameraMovement() {
		
		int playerX = (int) player.getHitbox().x;
		int playerY = (int) player.getHitbox().y;
		
		int playerXPosition = playerX - xLvlOffset;
		int playerYPosition = playerY - yLvlOffset;
		
		// Camera Move Right / Left
		if (playerXPosition > rightBorderLineX)
			xLvlOffset += playerXPosition - rightBorderLineX;
		else if (playerXPosition < leftBorderLineX)
			xLvlOffset += playerXPosition - leftBorderLineX;
		
		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;
		
		// Camera Move Down / Up
		if (playerYPosition > downBorderLineY)
			yLvlOffset += playerYPosition - downBorderLineY;
		else if (playerYPosition < upBorderLineY)
			yLvlOffset += playerYPosition - upBorderLineY;
		
		if (yLvlOffset > maxLvlOffsetY)
			yLvlOffset = maxLvlOffsetY;
		else if (yLvlOffset < 0)
			yLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		levelManager.draw(g, xLvlOffset, yLvlOffset);
		player.render(g, xLvlOffset, yLvlOffset);
		
		if (paused) {
			pause.draw(g);
		}
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setJump(true);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_ESCAPE:
			paused = true;
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setJump(false);
			break;
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (paused)
			pause.mouseClicked(e);

	}

	public void mouseReleased(MouseEvent e) {
		if (paused)
			pause.mouseReleased(e);

	}

	public void mouseMoved(MouseEvent e) {
		if (paused)
			pause.mouseMoved(e);

	}
}
