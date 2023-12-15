package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;

public class KeyboardInput implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInput(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING -> gamePanel.getGame().getPlaying().keyReleased(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (Gamestate.state) {
		case PLAYING -> gamePanel.getGame().getPlaying().keyPressed(e);
		}
	}
}