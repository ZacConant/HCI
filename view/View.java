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
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	private ShipAndGridControl shipControl;
	private ImageIcon up = new ImageIcon( "src/up_arrow.png" );
	private ImageIcon right = new ImageIcon( "src/right_arrow.png" );
	private ImageIcon down = new ImageIcon( "src/down_arrow.png" );
	private ImageIcon left = new ImageIcon( "src/left_arrow.png" );
	private ImageIcon start = new ImageIcon( "src/start.png" );
	private ImageIcon reset = new ImageIcon( "src/reset.png" );
	
//	private final int gridRows = 15;
//	private final int gridCols = 15;
//	private final int gameRow = 8;
//	private final int gameCol = 8;
	
	
	public View( Model model, Controller controller, JFrame frame ) 
	{
		this.model = model;
		this.controller = controller;
		this.frame = frame;
		this.shipControl = shipControl;
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		gameView( controller, model, frame );
	}
	
	private void gameView( Controller controller, Model model, JFrame frame )
	{
		// The main layout used in the design of the game
		BorderLayout border = new BorderLayout();
		frame.setLayout( border );
		
		imageResizing();
		
		
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

		
		//border.addLayoutComponent( mBar, BorderLayout.NORTH );
		
		// ---- END OF MENUBAR ---- //
		
		
		// **** LEFT **** //
		
		JPanel panelLeft = new JPanel();
		//panelLeft.setBounds( 0, 0, 200, 450);
		panelLeft.setPreferredSize( new Dimension(200, 450));
		GridLayout gridLeft = new GridLayout( 5, 1 );
		panelLeft.setLayout( gridLeft );
		
		JButton startButton = new JButton( start );
		startButton.setBorderPainted( false );
		startButton.setRequestFocusEnabled( false );
		startButton.addActionListener( controller );
		panelLeft.add( startButton );
		
		JButton resetButton = new JButton( reset );
		resetButton.setBorderPainted( false );
		resetButton.setRequestFocusEnabled( false );
		resetButton.addActionListener( controller );
		panelLeft.add( resetButton );
		
		panelLeft.add( new JButton ( "Button 3" ) );
		panelLeft.add( new JButton ( "Button 4" ) );
		panelLeft.add( new JButton ( "Button 5" ) );
		
		frame.add( panelLeft );
		border.addLayoutComponent( panelLeft , BorderLayout.WEST );
		
		// ---- END OF LEFT ---- //
		
		
		
		// **** BOTTOM **** //
		
		JPanel panelBottom = new JPanel();
		panelBottom.setPreferredSize( new Dimension( 800, 100 ) );
		GridLayout gridBottom = new GridLayout( 1, 4 );
		panelBottom.setLayout( gridBottom );
		JButton userControls = new JButton( "" );
		panelBottom.add( userControls );
		JButton userControls1 = new JButton( "" );
		panelBottom.add( userControls1 );
		
		GridLayout gridControls = new GridLayout( 3, 3 );
		JPanel panelControls = new JPanel( gridControls );
		
		panelControls.add( new JLabel("") );

		JButton upKey = new JButton( up );
		upKey.setBorderPainted( false );
		upKey.addActionListener( controller );
		upKey.setActionCommand( "faceUp" );
		upKey.setRequestFocusEnabled( false );
		panelControls.add( upKey );
		
		panelControls.add( new JLabel("") );
		
		
		JButton leftKey = new JButton( left );
		leftKey.setBorderPainted( false );
		leftKey.addActionListener( controller );
		leftKey.setActionCommand( "faceLeft" );
		leftKey.setRequestFocusEnabled( false );
		panelControls.add( leftKey );
		
		panelControls.add( new JLabel("") );
		
		JButton rightKey = new JButton( right );
		rightKey.setBorderPainted( false );
		rightKey.addActionListener( controller );
		rightKey.setActionCommand( "faceRight" );
		rightKey.setRequestFocusEnabled( false );
		panelControls.add( rightKey );
		
		
		panelControls.add( new JLabel("") );
		
		JButton downKey = new JButton( down );
		downKey.setBorderPainted( false );
		downKey.addActionListener( controller );
		downKey.setActionCommand("faceDown");
		downKey.setRequestFocusEnabled( false );
		panelControls.add( downKey );
		
		
		panelControls.add( new JLabel("") );


		
		

		



		

		
		panelBottom.add( panelControls );
		
//		JButton userControls2 = new JButton( "Controls2" );
//		panelBottom.add( userControls2 );
		
		
		JLabel userControls3 = new JLabel( "" );
		panelBottom.add( userControls3 );
		
		frame.add( panelBottom );
		border.addLayoutComponent( panelBottom, BorderLayout.SOUTH );
		
		// ---- END OF BOTTOM ---- //
		
		
		
		// **** CENTER **** //
		ShipAndGridControl centerDesign = new ShipAndGridControl( model );
		centerDesign.gridDesign( frame, border, model );
		
		//centerDesign.addKeyListener( controller );
		frame.addKeyListener( controller );
		frame.setFocusable( true );
		
	}
	
	public void imageResizing()
	{
		Image up1 = up.getImage();
		Image newUp = up1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.up = new ImageIcon( newUp );
		
		Image right1 = right.getImage();
		Image newRight = right1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.right = new ImageIcon( newRight );
		
		Image down1 = down.getImage();
		Image newDown = down1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.down = new ImageIcon( newDown );
		
		Image left1 = left.getImage();
		Image newLeft = left1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.left = new ImageIcon( newLeft );
		
		Image start1 = start.getImage();
		Image newStart = start1.getScaledInstance( 175, 175, java.awt.Image.SCALE_SMOOTH );
		this.start = new ImageIcon( newStart );
		
		Image reset1 = reset.getImage();
		Image newReset = reset1.getScaledInstance( 220, 220, java.awt.Image.SCALE_SMOOTH );
		this.reset = new ImageIcon( newReset );
	}
}
