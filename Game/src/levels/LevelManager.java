package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import static utilz.LoadData.*;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(GetSpriteAtlas(LEVEL_ONE_DATA));
	}

	private void importOutsideSprites() {
		BufferedImage img = GetSpriteAtlas(LEVEL_ATLAS);
		levelSprite = new BufferedImage[50];
		for (int j = 0; j < 7; j++)
			for (int i = 0; i < 7; i++) {
				int index = j * 7 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (int j = 0; j < levelOne.getLevelData().length; j++)
			for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i - xLvlOffset, 
						Game.TILES_SIZE * j - yLvlOffset, 
						Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}

}