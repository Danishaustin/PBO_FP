package levels;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.Game;
import object.Box;
import object.HintBox;
import object.Key;
import utilz.LoadData;

import static utilz.LoadData.*;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Key> keys;
	private ArrayList<Box> box;
	private ArrayList<HintBox> hints;
	private ArrayList<String> hintText;
	private int lvlTilesWideX, lvlTilesWideY;
	private int maxTilesOffsetX, maxTilesOffsetY;
	private int maxLvlOffsetX, maxLvlOffsetY;

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createHintText();
		createKey();
		createBox();
		createHint();
		offsetCalculation();
	}


	private void offsetCalculation() {
		
		lvlTilesWideX = img.getWidth();
		maxTilesOffsetX = lvlTilesWideX - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffsetX;
		
		lvlTilesWideY = img.getHeight();
		maxTilesOffsetY = lvlTilesWideY - Game.TILES_IN_HEIGHT;
		maxLvlOffsetY = Game.TILES_SIZE * maxTilesOffsetY;
		
	}

	private void createBox() {
		this.box = GetBoxes(img);
		
	}

	private void createKey() {
		this.keys = GetKeys(img);
		
	}
	
	private void createHintText() {
		hintText = GetHintText();
		
	}

	private void createHint() {
		hints = GetHintBoxes(img, hintText);
		
	}

	private void createLevelData() {
		this.lvlData = GetLevelData(img);
		
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}
	
	public int getLvlOffsetX() {
		return maxLvlOffsetX;
	}
	
	public int getLvlOffsetY() {
		return maxLvlOffsetY;
	}

	public ArrayList<Key> getKeys() {
		return keys;
	}


	public ArrayList<Box> getBoxes() {
		return box;
	}
	
	public ArrayList<HintBox> getHints() {
		return hints;
	}

}