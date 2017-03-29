package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;

/*
 *  Class takes user interaction and updates model
 */
public class Controller implements ActionListener {
	private Model model;
	
	public Controller(Model model) {
		
		// Needs to know model to update model data
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
