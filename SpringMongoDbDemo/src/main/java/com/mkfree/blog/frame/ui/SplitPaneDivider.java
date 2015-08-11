package com.mkfree.blog.frame.ui;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

public class SplitPaneDivider extends BasicSplitPaneDivider {

  public SplitPaneDivider( BasicSplitPaneUI p ) {
    super( p);
  }

  protected JButton createRightOneTouchButton() {
    JButton b = new Boton( Boton.DER, super.splitPane, BasicSplitPaneDivider.ONE_TOUCH_SIZE);
    Boolean boo = ((Boolean)UIManager.get( "SplitPane.oneTouchButtonsOpaque"));
    if ( boo != null ) {
      b.setOpaque( boo.booleanValue());
    }
    
    return b;
  }
  
  protected JButton createLeftOneTouchButton() {
    JButton b = new Boton( Boton.IZQ, super.splitPane, BasicSplitPaneDivider.ONE_TOUCH_SIZE);
    Boolean boo = ((Boolean)UIManager.get( "SplitPane.oneTouchButtonsOpaque"));
    if ( boo != null ) {
      b.setOpaque( boo.booleanValue());
    }
    
    return b;
  }
  
  public void paint( Graphics g) {
    super.paint( g);
    
    Graphics2D g2D = (Graphics2D)g;
    GradientPaint grad = null;
    if ( super.splitPane.getOrientation() == JSplitPane.VERTICAL_SPLIT ) {
    	grad = new GradientPaint( 0,0, Utils.getBrillo(), 
                                0,getHeight(), Utils.getSombra());
		}
		else {
    	grad = new GradientPaint( 0,0, Utils.getBrillo(), 
                                getWidth(),0, Utils.getSombra());
		}

    g2D.setPaint( grad);
    g2D.fillRect( 0,0, getWidth(),getHeight());
  }
  
  protected class Boton extends JButton {
    
    public static final int IZQ = 0;
    public static final int DER = 1;
    
    private JSplitPane splitPane;
    private int ots, dir;
    
    public Boton( int dir, JSplitPane sp, int ots) {
      this.dir = dir;
      splitPane = sp;
      this.ots = ots;
      
      setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR));
      setFocusPainted( false);
      setBorderPainted( false);
      setRequestFocusEnabled( false);
      setOpaque( false);
    }
    
    public void setBorder( Border border ) {
    }

    public void paint( Graphics g ) {
      if ( splitPane != null ) {
        int blocksize = Math.min( getDividerSize(), ots);
        
        g.setColor( LookAndFeel.getFocusColor());
        
        int[] xs = new int[3];
        int[] ys = new int[3];
        
        if ( orientation == JSplitPane.VERTICAL_SPLIT && dir == DER ) {
          xs = new int[] { 0, blocksize / 2, blocksize};
          ys = new int[] { 0, blocksize, 0};
        }
        else if ( orientation == JSplitPane.VERTICAL_SPLIT && dir == IZQ ) {
          xs = new int[] { 0, blocksize / 2, blocksize};
          ys = new int[] { blocksize, 0, blocksize};
        }
        else if ( orientation == JSplitPane.HORIZONTAL_SPLIT && dir == DER ) {
          xs = new int[] { 0, 0, blocksize};
          ys = new int[] { 0, blocksize, blocksize / 2};
        }
        else if ( orientation == JSplitPane.HORIZONTAL_SPLIT && dir == IZQ ) {
          xs = new int[] { 0, blocksize, blocksize};
          ys = new int[] { blocksize / 2, 0, blocksize};
        }
        
        g.fillPolygon( xs, ys, 3);
        g.setColor( LookAndFeel.getFocusColor().darker());
        g.drawPolygon( xs, ys, 3);
      }
    }
    
    public boolean isFocusable() {
      return false;
    }
  }
}

