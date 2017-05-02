package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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

	public boolean isNormal;
	private Model model;
	private JFrame frame;
	String helpText = new String(
		"Press the Enter key OR the 'PLAY' button (green button with a triangle) to begin the game!\n"+
		"When you do, you're adventure begins!\n "+
		"You are able to control the ship in the middle by rotating it to face four directions\n "+
		"(using the arrows on the screen OR the arrow keys on your keyboard)\n"+
		"and shoot at the incoming enemies (using the Space Bar OR by pressing the button with the target on it).\n"+
		"Press the Shift key OR the 'PAUSE' button (yellow button with two rectangles) to pause/freeze the game, \n"+
		"making all components stand still.\n"+
		"Press 'PLAY' again to continue a paused game.\n"+
		"Press the Ctrl key OR the 'RESET' button (red button with rotating arrows) to reset a paused game.\n"+
		"Your running score is recorded on the left of the screen and if you beat the highscore below that, \n"+
		"your score becomes the highscore!\n"+
		"There is an option to change the difficulty of the game for a greater challenge.\n"+
		"Click the difficulty button on the menu bar at the top of the screen(Only allowed before a new game).\n"+
		"Select your prefered difficulty.\n"+
		"Enjoy the game!");
	
	private Readable helpTextFile;

	public Controller(Model model, JFrame frame) {

		// Needs to know model to update model data
		this.model = model;
		this.frame = frame;
		//setNormal(true);
		this.isNormal = true;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		frame.requestFocusInWindow();
		//game controll hotkeys
		//PLAY hotkey
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
			model.start();
		}
		//RESET hotkey
		if(arg0.getKeyCode()==KeyEvent.VK_CONTROL){
			model.reset();
		}	

		//PAUSE hotkey
		if(arg0.getKeyCode()==KeyEvent.VK_SHIFT){
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
				//change difficulty in menu bar
				//setDifficulty("MEDIUM");
				if(e.getActionCommand().equals("Normal")){
					setNormal(true);
					model.setDifficulty("NORMAL");
					//setNormal(true);
				}
				//setDifficulty("HARD");
				if(e.getActionCommand().equals("Hard")){
					setNormal(false);
					model.setDifficulty("HARD");
					//setNormal(false);
				}
				// Help
				if(e.getActionCommand().equals("Help")){
					JOptionPane.showMessageDialog(frame,helpText,"Help for Galactic Turret",JOptionPane.PLAIN_MESSAGE);
				}
				// Exit
				if(e.getActionCommand().equals("Quit")){
					System.exit( 0 );
				}
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
		
		if(arg0.getSource() instanceof JMenu){
			JMenu menu = (JMenu)arg0.getSource();
			
			//QUIT
			if (menu.getActionCommand().equals("Quit")) {
				System.exit(0);
			}
			// Help
			else if (menu.getActionCommand().equals("Help")) {
				JOptionPane.showMessageDialog(frame,helpText,"Help for Galactic Turret",JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}
}
