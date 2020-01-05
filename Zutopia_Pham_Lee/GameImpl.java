import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import java.util.*;
import java.util.List;

import javafx.scene.media.*;
import javafx.scene.image.*;


public class GameImpl extends Pane implements Game {
	/**
	 * Defines different states of the game.
	 */
	public enum GameState {
		WON, LOST, ACTIVE, NEW
	}

	/**
	 * The width of the game board.
	 */
	public static final int WIDTH = 400;
	/**
	 * The height of the game board.
	 */
	public static final int HEIGHT = 600;


	// Instance variables
	private Ball ball;
	private Paddle paddle;
	private List<Label> allGame;
	private int count;

	/**
	 * Constructs a new GameImpl.
	 */
	public GameImpl () {
		setStyle("-fx-background-color: white;");
		count = 16;
		restartGame(GameState.NEW);
	}

	/**
	 * @return the title of the game
	 */
	public String getName () {
		return "Zutopia";
	}

	/**
	 * @return pane
	 */
	public Pane getPane () {
		return this;
	}

	/**
	 * Creates a label by putting x and y coordinates
	 * @param name the file name
	 * @param x x position (Layout x) of the image
	 * @param y y position (Layout y) of the image
	 * @return label (image with x and y position)
	 */
	private Label getImage(String name, int x, int y)
	{
		final Image image = new Image(getClass().getResourceAsStream(name));
		Label label = new Label("", new ImageView(image));
		label.setLayoutX(x);
		label.setLayoutY(y);

		return label;
	}
	/**
	 * Creates the Rows of Animals in the game and add them to the screen
	 */
	private void addAnimal (String[][] gamepos)
	{
		int space = WIDTH/9;
		int space2 = HEIGHT/8;

		// add picture to the screen accordingly to the 2D array
		// every image are space apart horizontally and space2 apart vertically
		for (int col = 0; col<4; col++)
		{
			for (int row = 0; row < 4; row++)
			{
				Label label = getImage(gamepos[col][row], (2*row+1)*space, (col*space2) + HEIGHT/20);
				getChildren().add(label);
				allGame.add(label);
			}
		}
	}


	/**
	 * Restarts a game when the player wins or loses the game.
	 * @param state the game state
	 */
	private void restartGame (GameState state) {
		getChildren().clear();  // remove all components from the game
		allGame = new ArrayList<>();


		// Create and add ball
		ball = new Ball();
		getChildren().add(ball.getCircle());  // Add the ball to the game board

		// Create and add animals
		String duck = "duck.jpg";
		String goat = "goat.jpg";
		String horse = "horse.jpg";

		String [][] gamePos = {{duck, goat, horse, duck},
					{goat, horse, duck, goat},
					{horse, duck, goat, horse},
					{duck, goat, horse, duck}};

		addAnimal(gamePos);

		// Create and add paddle
		paddle = new Paddle();
		getChildren().add(paddle.getRectangle());  // Add the paddle to the game board

		// Add start message
		final String message;
		if (state == GameState.LOST) {
			message = "Game Over with " + count + " animals remaining \n";
			count = 16;
		} else if (state == GameState.WON) {
			message = "You won!\n";
		} else {
			message = "";
		}
		final Label startLabel = new Label(message + "Click mouse to start");
		startLabel.setLayoutX(WIDTH / 2 - 50);
		startLabel.setLayoutY(HEIGHT / 2 + 100);
		getChildren().add(startLabel);

		// Add event handler to start the game
		setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle (MouseEvent e) {
				GameImpl.this.setOnMouseClicked(null);
				// As soon as the mouse is clicked, remove the startLabel from the game board
				ball.setCount(0);
				setOnMouseMoved(new EventHandler<MouseEvent>() {
									@Override
									public void handle(MouseEvent event) {
										paddle.moveTo(event.getX(), event.getY());
									}
								}
				);
				getChildren().remove(startLabel);
				run();
			}
		});
	}

	/**
	 * Begins the game-play by creating and starting an AnimationTimer.
	 */
	public void run () {
		// Instantiate and start an AnimationTimer to update the component of the game.
		new AnimationTimer () {
			private long lastNanoTime = -1;
			public void handle (long currentNanoTime) {
				if (lastNanoTime >= 0) {  // Necessary for first clock-tick.
					GameState state;
					if ((state = runOneTimestep(currentNanoTime - lastNanoTime)) != GameState.ACTIVE) {
						// Once the game is no longer ACTIVE, stop the AnimationTimer.
						stop();
						// Restart the game, with a message that depends on whether
						// the user won or lost the game.
						restartGame(state);
					}
				}
				// Keep track of how much time actually transpired since the last clock-tick.
				lastNanoTime = currentNanoTime;
			}
		}.start();
	}

	/**
	 * Updates the state of the game at each timestep. In particular, this method should
	 * move the ball, check if the ball collided with any of the animals, walls, or the paddle, etc.
	 * @param deltaNanoTime how much time (in nanoseconds) has transpired since the last update
	 * @return the current game state
	 */
	public GameState runOneTimestep (long deltaNanoTime) {

		if (ball.getCount() == 5)
		{
			return GameState.LOST;
		}
		ball.updatePosition(deltaNanoTime, paddle, checkCollisionAnimal());
		if (allGame.size() ==0)
		{
			return GameState.WON;
		}
		return GameState.ACTIVE;

	}

	/**
	 * makes sound for different names
	 * @param name the file name
	 * @return Audioclip the Autoclip from the given file
	 */
	private AudioClip makeSound(String name)
	{
		final AudioClip sound = new AudioClip(getClass().getClassLoader().getResource(name).toString());
		return sound;
	}

	/**
	 * Checks if there is collision between the ball and the picture of animals
	 * update and remove the animal if collision happens and update count of animals on screen
	 * @return true if they collide
	 */
	public boolean checkCollisionAnimal()
	{
		AudioClip bounce = makeSound("boing.wav");
		//create bounds for ball
		Bounds b = ball.getCircle().localToScene(ball.getCircle().getBoundsInLocal());
		for (Label i: allGame)
		{
			//create boundary for every picture
			Bounds a = i.localToScene(i.getBoundsInLocal());
			//as soon as the ball intersect with te picture
			//the picture/animal is removed from the screen and from allGame List
			if (b.intersects(a))
			{
				bounce.play();
				getChildren().remove(i);
				allGame.remove(i);
				count--;
				return true;
			}
		}
		return false;
	}
}
