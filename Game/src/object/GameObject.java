package object;

import static utilz.Constant.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObject {

	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex, aniSpeed = 10;
	protected int xDrawOffset, yDrawOffset;
	protected int xAdjustment, yAdjustment;

	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(objType)) {
				aniIndex = 0;
				if (objType == BOX || objType == EXPLOSION || objType == EXPLODING_BOX || objType == KEY_BOX) {
					doAnimation = false;
					active = false;
				}
			}
		}
	}

	public void reset() {
		aniIndex = 0;
		if (objType == SPIKE)
			aniIndex = 9;
		aniTick = 0;
		active = true;

		if (objType == BOX || objType == EXPLODING_BOX || objType == KEY_BOX || objType == SPIKE)
			doAnimation = false;
		else
			doAnimation = true;
	}

	protected void initHitbox(float width, float height) {
		hitbox = new Rectangle2D.Float(x + xAdjustment, y + yAdjustment, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
	}

	public void drawHitbox(Graphics g, int xLvlOffset, int yLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y - yLvlOffset, (int) hitbox.width, (int) hitbox.height);
	}

	public int getObjType() {
		return objType;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}
	
	public boolean getAnimation() {
		return this.doAnimation;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getAniTick() {
		return aniTick;
	}

}
