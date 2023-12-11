package ui;

import java.awt.Graphics;
import java.awt.Rectangle;

import gamestates.Gamestate;


public abstract class Buttons {
    protected int xPos, yPos, index;
    protected boolean mouseHover;
    protected Rectangle buttonArea;

    public Buttons(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    protected abstract void loadImgs();

    protected abstract void initButtonArea();

    public abstract void draw(Graphics g);

    public void update() {
        index = 0;
        if (mouseHover)
            index = 1;
    }

    public void setMouseHover(boolean mouseHover) {
        this.mouseHover = mouseHover;
    }

    public Rectangle getButtonArea() {
        return buttonArea;
    }
}
