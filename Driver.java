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
		frame.setResizable( false );
		frame.setSize(800, 600);
		frame.setTitle("Galactic Turret");

		// Initialize model ( time is in ms )
		Model model = new Model(65);
		
		// Initialize controller
		Controller controller = new Controller(model, frame);
		//frame.addKeyListener(controller);
		// Initialize view
		new View(model, controller, frame);
		
		frame.setVisible(true);
	}

}
