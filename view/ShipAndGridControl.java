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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import model.OrientationListener;
import model.PositionListener;

public class ShipAndGridControl extends JPanel implements PositionListener, OrientationListener
{
	private Model model;
	private ImageIcon xwing = new ImageIcon( "src/X_Wing_90.png" );
//	private ImageIcon xwing2 = new ImageIcon( "src/X_Wing_0.png" );
	private ImageIcon tie = new ImageIcon( "src/TIE_Fighter_Render.png" );
	private ImageIcon bolt = new ImageIcon( "src/blaster_bolt.png" );
	private ImageIcon explosion = new ImageIcon( "src/explosion2.png" );
	private JLabel playerShip = new JLabel();
	private JLabel enemyShip = new JLabel();
	private JLabel boltLabel = new JLabel();
	private final int gridRows = 11;
	private final int gridCols = 11;
	private final int gameRow = 5;
	private final int gameCol = 5;
	private JPanel[][] panelArray;
	
	public ShipAndGridControl( Model model )
	{
		
		this.model = model;
		this.playerShip.setIcon( xwing );
		this.enemyShip.setIcon( tie );
//		this.playerShip = new JLabel( xwing );
//		this.enemyShip = new JLabel( tie );
		
	}
	
	public void gridDesign(JFrame frame, BorderLayout border, Model model)
	{
		this.setPreferredSize( new Dimension( 450, 450));
		this.setBackground( Color.LIGHT_GRAY );
		
		GridLayout gridCenter = new GridLayout( gridRows, gridCols );
		this.setLayout( gridCenter );
		
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
			}
		}
		
		frame.add( this );
		
		border.addLayoutComponent( this , BorderLayout.CENTER );
		
		
		// TEST //
		imageSizing();
		this.playerShip.setIcon( xwing );
		
		
//		panelArray[ gameRow ][ 0 ].add( new JLabel( tie ) );
////		panelArray[ gameRow ][ 1 ].add( new JLabel( tie ) );
//		//panelArray[ gameRow ][ 1 ].add( enemyShip );
//		panelArray[ gameRow ][ 1 ].add( new JLabel( tie ) );
//		panelArray[ gameRow ][ 2 ].add( new JLabel( tie ) );
//		panelArray[ gameRow ][ 3 ].add( new JLabel( bolt ) );
//		//panelArray[ gameRow ][ 4 ].add( enemyShip );
//		panelArray[ gameRow ][ 4 ].add( new JLabel( explosion ) );
//		//panelArray[ gameRow ][ 5 ].add( new JLabel( ">") );
//		panelArray[ gameRow ][ 6 ].add( new JLabel( tie ) );
		

		
		model.addOrientationListener( this );
		model.addPositionListener( this );
		
		panelArray[ gameRow ][ gameCol ].add( playerShip );
		


		
//		panelArray[ gameRow ][ 8 ].add( new JLabel( "<") );
//		panelArray[ gameRow ][ 9 ].add( new JLabel( "<") );
//		panelArray[ gameRow ][ 10 ].add( new JLabel( "<") );
//		
//
//		
//		panelArray[ 0 ][ gameCol ].add( new JLabel( tie ) );
//		panelArray[ 1 ][ gameCol ].add( new JLabel( tie ) );
//		panelArray[ 2 ][ gameCol ].add( new JLabel( tie ) );
//		panelArray[ 3 ][ gameCol ].add( new JLabel( tie ) );
//		panelArray[ 4 ][ gameCol ].add( new JLabel( tie ) );
//		panelArray[ 6 ][ gameCol ].add( new JLabel( tie ) );
//		
//		panelArray[ 8 ][ gameCol ].add( new JLabel( tie ) );
//		panelArray[ 9 ][ gameCol ].add( new JLabel( "^") );
//		panelArray[ 10 ][ gameCol ].add( new JLabel( "^") );

	}
	
	public void imageSizing()
	{
		Image xWing = xwing.getImage();
		Image newXWing = xWing.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH);
		this.xwing = new ImageIcon( newXWing );
		
		Image tie2 = tie.getImage();
		Image newTie = tie2.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH);
		this.tie = new ImageIcon( newTie );
		
		Image bolt1 = bolt.getImage();
		Image newBolt = bolt1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.bolt = new ImageIcon( newBolt );
		
		Image explosion1 = explosion.getImage();
		Image newExplosion = explosion1.getScaledInstance( 37, 37, java.awt.Image.SCALE_SMOOTH );
		this.explosion = new ImageIcon( newExplosion );
	}
	
	// Resize an image icon
	public ImageIcon resizeImage( int width, int height, ImageIcon icon ) 
	{
		Image image = icon.getImage();
		Image newImage = image.getScaledInstance( width, height, java.awt.Image.SCALE_SMOOTH );
		return new ImageIcon( newImage );
	}
	
	public void playerNorth()
	{
		//panelArray[ gameRow ][ 7 ].add( playerShip );
		panelArray[ gameRow ][ 7 ].add( new JLabel( "^" ) );
	}
	
	public void playerEast()
	{
		panelArray[ gameRow ][ 7 ].add( new JLabel( ">" ) );
	}
	
	public void playerSouth()
	{
		panelArray[ gameRow ][ 7 ].add( new JLabel( "V" ) );
	}
	
	public void playerWest()
	{
		panelArray[ gameRow ][ 7 ].add( new JLabel( "<" ) );
	}

	@Override
	public void updateOrientation() 
	{
//		this.playerShip = new JLabel( model.getTurretImage() );
		this.playerShip.setIcon( model.getTurretImage() );
		//panelArray[ gameRow ][ 7 ].add( playerShip );
	}

	@Override
	public void updatePositions() 
	{

//		for ( int i = 0; i < 10; i++ )
//		{
//			if ( i != 5 )
//			{
//				// TODO: BUG TESTING
//				System.out.println( "This should print after pressing start" );
//				// - Actually this prints as soon as the program runs
//				
//				panelArray[ gameRow ][ i ].add( new JLabel( tie ) );
//			}
//
//		}
		

//		panelArray[ gameRow ][ 0 ].add( new JLabel( tie ) );
//		panelArray[ gameRow ][ 1 ].add( new JLabel( tie ) );
//		panelArray[ gameRow ][ 2 ].add( new JLabel( tie ) );
//		panelArray[ gameRow ][ 3 ].add( new JLabel( bolt ) );
//		//panelArray[ gameRow ][ 4 ].add( enemyShip );
//		panelArray[ gameRow ][ 4 ].add( new JLabel( explosion ) );
//		//panelArray[ gameRow ][ 5 ].add( new JLabel( ">") );
//		panelArray[ gameRow ][ 6 ].add( new JLabel( tie ) );
		
		
		
		
		String[] zero = model.getDirection( 0 );
		String[] ninety = model.getDirection( 90 );
		String[] oneEighty = model.getDirection( 180 );
		String[] twoSeventy = model.getDirection( 270 );
		//this.panelArray[ gameRow ][ 10 ].add( new JLabel( zero[ 10 ] ) );
		
		int indexJ1 = 10;
		int indexJ2 = 10;
		
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
			
			// Ninety Degrees
			if ( ninety[ index ].equals( "" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( null );
			}
			
			else if ( ninety[ index ].equals( "E" ) )
			{
				( ( JLabel ) ( this.panelArray[ index ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/TIE_Fighter_Render.png" ) ) );
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
			
			// Two Seventy Degrees
			if ( twoSeventy[ index ].equals( "" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( null );
			}
			
			else if ( oneEighty[ index ].equals( "E" ) )
			{
				( ( JLabel ) ( this.panelArray[ indexJ1 ][ gameCol ].getComponent( 0 ) ) ).setIcon( resizeImage( 37, 37, new ImageIcon( "src/TIE_Fighter_Render.png" ) ) );
			}
			
			--indexJ1;
		}
		
//		for ( int index = 0; index < 5; index++ )
//		{
//			this.panelArray[ index ][ gameCol].add( new JLabel( ninety[ index ] ) );
//		}
//		
//		for ( int index = 0; index < 5; index++ )
//		{
//			this.panelArray[ gameRow ][ index ].add( new JLabel( oneEighty[ index ] ) );
//		}
//		
//		
//		for ( int index = 0; index < 5; index++ )
//		{
//			this.panelArray[ indexJ2 ][ gameCol ].add( new JLabel( twoSeventy[ index ] ) );
//			--indexJ2;
//		}
		
	} // END OF UPDATEPOSITIONS()

}
