/**
 * Ryan Maynord
 * CS 3053 - Human Computer Interaction
 * Project
 * April 25th, 27th 2017
 * View.java
 */
package view;

import javax.swing.JFrame;

import controller.Controller;
import model.Model;

/*
 *  Class contains view components that represent model data and that are manipulated by the controller
 */
public class View 
{
	private Model model; // Needs model to get new information about its view components
	private Controller controller; // Needs controller to connect view components with user interaction
	private JFrame frame; // Window that holds app
	
	public View( Model model, Controller controller, JFrame frame ) 
	{
		this.model = model;
		this.controller = controller;
		this.frame = frame;
	}
	
	
}
