package com.mkfree.blog.frame.ui;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class ComboBoxUI extends MetalComboBoxUI {
  private boolean rollover = false;
  private boolean focus = false;
  private MiML miML;
  
  protected boolean oldOpaque;
  
  public static ComponentUI createUI( JComponent c) {
    return new ComboBoxUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
   
    oldOpaque = comboBox.isOpaque();
    comboBox.setOpaque( false);
  }

  protected void uninstallDefaults() {
    super.uninstallDefaults();
    
    comboBox.setOpaque( oldOpaque);
  }

  protected  void installListeners() {
    super.installListeners();

    miML = new MiML();
    comboBox.addMouseListener( miML);
    comboBox.addFocusListener( miML);
  }
  
  protected  void uninstallListeners() {
    super.uninstallListeners();

    comboBox.removeMouseListener( miML);
    comboBox.removeFocusListener( miML);
  }
  
  public Dimension getMinimumSize( JComponent c) {
    Dimension dim = super.getMinimumSize( c);
    
    if ( comboBox.isEditable() ) {
      dim.height = editor.getPreferredSize().height - 2;
    }
    
    dim.width += 20;
    
    return dim;
  }
  
  protected ComboBoxEditor createEditor() {
    return new NimRODComboBoxEditor();
  }
  
  protected JButton createArrowButton() {
    return new NimRODComboBoxButton( comboBox, UIManager.getIcon( "ComboBox.buttonDownIcon"), 
                                     (comboBox.isEditable() ? true : false),
                                     currentValuePane, listBox);
  }
  
  public class NimRODComboBoxEditor extends MetalComboBoxEditor  {
    public NimRODComboBoxEditor() {
      super();
      editor.setBorder( Borders.getComboEditorBorder());
    }
  }
  
  private final class NimRODComboBoxButton extends MetalComboBoxButton {
    private static final long serialVersionUID = 1L;

    public NimRODComboBoxButton( JComboBox cb, Icon icon, boolean editable, CellRendererPane pane, JList list) {
      super( cb, icon, editable, pane, list);

      miML = new MiML();
      addMouseListener( miML);
      addFocusListener( miML);
    }

    public void paintComponent( Graphics g) {
      boolean canijo = false;
      
      if ( iconOnly ) {
        Border bb = Borders.getComboButtonBorder();
        Insets ins = bb.getBorderInsets( comboBox);
        
        if ( ( getSize().height < (comboIcon.getIconHeight() + ins.top + ins.bottom) )
              ||
             ( getSize().width  < (comboIcon.getIconWidth() + ins.left + ins.right) ) 
           ) {
          canijo = true;
          bb = Borders.getThinGenBorder();
        }
        
        setBorder( bb);
        setMargin( new Insets( 0,5,0,7));
      }
      else {
        Border bb = Borders.getComboEditorBorder();
        Insets ins = bb.getBorderInsets( comboBox);
        
        if ( getSize().height < (getFont().getSize() + ins.top + ins.bottom) ) {
          canijo = true;
          bb = Borders.getThinGenBorder();
        }
        
        setBorder( bb);
        setOpaque( false);
      }

      if ( !iconOnly && comboBox != null) {
        try {
          g.setColor( getBackground());
          if ( !canijo ) {
            g.fillRect( 2,3, getWidth()-4, getHeight()-6);
          }
          else {
            g.fillRect( 0,0, getWidth(), getHeight());
          }
          g.drawLine( 3,2, getWidth()-4, 2);
          g.drawLine( 3,getHeight()-3, getWidth()-4, getHeight()-3);
          
          paintLeches( g);
        }
        catch ( Exception ex) {}
      }
      
      if ( iconOnly ) {
        RoundRectangle2D.Float boton = new RoundRectangle2D.Float(); 
        if ( canijo ) {
          boton.x = 0;
          boton.y = 0;
          boton.width = getWidth();
          boton.height = getHeight();
          boton.arcwidth = 1;
          boton.archeight = 1;
        }
        else {
          boton.x = 2;
          boton.y = 2;
          boton.width = getWidth() - 4;
          boton.height = getHeight() - 4;
          boton.arcwidth = 8;
          boton.archeight = 8;
        }
        
        setOpaque( false);
        paintLeches( g);
        
        ButtonModel mod = getModel();
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint grad = null;
        
        
        if ( mod.isPressed() || mod.isSelected() ) {
          grad = new GradientPaint( 0,0, Utils.getSombra(), 
                                    0,getHeight(), Utils.getBrillo());
          g2D.setPaint( grad);
          g2D.fill( boton);
        }
        else {
          grad = new GradientPaint( 0,0, Utils.getBrillo(), 
                                    0,getHeight(), Utils.getSombra());
          g2D.setPaint( grad);
          g2D.fill( boton);
        }
        
        g2D.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
      }
      
      if ( this.isEnabled() && !canijo ) {
        if ( focus ) {
          Utils.paintFocus( g, 1, 1, getWidth()-2, getHeight()-2, 4,4, 3, LookAndFeel.getFocusColor());
        }
        else if ( rollover ) {
          Utils.paintFocus( g, 1, 1, getWidth()-2, getHeight()-2, 4,4, 3, Utils.getColorAlfa( LookAndFeel.getFocusColor(), 150));
        }
      }
    }
    
    protected void paintLeches( Graphics g ) {
      boolean leftToRight = comboBox.getComponentOrientation().isLeftToRight();

      if (ui != null) {
        Graphics scratchGraphics = (g == null) ? null : g.create();
        try {
          ui.update( scratchGraphics, this);
        }
        finally {
          scratchGraphics.dispose();
        }
      }

      Insets insets = getInsets();

      int width = getWidth() - (insets.left + insets.right);
      int height = getHeight() - (insets.top + insets.bottom);

      if ( height <= 0 || width <= 0 ) {
        return;
      }

      int left = insets.left;
      int top = insets.top;
      int right = left + (width - 1);
      int bottom = top + (height - 1);

      int iconWidth = 0;
      int iconLeft = (leftToRight) ? right : left;

      if ( comboIcon != null ) {
        iconWidth = comboIcon.getIconWidth();
        int iconHeight = comboIcon.getIconHeight();
        int iconTop = 0;

        if ( iconOnly ) {
          iconLeft = (getWidth() / 2) - (iconWidth / 2);
          iconTop = (getHeight() / 2) - (iconHeight / 2);
        }
        else {
          if (leftToRight) {
            iconLeft = (left + (width - 1)) - iconWidth;
          }
          else {
            iconLeft = left;
          }
          iconTop = (top + ((bottom - top) / 2)) - (iconHeight / 2);
        }

        comboIcon.paintIcon( this, g, iconLeft, iconTop );

        if ( !iconOnly ) {
          g.setColor( Utils.getSombra());
          g.drawLine( iconLeft-5,6, iconLeft-5,getHeight()-6);

          g.setColor( Utils.getBrillo());
          g.drawLine( iconLeft-4,6, iconLeft-4,getHeight()-6);
        }

      }

      if ( !iconOnly && comboBox != null ) {
        ListCellRenderer renderer = comboBox.getRenderer();
        Component c;
        boolean renderPressed = getModel().isPressed();
        c = renderer.getListCellRendererComponent( listBox, comboBox.getSelectedItem(), -1, renderPressed, false);
        c.setFont(rendererPane.getFont());

        if ( model.isArmed() && model.isPressed() ) {
          c.setForeground( comboBox.getForeground());
          c.setBackground( getBackground());
        }
        else if ( !comboBox.isEnabled() ) {
          if ( isOpaque() ) {
            c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
          }
          c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
        }
        else {
          c.setForeground( comboBox.getForeground());
          c.setBackground( comboBox.getBackground());
        }

        int cWidth = width - (insets.right + iconWidth);

        boolean shouldValidate = false;
        if (c instanceof JPanel)  {
          shouldValidate = true;
        }

        if (leftToRight) {
          rendererPane.paintComponent( g, c, this, left, top, cWidth, height, shouldValidate );
        }
        else {
          rendererPane.paintComponent( g, c, this, left + iconWidth, top, cWidth, height, shouldValidate );
        }
      }
    }
  }
  
  //////////////////////////
  
  public class MiML extends MouseAdapter implements FocusListener {
    protected void refresh() {
      if ( comboBox != null && comboBox.getParent() != null ) {
        comboBox.getParent().repaint( comboBox.getX()-5, comboBox.getY()-5, 
                                      comboBox.getWidth()+10, comboBox.getHeight()+10);
      }
    }
    
    public void mouseExited( MouseEvent e) {
      rollover = false;
      refresh();
    }
    
    public void mouseEntered( MouseEvent e) {
      rollover = true;
      refresh();
    }
    
    public void focusGained( FocusEvent e) {
      focus = true;
      refresh();
    }
      
    public void focusLost( FocusEvent e) {
      focus = false;
      refresh();
    }
  }
}