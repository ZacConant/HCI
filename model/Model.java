package model;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

/*
 *  Class that represents the data being presented
 */
public class Model {
	private int turretOrientation;  // turret orientation in degrees (0, 90, 180, 270)
	private ArrayList<OrientationListener> orientationListeners; // list of components to update orientation
	private ArrayList<PositionListener> positionListeners; // component to update to show new positions of components
	private static final int GRID_DIMENSION = 11; // Number of rows and columns in grid
	private static final int NUMBER_DIRECTIONS = 4; // number of directions
	private String[][] directions; // Holds view components of 4 directions
	private String[][] enemies; // keep track of enemies
	private String[][] missiles; // keep track of missiles
	private int timeDelay; // Delay for each update in seconds
	private Timer timer; // Timer for application
	private ImageIcon[] xWings; // 4 angles for x wing image
	private boolean isShooting; // holds state of if turret is shooting
	private boolean isStarted; // check if game has started
	private boolean isGameOver;
	private int timerCount;
	private int score;
	private int highScore;
	private static final int SCORE_VALUE = 100;
	private ArrayList<ScoreListener> scoreListeners;
	private File highScoreFile;
	
	public Model(int timeDelay) {
		this.turretOrientation = 90;
		this.orientationListeners = new ArrayList<OrientationListener>();
		this.positionListeners = new ArrayList<PositionListener>();
		this.directions = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.enemies = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.missiles = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.timeDelay = timeDelay;
		this.xWings = new ImageIcon[NUMBER_DIRECTIONS];
		this.isShooting = false;
		this.isStarted = false;
		this.isGameOver = false;
		this.timerCount = 0;
		this.score = 0;
		this.highScoreFile = new File("src/highscore.txt");
		this.highScore = readHighScore();
		this.initializeDirections();
		this.loadXWings();
	}
	
	public void reset() {
		this.stop();
		this.turretOrientation = 90;
		this.isShooting = false;
		this.isStarted = false;
		this.isGameOver = false;
		this.timerCount = 0;
		this.score = 0;
		this.highScore = readHighScore();
		this.initializeDirections();
		this.notifyPositionListeners();
		this.notifyOrientationListeners();
		//this.notifyScoreListeners();
	}
	
	// Set all direction indices to empty string
	private void initializeDirections() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = 0; j < GRID_DIMENSION/2; ++j) {
				directions[i][j] = "";
				enemies[i][j] = "";
				missiles[i][j] = "";
			}
		}
	}
	
	// Get array index by degrees
	private int getDirectionIndex(int degrees) {
		if (degrees == 0) {
			return 0;
		}
		else if (degrees == 90) {
			return 1;
		}
		else if (degrees == 180) {
			return 2;
		}
		else if (degrees == 270) {
			return 3;
		}
		
		return -1;
	}
	
	// Set the turret orientation in degrees
	public void setTurretOrientation(int degrees) {
		this.turretOrientation = degrees;
		notifyOrientationListeners();
	}
	
	// Register a view component with an orientation listener
	public void addOrientationListener(OrientationListener listener) {
		this.orientationListeners.add(listener);
	}
	
	// Call all view components to update orientation
	public void notifyOrientationListeners() {
		for (OrientationListener listener : orientationListeners) {
			listener.updateOrientation();
		}
	}
	
	// Register a component with an position listener
	public void addPositionListener(PositionListener listener) {
		this.positionListeners.add(listener);
	}
	
	// Call all component that updates all view component positions
	public void notifyPositionListeners() {
		for (PositionListener listener : positionListeners) {
			listener.updatePositions();
		}
	}
	
	// Get array of strings representing state for certain direction
	public String[] getDirection(int degrees) {
		return this.directions[getDirectionIndex(degrees)];
	}
	
	// Load all 4 X wing images
	private void loadXWings() {
		xWings[0] = resizeImage(37, 37, new ImageIcon("src/X_Wing_0.png"));
		xWings[1] = resizeImage(37, 37, new ImageIcon("src/X_Wing_90.png"));
		xWings[2] = resizeImage(37, 37, new ImageIcon("src/X_Wing_180.png"));
		xWings[3] = resizeImage(37, 37, new ImageIcon("src/X_Wing_270.png"));
	}
	
	// Resize an image icon
	private ImageIcon resizeImage(int width, int height, ImageIcon icon) {
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}
	
	// Get the center turret image (this can be 4 different angles)
	public ImageIcon getTurretImage() {
		if (isGameOver) {
			return resizeImage(37, 37, new ImageIcon("src/explosion2.png"));
		}
		
		if (turretOrientation == 0) {
			return xWings[0];
		}
		else if (turretOrientation == 90) {
			return xWings[1];
		}
		else if (turretOrientation == 180) {
			return xWings[2];
		}
		else if (turretOrientation == 270) {
			return xWings[3];
		}
		
		return null;
	}
	
	// Get random direction selection -7 to 3. If < 0 don't render a new enemy. 30% chance of enemy appearing
	private int selectEnemyDirection() {
		Random random = new Random();
		return (random.nextInt(NUMBER_DIRECTIONS + 7) - 7);
	}
	
	// Shift the images in all arrays
	private void shiftDirectionComponents() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			if ((this.enemies[i][GRID_DIMENSION/2 - 1]).equals("E")) {
				this.isGameOver = true;
			}
			for (int j = (GRID_DIMENSION/2 - 2); j >= 0; --j) {
				int k = GRID_DIMENSION/2 - 2 - j;
				
				this.enemies[i][j+1] = this.enemies[i][j];
				this.missiles[i][k] = this.missiles[i][k+1];
			}
		}
	}
	
	// Shift the images in all arrays
	private void shiftMissiles() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = (GRID_DIMENSION/2 - 2); j >= 0; --j) {
				int k = GRID_DIMENSION/2 - 2 - j;
				
				this.missiles[i][k] = this.missiles[i][k+1];
			}
		}
	}
	
	// Add an enemy string to one of 4 arrays, or not at all
	private void addEnemy(int directionIndex) {
		
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			this.enemies[i][0] = "";
		}
		
		if (directionIndex >= 0) {
			this.enemies[directionIndex][0] += "E";
		}
	}
	
	// Add missile to direction selected
	private void addMissile(int directionIndex) {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			this.missiles[i][GRID_DIMENSION/2 - 1] = "";
		}
		
		if (isShooting) {
			this.missiles[directionIndex][GRID_DIMENSION/2 - 1] += "M";
			this.isShooting = false;
		}
	}
	
	private void updateDirections() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = 0; j < GRID_DIMENSION/2; ++j) {
				
				// Clear current space
				this.directions[i][j] = "";
				
				// Check for enemy and missile one position away from each other
				if (j > 0) {
					if (this.missiles[i][j].equals("M") && this.enemies[i][j-1].equals("E")) {
						this.missiles[i][j] = "";
						this.enemies[i][j-1] = "";
						this.directions[i][j-1] = "D";
						updateScore();
					}
					else if (this.missiles[i][j].equals("M") && this.enemies[i][j].equals("E")) {
						this.missiles[i][j] = "";
						this.enemies[i][j] = "";
						this.directions[i][j] = "D";
						updateScore();
					}
					else {
						this.directions[i][j] += this.missiles[i][j];
						this.directions[i][j] += this.enemies[i][j];
					}
				}
				// Enemy and missile can only be in same position
				else {
					if (this.missiles[i][j].equals("M") && this.enemies[i][j].equals("E")) {
						this.missiles[i][j] = "";
						this.enemies[i][j] = "";
						this.directions[i][j] = "D";
						updateScore();
					}
					else {
						this.directions[i][j] += this.missiles[i][j];
						this.directions[i][j] += this.enemies[i][j];
					}
				}
				
			}
		}
	}
	
	// Shoot a missile
	public void shoot() {
		this.isShooting = true;
	}
	
	// Print components at each direction
	private void print() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			System.out.print(i + ":");
			for (int j = 0; j < GRID_DIMENSION/2; ++j) {
				System.out.print(" " + this.directions[i][j]);
			}
			System.out.println("");
		}
		
		System.out.println("\n");
	}
	
	// Get current time delay
	public double getTimeDelay() {
		return this.timeDelay;
	}
	
	// Set current time delay
	public void setTimeDelay(int delay) {
		this.timeDelay = delay;
	}
	
	// Start timer to run at specified time delay
	public void start() {
		if (!this.isStarted) {
			this.timer = new Timer();
			this.timer.scheduleAtFixedRate(new TimerTask() { public void run() { move(); } }, 0, (long)this.timeDelay);
			this.isStarted = true;
		}
	}
	
	// Stop timer
	public void stop() {
		if (this.isStarted) {
			this.timer.cancel();
			this.isStarted = false;
		}
	}
	
	// Move view components
	public void move() {
		if (timerCount % 7 == 0) {
			int randomDirectionIndex = selectEnemyDirection();
			shiftDirectionComponents();
			addEnemy(randomDirectionIndex);
		}
		else {
			shiftMissiles();
		}
		
		addMissile(getDirectionIndex(turretOrientation));
		
		updateDirections();
		
		notifyPositionListeners();
		
		if (isGameOver) {
			gameOver();
		}
		
		timerCount += 1;
	}
	
	// Register a view component with an orientation listener
	public void addScoreListener(ScoreListener listener) {
		this.scoreListeners.add(listener);
	}
	
	// Call all view components to update orientation
	public void notifyScoreListeners() {
		for (ScoreListener listener : scoreListeners) {
			listener.updateScore();
		}
	}
	
	private void updateScore() {
		this.score += SCORE_VALUE;
		//notifyScoreListeners();
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getHighScore() {
		return this.highScore;
	}
	
	private void writeHighScore(int highScore) {
		try {
			PrintWriter pw = new PrintWriter(this.highScoreFile);
			pw.println(highScore);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private int readHighScore() {
		Scanner scanner;
		try {
			scanner = new Scanner(this.highScoreFile);
			return scanner.nextInt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private boolean isHighScore() {
		return this.score > this.highScore;
	}
	
	private void gameOver() {
		notifyOrientationListeners();
		stop();
		
		if (isHighScore()) {
			this.highScore = score;
			System.out.println("High:" + this.highScore);
			writeHighScore(this.highScore);
		}
		//notifyScoreListeners();
	}
	
	public boolean isGameOver() {
		return this.isGameOver;
	}
}
