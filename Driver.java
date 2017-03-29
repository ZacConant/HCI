import javax.swing.JFrame;

import controller.Controller;
import view.View;
import model.Model;
////
/*
 *  Class that ties the Model, View and Controller together
 */
public class Driver 
{

	public static void main(String[] args) 
	{
		
		// Initialize JFrame
		JFrame frame = new JFrame();
		frame.setSize(1100, 600);
		frame.setTitle("Galatic Turret");
		
		// Initialize model
		Model model = new Model(11, 11, 1);
		
		// Initialize controller
		Controller controller = new Controller(model);
		
		// Initialize view
		new View(model, controller, frame);
		
		frame.setVisible(true);
	}

}
