/*
 * A snake game
 */
import java.util.Random;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	static final int SCREEN_WIDTH  = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE     = 25;
	static final int GAME_UNITS    = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY         = 75;
	final int x[] = new int[GAME_UNITS];                   // will hold all the x coordinates of the body parts
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int mouseEaten;
	int mouseX;                                            // Coordinates of the location of the apples
	int mouseY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	//Image imageJerry = new ImageIcon("jerryTheMouse.png").getImage();

	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}
	
	/*
	 *  The startGame() method will start the game run it is run in the panel
	 *  calls in the newMouse() method: Which is used to generate a mouse in a random location
	 *  By initializing the variable running as true, it will call on the draw method to draw the objects
	 *  Initializing the variable timer and call the .start() method to start the time
	 */
	

	public void startGame() {
		newMouse();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		

	}
	
	/*
	 * paintComponent(Graphics g) method:
	 * Calls the UI delegate's paint method,
	 * @param g: Graphics
	 * 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	/*
	 *  The draw(Graphics g) method:
	 *  Calls in graphics and draws the components
	 *  If the variable "running" is true: The method will draw the snake game( game, score board, apple, snake body parts, and lines)
	 *  If the variable "running" is false: The draw() method will call the gameOver(g) method and it will draw a GAME OVER! screen
	 */
	public void draw(Graphics g) {
		
		if(running ) {
		for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
			g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
			g.drawLine(i * 0, i*UNIT_SIZE, i * SCREEN_WIDTH, i * UNIT_SIZE);
		}
		g.setColor(Color.YELLOW);           //(IF YOU WANT TO USE A DOT RATHER A MOUSE)--> for people who like Jerry
		g.fillOval(mouseX, mouseY, UNIT_SIZE, UNIT_SIZE);
		
	//	g.drawImage(imageJerry, mouseX, mouseY, UNIT_SIZE, UNIT_SIZE, getFocusCycleRootAncestor());
		
		for(int i = 0; i < bodyParts; i++) {
			if(i == 0) {
				g.setColor(Color.BLUE);
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(45, 180, 0));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Ink Free",Font.BOLD, 30));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score:" + mouseEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:" + mouseEaten))/2,g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
	}
	/*
	 * The newMouse() Method: sets up the random location of the apple in the grid
	 * mouseX: is the X coordinates
	 * mouseY: is the Y coordinates
	 */
	public void newMouse() {
		mouseX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		mouseY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
	}
	/*
	 * The move() method: Moves the body parts of the snake. By calling each direction of variable direction,
	 * it changes the coordinates of x[0] & y[0], snake head, up = 'U', down = 'D', right = 'R', left = 'L'
	 */
	public void move() {
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case'U':
			y[0] = y[0]-UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0]+UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0]-UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0]+UNIT_SIZE;
			break;
		
		}
	}
	/*
	 * The checkMouse() Method: Checks when the head of the snake x[0],y[0] coordinates matches the mouse
	 * coordinates and which shows that the mouse has been eaten.
	 * bodyParts increase by 1, mouseEaten increases by 1, and a new mouse is randomly placed.
	 */
	public void checkMouse() {
		if((x[0] == mouseX) & (y[0] == mouseY)) {
			bodyParts++;
			mouseEaten++;
			newMouse();
			
		}
	}
	/*
	 * The checkCollusions() Method: Checks if the snake has hit any collusions with the borders, 
	 * or with its own  body. meaning game over
	 */
	public void checkCollusions() {
		// Checks if head collides with body
		for(int i = bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		// check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		// check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// check if head touches top border
		if(y[0]< 0) {
			running = false;
		}
		// check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		// stop the timer once the game ends
		if(!running) {
			timer.stop();
		}
		
	}
	/*
	 * The gameOver(Graphics g) Method: 
	 * this method will create a new graphics GUI which will display to the user that its game over
	 */
	public void gameOver(Graphics g) {
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER! :(", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER! :("))/2, SCREEN_HEIGHT/2);
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Ink Free",Font.BOLD, 30));
		//FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score:" + mouseEaten , (SCREEN_WIDTH - metrics.stringWidth("Score:" + mouseEaten))/2,g.getFont().getSize());
		
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Ink Free",Font.BOLD, 30));
		//FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Press ESC to exit program" , (950 - metrics.stringWidth("Press ESC to exit program"))/2,500);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkMouse();
			checkCollusions();
		}
		repaint();
	}
	public class myKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				
				
					break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
				
			}
			
		}
	}

}
