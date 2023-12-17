package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Game;
import ui.PlayHomeButton;

public class Pause extends States {
	
	private static final int PLAY_BUTTON = 0;
	private static final int HOME_BUTTON = 1;
	
	private PlayHomeButton[] buttons = new PlayHomeButton[2];
	private Font font;

	public Pause(Game game) {
		super(game);
		loadButtonImg();
		font = new Font("Arial", Font.BOLD, 100);
	}

	private void loadButtonImg() {
		buttons[PLAY_BUTTON] = new PlayHomeButton(Game.GAME_WIDTH / 2 + 100, (int) (200 * Game.SCALE), 0);
		buttons[HOME_BUTTON] = new PlayHomeButton(Game.GAME_WIDTH / 2 - 100, (int) (200 * Game.SCALE), 1);
	}

	@Override
	public void update() {
		for(PlayHomeButton pb : buttons)
			pb.update();
	}

	public void draw(Graphics g, Gamestate state) {
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		
		switch(state) {
		case PAUSED:
			g.drawString("Pause", Game.GAME_WIDTH/2 - 150, (int)(150 * Game.SCALE));
			break;
		case GAMEOVER:
			g.setColor(Color.RED);
			g.drawString("You Died", Game.GAME_WIDTH/2 - 210, (int)(150 * Game.SCALE));
			break;
		case FINISH:
			g.drawString("Level Completed", Game.GAME_WIDTH/2 - 375, (int)(150 * Game.SCALE));
			break;
		default:
			break;
		}

		for(PlayHomeButton pb : buttons)
			pb.draw(g);
		
	}
	
	public void mouseMoved(MouseEvent e) {
		for(PlayHomeButton pb : buttons) {
			pb.setMouseHover(false);
			
			if(isInButton(e, pb)) {
				pb.setMouseHover(true);
				break;
			}
		}
	}
	
	public void mouseClicked(MouseEvent e, Gamestate state) {
		if(isInButton(e, buttons[0])) {
			if(state == Gamestate.GAMEOVER) {
				game.getPlaying().resetAll();
			}
			else if(state == Gamestate.FINISH) {
				
			}
			game.getPlaying().unpauseGame();
		}
		else if(isInButton(e, buttons[1])) {
			Gamestate.state = Gamestate.MENU;
			game.getPlaying().resetAll();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		for(PlayHomeButton pb : buttons) {
			if(!isInButton(e, pb)) {
				pb.setMouseHover(false);
				break;
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
