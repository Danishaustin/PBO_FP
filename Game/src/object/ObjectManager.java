package object;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadData;
import static utilz.Constant.ObjectConstants.*;

public class ObjectManager {

	private Playing playing;
	private BufferedImage[] keyImg, boxImg, explosionImg, hintImg;
	private ArrayList<Key> keys;
	private ArrayList<Box> boxes;
	private ArrayList<HintBox> hints;
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private Rectangle2D.Float playerHitbox;
	
	private int totalKey;

	public ObjectManager(Playing playing) {
		this.playing = playing;
		
		loadImgs();
	}
	
	private void loadImgs() {
		BufferedImage keySprite = LoadData.GetSpriteAtlas(LoadData.KEY_SPRITES);
		keyImg = new BufferedImage[8];
		
		for (int i = 0; i < keyImg.length; i++)
			keyImg[i] = keySprite.getSubimage(32 * i, 0, 32, 28);

		BufferedImage boxSprite = LoadData.GetSpriteAtlas(LoadData.BOX_SPRITES);
		boxImg = new BufferedImage[8];
		
		for (int i = 0; i < boxImg.length; i++)
			boxImg[i] = boxSprite.getSubimage(40 * i, 0, 40, 21);
		
		BufferedImage explosionSprite = LoadData.GetSpriteAtlas(LoadData.EXPLOSION_SPRITES);
		explosionImg = new BufferedImage[8];
		
		for (int i = 0; i < explosionImg.length; i++)
			explosionImg[i] = explosionSprite.getSubimage(48 * i, 0, 48, 48);
		
		BufferedImage hintSprite = LoadData.GetSpriteAtlas(LoadData.HINT_SPRITES);
		hintImg = new BufferedImage[6];
		
		for (int i = 0; i < hintImg.length; i++)
			hintImg[i] = hintSprite.getSubimage(32 * i, 0, 32, 22);
	}
	
	public void loadObjects(Level newLevel) {
		keys = new ArrayList<>(newLevel.getKeys());
		boxes = new ArrayList<>(newLevel.getBoxes());
		hints = new ArrayList<>(newLevel.getHints());
		explosions.clear();
	}
	
	public void update() {
		for (Key k : keys)
			if (k.isActive())
				k.update();

		for (Box b : boxes)
			if (b.isActive())
				b.update();
		
		for (HintBox hb : hints){
			if (hb.isActive())
				hb.update();
		}
		
		if (!explosions.isEmpty()) {
			if(!explosions.getFirst().isActive()) {
				explosions.removeFirst();
			}
			explosionUpdate();
		}
	}

	private void explosionUpdate() {
		for (Explosion e : explosions) {
			if(e.isActive()) {
				e.update();
				playerHitbox = playing.getPlayer().getHitbox();
				if(e.hitPlayer(playerHitbox)) {
					playing.isGameOver();
				}
			}
		}
		
	}

	public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
		drawBoxes(g, xLvlOffset, yLvlOffset);
		drawKeys(g, xLvlOffset, yLvlOffset);
		drawHints(g, xLvlOffset, yLvlOffset);
		
		if(!explosions.isEmpty()) {
			drawExplosion(g, xLvlOffset, yLvlOffset);
		}
	}

	private void drawHints(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (HintBox hb : hints)
			if (hb.isActive()) {
				g.drawImage(hintImg[hb.getAniIndex()], (int) (hb.getHitbox().x - hb.getxDrawOffset() - xLvlOffset), 
						(int) (hb.getHitbox().y - hb.getyDrawOffset() - yLvlOffset), 
						HINT_WIDTH,
						HINT_HEIGHT, 
						null);
				hb.drawHitbox(g, xLvlOffset, yLvlOffset);
			}
		
	}

	private void drawExplosion(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Explosion e : explosions) {
			if(e.isActive()) {
				g.drawImage(explosionImg[e.getAniIndex()], (int) (e.getHitbox().x - e.getxDrawOffset() - xLvlOffset), 
						(int) (e.getHitbox().y - e.getyDrawOffset() - yLvlOffset), 
						EXPLOSION_WIDTH,
						EXPLOSION_HEIGHT, 
						null);
				e.drawHitbox(g, xLvlOffset, yLvlOffset);
			}
		}
		
	}

	private void drawBoxes(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Box b : boxes)
			if (b.isActive()) {
				g.drawImage(boxImg[b.getAniIndex()], (int) (b.getHitbox().x - b.getxDrawOffset() - xLvlOffset), 
						(int) (b.getHitbox().y - b.getyDrawOffset() - yLvlOffset), 
						BOX_WIDTH,
						BOX_HEIGHT, 
						null);
				b.drawHitbox(g, xLvlOffset, yLvlOffset);
			}
	}

	private void drawKeys(Graphics g, int xLvlOffset, int yLvlOffset) {
		for (Key k : keys)
			if (k.isActive()) {
				g.drawImage(keyImg[k.getAniIndex()], (int) (k.getHitbox().x - k.getxDrawOffset() - xLvlOffset), 
						(int) (k.getHitbox().y - k.getyDrawOffset() - yLvlOffset), 
						KEY_WIDTH, 
						KEY_HEIGHT,
						null);
				k.drawHitbox(g, xLvlOffset, yLvlOffset);
			}
	}

	public void checkObjectTouched() {
		playerHitbox = playing.getPlayer().getHitbox();
		
		for (Key k : keys)
			if (k.isActive()) {
				if (playerHitbox.intersects(k.getHitbox())) {
					k.setActive(false);
					playing.setKeysLeftDecrease();
				}
			}
		
		for (Box b : boxes)
			if (b.isActive()) {
				if (playerHitbox.intersects(b.getHitbox())) {
					if(b.getObjType() == EXPLODING_BOX) {
						explosions.add(new Explosion(b.x, b.y, EXPLOSION));
					} else if (b.getObjType() == KEY_BOX) {
						keys.add(new Key(b.x, b.y, KEY));
					}
					b.setAnimation(true);
				}
			}
		
		for (HintBox hb : hints)
			if (hb.isActive()) {
				if (playerHitbox.intersects(hb.getHitbox())) {
					playing.hintTextDraw(hb.getText());
				}
			}
	}
	

//	public void checkObjectHit(Rectangle2D.Float attackbox) {
//		for (GameContainer gc : containers)
//			if (gc.isActive()) {
//				if (gc.getHitbox().intersects(attackbox)) {
//					gc.setAnimation(true);
//					int type = 0;
//					if (gc.getObjType() == BARREL)
//						type = 1;
//					potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2), (int) (gc.getHitbox().y - gc.getHitbox().height / 2), type));
//					return;
//				}
//			}
//	}

	public void resetAllObjects(Level level) {
		this.loadObjects(level);
		for (Key k : keys)
			k.reset();

		for (Box b : boxes)
			b.reset();
		
		for (HintBox hb : hints)
			hb.reset();
	}
	
	public int getTotalKey() {
		totalKey = this.keys.size();
		for(Box b : boxes) {
			if(b.objType == KEY_BOX)
				totalKey++;
		}
		return this.totalKey;
	}
}