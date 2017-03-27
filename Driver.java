import controller.Controller;
import view.View;
import model.Model;

// Class that ties the project together
public class Driver {

	public static void main(String[] args) {
		
		// Initialize model
		Model model = new Model();
		
		// Initialize controller
		Controller controller = new Controller(model);
		
		// Initialize view
		new View(model, controller);
	}

}
