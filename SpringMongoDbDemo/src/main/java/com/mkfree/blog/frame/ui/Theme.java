package com.mkfree.blog.frame.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

public class Theme extends DefaultMetalTheme{
	public static final int DEFAULT_MENU_OPACITY	=	195;//默认菜单透明度
	public static final int DEFAULT_FRAME_OPACITY	=	180;//默认框架透明度
	
	//主调颜色
	private ColorUIResource primary1 = new ColorUIResource(0,133,235);
	private ColorUIResource primary2 = new ColorUIResource(0,143,245);
	private ColorUIResource primary3 = new ColorUIResource(0,153,255);
	//二级颜色
	private ColorUIResource secondary1 = new ColorUIResource(220,220,220);
	private ColorUIResource secondary2 = new ColorUIResource(230,230,230);
	private ColorUIResource secondary3 = new ColorUIResource(240,240,240);
	private ColorUIResource black = new ColorUIResource( 0, 0, 0);	  
	private ColorUIResource white = new ColorUIResource( 250, 250, 250);
	
	//字体
	private FontUIResource font = new FontUIResource( "SansSerif", Font.PLAIN, 12);
	private FontUIResource boldFont = new FontUIResource( "SansSerif", Font.BOLD, 12);
	
	//不透明菜单/框架值
	private int opacidaMenu = DEFAULT_MENU_OPACITY;
	private int opacidaFrame = DEFAULT_FRAME_OPACITY;
	
	//构造函数
	public Theme(){
		super();
	}
	public Theme(Color base){
		super();
		setPrimary(base);
	}
	//设置前景色
	public void setPrimary(Color color){
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		this.primary1 = new ColorUIResource( new Color( (r>20 ? r-20 : 0), (g>20 ? g-20 : 0), (b>20 ? b-20 : 0)));
		this.primary2 = new ColorUIResource( new Color( (r>10 ? r-10 : 0), (g>10 ? g-10 : 0), (b>10 ? b-10 : 0)));
	    this.primary3 = new ColorUIResource(color);
	}
	//设置背景色
	public void setSecondary(Color color){
		int r = color.getRed();
	    int g = color.getGreen();
	    int b = color.getBlue();
	    
	    this.secondary1 = new ColorUIResource( new Color( (r>20 ? r-20 : 0), (g>20 ? g-20 : 0), (b>20 ? b-20 : 0)));
	    this.secondary2 = new ColorUIResource( new Color( (r>10 ? r-10 : 0), (g>10 ? g-10 : 0), (b>10 ? b-10 : 0)));
	    this.secondary3 = new ColorUIResource( color);
	}
	//分别设置前景色和背景色
	public void setPrimary1( Color col) { 
		this.primary1 = new ColorUIResource( col); 
	}
	public void setPrimary2( Color col) { 
	    this.primary2 = new ColorUIResource( col); 
	}
	public void setPrimary3( Color col) { 
	    this.primary3 = new ColorUIResource( col); 
	}
	
	public void setSecondary1( Color col) { 
	    this.secondary1 = new ColorUIResource( col); 
	}
	public void setSecondary2( Color col) { 
	    this.secondary2 = new ColorUIResource( col); 
	}
	public void setSecondary3( Color col) { 
	    this.secondary3 = new ColorUIResource( col); 
	}
	public void setBlack( Color col) { 
	    black = new ColorUIResource( col); 
	}
	public void setWhite( Color col) { 
	    white = new ColorUIResource( col); 
	}
	//设置透明度、获取透明度
	public void setOpacity( int val) {
	    setMenuOpacity( val);
	}
  
	public int getOpacity() {
		return getMenuOpacity();
	}
  
	public void setMenuOpacity( int val) {
		opacidaMenu = val;
	}
  
	public int getMenuOpacity() {
		return opacidaMenu;
	}
  
	public void setFrameOpacity( int val) {
		opacidaFrame = val;
	}
  
	public int getFrameOpacity() {
		return opacidaFrame;
	}
	//获得字体
	public FontUIResource	getControlTextFont() {
	  	return font;
	}
	  
	public FontUIResource	getMenuTextFont() {
	  	return font;
	}
	  
	public FontUIResource getSubTextFont() {
	  	return font;
	} 
	           	
	public FontUIResource	getSystemTextFont()  {
	  	return boldFont;
	}
	           	
	public FontUIResource	getUserTextFont()  {
	  	return font;
	}
	           	
	public FontUIResource	getWindowTitleFont() {
	  	return boldFont;
	}
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		
		buf.append("theme.f1"+encode(primary1)+"\n");
		buf.append("theme.f2"+encode(primary2)+"\n");
		buf.append("theme.f3"+encode(primary3)+"\n");
		
		buf.append( "theme.w=" + encode( white) + "\n");
		buf.append( "theme.b=" + encode( black) + "\n");
		buf.append( "theme.menuOpacity=" + opacidaMenu + "\n");
		buf.append( "theme.frameOpacity=" + opacidaFrame + "\n");
		
		return buf.toString();		
	}
	//转换SEXRGB
	protected String encode( Color col) {
	    String r = Integer.toHexString( col.getRed()).toUpperCase();
	    String g = Integer.toHexString( col.getGreen()).toUpperCase();
	    String b = Integer.toHexString( col.getBlue()).toUpperCase();
	    
	    
	    return "#" + ( r.length() == 1 ? "0" + r : r )
	               + ( g.length() == 1 ? "0" + g : g )
	               + ( b.length() == 1 ? "0" + b : b );
	 }
	@Override
	protected ColorUIResource getPrimary1() {
		// TODO Auto-generated method stub
		return primary1;
	}
	@Override
	protected ColorUIResource getPrimary2() {
		// TODO Auto-generated method stub
		return primary2;
	}
	@Override
	protected ColorUIResource getPrimary3() {
		// TODO Auto-generated method stub
		return primary3;
	}
	@Override
	protected ColorUIResource getSecondary1() {
		// TODO Auto-generated method stub
		return secondary1;
	}
	@Override
	protected ColorUIResource getSecondary2() {
		// TODO Auto-generated method stub
		return secondary2;
	}
	@Override
	protected ColorUIResource getSecondary3() {
		// TODO Auto-generated method stub
		return secondary3;
	}
	
}
