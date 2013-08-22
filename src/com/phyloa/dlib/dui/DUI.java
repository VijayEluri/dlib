package com.phyloa.dlib.dui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import com.phyloa.dlib.renderer.Renderer2D;
import com.phyloa.dlib.util.DKeyHandler;
import com.phyloa.dlib.util.DMouseHandler;

public class DUI implements DMouseListener, DKeyListener
{
	DEventMapper dem;
	ArrayList<DUIListener> listeners = new ArrayList<DUIListener>();
	
	DUIElement focus = null;
	DUIElement hover = null;
	DUIElement rootPane = new DPanel( 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE );
	
	DUIElement topPanelOwner = rootPane;
	DUIElement topPanel = new DPanel( 0, 0, 0, 0 );
	
	boolean enabled = true;
	
	public DUITheme theme = DUITheme.defaultTheme;
	
	public DUI( DEventMapper dem )
	{
		this.dem = dem;
		dem.addDKeyListener( this );
		dem.addDMouseListener( this );
		rootPane.ui = this;
		topPanel.visible = false;
	}
	
	public DUI( DEventMapper dem, int x, int y, int width, int height )
	{
		this( dem );
		rootPane = new DPanel( x, y, width, height );
		rootPane.ui = this;
	}
	
	public void setTopPanel( DUIElement owner, DUIElement topPanel )
	{
		topPanelOwner.losingTopPanel( this.topPanel );
		topPanelOwner = owner;
		this.topPanel = topPanel;
		topPanel.setUI( this );
	}
	
	public void update()
	{
		if( enabled )
		{
			if( topPanel.visible )
			{
				topPanel.update( this );
				topPanel.updateChildren( this );
			}
			rootPane.update( this );
			rootPane.updateChildren( this );
		}
	}
	
	public void render( Renderer2D r )
	{
		if( enabled )
		{
			rootPane.render( r );
			rootPane.renderChildren( r );
			if( topPanel.visible )
			{
				topPanel.render( r );
				topPanel.renderChildren( r );
			}
		}
	}
	
	public void addDUIListener( DUIListener l )
	{
		if( !listeners.contains( l ) ) listeners.add( l );
	}
	
	public void add( DUIElement e )
	{
		rootPane.add( e );
		e.setUI( this );
	}
	
	public void event( DUIEvent e )
	{
		for( DUIListener l : listeners )
		{
			l.event( e );
		}
	}

	public DUIElement getFocus()
	{
		return focus;
	}

	public void setFocus( DUIElement focus )
	{
		this.focus = focus;
	}

	public void mouseMoved( DMouseEvent e )
	{
		if( enabled )
		{
			if( topPanel.visible && topPanel.isInside( e.x, e.y ) )
			{
				topPanel.mouseMoved( e );
				topPanel.handleChildrenMouseMoved( e );
			}
			else
			{
				rootPane.mouseMoved( e );
				rootPane.handleChildrenMouseMoved( e );
			}
		}
	}

	public void mousePressed( DMouseEvent e )
	{
		if( enabled )
		{
			if( topPanel.visible && topPanel.isInside( e.x, e.y ) )
			{
				topPanel.mousePressed( e );
				topPanel.handleChildrenMousePressed( e );
			}
			else
			{
				rootPane.mousePressed( e );
				rootPane.handleChildrenMousePressed( e );
			}
		}
	}

	public void mouseReleased( DMouseEvent e )
	{
		if( enabled )
		{
			if( topPanel.visible && topPanel.isInside( e.x, e.y ) )
			{
				topPanel.mouseReleased( e );
				topPanel.handleChildrenMouseReleased( e );
			}
			else
			{
				rootPane.mouseReleased( e );
				rootPane.handleChildrenMouseReleased( e );
			}
		}
	}

	@Override
	public void keyPressed( DKeyEvent dke )
	{
		if( enabled && focus != null )
		{
			focus.keyPressed( dke );
		}
	}

	@Override
	public void keyReleased( DKeyEvent dke )
	{
		if( enabled && focus != null )
		{
			focus.keyReleased( dke );
		}
	}

	@Override
	public void mouseEntered( DMouseEvent e )
	{
		
	}

	@Override
	public void mouseExited( DMouseEvent e )
	{
		
	}

	@Override
	public void mouseDragged( DMouseEvent e )
	{
		if( enabled )
		{
			if( topPanel.visible && topPanel.isInside( e.x, e.y ) )
			{
				topPanel.mouseDragged( e );
				topPanel.handleChildrenMouseDragged( e );
			}
			else
			{
				rootPane.mouseDragged( e );
				rootPane.handleChildrenMouseDragged( e );
			}
		}
	}
	
	public void setEnabled( boolean enabled )
	{
		if( this.enabled == enabled ) return;
		this.enabled = enabled;
		dem.setEnabled( enabled );
		if( enabled )
		{
			dem.addDKeyListener( this );
			dem.addDMouseListener( this );
		} 
		else
		{
			dem.removeDKeyListener( this );
			dem.removeDMouseListener( this );
		}
	}

	@Override
	public void mouseWheel( DMouseEvent dme )
	{
		if( enabled )
		{
			rootPane.mouseWheel( dme );
			rootPane.handleChildrenMouseWheel( dme );
		}
	}
}
