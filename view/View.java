/**
 * Ryan Maynord, Zachary Conant, Nathan Pierce
 * CS 3053 - Human Computer Interaction
 * Project
 * April 25th, 27th 2017
 * View.java
 */
package view;

import java.awt.BorderLayout;
//import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.GridLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JSeparator;

import controller.Controller;
import model.Model;

/**
 *  Class contains view components that represent model data and that are manipulated by the controller
 */
public class View 
{
	private Model model; 				// Needs model to get new information about its view components
	private Controller controller; 		// Needs controller to connect view components with user interaction
	private JFrame frame; 				// Window that holds app
	private final int gridRows = 15;
	private final int gridCols = 15;
	
	public View( Model model, Controller controller, JFrame frame ) 
	{
		this.model = model;
		this.controller = controller;
		this.frame = frame;
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		gameView( controller, model, frame );
	}
	
	private void gameView( Controller controller, Model model, JFrame frame )
	{
		// The main layout used in the design of the game
		BorderLayout border = new BorderLayout();
		
		
		// **** MENU BAR **** //
		
		JMenuBar mBar = new JMenuBar();
		mBar.setBorderPainted( true );
		mBar.setBackground( Color.BLACK );
		frame.setJMenuBar( mBar );
		
		/* 'FILE' TAB */
		JMenu mFile = new JMenu( "File" );
		mFile.setBorder( null );
		mFile.setBorderPainted( true );
		mFile.setBackground( Color.LIGHT_GRAY );
		// mBar.add( mFile );
		
		JMenuItem mItemQuit = new JMenuItem( "Quit" );
		mItemQuit.addActionListener( controller );
		
		mFile.add( mItemQuit );
		mBar.add( mFile );
		
		
		
		/* 'HELP' TAB */
		JMenu mHelp = new JMenu( "Help" );
		mHelp.setBorder( null );
		mHelp.setBorderPainted( true );
		mHelp.setBackground( Color.LIGHT_GRAY );
		
		JMenuItem mItemAbout = new JMenuItem( "About" );
		mItemAbout.addActionListener( controller );
		
		mHelp.add( mItemAbout );
		mBar.add( mHelp );

		
		border.addLayoutComponent( mBar, BorderLayout.NORTH );
		
		// ---- END OF MENUBAR ---- //
		
		
		// **** LEFT **** //
		
		JPanel panelLeft = new JPanel();
		GridLayout gridLeft = new GridLayout( 5, 1 );
		panelLeft.setLayout( gridLeft );
		panelLeft.add( new JLabel ( " " ) );
		panelLeft.add( new JButton ( "Button 2" ) );
		panelLeft.add( new JButton ( "Button 3" ) );
		panelLeft.add( new JButton ( "Button 4" ) );
		panelLeft.add( new JButton ( "Button 5" ) );
		
		frame.add( panelLeft );
		border.addLayoutComponent( panelLeft , BorderLayout.WEST );
		
		// ---- END OF LEFT ---- //
		
		
		
		// **** BOTTOM **** //
		
		// ---- END OF BOTTOM ---- //
		
		
		
		
		// **** CENTER **** //
		JPanel panelCenter = new JPanel();
		GridLayout gridCenter = new GridLayout( gridRows, gridCols );
		panelCenter.setLayout( gridCenter );
		
		
		
		
		
		
		
		
		
		
	}
}
