/**
 * Ryan Maynord, Zachary Conant, Nathan Pierce
 * CS 3053 - Human Computer Interaction
 * Project
 * April 25th, 27th 2017
 * ShipAndGridControl.java
 */

package view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
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
	private final ImageIcon tie = new ImageIcon( "Imperial_Fighter.png" );
	private JLabel playerShip;
	private final JLabel enemyShip;
	private final int gridRows = 15;
	private final int gridCols = 15;
	private final int gameRow = 7;
	private final int gameCol = 7;
	private JPanel[][] panelArray;
	
	public ShipAndGridControl()
	{
		
		this.model = model;
		this.playerShip = new JLabel( xwing );
		this.enemyShip = new JLabel( tie );
		
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
		panelArray[ gameRow ][ 0 ].add( new JLabel( ">>") );
		panelArray[ gameRow ][ 1 ].add( new JLabel( ">") );
		panelArray[ gameRow ][ 2 ].add( new JLabel( ">") );
		panelArray[ gameRow ][ 3 ].add( new JLabel( ">") );
		panelArray[ gameRow ][ 4 ].add( new JLabel( ">") );
		panelArray[ gameRow ][ 5 ].add( new JLabel( ">") );
		panelArray[ gameRow ][ 6 ].add( new JLabel( ">") );
		
		//this.playerShip = new JLabel( xwing );
		//this.playerShip = new JLabel( xwing2 );
		panelArray[ gameRow ][ gameCol ].add( playerShip );
		
		panelArray[ gameRow ][ 8 ].add( new JLabel( "<") );
		panelArray[ gameRow ][ 9 ].add( new JLabel( "") );
		panelArray[ gameRow ][ 10 ].add( new JLabel( "<") );
		panelArray[ gameRow ][ 11 ].add( new JLabel( "<") );
		panelArray[ gameRow ][ 12 ].add( new JLabel( "<") );
		panelArray[ gameRow ][ 13 ].add( new JLabel( "") );
		panelArray[ gameRow ][ 14 ].add(new JLabel("<<"));
		
		panelArray[ 0 ][ gameCol ].add( new JLabel( "VV") );
		panelArray[ 1 ][ gameCol ].add( new JLabel( "V") );
		panelArray[ 2 ][ gameCol ].add( new JLabel( "V") );
		panelArray[ 3 ][ gameCol ].add( new JLabel( "V") );
		panelArray[ 4 ][ gameCol ].add( new JLabel( "V") );
		panelArray[ 5 ][ gameCol ].add( new JLabel( "V") );
		panelArray[ 6 ][ gameCol ].add( new JLabel( "V") );
		
		panelArray[ 8 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 9 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 10 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 11 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 12 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 13 ][ gameCol ].add( new JLabel( "^") );
		panelArray[ 14 ][ gameCol ].add( new JLabel( "^^") );
	}
//	
//	public void shipSizing()
//	{
//		Image xWing = xwing.getImage();
//		Image newXWing = xWing.getScaledInstance( 25, 25, java.awt.Image.SCALE_SMOOTH);
//		this.xwing = new ImageIcon( newXWing );
//	}
	
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
		this.playerShip = new JLabel( model.getTurretImage() );
		//panelArray[ gameRow ][ 7 ].add( playerShip );
	}

	@Override
	public void updatePositions() 
	{
		// TODO Auto-generated method stub
		
	}

}
