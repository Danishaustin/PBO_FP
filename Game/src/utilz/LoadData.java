package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadData {

	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "mossy_tileset.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String MENU_BUTTONS = "menu_buttons.png";
	public static final String MENU_TITLE = "menu_title.png";
	public static final String PAUSE_BUTTONS = "pause_buttons.png";
	public static final String BACKGROUND = "background.png";

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

	public static int[][] GetLevelData() {
		
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
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
}