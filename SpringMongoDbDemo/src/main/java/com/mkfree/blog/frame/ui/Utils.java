package com.mkfree.blog.frame.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class Utils {
	static final int MATRIX_FAT = 5;//富矩阵
	static final int MATRIX_THIN = 3;//瘦矩阵
	static Kernel kernelFat;
	static Kernel kernelThin;
	protected static Color rollColor;
	static Theme iniCustomColors(Theme th,String selection,String background,String f1,String f2,String f3,String b1,String b2,String b3,String w,String b,String opMenu,String opFrame){
		if(selection != null){
			th.setPrimary(Color.decode(selection));
		}
		if(background != null){
			th.setSecondary(Color.decode(background));
		}
		if(f1 != null){th.setPrimary1(Color.decode(f1));};
		if(f2 != null){th.setPrimary2(Color.decode(f2));};
		if(f3 != null){th.setPrimary3(Color.decode(f3));};
		
		if(b1 != null){th.setSecondary1(Color.decode(b1));};
		if(b2 != null){th.setSecondary2(Color.decode(b2));};
		if(b3 != null){th.setSecondary3(Color.decode(b3));};
		
		if(w != null){th.setWhite(Color.decode(w));};
		if(b != null){th.setBlack(Color.decode(b));};
		
		if(opMenu != null){ th.setMenuOpacity(Integer.parseInt(opMenu));};
		if(opFrame != null){ th.setFrameOpacity(Integer.parseInt(opFrame));};
		
		return th;
	}
	static ColorUIResource getColorTercio( Color a, Color b) {
	    return new ColorUIResource( propInt( a.getRed(), b.getRed(), 3),
	                                propInt( a.getGreen(), b.getGreen(), 3),
	                                propInt( a.getBlue(), b.getBlue(), 3));
	}
	private static int propInt( int a, int b, int prop) {
	    return b + ((a-b) / prop);
	}
	static byte[] readStream( InputStream input) throws IOException {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		int read;
	    byte[] buffer = new byte[256];
	    
	    while ( (read = input.read( buffer, 0, 256)) != -1 ) {
	      bytes.write( buffer, 0, read);
	    }
	    
	    return bytes.toByteArray();
	}
	static ImageIcon loadRes( String fich) {
	    try {
	      return new ImageIcon( Toolkit.getDefaultToolkit().createImage( readStream(LookAndFeel.class.getResourceAsStream(fich))));
	    }
	    catch ( Exception ex) {
	      ex.printStackTrace();
	      System.out.println( "not find " + fich);
	      return null;
	    }
	}
	static Color getSombra() {
	    return getColorAlfa( getColorTercio( LookAndFeel.getControlDarkShadow(), Color.black),
	                         64);
	}
	static Color getColorAlfa( Color col, int alfa) {
	    return new Color( col.getRed(), col.getGreen(), col.getBlue(), alfa);
	}
	static Color getBrillo() {
	    return getColorAlfa( getColorTercio( LookAndFeel.getControlHighlight(), Color.white),
	                         64);
	}
	static float getFrameOpacityFloat() {
	    return getFrameOpacity() / 255f;
	}
	static int getFrameOpacity() {
	    try {
	      Theme th = (Theme)LookAndFeel.theme;
	      return th.getFrameOpacity();
	    }
	    catch ( Throwable ex) {
	      return Theme.DEFAULT_FRAME_OPACITY;
	    }
	}
	 static Color getRolloverColor() {
		    if ( rollColor == null ) {
		      rollColor = getColorAlfa( UIManager.getColor( "Button.focus"), 40);
		    }
		    
		    return rollColor;
	 }
	 static void paintFocus( Graphics g, int x, int y, int width, int height, int r1, int r2, Color color) {
		    paintFocus( g, x, y, width, height, r1, r2, 2.0f, color);
	 }
	 static void paintFocus( Graphics g, int x, int y, int width, int height, int r1, int r2, float grosor, Color color) {
		    Graphics2D g2d = (Graphics2D)g;
		    g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    Stroke oldStroke = g2d.getStroke();
		    
		    g2d.setColor( color);
		    g2d.setStroke( new BasicStroke( grosor));
		    if ( r1 == 0 && r2 == 0 ) {
		      g.drawRect( x, y, width, height);
		    }
		    else {
		      g.drawRoundRect( x,y, width-1,height-1, r1,r2);
		    }
		    
		    g2d.setStroke( oldStroke);
		    
		    g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
	 }
		  
}