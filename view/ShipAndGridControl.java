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
				this.add( panelArray[ i ][ j ] );
			}
		}
		
		frame.add( this );
		
		border.addLayoutComponent( this , BorderLayout.CENTER );
		
		
		// TEST //
		imageSizing();
		
		
		panelArray[ gameRow ][ 0 ].add( new JLabel( tie ) );
//		panelArray[ gameRow ][ 1 ].add( new JLabel( tie ) );
		//panelArray[ gameRow ][ 1 ].add( enemyShip );
		panelArray[ gameRow ][ 1 ].add( new JLabel( tie ) );
		panelArray[ gameRow ][ 2 ].add( new JLabel( tie ) );
		panelArray[ gameRow ][ 3 ].add( new JLabel( bolt ) );
		//panelArray[ gameRow ][ 4 ].add( enemyShip );
		panelArray[ gameRow ][ 4 ].add( new JLabel( explosion ) );
		//panelArray[ gameRow ][ 5 ].add( new JLabel( ">") );
		panelArray[ gameRow ][ 6 ].add( new JLabel( tie ) );
		

		
		model.addOrientationListener( this );
		
		panelArray[ gameRow ][ gameCol ].add( new JLabel( xwing ) );

		
		panelArray[ gameRow ][ 8 ].add( new JLabel( "<") );
		panelArray[ gameRow ][ 9 ].add( new JLabel( "<") );
		panelArray[ gameRow ][ 10 ].add( new JLabel( "<") );
		

		
		panelArray[ 0 ][ gameCol ].add( new JLabel( tie ) );
		panelArray[ 1 ][ gameCol ].add( new JLabel( tie ) );
		panelArray[ 2 ][ gameCol ].add( new JLabel( tie ) );
		panelArray[ 3 ][ gameCol ].add( new JLabel( tie ) );
		panelArray[ 4 ][ gameCol ].add( new JLabel( tie ) );
		panelArray[ 6 ][ gameCol ].add( new JLabel( tie ) );
		
		panelArray[ 8 ][ gameCol ].add( new JLabel( tie ) );
		panelArray[ 9 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 10 ][ gameCol ].add( new JLabel( "^") );

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
	
	public void missiles()
	{
//		JPanel missilePanel = new JPanel();
//		JLabel missileLabel = new JLabel();
//		
//		Canvas c = new Canvas();
//		Graphics missile = c.getGraphics();
//		missile.setColor( Color.RED );
//		missile.fillRect( 0, 0, 18, 5);
//		missileLabel.add( )
//		//Rectangle missiles = new Rectangle( 18, 5 );
////		Graphics missiles = new Graphics();
////		setBackground( Color.RED );
////		missiles.fillRect( 0, 0, 18, 5);
//		
//		//Color red = new Color( Color.RED );
		
		
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
		// TODO Auto-generated method stub
		
	}

}
