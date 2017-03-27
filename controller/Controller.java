package controller;

import model.Model;

/*
 *  Class takes user interaction and updates model
 */
public class Controller {
	private Model model;
	
	public Controller(Model model) {
		
		// Needs to know model to update model data
		this.model = model;
	}
}
