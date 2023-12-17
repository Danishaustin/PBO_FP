package object;

import java.awt.geom.Rectangle2D;

import main.Game;
import static utilz.Constant.ObjectConstants.EXPLOSION_SCALE;

public class Explosion extends GameObject {
	
	private float width = 35 * 1.5f;
	private float height = 35 * 1.5f;
	private boolean hittingPlayer;

	public Explosion(int x, int y, int objType) {
		super(x, y, objType);
		
		xDrawOffset = (int) (5.5f * Game.SCALE * EXPLOSION_SCALE);
		yDrawOffset = (int) (6.5f * Game.SCALE * EXPLOSION_SCALE);
		
		xAdjustment = (int)((Game.TILES_DEFAULT_SIZE/2 - width/2) * Game.SCALE);
		yAdjustment = (int)((8 + Game.TILES_DEFAULT_SIZE - height) * Game.SCALE);
		
		initHitbox(width, height);
	}
	
	public void update() {
		updateAnimationTick();
	}
	
	public boolean hitPlayer(Rectangle2D.Float playerHitbox) {
		hittingPlayer = false;
		if(aniIndex >= 1 && aniIndex < 6) {
			if(playerHitbox.intersects(this.hitbox)) {
				hittingPlayer = true;
			}
		}
		
		return this.hittingPlayer;
	}

}
