package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;

/*
 *  Class takes user interaction and updates model
 */
public class Controller implements KeyListener, ActionListener{

	private Model model;

	public Controller(Model model) {

		// Needs to know model to update model data
		this.model = model;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
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
				//START button
				if(e.getActionCommand().equals("startGame")){
					model.start();
				}
				//QUIT in Jmenubar
				if(e.getActionCommand().equals("stopGame")){
					model.stop();
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
	}
}
