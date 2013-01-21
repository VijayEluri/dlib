package com.phyloa.dlib.dui;

import com.phyloa.dlib.renderer.Renderer2D;

public class DScrollPane extends DUIElement
{
	DPanel innerPane;
	int scrollx;
	int scrolly;
	
	public DScrollPane( int x, int y, int width, int height )
	{
		super( x, y, width, height );
	}
	
	public void setInnerPane( DPanel innerPane )
	{
		this.innerPane = innerPane;
	}

	public void keyPressed( DKeyEvent dke )
	{
		
	}

	public void keyReleased( DKeyEvent dke )
	{
		
	}

	public void mouseEntered( DMouseEvent e )
	{
		
	}

	public void mouseExited( DMouseEvent e )
	{
		
	}

	public void mousePressed( DMouseEvent e )
	{
		
	}

	public void mouseReleased( DMouseEvent e )
	{
		
	}

	public void mouseMoved( DMouseEvent e )
	{
		
	}

	public void mouseDragged( DMouseEvent e )
	{
		
	}

	public void render( Renderer2D r )
	{
		
	}

	public void update( DUI ui )
	{
		
	}
	
	@Override
	public void renderChildren( Renderer2D r )
	{
		if( visible )
		{
			r.pushMatrix();
			r.translate( x, y );
			r.translate( -scrollx, -scrolly );
			for( int i = 0; i < children.size(); i++ )
			{
				children.get( i ).render( r );
				children.get( i ).renderChildren( r );
			}
			r.popMatrix();
		}
	}
}
