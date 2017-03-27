package view;

import controller.Controller;
import model.Model;

// Class contains view components that represent model data and that are manipulated by the controller
public class View {
	private Model model;
	private Controller controller;
	
	public View(Model model, Controller controller) {
		
		// Needs model to get new information about its view components
		this.model = model;
		
		// Needs controller to connect view components with user interaction
		this.controller = controller;
	}
}
