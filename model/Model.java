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
	private boolean isPlaying; // check if game has started
	private boolean isRunning; // check if game is running (still running even if paused)
	private boolean isGameOver; // check if game over
	private boolean isHighScore;
	private boolean isEnd; // End of game
	private int timerCount; // increments by 1 on every count
	private int score; // current score
	private int highScore; // current high score
	private static final int ENEMY_SCORE_VALUE = 100; // normal enemy points value
	private static final int VADER_SCORE_VALUE = 200; // vader points value
	private ArrayList<ScoreListener> scoreListeners; // listens for changes in score
	private File highScoreFile; // File with current high score written
	private String difficulty; // current difficulty
	private int difficultySpeed; // determines how fast enemies move
	private ArrayList<DifficultyListener> difficultyListeners; // listens for changes in difficulty
	private int vaderTracker; // determines if a vader should be spawned
	
	public Model(int timeDelay) {
		this.turretOrientation = 90;
		this.orientationListeners = new ArrayList<OrientationListener>();
		this.positionListeners = new ArrayList<PositionListener>();
		this.scoreListeners = new ArrayList<ScoreListener>();
		this.difficultyListeners = new ArrayList<DifficultyListener>();
		this.directions = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.enemies = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.missiles = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.timeDelay = timeDelay;
		this.xWings = new ImageIcon[NUMBER_DIRECTIONS];
		this.isShooting = false;
		this.isPlaying = false;
		this.isGameOver = false;
		this.isRunning = false;
		this.isHighScore = false;
		this.isEnd = false;
		this.timerCount = 0;
		this.score = 0;
		this.highScoreFile = new File("src/highscore.txt");
		this.difficulty = "NORMAL";
		this.difficultySpeed = 8;
		this.vaderTracker = 0;
		this.highScore = readHighScore();
		this.initializeDirections();
		this.loadXWings();
	}
	
	// Reset the game (reset button)
	public void reset() {
		this.stop();
		this.turretOrientation = 90;
		this.isShooting = false;
		this.isPlaying = false;
		this.isGameOver = false;
		this.isRunning = false;
		this.isEnd = false;
		this.isHighScore = false;
		this.notifyPositionListeners();
		this.notifyOrientationListeners();
		this.notifyScoreListeners();
		this.timerCount = 0;
		this.score = 0;
		this.highScore = readHighScore();
		this.vaderTracker = 0;
		this.initializeDirections();
		this.notifyPositionListeners();
		this.notifyOrientationListeners();
		this.notifyScoreListeners();
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
			if ((this.enemies[i][GRID_DIMENSION/2 - 1]).startsWith("E")) {
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
		
		if (this.difficulty == "HARD") {
			if (directionIndex >= 0) {
				if (this.vaderTracker == 3) {
					this.enemies[directionIndex][0] += "EV1";
					this.vaderTracker = 0;
				}
				else {
					this.enemies[directionIndex][0] += "E";
					++this.vaderTracker;
				}
			}
		}
		else {
			if (directionIndex >= 0) {
				this.enemies[directionIndex][0] += "E";
			}
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
	
	// Update all arrays with new values based on position of components
	private void updateDirections() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = 0; j < GRID_DIMENSION/2; ++j) {
				
				// Clear current space
				this.directions[i][j] = "";
				
				if (j < GRID_DIMENSION/2 - 1) {
					
					// If missile and enemy are in same position
					if (this.missiles[i][j].equals("M") && this.enemies[i][j].startsWith("E")) {
						
						// If undamaged vader
						if (this.enemies[i][j].equals("EV1")) {
							this.missiles[i][j] = "";
							this.enemies[i][j] = "EV2";
							this.directions[i][j] = "EV2";
						}
						// If not undamaged vader, destroy the ship
						else {
							int score;
							
							if (this.enemies[i][j].equals("EV2")) {
								score = VADER_SCORE_VALUE;
							}
							else {
								score = ENEMY_SCORE_VALUE;
							}
							
							this.missiles[i][j] = "";
							this.enemies[i][j] = "";
							this.directions[i][j] = "D";
							updateScore(score);
						}
					}
					// Check for enemy and missile one position away from each other
					else if (this.missiles[i][j+1].equals("M") && this.enemies[i][j].startsWith("E")) {
						
						if (j < GRID_DIMENSION/2 - 2) {
							
							// For enemies next to each other
							// Makes sure that enemy closer to missile is destroyed first
							if (!(this.enemies[i][j+1].startsWith("E"))) {
								
								// If undamaged vader
								if (this.enemies[i][j].equals("EV1")) {
									this.missiles[i][j+1] = "";
									this.enemies[i][j] = "EV2";
									this.directions[i][j] = "EV2";
								}
								// If not undamaged vader, destroy the ship
								else {
									int score;
									
									if (this.enemies[i][j].equals("EV2")) {
										score = VADER_SCORE_VALUE;
									}
									else {
										score = ENEMY_SCORE_VALUE;
									}
									
									this.missiles[i][j+1] = "";
									this.enemies[i][j] = "";
									this.directions[i][j] = "D";
									updateScore(score);
								}
							}
						}
						else {
							if (this.enemies[i][j].equals("EV1")) {
								this.missiles[i][j+1] = "";
								this.enemies[i][j] = "EV2";
								this.directions[i][j] = "EV2";
							}
							else {
								int score;
								
								if (this.enemies[i][j].equals("EV2")) {
									score = VADER_SCORE_VALUE;
								}
								else {
									score = ENEMY_SCORE_VALUE;
								}
								
								this.missiles[i][j+1] = "";
								this.enemies[i][j] = "";
								this.directions[i][j] = "D";
								updateScore(score);
							}
						}
					}
					// If no missiles interfere with enemies just update arrays
					else {
						if (!(this.directions[i][j]).equals("D")) {
							this.directions[i][j] += this.missiles[i][j];
							this.directions[i][j] += this.enemies[i][j];
						}
					}
				}
				// Enemy and missile can only be in same position
				else {
					
					// If missile and enemy are in same position
					if (this.missiles[i][j].equals("M") && this.enemies[i][j].startsWith("E")) {
						
						// If undamaged vader
						if (this.enemies[i][j].equals("EV1")) {
							this.missiles[i][j] = "";
							this.enemies[i][j] = "EV2";
							this.directions[i][j] = "EV2";
						}
						// If not undamaged vader, destroy the ship
						else {
							int score;
							
							if (this.enemies[i][j].equals("EV2")) {
								score = VADER_SCORE_VALUE;
							}
							else {
								score = ENEMY_SCORE_VALUE;
							}
							
							this.missiles[i][j] = "";
							this.enemies[i][j] = "";
							this.directions[i][j] = "D";
							updateScore(score);
						}
					}
					// If no missiles interfere with enemies just update arrays
					else {
						if (!(this.directions[i][j]).equals("D")) {
							this.directions[i][j] += this.missiles[i][j];
							this.directions[i][j] += this.enemies[i][j];
						}
					}
			    }
				
			}
		}
	}
	
	// Shoot a missile
	public void shoot() {
		if (isPlaying) {
			this.isShooting = true;
		}
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
		this.isRunning = true;
		
		if (!isPlaying) {
			if (isGameOver()) {
				reset();
			}
			this.timer = new Timer();
			this.timer.scheduleAtFixedRate(new TimerTask() { public void run() { move(); } }, 0, (long)this.timeDelay);
			this.isPlaying = true;
		}
	}
	
	// Stop timer
	public void stop() {
		if (this.isPlaying) {
			this.timer.cancel();
			this.isPlaying = false;
		}
	}
	
	// Move view components
	public void move() {
		
		// If paused don't update anything
		if (!isPaused()) {
			
			// Update enemies by difficulty speed constant
			if (timerCount % difficultySpeed == 0) {
				int randomDirectionIndex = selectEnemyDirection();
				shiftDirectionComponents();
				addEnemy(randomDirectionIndex);
			}
			// Otherwise just update missiles
			else {
				shiftMissiles();
			}
			
			// Add missile if shooting
			addMissile(getDirectionIndex(turretOrientation));
			
			// Update all components
			updateDirections();
			
			// Tell view to rerender
			notifyPositionListeners();
			
			// If gameover
			if (isGameOver) {
				gameOver();
			}
			
			// Increment timer count
			timerCount += 1;
		}
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
	
	// Increment score by given value
	private void updateScore(int score) {
		this.score += score;
		notifyScoreListeners();
	}
	
	// get current score
	public int getScore() {
		return this.score;
	}
	
	// get current highscore
	public int getHighScore() {
		return this.highScore;
	}
	
	// Write highscore to an external file
	private void writeHighScore(int highScore) {
		try {
			PrintWriter pw = new PrintWriter(this.highScoreFile);
			pw.println(highScore);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// Read highscore from external file
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
	
	// Check if new highscore achieved
	public boolean isHighScore() {
		return this.isHighScore;
	}
	
	// Check score and highscore
	private boolean checkHighScore() {
		return this.score > this.highScore;
	}
	
	// Triggers a game over
	private void gameOver() {
		
		// Set game to not running
		this.isRunning = false;
		this.isEnd = true;
		
		// If highscore, override in file
		if (checkHighScore()) {
			this.highScore = score;
			this.isHighScore = true;
			System.out.println("New High Score: " + this.highScore);
			writeHighScore(this.highScore);
		}
		
		// One last cycle then stop
		notifyOrientationListeners();
		notifyPositionListeners();
		stop();
		
		// Render new scores
		notifyScoreListeners();
	}
	
	// temporarily stop game play
	public void pause() {
		stop();
	}
	
	// check if is paused
	public boolean isPaused() {
		return !this.isPlaying;
	}
	
	// check if is a game over
	public boolean isGameOver() {
		return this.isGameOver;
	}
	
	// get current difficulty of game
	public String getDifficulty() {
		return this.difficulty;
	}
	
	// set the difficulty
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
		if (difficulty.equals("HARD")) {
			this.difficultySpeed = 5;
		}
		else {
			this.difficultySpeed = 8;
		}
		
		notifyDifficultyListeners();
	}
	
	// Register a view component with an orientation listener
	public void addDifficultyListener(DifficultyListener listener) {
		this.difficultyListeners.add(listener);
	}
	
	// Call all view components to update orientation
	public void notifyDifficultyListeners() {
		for (DifficultyListener listener : difficultyListeners) {
			listener.updateDifficulty();
		}
	}
	
	// check if game is running
	public boolean isRunning() {
		return this.isRunning;
	}
	
	// Check if end of game
	public boolean isEnd() {
		return this.isEnd;
	}
 }
