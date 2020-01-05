import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class that implements a ball with a position and velocity.
 */
public class Ball {
	// Constants
	/**
	 * The radius of the ball.
	 */
	public static final int BALL_RADIUS = 8;
	/**
	 * The initial velocity of the ball in the x direction.
	 */
	public static final double INITIAL_VX = 1e-7;
	/**
	 * The initial velocity of the ball in the y direction.
	 */
	public static final double INITIAL_VY = 1e-7;
	/**
	 * sounds for bouncing off the bottom
	 */
	public final AudioClip shatter = new AudioClip(getClass().getClassLoader().getResource("shatter.wav").toString());

	// Instance variables
	// (x,y) is the position of the center of the ball.
	private double x, y;
	private double vx, vy;
	private Circle circle;
	private int count;



	/**
	 * @return the Circle object that represents the ball on the game board.
	 */
	public Circle getCircle () {
		return circle;
	}

	/**
	 * Constructs a new Ball object at the centroid of the game board
	 * with a default velocity that points down and right.
	 */
	public Ball () {
		x = GameImpl.WIDTH/2;
		y = GameImpl.HEIGHT/2;
		vx = INITIAL_VX;
		vy = INITIAL_VY;

		circle = new Circle(BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		circle.setLayoutX(x - BALL_RADIUS);
		circle.setLayoutY(y - BALL_RADIUS);
		circle.setFill(Color.BLACK);
	}

	/**
	 * Updates the position of the ball, given its current position and velocity,
	 * based on the specified elapsed time since the last update.
	 * where all the bouncing happens
	 * @param deltaNanoTime the number of nanoseconds that have transpired since the last update
	 * @param paddle the paddle
	 * @param checkCollisionAnimal boolean of if the ball collide with any animal picture
	 */
	public void updatePosition (long deltaNanoTime, Paddle paddle, boolean checkCollisionAnimal) {
		//check if ball id collide with left wall --> bounce back in 90 degree
		if(x <= BALL_RADIUS && vx <0)
		{
			vx = -vx;
		}
		//check collision with right wall --> bounce back
		else if ( x >= 400 - BALL_RADIUS && vx>0)
		{
			vx  = -vx;
		}
		//check collision with top edge and bottom side of the paddle-->bounce
		else if((y <= BALL_RADIUS || checkCollision(paddle) == "bottom")&& vy <0)
		{
			vy =-vy;
		}
		//check collision with bottom edge anf top side of paddle->bounce
		else if ((y>= 600 - BALL_RADIUS || checkCollision(paddle) == "top") && vy>0)
		{
			vy = -vy;
			if (y>= 600 - BALL_RADIUS) {
				shatter.play();
				count += 1; //update count if collide with the bottom edge
			}
		}
		//check collision with animal --> increase speed and bounce
		if (checkCollisionAnimal)
		{
			vx = (1.15)*vx;
			vy= (1.15) *vy;
			if (x>=y)
			{
				vx = -(vx);
			}
			else
			{
				vy = -(vy);
			}
		}
		double dx = vx * deltaNanoTime;
		double dy = vy * deltaNanoTime;
		x += dx;
		y += dy;
		circle.setTranslateX(x - (circle.getLayoutX() + BALL_RADIUS));
		circle.setTranslateY(y - (circle.getLayoutY() + BALL_RADIUS));
	}

	/**
	 * getter -> get count value
	 * @return count
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * a setter that set count equal to newCount
	 * @param newCount new value for count
	 */
	public void setCount(int newCount)
	{
		count = newCount;
	}

	/**
	 * Check the collision between the paddle and the ball
	 * @param paddle the paddle
	 * @return String value of the location where they collide.
	 */
	private String checkCollision(Paddle paddle)
	{
		double radius = circle.getRadius();

		double ballXLeft = x - radius;
		double ballXRight = x+ radius;
		double ballYUp = y -radius;
		double ballYDown = y +radius;

		double paddleXLeft = paddle.getX() - paddle.PADDLE_WIDTH/2;
		double paddleXRight = paddle.getX() + paddle.PADDLE_WIDTH/2;
		double paddleYTop = paddle.getY() - paddle.PADDLE_HEIGHT/2;
		double paddleYBottom = paddle.getY() + paddle.PADDLE_HEIGHT/2;

		if ( ballXRight >= paddleXLeft && ballXLeft <= paddleXRight )
		{
			if (ballYDown >= paddleYTop && ballYUp < paddleYTop)
			{
				return "top";
			}
			else if (ballYUp <= paddleYBottom && ballYDown > paddleYBottom)
			{
				return "bottom";
			}
		}
		return "none";
	}

}

