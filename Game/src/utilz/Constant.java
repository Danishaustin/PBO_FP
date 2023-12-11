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