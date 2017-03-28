package model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
 *  Class that represents the data being presented
 */
public class Model {
	private int turretOrientation;  // turret orientation in degrees (0, 90, 180, 270)
	private ArrayList<OrientationListener> orientationListeners; // list of components to update orientation
	private int numberGridRows; // Number of rows in grid
	private int numberGridColumns; // Number of columns in grid
	private String[][] grid; // Grid representing positions of view components and their interactions
	private int turretPositionX; // Center x position of turret
	private int turretPositionY; // Center y position of turret
	private double timeDelay; // Delay for each update in seconds
	private Timer timer; // Timer for application
	private TimerTask task; // TimerTask that is scheduled with timer
	
	public Model(int numberGridRows, int numberGridColumns, int timeDelay) {
		this.turretOrientation = 0;
		this.orientationListeners = new ArrayList<OrientationListener>();
		this.numberGridRows = numberGridRows;
		this.numberGridColumns = numberGridColumns;
		this.grid = new String[numberGridColumns][numberGridRows];
		this.turretPositionX = numberGridRows/2;
		this.turretPositionY = numberGridColumns/2;
		this.timeDelay = timeDelay;
		this.initializerTimer();
		this.initializeGrid();
		this.start();
		this.addEnemy(270);
		this.printGrid();
	}
	
	// Get the turret orientation in degrees
	public int getTuerretOrientation() {
		return this.turretOrientation;
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
	
	// Get number of rows in grid
	// This must be odd!
	public int getNumberGridRows() {
		return this.numberGridRows;
	}
	
	// Set number of rows in grid
	// This must be odd!
	public void setNumberGridRows(int rows) {
		this.numberGridRows = rows;
	}
	
	// Initialize all spaces to an empty string
	public void initializeGrid() {
		for (int i = 0; i < numberGridColumns; ++i) {
			for (int j = 0; j < numberGridRows; ++j) {
				if (i == turretPositionX && j == turretPositionY) {
					grid[i][j] = "T";
				}
				else {
					grid[i][j] = " ";
				}
			}
		}
	}
	
	// Print representation of current state for grid
	public void printGrid() {
		String gridString = "";
		
		for (int i = 0; i < numberGridRows; ++i) {
			
			gridString += "[";
			
			for (int j = 0; j < numberGridColumns; ++j) {
				gridString += grid[j][i] + " ";
			}
			
			gridString += "]\n";
		}
		
		System.out.println(gridString);
	}
	
	// Get center x position of turret
	public int getTurretPositionX() {
		return this.turretPositionX;
	}
	
	// Set center x position of turret
	public void setTurretPositionX(int x) {
		this.turretPositionX = x;
	}
	
	// Get center y position of turret
	public int getTurretPositionY() {
		return this.turretPositionY;
	}
	
	// Set center y position of turret
	public void setTurretPositionY(int y) {
		this.turretPositionY = y;
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
	
	public void addEnemy(int degrees) {
		if (degrees == 0) {
			grid[numberGridColumns-1][turretPositionY] = "E";
		}
		else if (degrees == 90) {
			grid[turretPositionX][0] = "E";
		}
		else if (degrees == 180) {
			grid[0][turretPositionY] = "E";
		}
		else if (degrees == 270) {
			grid[turretPositionX][numberGridRows-1] = "E";
		}
	}
}
