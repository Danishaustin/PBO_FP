package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadData;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(LoadData.GetLevelData());
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadData.GetSpriteAtlas(LoadData.LEVEL_ATLAS);
		levelSprite = new BufferedImage[50];
		for (int j = 0; j < 7; j++)
			for (int i = 0; i < 7; i++) {
				int index = j * 7 + i;
				levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}

	public void draw(Graphics g) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
			}
	}

	public void update() {

	}

	public Level getCurrentLevel() {
		return levelOne;
	}

}