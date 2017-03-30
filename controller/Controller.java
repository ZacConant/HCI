package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import model.Model;

/*
 *  Class takes user interaction and updates model
 */
public class Controller implements KeyListener{
	private Model model;
	
	public Controller(Model model) {
		
		// Needs to know model to update model data
		this.model = model;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getSource().equals(KeyEvent.VK_UP)){
			//model.setTurretOrientation(90);
			System.out.println("Up Pressed");
		}
		if(arg0.getSource().equals(KeyEvent.VK_DOWN)){
			//model.setTurretOrientation(90);
			System.out.println("Down Pressed");
		}
		if(arg0.getSource().equals(KeyEvent.VK_LEFT)){
			//model.setTurretOrientation(90);
			System.out.println("Left Pressed");
		}
		if(arg0.getSource().equals(KeyEvent.VK_RIGHT)){
			//model.setTurretOrientation(90);
			System.out.println("Right Pressed");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource().equals(KeyEvent.VK_RIGHT)){
			//model.setTurretOrientation(90);
			System.out.println("Right Pressed");
		}
	}
}
