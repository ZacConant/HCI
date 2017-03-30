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
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private final int gameRow = 8;
	private final int gameCol = 8;
	
	
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
		panelLeft.setBounds( 0, 0, 200, 450);
		GridLayout gridLeft = new GridLayout( 5, 1 );
		panelLeft.setLayout( gridLeft );
		panelLeft.add( new JButton ( " BUTT " ) );
		panelLeft.add( new JButton ( "Button 2" ) );
		panelLeft.add( new JButton ( "Button 3" ) );
		panelLeft.add( new JButton ( "Button 4" ) );
		panelLeft.add( new JButton ( "Button 5" ) );
		
		frame.add( panelLeft );
		border.addLayoutComponent( panelLeft , BorderLayout.WEST );
		
		// ---- END OF LEFT ---- //
		
		
		
		// **** BOTTOM **** //
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBounds( 201, 451, 899, 105);
		GridLayout gridBottom = new GridLayout( 1, 3 );
		panelBottom.setLayout( gridBottom );
		JButton userControls = new JButton( "Controls" );
		panelBottom.add( userControls );
		
		frame.add( panelBottom );
		border.addLayoutComponent( panelBottom, BorderLayout.SOUTH );
		
		// ---- END OF BOTTOM ---- //
		
		
		
		
		// **** CENTER **** //
		
		JPanel panelCenter = new JPanel();
		//panelCenter.setBounds( 400, 0, 200, 450);
		GridLayout gridCenter = new GridLayout( gridRows, gridCols );
		panelCenter.setLayout( gridCenter );
		
		// Creating grid
		JPanel[][] panelArray = new JPanel[ gridRows ][ gridCols ];
		
		for( int i = 0; i < gridRows; i++ )
		{
			for( int j = 0; j < gridCols; j++ )
			{
				panelArray[ i ][ j ] = new JPanel();
				//frame.add( panelArray[ i ][ j ]);
				panelCenter.add( panelArray[ i ][ j ] );
			}
		}
//		
//		JPanel wrapper = new JPanel( new FlowLayout( FlowLayout.LEFT));
//		wrapper.add( panelCenter );
//		
		//panelCenter.setLayout( gridCenter );
		frame.add( panelCenter );
		border.addLayoutComponent( panelCenter , BorderLayout.CENTER );
		
		panelArray[ 8 ][ 2 ].add( new JLabel( ">") );
		panelArray[ 8 ][ 3 ].add( new JLabel( ">") );
		panelArray[ 8 ][ 8 ].add( new JLabel( "^") );
		panelArray[ 8 ][ 9 ].add( new JLabel( "<") );
		panelArray[8][14].add(new JLabel("<"));
		
		
		
		
		
		
		
	}
	
	
}
