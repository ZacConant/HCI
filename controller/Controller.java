package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;

/*
 *  Class takes user interaction and updates model
 */
public class Controller implements KeyListener, ActionListener, MenuListener{

	private Model model;
	private JFrame frame;
	String helpText = new String("Help for Galactic Turret: "+
		"Press the Enter key OR the 'PLAY' button (green button with a triangle) to begin the game!"+
		"When you do, you're adventure begins! You are able to control the ship in the middle by rotating it to face four directions (using the arrows on the screen OR the arrow keys on your keyboard) and shoot at the incoming enemies (using the Space Bar OR by pressing the button with the target on it)."+
		"Press the Ctrl key OR the 'PAUSE' button (yellow button with two rectangles) to pause/freeze the game, making all components stand still."+
		"Press 'PLAY' again to continue a paused game."+
		"Press the Shift key OR the 'RESET' button (red button with rotating arrows) to reset a paused game."+
		"Your running score is recorded on the left of the screen and if you beat the highscore below that, your score becomes the highscore!"+
		"There is an option to change the difficulty of the game for a greater challenge.....");
	private Readable helpTextFile;

	public Controller(Model model, JFrame frame) {

		// Needs to know model to update model data
		this.model = model;
		this.frame = frame;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		//game controll hotkeys
		//PLAY hotkey
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
			model.start();
		}
		//RESET hotkey
		if(arg0.getKeyCode()==KeyEvent.VK_SHIFT){
			model.reset();
		}	

		//PAUSE hotkey
		if(arg0.getKeyCode()==KeyEvent.VK_CONTROL){
			model.pause();
		}	

		//arrow key actions implemented
		//UP
		if(arg0.getKeyCode()==KeyEvent.VK_UP){
			model.setTurretOrientation(90);
		}
		//DOWN
		if(arg0.getKeyCode()==KeyEvent.VK_DOWN){
			model.setTurretOrientation(270);
		}
		//LEFT
		if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
			model.setTurretOrientation(180);
		}
		//RIGHT
		if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
			model.setTurretOrientation(0);
		}
		//SHOOT
		if(arg0.getKeyCode()==KeyEvent.VK_SPACE){
			//shoot method
			model.shoot();
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public void actionPerformed(ActionEvent e) {
		//directional buttons actions implemented
				//PLAY button
				if(e.getActionCommand().equals("playGame")){
					model.start();
				}
				//RESET button
				if(e.getActionCommand().equals("resetGame")){
					model.reset();
				}
				//PAUSE button
				if(e.getActionCommand().equals("pauseGame")){
					model.pause();
				}
				//UP
				if(e.getActionCommand().equals("faceUp")){
					model.setTurretOrientation(90);
				}
				//DOWN
				if(e.getActionCommand().equals("faceDown")){
					model.setTurretOrientation(270);
				}
				//LEFT
				if(e.getActionCommand().equals("faceLeft")){
					model.setTurretOrientation(180);
				}
				//RIGHT
				if(e.getActionCommand().equals("faceRight")){
					model.setTurretOrientation(0);
				}
				//FIRE
				if(e.getActionCommand().equals("fire")){
					model.shoot();
				}
				//QUIT
				if(e.getActionCommand().equals("Quit")){
					System.exit(0);
				}
				
				//Combo Box difficulty changing 
				//setDifficulty("EASY");
				//setDifficulty("MEDIUM");
				//setDifficulty("HARD");
	}

	@Override
	public void menuCanceled(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent arg0) {
		// TODO Auto-generated method stub
		//QUIT
		//if(arg0.getActionCommand.equals("Quit")){
			//System.exit(0);
		//}
		//HELP
		//if(arg0.getActionCommand.equals("Help")){
			//frame.
			JOptionPane.showMessageDialog(frame,helpText,"Help",JOptionPane.PLAIN_MESSAGE);
		//}
	}
	/*
	// Read highscore from external file
		private int readHighScore() {
			Scanner scanner;
			try {
				scanner = new Scanner(this.helpTextFile);
				return scanner.nextInt();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return -1;
		}
		*/
}
