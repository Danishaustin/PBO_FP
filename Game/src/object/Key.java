package object;

import main.Game;

public class Key extends GameObject {
	
	private int width = 24;
	private int height = 28;

	public Key(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;

		xDrawOffset = (int) (4 * Game.SCALE);
		yDrawOffset = 0;
		
		xAdjustment = (int)((Game.TILES_DEFAULT_SIZE/2 - width/2) * Game.SCALE);
		yAdjustment = (int)((Game.TILES_DEFAULT_SIZE - height) * Game.SCALE);
		
		initHitbox(width, height);

	}
	
	public void update() {
		updateAnimationTick();
	}

}
