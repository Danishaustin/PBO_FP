package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;


public class Menu extends States {
	
	private static final int PLAY_BUTTON = 0;
	private static final int EXIT_BUTTON = 1;
	
	private MenuButton[] buttons = new MenuButton[2];

	public Menu(Game game) {
		super(game);
		loadButtonImage();
	}


	private void loadButtonImage() {
		buttons[PLAY_BUTTON] = new MenuButton(Game.GAME_WIDTH / 2, (int) (160 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[EXIT_BUTTON] = new MenuButton(Game.GAME_WIDTH / 2, (int) (260 * Game.SCALE), 1, Gamestate.EXIT);
	}

	@Override
	public void update() {
		for(MenuButton mb : buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(50, 47, 48));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		for(MenuButton mb : buttons)
			mb.draw(g);
	}
	
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseHover(false);
			
			if(isInButton(e, mb)) {
				mb.setMouseHover(true);
				break;
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isInButton(e, mb)) {
				mb.applyGamestate();
				if(mb.getGamestate() == Gamestate.PLAYING) {
					game.getPlaying().unpauseGame();
				}
				break;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(!isInButton(e, mb)) {
				mb.setMouseHover(false);
				break;
			}
		}
	}

}
