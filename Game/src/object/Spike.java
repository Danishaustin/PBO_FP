package object;

import static utilz.Constant.ObjectConstants.SPIKE;

import java.awt.geom.Rectangle2D;

import main.Game;

public class Spike extends GameObject{
	
	private int width = 32;
	private int height = 25;
	private boolean hittingPlayer;

	public Spike(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation = true;
		
		if (objType == SPIKE) {
			aniIndex = 9;
			doAnimation = false;
		}
		aniSpeed = 10;
		
		xAdjustment = (int)((Game.TILES_DEFAULT_SIZE/2 - width/2) * Game.SCALE);
		yAdjustment = (int)((10 + Game.TILES_DEFAULT_SIZE - height) * Game.SCALE);
		
		initHitbox(width, height);
	}
	
	public void update() {
		if(doAnimation)
			updateAnimationTick();
	}
	
	public boolean hitPlayer(Rectangle2D.Float playerHitbox) {
		hittingPlayer = false;
		if(aniIndex >= 9) {
			if(playerHitbox.intersects(this.hitbox)) {
				hittingPlayer = true;
			}
		}
		
		return this.hittingPlayer;
	}

}
