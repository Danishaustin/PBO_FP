package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import utilz.LoadData;
import static utilz.Constant.UI.Buttons.*;

public class MenuButton extends Buttons {
	private int rowIndex;
	private int xOffsetCenter = B_WIDTH / 2;
	private BufferedImage[] img;
	private Gamestate state;

	public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
		super(xPos, yPos);
		this.state = state;
		this.rowIndex = rowIndex;
		loadImgs();
		initButtonArea();
	}

	@Override
	protected void loadImgs() {
		img = new BufferedImage[2];
		BufferedImage temp = LoadData.GetSpriteAtlas(LoadData.MENU_BUTTONS);
		for (int i = 0; i < img.length; i++)
			img[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
	}

	@Override
	protected void initButtonArea() {
		buttonArea = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
	}
	
    public void applyGamestate() {
        Gamestate.state = state;
    }
    
    public Gamestate getGamestate() {
    	return this.state;
    }

}