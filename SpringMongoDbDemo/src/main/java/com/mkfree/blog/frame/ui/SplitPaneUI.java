package com.mkfree.blog.frame.ui;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.metal.MetalSplitPaneUI;

public class SplitPaneUI extends MetalSplitPaneUI {

  public static ComponentUI createUI( JComponent c) {
	  return new SplitPaneUI();
  }

  public BasicSplitPaneDivider createDefaultDivider() {
	  return new SplitPaneDivider( this);
  }
}