package com.mkfree.blog.frame.ui;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.plaf.metal.MetalButtonUI;


public class ButtonUI extends MetalButtonUI{
	protected MiListener miml;
	  
	  protected boolean oldOpaque;

		public static ComponentUI createUI( JComponent c) {
	    return new ButtonUI();
	  }
		
	  public void installDefaults( AbstractButton button) {
	    super.installDefaults( button);

	    button.setBorder( Borders.getButtonBorder());
	    
	    selectColor = LookAndFeel.getFocusColor();
	  }
	  
	  public void unsinstallDefaults( AbstractButton button) {
	    super.uninstallDefaults( button);
	    
	    button.setBorder( MetalBorders.getButtonBorder());
	  }
	  
	  public void installListeners( AbstractButton b) {
	    super.installListeners( b);
	    
	    miml = new MiListener( b);
	    b.addMouseListener( miml);
	    b.addPropertyChangeListener( miml);
	    b.addFocusListener( miml);
	  }
	  
	  protected void uninstallListeners( AbstractButton b) {
	    b.removeMouseListener( miml);
	    b.removePropertyChangeListener( miml);
	    b.removeFocusListener( miml);
	  }
	  
	  protected void paintButtonPressed( Graphics g, AbstractButton b) {
	    if ( !oldOpaque ) {
	      return;
	    }
	    
	  	if ( b.isContentAreaFilled() ) {
	      Graphics2D g2D = (Graphics2D)g;
	      g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      g2D.setColor( Utils.getColorAlfa( selectColor, 100));
	      RoundRectangle2D.Float boton = hazBoton( b);
	      g2D.fill( boton);
	      g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		  }
		}

		protected void paintFocus( Graphics g, AbstractButton b,
															 Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
	    if ( !b.isFocusPainted() || !oldOpaque ) {
	      return;
	    }
	    if ( b.getParent() instanceof JToolBar ) {
	      return;  
	    }
	    
	    Utils.paintFocus( g, 3,3, b.getWidth()-6, b.getHeight()-6, 2, 2, LookAndFeel.getFocusColor());
	  }
	  
	  public void update( Graphics g, JComponent c) {
	    oldOpaque = c.isOpaque();
	    
	    if ( c.getParent() instanceof JToolBar ) {
	      super.update( g,c);
	    }
	    else {
	      c.setOpaque( false);
	      super.update( g,c);
	      c.setOpaque( oldOpaque);
	    }
	  }
	  
	  public void paint( Graphics g, JComponent c) {  
	    ButtonModel mod = ((AbstractButton)c).getModel();
	    
	    if ( oldOpaque ) {
	      Graphics2D g2D = (Graphics2D)g;
	      g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      
	      RoundRectangle2D.Float boton = hazBoton( c);
	      
	      g.setClip( boton);
	      
	      g2D.setColor( LookAndFeel.getControl());
	      g2D.fill( boton);
	      
	      if ( c.getParent() instanceof JToolBar ) {
	        if ( mod.isRollover() || mod.isPressed() || mod.isSelected()) {
	          c.setBorder( Borders.getGenBorder());
	        }
	        else {
	          c.setBorder( Borders.getEmptyGenBorder());
	        }
	        
	        if ( mod.isPressed() || mod.isSelected() ) {
	          g2D.setColor( LookAndFeel.getFocusColor());
	          g2D.fill( boton);
	        }
	      }
	      else {
	        GradientPaint grad = null;
	        
	        if ( mod.isPressed() || mod.isSelected() ) {
	        	grad = new GradientPaint( 0,0, Utils.getSombra(), 
	                                    0,c.getHeight(), Utils.getBrillo());
	    		}
	    		else {
	        	grad = new GradientPaint( 0,0, Utils.getBrillo(), 
	                                    0,c.getHeight(), Utils.getSombra());
	    		}
	    		
	    		g2D.setPaint( grad);
	        g2D.fill( boton);
	        
	        if ( mod.isRollover() ) {
	          g2D.setColor( Utils.getRolloverColor());
	          g2D.fill( boton);
	        }
	      }
	      
	      g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
	    }

	    super.paint( g, c);
	  }
	  
	  private RoundRectangle2D.Float hazBoton( JComponent c) {
	    RoundRectangle2D.Float boton = new RoundRectangle2D.Float(); 
	    boton.x = 0;
	    boton.y = 0;
	    boton.width = c.getWidth();
	    boton.height = c.getHeight();
	    boton.arcwidth = 8;
	    boton.archeight = 8;
	    
	    return boton;
	  }
	  
	  /////////////////////////////////////
	  
	  public class MiListener extends MouseInputAdapter implements PropertyChangeListener, FocusListener {
	    private AbstractButton papi;
	    
	    MiListener( AbstractButton b) {
	      papi = b;
	    }
	    
	    public void refresh() {
	      if ( papi != null && papi.getParent() != null ) {
	        papi.getParent().repaint( papi.getX()-5, papi.getY()-5, 
	                                  papi.getWidth()+10, papi.getHeight()+10);
	      }
	    }
	    
	    public void mouseEntered( MouseEvent e) {
	      papi.getModel().setRollover( true);
	      refresh();
	    }

	    public void mouseExited( MouseEvent e) {
	      papi.getModel().setRollover( false);
	      refresh();
	    }
	    
	    public void mousePressed(MouseEvent e) {
	      papi.getModel().setRollover( false);
	      refresh();
	    }

	    public void mouseReleased(MouseEvent e) {
	      papi.getModel().setRollover( false);
	      refresh();
	    }

	    public void propertyChange( PropertyChangeEvent evt) {
	      if ( evt.getPropertyName().equals( "enabled") ) {
	        refresh();
	      }
	    }

	    public void focusGained( FocusEvent e) {
	      refresh();
	    }

	    public void focusLost( FocusEvent e) {
	      refresh();
	    }
	  }
}
