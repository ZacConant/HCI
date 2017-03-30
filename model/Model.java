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
	private static final int NUMBER_GRID_ROWS = 15; // Number of rows in grid
	private static final int NUMBER_GRID_COLUMNS = 15; // Number of columns in grid
	private static final int NUMBER_DIRECTIONS = 4; // number of directions
	private String[][] directions;
	private int turretPositionX; // Center x position of turret
	private int turretPositionY; // Center y position of turret
	private double timeDelay; // Delay for each update in seconds
	private Timer timer; // Timer for application
	private TimerTask task; // TimerTask that is scheduled with timer
	
	public Model(int timeDelay) {
		this.turretOrientation = 0;
		this.orientationListeners = new ArrayList<OrientationListener>();
		this.directions = new String[NUMBER_DIRECTIONS][NUMBER_GRID_ROWS/2];
		this.turretPositionX = NUMBER_GRID_ROWS/2;
		this.turretPositionY = NUMBER_GRID_COLUMNS/2;
		this.timeDelay = timeDelay;
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
	
	public ImageIcon[] getImages(int degrees) {
		return null;
	}
	
	// Get random direction selection -1 to 3. If -1 don't render a new enemy
	private int selectEnemyDirection() {
		Random random = new Random();
		return (random.nextInt(NUMBER_DIRECTIONS + 1) - 1);
	}
	
	private void shiftDirectionComponents() {
		for (int i = 0; i < NUMBER_DIRECTIONS; ++i) {
			for (int j = (NUMBER_GRID_ROWS/2 - 2); j >= 0; --j) {
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
			for (int j = 0; j < NUMBER_GRID_ROWS/2; ++j) {
				System.out.print(" " + this.directions[i][j]);
			}
			System.out.println("\n");
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
				int directionIndex = selectEnemyDirection();
				shiftDirectionComponents();
				addEnemy(directionIndex);
				print();
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
		
	}
}
