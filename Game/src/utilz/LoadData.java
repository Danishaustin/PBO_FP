package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadData {

	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "mossy_tileset.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";

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

	public static int[][] GetLevelData() {
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

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