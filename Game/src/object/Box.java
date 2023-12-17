package object;

import main.Game;

public class Box extends GameObject {
	private int width = 25;
	private int height = 18;

	public Box(int x, int y, int objType) {
		super(x, y, objType);

		xDrawOffset = (int) (7 * Game.SCALE);
		yDrawOffset = (int) (3 * Game.SCALE);
		
		xAdjustment = (int)((Game.TILES_DEFAULT_SIZE/2 - width/2) * Game.SCALE);
		yAdjustment = (int)((8 + Game.TILES_DEFAULT_SIZE - height) * Game.SCALE);
		
		initHitbox(width, height);
	}
	
	public void update() {
		if (doAnimation)
			updateAnimationTick();
	}

}
