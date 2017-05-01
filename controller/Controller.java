package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
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
		//}
	}
}
