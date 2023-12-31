package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class Collision {

	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		
		int levelWidth = lvlData[0].length * Game.TILES_SIZE;
		int levelHeight = lvlData.length * Game.TILES_SIZE;
		
		if (x < 0 || x >= levelWidth)
			return true;
		if (y < 0 || y >= levelHeight)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value < 49 && value >= 0)
			return true;
		return false;
	}
	
	public static float xWallCollide(Rectangle2D.Float hitbox, float xSpeed) {
		int currentPosition = (int)(hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0) {
			int xTilePosition = currentPosition * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return xTilePosition + xOffset - 1;
		}else {
			return currentPosition * Game.TILES_SIZE;
		}
	}
	
	public static float yWallCollide(Rectangle2D.Float hitbox, float airAccelerate) {
		int currentPosition = (int)(hitbox.y / Game.TILES_SIZE);
		if(airAccelerate > 0) {
			int yTilePosition = currentPosition * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return yTilePosition + yOffset - 1;
		}else {
			return currentPosition * Game.TILES_SIZE;
		}
	}
}