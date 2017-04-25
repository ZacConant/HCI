import javax.swing.JFrame;

import controller.Controller;
import view.View;
import model.Model;

/*
 *  Class that ties the Model, View and Controller together
 */
public class Driver 
{

	public static void main(String[] args) throws Exception
	{
		
		// Initialize JFrame
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Galatic Turret");
		
		// Initialize model
		Model model = new Model(60);
		
		// Initialize controller
		Controller controller = new Controller(model);
		//frame.addKeyListener(controller);
		// Initialize view
		new View(model, controller, frame);
		
		frame.setVisible(true);
	}

}
