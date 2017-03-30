package model;

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
	private static final int GRID_DIMENSION = 15; // Number of rows and columns in grid
	private static final int NUMBER_DIRECTIONS = 4; // number of directions
	private String[][] directions; // Holds view components of 4 directions
	private int turretPositionX; // Center x position of turret
	private int turretPositionY; // Center y position of turret
	private double timeDelay; // Delay for each update in seconds
	private Timer timer; // Timer for application
	private TimerTask task; // TimerTask that is scheduled with timer
	private ImageIcon imperialFighter;
	
	public Model(int timeDelay) {
		this.turretOrientation = 0;
		this.orientationListeners = new ArrayList<OrientationListener>();
		this.directions = new String[NUMBER_DIRECTIONS][GRID_DIMENSION/2];
		this.turretPositionX = GRID_DIMENSION/2;
		this.turretPositionY = GRID_DIMENSION/2;
		this.timeDelay = timeDelay;
		this.imperialFighter = new ImageIcon("src/Imperial_Fighter.png");
		this.initializerTimer();
		this.start();
	}
	
	// Set the turret orientation in degrees
	public void setTurretOrientation(int degrees) {
		this.turretOrientation = degrees;
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
	
	public ImageIcon[] getImages(int degrees) {
		ImageIcon[] images = new ImageIcon[GRID_DIMENSION/2];
		int directionIndex = -1;
		
		if (degrees == 0) {
			directionIndex = 0;
		}
		else if (degrees == 90) {
			directionIndex = 1;
		}
		else if (degrees == 180) {
			directionIndex = 2;
		}
		else {
			directionIndex = 3;
		}
		
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
	
	// Get random direction selection -1 to 3. If -1 don't render a new enemy
	private int selectEnemyDirection() {
		Random random = new Random();
		return (random.nextInt(NUMBER_DIRECTIONS + 1) - 1);
	}
	
	private void shiftDirectionComponents() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = (GRID_DIMENSION/2 - 2); j >= 0; --j) {
				this.directions[i][j+1] = this.directions[i][j];
			}
		}
	}
	
	private void addEnemy(int directionIndex) {
		
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			this.directions[i][0] = null;
		}
		
		if (directionIndex >= 0) {
			this.directions[directionIndex][0] = "E";
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
		int directionIndex = selectEnemyDirection();
		shiftDirectionComponents();
		addEnemy(directionIndex);
		notifyPositionListeners();
	}
}
