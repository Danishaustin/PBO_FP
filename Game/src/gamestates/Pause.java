package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;
import ui.PauseButton;

public class Pause extends States {
	
	private static final int PLAY_BUTTON = 0;
	private static final int HOME_BUTTON = 1;
	
	private PauseButton[] buttons = new PauseButton[2];

	public Pause(Game game) {
		super(game);
		loadButtonImg();
	}

	private void loadButtonImg() {
		buttons[PLAY_BUTTON] = new PauseButton(Game.GAME_WIDTH / 2 - 100, (int) (200 * Game.SCALE), 0);
		buttons[HOME_BUTTON] = new PauseButton(Game.GAME_WIDTH / 2 + 100, (int) (200 * Game.SCALE), 1);
	}

	@Override
	public void update() {
		for(PauseButton pb : buttons)
			pb.update();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		for(PauseButton pb : buttons)
			pb.draw(g);
		
	}
	
	public void mouseMoved(MouseEvent e) {
		for(PauseButton pb : buttons) {
			pb.setMouseHover(false);
			
			if(isInButton(e, pb)) {
				pb.setMouseHover(true);
				break;
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if(isInButton(e, buttons[0])) {
			game.getPlaying().unpauseGame();
		}
		else if(isInButton(e, buttons[1])) {
			Gamestate.state = Gamestate.MENU;
			game.getPlaying().getPlayer().reset();
			game.getPlaying().unpauseGame();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		for(PauseButton pb : buttons) {
			if(!isInButton(e, pb)) {
				pb.setMouseHover(false);
				break;
			}
		}
	}

}
