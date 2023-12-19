package utilz;

import main.Game;

public class Constant {
	
	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 150;
			public static final int B_HEIGHT_DEFAULT = 80;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
			public static final int B_SQUARE_SIZE_DEFAULT = 80;
			public static final int B_SQUARE_SIZE = (int) (B_SQUARE_SIZE_DEFAULT * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class Hint {
		
		public static final String HINT_TEXT_0 = "A little girl goes to the     store and buys one dozen eggs.As she is going home, all but  three eggs break. How many eggsare left unbroken. You have a choice 1-8";
		public static final String HINT_TEXT_1 = "The thorn is the savior, the closest believer is the one who will survive";
	}
	
	public static class ObjectConstants {

		public static final int KEY = 0;
		public static final int BOX = 1;
		public static final int EXPLODING_BOX = 2;
		public static final int KEY_BOX = 3;
		public static final int HINT_BOX = 4;
		public static final int SPIKE = 5;
		public static final int SPIKE_TRAP = 6;
		public static final int EXPLOSION = 100;

//		public static final int CANNON_LEFT = 4;
//		public static final int CANNON_RIGHT = 5;
//		public static final int TORCH = 6;
//		public static final int TORCH_LIT = 7;

		public static final int KEY_WIDTH_DEFAULT = 32;
		public static final int KEY_HEIGHT_DEFAULT = 28;
		public static final int KEY_WIDTH = (int) (Game.SCALE * KEY_WIDTH_DEFAULT);
		public static final int KEY_HEIGHT = (int) (Game.SCALE * KEY_HEIGHT_DEFAULT);
		
		public static final int BOX_WIDTH_DEFAULT = 40;
		public static final int BOX_HEIGHT_DEFAULT = 21;
		public static final int BOX_WIDTH = (int) (Game.SCALE * BOX_WIDTH_DEFAULT);
		public static final int BOX_HEIGHT = (int) (Game.SCALE * BOX_HEIGHT_DEFAULT);
		
		public static final int EXPLOSION_WIDTH_DEFAULT = 48;
		public static final int EXPLOSION_HEIGHT_DEFAULT = 48;
		public static final float EXPLOSION_SCALE = 1.5f;
		public static final int EXPLOSION_WIDTH = (int) (Game.SCALE * EXPLOSION_WIDTH_DEFAULT * EXPLOSION_SCALE);
		public static final int EXPLOSION_HEIGHT = (int) (Game.SCALE * EXPLOSION_HEIGHT_DEFAULT * EXPLOSION_SCALE);
		
		public static final int HINT_WIDTH_DEFAULT = 32;
		public static final int HINT_HEIGHT_DEFAULT = 22;
		public static final int HINT_WIDTH = (int) (Game.SCALE * HINT_WIDTH_DEFAULT);
		public static final int HINT_HEIGHT = (int) (Game.SCALE * HINT_HEIGHT_DEFAULT);
		
		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 22;
		public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
		public static final int SPIKE_HEIGHT = (int) (Game.SCALE * (SPIKE_HEIGHT_DEFAULT + 12));

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case KEY, BOX, EXPLOSION:
				return 8;
			case HINT_BOX:
				return 6;
			case SPIKE, SPIKE_TRAP:
				return 15;
			}
			return 1;
		}
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int DEATH = 4;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 4;
			case IDLE:
				return 6;
			case JUMP:
			case FALLING:
				return 3;
			case DEATH:
				return 9;
			default:
				return 1;
			}
		}
	}

}