package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.Game;
import object.Box;
import object.HintBox;
import object.Key;
import object.Spike;

import static utilz.Constant.ObjectConstants.*;
import static utilz.Constant.Hint.*;

public class LoadData {

	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "mossy_tileset.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String MENU_BUTTONS = "menu_buttons.png";
	public static final String MENU_TITLE = "menu_title.png";
	public static final String PAUSE_BUTTONS = "pause_buttons.png";
	public static final String BACKGROUND = "background.png";
	public static final String SPIKE_TRAP_SPRITES = "spike_trap_sprites.png";
	public static final String KEY_SPRITES = "key_sprites.png";
	public static final String TORCH_SPRITES = "torch_sprites.png";
	public static final String BOX_SPRITES = "box_sprites.png";
	public static final String CANNON_SPRITES = "cannon_sprites.png";
	public static final String EXPLOSION_SPRITES = "explosion_sprites.png";
	public static final String HINT_SPRITES = "hint_sprites.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadData.class.getResourceAsStream("/" + fileName);
		try {
			if (is != null) {
		        img = ImageIO.read(is);
		        if (img == null) {
		            throw new IOException("Failed to load image for file: " + fileName);
		        }
		    } else {
		        throw new IOException("Input stream is null for file: " + fileName);
		    }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
	            if (is != null) {
	                is.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		return img;
	}
	
	public static BufferedImage[] GetAnimationSprite(BufferedImage spriteSheets, int xIndexStart, int yIndexStart, int aniLength) {
		BufferedImage[] animations = new BufferedImage[aniLength];
		for(int i = 0; i < aniLength; i++) {
			animations[i] = spriteSheets.getSubimage(xIndexStart * 32 + i * 32, yIndexStart * 32, 32, 32);
		}
		
		return animations;
	}
	
//	public static BufferedImage[] GetSpriteFile(String filePath) {
//		File path = new File(filePath);
//		File[] allFiles = path.listFiles();
//		
//		if(allFiles != null) {
//			
//			BufferedImage[] allImage = new BufferedImage[allFiles.length];
//			
//			for(int i = 0; i < allFiles.length; i++) {
//				try {
//					allImage[i] = ImageIO.read(allFiles[i]);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			return allImage;
//		}
//		
//		return null;
//		
//	}

	public static int[][] GetLevelData(BufferedImage img) {
		
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 49)
					value = 49;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
	
	public static ArrayList<Key> GetKeys(BufferedImage img) {
		ArrayList<Key> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == KEY)
					list.add(new Key(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
			}

		return list;
	}

	public static ArrayList<Box> GetBoxes(BufferedImage img) {
		ArrayList<Box> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == BOX || value == EXPLODING_BOX || value == KEY_BOX) 
					list.add(new Box(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
			}

		return list;
	}
	
	public static ArrayList<HintBox> GetHintBoxes(BufferedImage img, ArrayList<String> text) {
		ArrayList<HintBox> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value >= 0 && value < 5) 
					list.add(new HintBox(i * Game.TILES_SIZE, j * Game.TILES_SIZE, HINT_BOX , text.get(value)));
			}

		return list;
	}
	
	public static ArrayList<String> GetHintText() {
		ArrayList<String> list = new ArrayList<>();
		
		list.add(HINT_TEXT_0);
		list.add(HINT_TEXT_1);
		
		return list;
	}
	
	public static ArrayList<Spike> GetSpikes(BufferedImage img){
		ArrayList<Spike> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getBlue();
				if (value == SPIKE || value == SPIKE_TRAP) 
					list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
			}

		return list;
	}

}