package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
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
	private int turretPositionX; // Center x position of turret
	private int turretPositionY; // Center y position of turret
	private double timeDelay; // Delay for each update in seconds
	private Timer timer; // Timer for application
	private TimerTask task; // TimerTask that is scheduled with timer
	private ImageIcon imperialFighter; // Imperial Fighter image (all are same orientation)
	private ImageIcon[] xWings; // 4 angles for x wing image
	private boolean isShooting = false; // holds state of if turret is shooting
	
	public Model(int timeDelay) {
		this.turretOrientation = 0;
		this.orientationListeners = new ArrayList<OrientationListener>();
		this.positionListeners = new ArrayList<PositionListener>();
		this.directions = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.turretPositionX = GRID_DIMENSION/2;
		this.turretPositionY = GRID_DIMENSION/2;
		this.timeDelay = timeDelay;
		this.imperialFighter = new ImageIcon("src/Imperial_Fighter.png");
		this.xWings = new ImageIcon[NUMBER_DIRECTIONS];
		this.initializeDirections();
		this.loadXWings();
		this.initializerTimer();
		this.start();
	}
	
	private void initializeDirections() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = 0; j < GRID_DIMENSION/2; ++j) {
				directions[i][j] = "";
			}
		}
	}
	
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
	
	// Get all images that move with timing of game (enemies, missiles)
	public ImageIcon[] getImages(int degrees) {
		ImageIcon[] images = new ImageIcon[GRID_DIMENSION/2];
		int directionIndex = getDirectionIndex(degrees);
		
		for (int i = 0; i < GRID_DIMENSION/2; ++i) {
			String panel = this.directions[directionIndex][i];
			
			if (panel != null) {
				if (panel.equals("E")) {
					images[i] = imperialFighter;
				}
			}
		}
		
		return images;
	}
	
	// Load all 4 X wing images
	private void loadXWings() {
		xWings[0] = resizeImage(37, 37, new ImageIcon("src/X_Wing_0.png"));
		xWings[1] = resizeImage(37, 37, new ImageIcon("src/X_Wing_90.png"));
		xWings[2] = resizeImage(37, 37, new ImageIcon("src/X_Wing_180.png"));
		xWings[3] = resizeImage(37, 37, new ImageIcon("src/X_Wing_270.png"));
	}
	
	private ImageIcon resizeImage(int width, int height, ImageIcon icon) {
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImage);
	}
	
	// Get the center turret image (this can be 4 different angles)
	public ImageIcon getTurretImage() {
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
			for (int j = (GRID_DIMENSION/2 - 2); j >= 0; --j) {
				this.directions[i][j+1] = this.directions[i][j];
				/*if (this.directions[i][j] == "E") {
					this.directions[i][j+1] += this.directions[i][j];
				}
				else if (this.directions[i][j] == "M") {
					this.directions[i][j-1] += this.directions[i][j];
					this.directions[i][j] = "";
				}*/
			}
		}
	}
	
	// Add an enemy string to one of 4 arrays, or not at all
	private void addEnemy(int directionIndex) {
		
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			this.directions[i][0] = "";
		}
		
		if (directionIndex >= 0) {
			this.directions[directionIndex][0] += "E";
		}
	}
	
	private void addMissile(int directionIndex) {
		if (directionIndex >= 0) {
			this.directions[directionIndex][GRID_DIMENSION/2 - 1] += "M";
		}
		
		this.isShooting = false;
	}
	
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
	public void setTimeDelay(double delay) {
		this.timeDelay = delay;
	}
	
	// Initialize timer
	public void initializerTimer() {
		this.timer = new Timer();
		this.task = new TimerTask() {
			public void run() {
				move();
			}
		};
	}
	
	// Start timer to run at specified time delay
	public void start() {
		this.timer.scheduleAtFixedRate(this.task, 0, (long)this.timeDelay*1000);
	}
	
	// Stop timer
	public void stop() {
		this.timer.cancel();
	}
	
	// Move view components
	public void move() {
		int randomDirectionIndex = selectEnemyDirection();
		shiftDirectionComponents();
		addEnemy(randomDirectionIndex);
		
		if (randomDirectionIndex % 2 == 0 && randomDirectionIndex >= 0) {
			shoot();
		}
		
		if (isShooting) {
			addMissile(getDirectionIndex(turretOrientation));
		}
		
		notifyPositionListeners();
		
		print();
	}
}
