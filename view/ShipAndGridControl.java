/**
 * Ryan Maynord, Zachary Conant, Nathan Pierce
 * CS 3053 - Human Computer Interaction
 * Project
 * April 25th, 27th 2017
 * ShipAndGridControl.java
 */

package view;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import controller.Controller;
import model.DifficultyListener;
import model.Model;
import model.OrientationListener;
import model.PositionListener;

public class ShipAndGridControl extends JPanel implements PositionListener, OrientationListener, DifficultyListener
{
	private Model model;
	private Controller controller;
	private JFrame frame;
	private ImageIcon xwing = new ImageIcon( "src/X_Wing_90.png" );
	private ImageIcon tie = new ImageIcon( "src/TIE_Fighter_Render.png" );
	private ImageIcon boltHorizontal = new ImageIcon( "src/blaster_bolt_horizontal.png" );
	private ImageIcon boltVertical = new ImageIcon( "src/blaster_bolt_vertical.png" );
	private ImageIcon explosion = new ImageIcon( "src/explosion2.png" );
	private ImageIcon highScore = new ImageIcon( "src/high_score.png" );
	private Image deathStar = Toolkit.getDefaultToolkit().createImage( "src/deathstar1.jpg" );
	private JLabel playerShip = new JLabel();
	private JLabel enemyShip = new JLabel();
	private JLabel boltLabel = new JLabel();
	private JLabel diffLabel;
	private JLabel currentHighScore;
	private final int gridRows = 11;
	private final int gridCols = 11;
	private final int gameRow = 5;
	private final int gameCol = 5;
	private JPanel[][] panelArray;
	private JPanel diffPanel;
	private JPanel panelBottom;
	private JMenu mDifficulty;
	private JRadioButtonMenuItem normal;
	private JRadioButtonMenuItem hard;
	
	
	/**
	 * Constructor. Displays the moving ships and updated graphics.
	 * @param model
	 * @param frame
	 * @param mDifficulty
	 * @param currentHighScore
	 * @param diffPanel
	 * @param panelBottom
	 * @param diffLabel
	 * @param normal
	 * @param hard
	 */
	public ShipAndGridControl( Model model, Controller controller, JFrame frame, JMenu mDifficulty, JLabel currentHighScore, 
			JPanel diffPanel, JPanel panelBottom, JLabel diffLabel, JRadioButtonMenuItem normal, JRadioButtonMenuItem hard )
	{
		
		this.model = model;
		this.controller = controller;
		this.playerShip.setIcon( xwing );
		this.enemyShip.setIcon( tie );
		this.frame = frame;
		this.mDifficulty = mDifficulty;
		this.currentHighScore = currentHighScore;
		this.diffPanel = diffPanel;
		this.panelBottom = panelBottom;
		this.diffLabel = diffLabel;
		this.normal = normal;
		this.hard = hard;
//		this.playerShip = new JLabel( xwing );
//		this.enemyShip = new JLabel( tie );
		
	}
	
	/**
	 * Handles the moving ships between grids, updates images.
	 * @param frame
	 * @param border
	 * @param model
	 */
	public void gridDesign( JFrame frame, BorderLayout border, Model model )
	{
		
		this.setPreferredSize( new Dimension( 450, 450 ) );
		this.setOpaque( false );
		
		
		GridLayout gridCenter = new GridLayout( gridRows, gridCols );
		this.setLayout( gridCenter );
		
		// TEST
		//JPanel behindCenter = new JPanel();
		//~
		//this.add( behindCenter );
		
		// Creating grid
		this.panelArray = new JPanel[ gridRows ][ gridCols ];

		
		for ( int i = 0; i < gridRows; i++ )
		{
			for( int j = 0; j < gridCols; j++ )
			{
				panelArray[ i ][ j ] = new JPanel();
				panelArray[ i ][ j ].setOpaque( false );
				panelArray[ i ][ j ].add( new JLabel() );
				this.add( panelArray[ i ][ j ] );
				//behindCenter.add( panelArray[ i ][ j ] );
			}
		}
		
		//this.add( behindCenter );
		frame.add( this );
		
		border.addLayoutComponent( this , BorderLayout.CENTER );
		
		
		
		// TEST //
		imageSizing();
		this.playerShip.setIcon( xwing );
		

		// Listeners
		model.addOrientationListener( this );
		model.addPositionListener( this );
		model.addDifficultyListener( this );
		
		panelArray[ gameRow ][ gameCol ].add( playerShip );

	}
	
	/**
	 * Resizes all of the images to appropriate game size.
	 */
	public void imageSizing()
	{
		Image xWing = xwing.getImage();
		Image newXWing = xWing.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.xwing = new ImageIcon( newXWing );
		
		Image tie2 = tie.getImage();
		Image newTie = tie2.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.tie = new ImageIcon( newTie );
		
		Image bolt1 = boltHorizontal.getImage();
		Image newBolt = bolt1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.boltHorizontal = new ImageIcon( newBolt );
		
		Image explosion1 = explosion.getImage();
		Image newExplosion = explosion1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.explosion = new ImageIcon( newExplosion );
	}
	
	/**
	 * Method that can be called multiple times to resize a specific imageIcon
	 * @param width
	 * @param height
	 * @param icon
	 * @return
	 */
	public ImageIcon resizeImage( int width, int height, ImageIcon icon ) 
	{
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance( width, height, java.awt.Image.SCALE_SMOOTH );
		return new ImageIcon( newImage );
	} 

	/**
	 * Updates the orientation of the user ship. Also controls the difficulty appearance settings.
	 */
	@Override
	public void updateOrientation() 
	{
		this.playerShip.setIcon( model.getTurretImage() );
		
		//TODO: Will need to be moved
		if ( controller.isNormal() == true )
		{
			System.out.println("Test!!!");
			normal.setArmed( true );
			hard.setArmed( false );
		}
		
		if ( controller.isNormal() == false )
		{
			hard.setArmed( true );
			normal.setArmed( false );
		}
	}

	/**
	 * Updates the images and locations of the enemy ships. Also contains an isEnd method which is used to display dialog boxes, 
	 * 	update high score.
	 */
	@Override
	public void updatePositions() 
	{
		//TODO: Will need to be moved
		// Makes sure the 'Difficulty' menu cannot be accessed whenever the game is in motion
		if ( model.isRunning() )
		{
			mDifficulty.setEnabled( false );
		}
		else
		{
			mDifficulty.setEnabled( true );
		}
		
		
		String[] zero = model.getDirection( 0 );
		String[] ninety = model.getDirection( 90 );
		String[] oneEighty = model.getDirection( 180 );
		String[] twoSeventy = model.getDirection( 270 );
		
		int indexJ1 = 10;
		
		for ( int index = 0; index < 5; index++ )
		{
			// Zero Degrees
			if ( zero[ index ].equals( "" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ indexJ1 ].getComponent( 0 ) ) ).setIcon( null );
			}
			
			else if ( zero[ index ].equals( "E" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ indexJ1 ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/TIE_Fighter_Render.png" ) ) );
			}
			
			else if ( zero[ index ].equals( "M" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ indexJ1 ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/blaster_bolt_horizontal.png" ) ) );
			}
			
			else if ( zero[ index ].equals( "D" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ indexJ1 ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/explosion2.png" ) ) );
			}
			
			else if ( zero[ index ].equals( "EV1" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ indexJ1 ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader.png" ) ) );
			}
			
			else if ( zero[ index ].equals( "EV2" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ indexJ1 ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader_Damaged.png" ) ) );
			}
			
			
			// Ninety Degrees
			if ( ninety[ index ].equals( "" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( null );
			}
			
			else if ( ninety[ index ].equals( "E" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/TIE_Fighter_Render.png" ) ) );
			}
			
			else if ( ninety[ index ].equals( "M" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/blaster_bolt_vertical.png" ) ) );
			}
			
			else if ( ninety[ index ].equals( "D" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/explosion2.png" ) ) );
			}
			
			else if ( ninety[ index ].equals( "EV1" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader.png" ) ) );
			}
			
			else if ( ninety[ index ].equals( "EV2" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader_Damaged.png" ) ) );
			}
			
			
			// One Eighty Degrees
			if ( oneEighty[ index ].equals( "" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ index ].getComponent( 0 ) ) ).setIcon( null );
			}
			
			else if ( oneEighty[ index ].equals( "E" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ index ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/TIE_Fighter_Render.png" ) ) );
			}
			
			else if ( oneEighty[ index ].equals( "M" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ index ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/blaster_bolt_horizontal.png" ) ) );
			}
			
			else if ( oneEighty[ index ].equals( "D" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ index ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/explosion2.png" ) ) );
			}
			
			else if ( oneEighty[ index ].equals( "EV1" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ index ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader.png" ) ) );
			}
			
			else if ( oneEighty[ index ].equals( "EV2" ) )
			{
				( ( JLabel ) ( this.panelArray[ gameRow ][ index ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader_Damaged.png" ) ) );
			}
			
			
			// Two Seventy Degrees
			if ( twoSeventy[ index ].equals( "" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( null );
			}
			
			else if ( twoSeventy[ index ].equals( "E" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/TIE_Fighter_Render.png" ) ) );
			}
			
			else if ( twoSeventy[ index ].equals( "M" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/blaster_bolt_vertical.png" ) ) );
			}
			
			else if ( twoSeventy[ index ].equals( "D" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/explosion2.png" ) ) );
			}
			
			else if ( twoSeventy[ index ].equals( "EV1" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader.png" ) ) );
			}
			
			else if ( twoSeventy[ index ].equals( "EV2" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/Darth_Vader_Damaged.png" ) ) );
			}
			
			--indexJ1;
		}
		
		if ( model.isEnd() == true )
		{
			System.out.println( model.isHighScore() + " hs");
			gameOver( frame );
			
			GridLayout diffGrid = new GridLayout(2, 1);
			
			//diffPanel.
//			diffPanel.removeAll();
//			//currentHighScore.removeAll();
//			diffPanel = new JPanel( diffGrid );
			
//			diffLabel = new JLabel( " High Score" );
//			Font diffFont = getFont().deriveFont( Font.PLAIN, 28 );
//			diffLabel.setFont( diffFont );
//			diffLabel.setForeground( new Color( 75, 213, 238 ) );
			diffPanel.add( diffLabel );
			
			//currentHighScore = new JLabel( "      " + Integer.toString( model.getHighScore() ) );
			//currentHighScore = new JLabel();
		
			Font hsFont = currentHighScore.getFont();
			currentHighScore.setFont( hsFont.deriveFont( Font.CENTER_BASELINE , 30) );
			diffPanel.add( currentHighScore );
			
			//panelBottom.add( diffPanel );
		}
		
	} // END OF UPDATEPOSITIONS()
	
	/**
	 * Displays a specific dialog box at the end of the game depending upon whether or not a new high score was achieved. 
	 * @param frame
	 */
	public void gameOver( JFrame frame )
	{
		if ( model.isHighScore() == true )
		{
			JOptionPane.showMessageDialog( frame, " Nice Try ", "Game Over", JOptionPane.INFORMATION_MESSAGE, highScore );
			System.out.println("WOWOWOW: nhs " );
		}
		
		else
			JOptionPane.showMessageDialog( frame, "", "Game Over", JOptionPane.PLAIN_MESSAGE );
	}

	
	@Override
	public void updateDifficulty() 
	{
		if ( controller.isNormal() == true )
		{
			System.out.println("Normal is true");
			//hard.setArmed( false );
//			normal.setArmed( true );
//			hard.setArmed( false );
			hard.setSelected( false );
			normal.setSelected( true );
		}
		
		if ( controller.isNormal() == false )
		{
			System.out.println("Normal is false");
			normal.setSelected( false );
			hard.setSelected( true );
//			hard.setArmed( true );
//			normal.setArmed( false );
			//hard.setArmed( true );
		}
	}

}
