package gamestates;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;
import ui.Buttons;

public abstract class States {
	
	protected Game game;
	
	public States (Game game) {
		this.game = game;
	}
	
	public boolean isInButton(MouseEvent e, Buttons b) {
		return b.getButtonArea().contains(e.getX(), e.getY());
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics g);

}
