package object;

import main.Game;

public class HintBox extends GameObject{
	
	private int width = 32;
	private int height = 22;
	private String hintText;

	public HintBox(int x, int y, int objType, String hintText) {
		super(x, y, objType);
		this.hintText = hintText;
		doAnimation = true;

		xDrawOffset = 0;
		yDrawOffset = 0;
		
		xAdjustment = (int)((Game.TILES_DEFAULT_SIZE/2 - width/2) * Game.SCALE);
		yAdjustment = (int)((Game.TILES_DEFAULT_SIZE - height) * Game.SCALE);
		
		initHitbox(width, height);

	}
	
	public void update() {
		updateAnimationTick();
	}
	
	public String getText() {
		return this.hintText;
	}
}
