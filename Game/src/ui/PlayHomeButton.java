package ui;

import static utilz.Constant.UI.Buttons.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import utilz.LoadData;

public class PlayHomeButton extends Buttons {
	private int rowIndex;
	private int xOffsetCenter = B_SQUARE_SIZE / 2;
	private BufferedImage[] img;

	public PlayHomeButton(int xPos, int yPos, int rowIndex) {
		super(xPos, yPos);
		this.rowIndex = rowIndex;
		loadImgs();
		initButtonArea();
	}
	
	protected void loadImgs() {
		img = new BufferedImage[2];
		BufferedImage temp = LoadData.GetSpriteAtlas(LoadData.PAUSE_BUTTONS);
		for (int i = 0; i < img.length; i++)
			img[i] = temp.getSubimage(i * B_SQUARE_SIZE_DEFAULT, rowIndex * B_SQUARE_SIZE_DEFAULT, B_SQUARE_SIZE_DEFAULT, B_SQUARE_SIZE_DEFAULT);
	}

	protected void initButtonArea() {
		buttonArea = new Rectangle(xPos - xOffsetCenter, yPos, B_SQUARE_SIZE, B_SQUARE_SIZE);
	}

	public void draw(Graphics g) {
		g.drawImage(img[index], xPos - xOffsetCenter, yPos, B_SQUARE_SIZE, B_SQUARE_SIZE, null);
	}
	
}
