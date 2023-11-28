package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;

import utilz.LoadData;
import static utilz.Constant.PlayerConstants.*;
import static utilz.Collision.*;

public class Player {
	// player initialization
	float x, y;
	int width, height;
	private Rectangle2D.Float hitbox;
	private float xDrawOffset = 24 * Game.SCALE;
	private float yDrawOffset = 24 * Game.SCALE;
	
	// player animations
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 10;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean left, right, jump;
	private float playerSpeed = 2.0f;
	
	// map data
	private int[][] lvlData;
	
	// player gravity
	private float airAccelerate = 0f;
	private float gravity = 0.06f * Game.SCALE;
	private float jumpAccelerate = -3f * Game.SCALE;
	private float fallAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
	
	// player flip image
	private int flipX = 0;
	private int flipWidth = 1;

	public Player(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		loadAnimations();
		initHitbox(x, y, 22 * Game.SCALE, 20 * Game.SCALE);

	}
	
	// Rendering Method
	
	private void initHitbox(float x, float y, float width, float height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
	}
	
	private void drawHitbox(Graphics g) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);

	}

	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) + flipX, (int) (hitbox.y - yDrawOffset), 
				width * flipWidth, height, null);
		drawHitbox(g);
	}
	
	// Animations Method
	
	private void loadAnimations() {

		BufferedImage img = LoadData.GetSpriteAtlas(LoadData.PLAYER_ATLAS);

		animations = new BufferedImage[5][9];
		
		animations[IDLE] = LoadData.GetAnimationSprite(img, 0, 0, GetSpriteAmount(IDLE));
		animations[RUNNING] = LoadData.GetAnimationSprite(img, 4, 1, GetSpriteAmount(RUNNING));
		animations[JUMP] = LoadData.GetAnimationSprite(img, 0, 2, GetSpriteAmount(JUMP));
		animations[FALLING] = LoadData.GetAnimationSprite(img, 4, 2, GetSpriteAmount(FALLING));
		animations[DEATH] = LoadData.GetAnimationSprite(img, 0, 3, GetSpriteAmount(DEATH));
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
			}

		}

	}
	
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		
		if(inAir) {
			if(airAccelerate < 0)
				playerAction = JUMP;
			if(airAccelerate > 0)
				playerAction = FALLING;
		}

		if (startAni != playerAction)
			resetAniTick();
	}

	
	// Moving Method

	private void updatePos() {
		moving = false;
		inAirCheck();
		if (!left && !right && !jump && !inAir)
			return;

		float xSpeed = 0;
		
		if(jump && !inAir) {
			inAir = true;
			airAccelerate = jumpAccelerate;
		}
		if (left) {
			xSpeed -= playerSpeed;
			flipImage(true);
		}
		if (right) {
			xSpeed += playerSpeed;
			flipImage(false);
		}
		
		if(inAir) {
			if(CanMoveHere(hitbox.x, hitbox.y + airAccelerate, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airAccelerate;
				airAccelerate += gravity;
				updateXPos(xSpeed);
			}else {
				hitbox.y = yWallCollide(hitbox, airAccelerate);
				if(airAccelerate > 0) {
					inAir = false;
					airAccelerate = 0;
				}
				else
					airAccelerate = fallAfterCollision;
				updateXPos(xSpeed);
			}
		}else {
			updateXPos(xSpeed);
		}
		
		moving = true;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		}else {
			hitbox.x = xWallCollide(hitbox, xSpeed);
		}
	}
	
	private void inAirCheck() {
		if(CanMoveHere(hitbox.x, hitbox.y + 1, hitbox.width, hitbox.height, lvlData)){
			inAir = true;
		}
	}
	
	private void flipImage(boolean flip) {
		if(flip) {
			flipX = width;
			flipWidth = -1;
			xDrawOffset = 18 * Game.SCALE;
		} else {
			flipX = 0;
			flipWidth = 1;
			xDrawOffset = 24 * Game.SCALE;
		}
	}
	
	// Load Level Data

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
	}
	
	// Set Direction Booleans

	public void resetDirBooleans() {
		left = false;
		right = false;
		jump = false;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}