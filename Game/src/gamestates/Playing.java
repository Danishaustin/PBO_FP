package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import levels.LevelManager;
import main.Game;
import main.Player;
import object.ObjectManager;
import utilz.LoadData;

public class Playing extends States {
	
	private Player player;
	private Pause pause;
	private LevelManager levelManager;
	private ObjectManager objectManager;
	private Font font;
	BufferedImage background = LoadData.GetSpriteAtlas(LoadData.BACKGROUND);
	private Gamestate state;
	
	private boolean hintShow = false;
	private String hintText;
	private int subStringLength = 30;
	private int textTotalLine = 0;
	
	private int xLvlOffset = 0, yLvlOffset = 0;
	
	private int leftBorderLineX = (int) (0.45 * Game.GAME_WIDTH);
	private int rightBorderLineX = (int) (0.55 * Game.GAME_WIDTH);
	private int maxLvlOffsetX;
	
	private int upBorderLineY = (int) (0.45 * Game.GAME_HEIGHT);
	private int downBorderLineY = (int) (0.55 * Game.GAME_HEIGHT);
	private int maxLvlOffsetY;
	
	private int keysLeft;
	private int stringPositionX = (int)(10 * Game.SCALE);
	private int stringPositionY = (int)(20 * Game.SCALE);
	
	public Playing(Game game) {
		super(game);
		initClasses();
		initOffset();
		objectManager.loadObjects(levelManager.getCurrentLevel());
		keysLeft = objectManager.getTotalKey();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		objectManager = new ObjectManager(this);
		player = new Player(200, 1104, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		pause = new Pause(game);
		font = new Font("Arial", Font.BOLD, 20);

	}
	
	private void initOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffsetX();;
		maxLvlOffsetY = levelManager.getCurrentLevel().getLvlOffsetY();;
	}

	@Override
	public void update() {
		if(state != Gamestate.PLAYING || hintShow) {
			pause.update();
		} else {
			levelManager.update();
			objectManager.update();
			player.update();
			cameraMovement();
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
		objectManager.draw(g, xLvlOffset, yLvlOffset);
		player.render(g, xLvlOffset, yLvlOffset);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Keys Left: " + this.keysLeft, stringPositionX, stringPositionY);
		g.drawString("Press F to interact with object", stringPositionX, stringPositionY + 30);
		
		if (hintShow) {
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			
			g.setFont(font);
			g.setColor(Color.WHITE);
			for (int i = 0; i < textTotalLine; i++) {
	            int start = i * subStringLength;
	            int end = Math.min((i + 1) * subStringLength, hintText.length());
	            String line = hintText.substring(start, end);
	            
				g.drawString(line, Game.GAME_WIDTH/2 - 150, (int)((100 + 20 * i) * Game.SCALE));
			}
			g.drawString("Press ESC to leave", Game.GAME_WIDTH/2 - 150, (int)(350 * Game.SCALE));

		}
			
		
		if (state != Gamestate.PLAYING) {
			pause.draw(g, state);
		}
		
	}

	public Player getPlayer() {
		return player;
	}
	
	public void hintTextDraw(String text) {
		this.hintShow = true;
		this.hintText = text;
		this.textTotalLine = (int) Math.ceil((double) this.hintText.length() / subStringLength);
	}
	
	public void unpauseGame() {
		state = Gamestate.PLAYING;
	}
	
	public void isGameOver() {
		state = Gamestate.GAMEOVER;
	}
	
	public void levelIsCompleted() {
		state = Gamestate.FINISH;
	}
	
	public void resetAll() {
		player.reset();
		objectManager.resetAllObjects(levelManager.getCurrentLevel());
		keysLeft = objectManager.getTotalKey();
	}
	
	public void setKeysLeftDecrease() {
		this.keysLeft--;
		if(keysLeft == 0) {
			state = Gamestate.FINISH;
		}
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
		case KeyEvent.VK_F:
			objectManager.checkObjectTouched();
			break;
		case KeyEvent.VK_ESCAPE:
			if (hintShow) {
				hintShow = false;
				break;
			}
			if (state != Gamestate.PLAYING)
				break;
			state = Gamestate.PAUSED;
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
		if (state != Gamestate.PLAYING)
			pause.mouseClicked(e, state);

	}

	public void mouseReleased(MouseEvent e) {
		if (state != Gamestate.PLAYING)
			pause.mouseReleased(e);

	}

	public void mouseMoved(MouseEvent e) {
		if (state != Gamestate.PLAYING)
			pause.mouseMoved(e);

	}
	
}
