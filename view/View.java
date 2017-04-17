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
import java.awt.Font;
import java.awt.GraphicsEnvironment;
//import java.awt.GridLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu.Separator;
import javax.swing.JSeparator;

import controller.Controller;
import model.Model;
import model.ScoreListener;

/**
 *  Class contains view components that represent model data and that are manipulated by the controller
 */
public class View implements ScoreListener
{
	private Model model; 				// Needs model to get new information about its view components
	private Controller controller; 		// Needs controller to connect view components with user interaction
	private JFrame frame; 				// Window that holds app
	private ShipAndGridControl shipControl;
	private int score;
	private ImageIcon up = new ImageIcon( "src/up_arrow.png" );
	private ImageIcon right = new ImageIcon( "src/right_arrow.png" );
	private ImageIcon down = new ImageIcon( "src/down_arrow.png" );
	private ImageIcon left = new ImageIcon( "src/left_arrow.png" );
	private ImageIcon start = new ImageIcon( "src/start.png" );
	//private ImageIcon reset = new ImageIcon( "src/reset.png" );
	private ImageIcon fire = new ImageIcon( "src/target.png" );
	private ImageIcon highScore = new ImageIcon( "src/high_score.png" );
	private ImageIcon reset = new ImageIcon( "src/reset_button_1.png" );
	private ImageIcon play = new ImageIcon( "src/play_button_1.png" );
	private ImageIcon pause = new ImageIcon( "src/pause_button_1.png" );
	
//	private final int gridRows = 15;
//	private final int gridCols = 15;
//	private final int gameRow = 8;
//	private final int gameCol = 8;
	
	
	public View( Model model, Controller controller, JFrame frame ) throws Exception 
	{
		this.model = model;
		this.controller = controller;
		this.frame = frame;
		this.shipControl = shipControl;
		this.score = score;
		
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		gameView( controller, model, frame );
	}
	
	private void gameView( Controller controller, Model model, JFrame frame ) throws Exception
	{
		// The main layout used in the design of the game
		BorderLayout border = new BorderLayout();
		frame.setLayout( border );
		
		imageResizing();
		
		
		// **** MENU BAR **** //
		
		JMenuBar mBar = new JMenuBar();
		mBar.setBorderPainted( true );
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
		
		//JButton startButton = new JButton( start );
		JButton playButton = new JButton( play );
		playButton.setBorderPainted( false );
		playButton.setRequestFocusEnabled( false );
		playButton.addActionListener( controller );
		playButton.setActionCommand( "startGame" );
		panelLeft.add( playButton );
		
		JButton pauseButton = new JButton( pause );
		pauseButton.setBorderPainted( false );
		pauseButton.setRequestFocusEnabled( false );
		pauseButton.addActionListener( controller );
		pauseButton.setActionCommand( "pauseGame" );
		panelLeft.add( pauseButton );
		
		JButton resetButton = new JButton( reset );
		resetButton.setBorderPainted( false );
		resetButton.setRequestFocusEnabled( false );
		resetButton.addActionListener( controller );
		resetButton.setActionCommand( "resetGame" );
		panelLeft.add( resetButton );
		
		panelLeft.add( new JButton ( "Button 4" ) );
		//panelLeft.add( new JButton ( "Button 5" ) );
		JLabel scoreLabel = new JLabel( " Score " );
		scoreLabel.setFont( getFont() );
		scoreLabel.setForeground( new Color( 229, 177, 58 ));
		panelLeft.add( scoreLabel );
		
		frame.add( panelLeft );
		border.addLayoutComponent( panelLeft , BorderLayout.WEST );
		
		// ---- END OF LEFT ---- //
		
		
		
		// **** BOTTOM **** //
		
		JPanel panelBottom = new JPanel();
		panelBottom.setPreferredSize( new Dimension( 800, 100 ) );
		GridLayout gridBottom = new GridLayout( 1, 4 );
		panelBottom.setLayout( gridBottom );
		
		
//		JButton userControls = new JButton( "" );
//		panelBottom.add( userControls );
		
		// TODO: This will replace the above button
		JLabel userScore = new JLabel( );
		userScore.setText( Integer.toString( score ) );
		panelBottom.add( userScore );
		
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
		
		//panelControls.add( new JLabel("") );
		JButton trigger = new JButton( fire );
		trigger.setBorderPainted( false );
		trigger.addActionListener( controller );
		//trigger.setActionCommand( );
		trigger.setRequestFocusEnabled( false );
		panelControls.add( trigger );
		
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
		
		//gameOver();
		//JOptionPane.showMessageDialog( frame, "", "Game Over", JOptionPane.INFORMATION_MESSAGE, highScore );
		
		// Listeners
		model.addScoreListener( this );
		
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
		
		Image play1 = play.getImage();
		Image newPlay = play1.getScaledInstance( 150,  80, java.awt.Image.SCALE_SMOOTH );
		this.play = new ImageIcon( newPlay );
		
		Image reset1 = reset.getImage();
		Image newReset = reset1.getScaledInstance( 150, 80, java.awt.Image.SCALE_SMOOTH );
		this.reset = new ImageIcon( newReset );
		
		Image fire1 = fire.getImage();
		Image newFire = fire1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.fire = new ImageIcon( newFire );
		
		Image highScore1 = highScore.getImage();
		Image newHighScore = highScore1.getScaledInstance( 100, 100, java.awt.Image.SCALE_SMOOTH );
		this.highScore = new ImageIcon( newHighScore );
		
		Image pause1 = pause.getImage();
		Image newPause = pause1.getScaledInstance( 150, 80, java.awt.Image.SCALE_SMOOTH );
		this.pause = new ImageIcon( newPause );
	}
	
	public Font getFont() throws Exception
	{
		URL swFont = new URL( "http://www.webpagepublicity.com/free-fonts/s/Star%20Jedi.ttf");
		Font font = Font.createFont(Font.TRUETYPE_FONT, swFont.openStream() );
		font = font.deriveFont( Font.PLAIN, 44 );
		
		GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
		graphics.registerFont(font);
		
		return font;
	}
	
	public void gameOver()
	{
		// TODO: Needs to be removed once I can get the high score
		JOptionPane.showMessageDialog( frame, "", "Game Over", JOptionPane.PLAIN_MESSAGE );
		
//		if ( model.getHighScore() == true )
//		{
//			JOptionPane.showMessageDialog( frame, "", "Game Over", JOptionPane.INFORMATION_MESSAGE, highScore );
//		}
//		else
//			JOptionPane.showMessageDialog( frame, "", "Game Over", JOptionPane.PLAIN_MESSAGE );
	}

	@Override
	public void updateScore() 
	{
		this.score = model.getScore();
		System.out.println( model.getScore() );
	}
}
