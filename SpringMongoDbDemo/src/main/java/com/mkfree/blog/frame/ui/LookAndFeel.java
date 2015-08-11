package com.mkfree.blog.frame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.Kernel;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

public class LookAndFeel extends MetalLookAndFeel{
	String fichTheme = "";
	protected static MetalTheme theme;
	public LookAndFeel(){
		super();
		Theme th = new Theme();
		
		String f1,f2,f3,b1,b2,b3,selection,background,w,b,opMenu,opFrame;
		
		String fiche=null;
		
		try{
			fiche = System.getProperty("theme.themeFile");
			fiche = (fiche==null ? "themeFile.theme":fiche);
			Properties props = new Properties();
			InputStream res = null;
			try{
				res = new FileInputStream(fiche);
			}catch(Exception ex){
				fiche = (fiche.equals("themeFile.theme")?"/"+fiche:fiche);
				res = this.getClass().getResourceAsStream(fiche);
			}
			props.load(res);
			selection = props.getProperty("theme.selection");
			background = props.getProperty("theme.background");
			
			f1 = props.getProperty("theme.f1");
			f2 = props.getProperty("theme.f2");
			f3 = props.getProperty("theme.f3");
			
			b1 = props.getProperty("theme.b1");
			b2 = props.getProperty("theme.b2");
			b3 = props.getProperty("theme.b3");
			
			w = props.getProperty("theme.w");
			b = props.getProperty("theme.b");
			
			opMenu = props.getProperty("theme.menuOpacity");
			opFrame = props.getProperty("theme.frameOpacity");
			
			th = Utils.iniCustomColors(th, selection, background, f1, f2, f3, b1, b2, b3, w, b, opMenu, opFrame);
			
			fichTheme = fiche;
		}catch(Exception e){
			th = new Theme();
		}
		try{
			selection = System.getProperty("theme.selection");
			background = System.getProperty("theme.background");
			
			f1 = System.getProperty("theme.f1");
			f2 = System.getProperty("theme.f2");
			f3 = System.getProperty("theme.f3");
			
			b1 = System.getProperty("theme.b1");
			b2 = System.getProperty("theme.b2");
			b3 = System.getProperty("theme.b3");
			
			w = System.getProperty( "theme.w");
			b = System.getProperty( "theme.b");
		      
			opMenu = System.getProperty( "theme.menuOpacity");
			opFrame = System.getProperty(  "theme.frameOpacity");
			
			th = Utils.iniCustomColors(th, selection, background, f1, f2, f3, b1, b2, b3, w, b, opMenu, opFrame);
		}catch(Exception e){
			if(fichTheme.length()==0){
				th = new Theme();
			}
		}
		setCurrentTheme(th);
		
		float[] elements = new float[Utils.MATRIX_FAT*Utils.MATRIX_FAT];
		for (int i=0; i<elements.length; i++){
			elements[i]=0.1f;
		}
		int mid = Utils.MATRIX_FAT/2+1;
		elements[mid*mid]=.2f;
		
		Utils.kernelFat = new Kernel(Utils.MATRIX_FAT,Utils.MATRIX_FAT,elements);
		
		elements = new float[Utils.MATRIX_THIN*Utils.MATRIX_THIN];
		for(int i =0; i<elements.length;i++){
			elements[i] = 0.1f;
		}
		mid = Utils.MATRIX_THIN/2+1;
		elements[mid*mid] = .2f;
		
		Utils.kernelThin = new Kernel(Utils.MATRIX_THIN,Utils.MATRIX_THIN,elements);
	}
	public static void setCurrentTheme( MetalTheme t) {
	    MetalLookAndFeel.setCurrentTheme( t);	    
	    theme = t;
	    Utils.rollColor = null;
	}
	@Override
	protected void initComponentDefaults(UIDefaults table) {
		super.initComponentDefaults(table);
		try {
		      ColorUIResource cFore = (ColorUIResource)table.get( "MenuItem.disabledForeground");
		      ColorUIResource cBack = (ColorUIResource)table.get( "MenuItem.foreground");
		      
		      ColorUIResource col = Utils.getColorTercio( cBack, cFore);
		      table.put(  "MenuItem.disabledForeground", col);
		      table.put(  "Label.disabledForeground", col);
		      table.put(  "CheckBoxMenuItem.disabledForeground", col);
		      table.put(  "Menu.disabledForeground", col);
		      table.put(  "RadioButtonMenuItem.disabledForeground", col);
		      table.put(  "ComboBox.disabledForeground", col);
		      table.put(  "Button.disabledText", col);
		      table.put(  "ToggleButton.disabledText", col);
		      table.put(  "CheckBox.disabledText", col);
		      table.put(  "RadioButton.disabledText", col);
		      
		      ColorUIResource col2 = Utils.getColorTercio( LookAndFeel.getWhite(),
		                                                         (Color)table.get( "TextField.inactiveBackground"));
		      table.put( "TextField.inactiveBackground", col2);
		    }
		    catch ( Exception ex) {
		      ex.printStackTrace();
		    }
		    table.put( "MenuBar.border", Borders.getMenuBarBorder());

		    Font fontMenu = ((Font)table.get( "Menu.font")).deriveFont( Font.BOLD);
		    table.put( "MenuItem.acceleratorFont", fontMenu);
		    table.put( "RadioButtonMenuItem.acceleratorFont", fontMenu);
		    table.put( "CheckBoxMenuItem.acceleratorFont", fontMenu);
		    
		    ColorUIResource colAcce = Utils.getColorTercio( (ColorUIResource)table.get( "MenuItem.foreground"),
		                                                          (ColorUIResource)table.get( "MenuItem.acceleratorForeground")
		                                                        );

		    table.put( "MenuItem.acceleratorForeground", colAcce);
		    table.put( "RadioButtonMenuItem.acceleratorForeground", colAcce);
		    table.put( "CheckBoxMenuItem.acceleratorForeground", colAcce);
		    
		    table.put( "BorderPopupMenu.SombraBajIcon", Utils.loadRes("/com/mkfree/frame/icons/SombraMenuBajo.png"));
		    table.put( "BorderPopupMenu.SombraDerIcon", Utils.loadRes( "/com/mkfree/frame/icons/SombraMenuDer.png"));
		    table.put( "BorderPopupMenu.SombraEsqIcon", Utils.loadRes( "/com/mkfree/frame/icons/SombraMenuEsq.png"));
		    table.put( "BorderPopupMenu.SombraUpIcon", Utils.loadRes( "/com/mkfree/frame/icons/SombraMenuUp.png"));
		    table.put( "BorderPopupMenu.SombraIzqIcon", Utils.loadRes( "/com/mkfree/frame/icons/SombraMenuIzq.png"));
		    
		    table.put( "Tree.collapsedIcon", IconFactory.getTreeCollapsedIcon());
		    table.put( "Tree.expandedIcon", IconFactory.getTreeExpandedIcon());
		    table.put( "Tree.closedIcon", Utils.loadRes( "/com/mkfree/frame/icons/TreeDirCerrado.png"));
		    table.put( "Tree.openIcon", Utils.loadRes( "/com/mkfree/frame/icons/TreeDirAbierto.png"));
		    table.put( "Tree.leafIcon", Utils.loadRes( "/com/mkfree/frame/icons/TreeFicheroIcon.png"));
		    table.put( "Tree.PelotillaIcon", Utils.loadRes( "/com/mkfree/frame/icons/TreePelotilla.png"));
		    
		    table.put( "FileView.directoryIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogDirCerrado.png"));
		    table.put( "FileView.fileIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogFicheroIcon.png"));
		    table.put( "FileView.floppyDriveIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogFloppyIcon.png"));
		    table.put( "FileView.hardDriveIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogHDIcon.png"));
		    table.put( "FileChooser.newFolderIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogNewDir.png"));
		    table.put( "FileChooser.homeFolderIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogHome.png"));
		    table.put( "FileChooser.upFolderIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogDirParriba.png"));
		    table.put( "FileChooser.detailsViewIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogDetails.png"));
		    table.put( "FileChooser.listViewIcon", Utils.loadRes( "/com/mkfree/frame/icons/DialogList.png"));
		    
		    table.put( "CheckBoxMenuItem.checkIcon", IconFactory.getCheckBoxMenuItemIcon());
		    table.put( "RadioButtonMenuItem.checkIcon", IconFactory.getRadioButtonMenuItemIcon());
		    
		    table.put( "ComboBox.flechaIcon", Utils.loadRes( "/com/mkfree/frame/icons/ComboButtonDown.png"));
		    table.put( "ComboBox.buttonDownIcon", IconFactory.getComboFlechaIcon());
		    
		    table.put( "Menu.checkIcon", IconFactory.getBandaMenuItemIcon());
		    table.put( "MenuItem.checkIcon", IconFactory.getBandaMenuItemIcon());
		    table.put( "MenuCheckBox.iconBase", Utils.loadRes( "/com/mkfree/frame/icons/MenuCheckBoxBase.png"));
		    table.put( "MenuCheckBox.iconTick", Utils.loadRes( "/com/mkfree/frame/icons/MenuCheckBoxTick.png"));
		    table.put( "MenuRadioButton.iconBase", Utils.loadRes( "/com/mkfree/frame/icons/MenuRadioBase.png"));
		    table.put( "MenuRadioButton.iconTick", Utils.loadRes( "/com/mkfree/frame/icons/MenuRadioTick.png"));
		    table.put( "CheckBox.iconBase", Utils.loadRes( "/com/mkfree/frame/icons/CheckBoxBase.png"));
		    table.put( "CheckBox.iconTick", Utils.loadRes( "/com/mkfree/frame/icons/CheckBoxTick.png"));
		    table.put( "RadioButton.iconBase", Utils.loadRes( "/com/mkfree/frame/icons/RadioButtonBase.png"));
		    table.put( "RadioButton.iconTick", Utils.loadRes( "/com/mkfree/frame/icons/RadioButtonTick.png"));
		    
		    // Iconos para los borders generales
		    table.put( "BordeGenSup", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenSup.png"));
		    table.put( "BordeGenSupDer", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenSupDer.png"));
		    table.put( "BordeGenDer", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenDer.png"));
		    table.put( "BordeGenInfDer", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenInfDer.png"));
		    table.put( "BordeGenInf", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenInf.png"));
		    table.put( "BordeGenInfIzq", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenInfIzq.png"));
		    table.put( "BordeGenIzq", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenIzq.png"));
		    table.put( "BordeGenSupIzq", Utils.loadRes( "/com/mkfree/frame/icons/BordeGenSupIzq.png"));
		    
		    // Bordes generales
		    table.put( "List.border", Borders.getGenBorder());
		    table.put( "ScrollPane.viewportBorder", Borders.getGenBorder());
		    table.put( "Menu.border", Borders.getGenMenuBorder());
		    table.put( "ToolBar.border", Borders.getToolBarBorder());
		    table.put( "TextField.border", Borders.getTextFieldBorder());
		    table.put( "TextArea.border", Borders.getTextFieldBorder());
		    table.put( "FormattedTextField.border", Borders.getTextFieldBorder());
		    table.put( "PasswordField.border", Borders.getTextFieldBorder());
		    table.put( "ToolTip.border", Borders.getToolTipBorder());
		     
		    table.put( "ScrollPane.border", Borders.getScrollPaneBorder());
		    

		    ColorUIResource col2 = Utils.getColorTercio( LookAndFeel.getFocusColor(),
		                                                      (Color)table.get( "TextField.inactiveBackground"));
		    table.put( "ToolTip.background", col2);
		    table.put( "ToolTip.font", ((Font)table.get( "Menu.font")));
		    
		    table.put( "Spinner.editorBorderPainted", new Boolean( false));
		    table.put( "Spinner.border", Borders.getTextFieldBorder());
		    table.put( "Spinner.arrowButtonBorder", BorderFactory.createEmptyBorder());
		    table.put( "Spinner.nextIcon", IconFactory.getSpinnerNextIcon());
		    table.put( "Spinner.previousIcon", IconFactory.getSpinnerPreviousIcon());
		    
		    table.put( "OptionPane.errorIcon", Utils.loadRes( "/com/mkfree/frame/icons/Error.png"));
		    table.put( "OptionPane.informationIcon", Utils.loadRes( "/com/mkfree/frame/icons/Inform.png"));
		    table.put( "OptionPane.warningIcon", Utils.loadRes( "/com/mkfree/frame/icons/Warn.png"));
		    table.put( "OptionPane.questionIcon", Utils.loadRes( "/com/mkfree/frame/icons/Question.png"));
		    
		    table.put( "Slider.horizontalThumbIcon", IconFactory.getSliderHorizontalIcon());
		    table.put( "Slider.verticalThumbIcon", IconFactory.getSliderVerticalIcon());
		    table.put( "Slider.horizontalThumbIconImage", Utils.loadRes( "/com/mkfree/frame/icons/HorizontalThumbIconImage.png"));
		    table.put( "Slider.verticalThumbIconImage", Utils.loadRes( "/com/mkfree/frame/icons/VerticalThumbIconImage.png"));
		    
		    table.put( "ScrollBar.horizontalThumbIconImage", Utils.loadRes( "/com/mkfree/frame/icons/HorizontalScrollIconImage.png"));
		    table.put( "ScrollBar.verticalThumbIconImage", Utils.loadRes( "/com/mkfree/frame/icons/VerticalScrollIconImage.png"));
		    table.put( "ScrollBar.northButtonIconImage", Utils.loadRes( "/com/mkfree/frame/icons/ScrollBarNorthButtonIconImage.png"));
		    table.put( "ScrollBar.southButtonIconImage", Utils.loadRes( "/com/mkfree/frame/icons/ScrollBarSouthButtonIconImage.png"));
		    table.put( "ScrollBar.eastButtonIconImage", Utils.loadRes( "/com/mkfree/frame/icons/ScrollBarEastButtonIconImage.png"));
		    table.put( "ScrollBar.westButtonIconImage", Utils.loadRes( "/com/mkfree/frame/icons/ScrollBarWestButtonIconImage.png"));
		    table.put( "ScrollBar.northButtonIcon", IconFactory.getScrollBarNorthButtonIcon());
		    table.put( "ScrollBar.southButtonIcon", IconFactory.getScrollBarSouthButtonIcon());
		    table.put( "ScrollBar.eastButtonIcon", IconFactory.getScrollBarEastButtonIcon());
		    table.put( "ScrollBar.westButtonIcon", IconFactory.getScrollBarWestButtonIcon());
		    
		    table.put( "Button.margin", new InsetsUIResource( 5,14, 5,14));
		    table.put( "ToggleButton.margin", new InsetsUIResource( 5,14, 5,14));
		    
		    table.put( "Desktop.background", table.get( "MenuItem.background"));
		    table.put( "InternalFrame.border", Borders.getInternalFrameBorder());
		    
		    table.put( "InternalFrame.NimCloseIcon", Utils.loadRes( "/com/mkfree/frame/icons/FrameClose.png"));
		    table.put( "InternalFrame.NimCloseIconRoll", Utils.loadRes( "/com/mkfree/frame/icons/FrameCloseRoll.png"));
		    table.put( "InternalFrame.NimCloseIconPush", Utils.loadRes( "/com/mkfree/frame/icons/FrameClosePush.png"));
		    
		    table.put( "InternalFrame.NimMaxIcon", Utils.loadRes( "/com/mkfree/frame/icons/FrameMaximiza.png"));
		    table.put( "InternalFrame.NimMaxIconRoll", Utils.loadRes( "/com/mkfree/frame/icons/FrameMaximizaRoll.png"));
		    table.put( "InternalFrame.NimMaxIconPush", Utils.loadRes( "/com/mkfree/frame/icons/FrameMaximizaPush.png"));
		    
		    table.put( "InternalFrame.NimMinIcon", Utils.loadRes( "/com/mkfree/frame/icons/FrameMinimiza.png"));
		    table.put( "InternalFrame.NimMinIconRoll", Utils.loadRes( "/com/mkfree/frame/icons/FrameMinimizaRoll.png"));
		    table.put( "InternalFrame.NimMinIconPush", Utils.loadRes( "/com/mkfree/frame/icons/FrameMinimizaPush.png"));
		    
		    table.put( "InternalFrame.NimResizeIcon", Utils.loadRes( "/com/mkfree/frame/icons/FrameResize.png"));
		    table.put( "InternalFrame.NimResizeIconRoll", Utils.loadRes( "/com/mkfree/frame/icons/FrameResizeRoll.png"));
		    table.put( "InternalFrame.NimResizeIconPush", Utils.loadRes( "/com/mkfree/frame/icons/FrameResizePush.png"));
		    
		    table.put( "InternalFrame.closeIcon", IconFactory.getFrameCloseIcon());
		    table.put( "InternalFrame.minimizeIcon", IconFactory.getFrameAltMaximizeIcon());
		    table.put( "InternalFrame.maximizeIcon", IconFactory.getFrameMaxIcon());
		    table.put( "InternalFrame.iconifyIcon", IconFactory.getFrameMinIcon());
		    table.put( "InternalFrame.icon", Utils.loadRes( "/com/mkfree/frame/icons/Frame.png"));
		    table.put( "NimRODInternalFrameIconLit.width", new Integer( 20));
		    table.put( "NimRODInternalFrameIconLit.height", new Integer( 20));
		    
		    Font fontIcon = ((Font)table.get( "InternalFrame.titleFont")).deriveFont( Font.BOLD);
		    table.put( "DesktopIcon.font", fontIcon);
		    table.put( "NimRODDesktopIcon.width", new Integer( 80));
		    table.put( "NimRODDesktopIcon.height", new Integer( 60));
		    table.put( "NimRODDesktopIconBig.width", new Integer( 48));
		    table.put( "NimRODDesktopIconBig.height", new Integer( 48));
		    
		    table.put( "InternalFrame.activeTitleBackground", getMenuSelectedBackground());
		    table.put( "InternalFrame.activeTitleGradient", getMenuSelectedBackground().darker());
		    table.put( "InternalFrame.inactiveTitleBackground", getMenuBackground().brighter());
		    table.put( "InternalFrame.inactiveTitleGradient", getMenuBackground().darker());
	}
	@Override
	protected void initSystemColorDefaults(UIDefaults arg0) {
		super.initSystemColorDefaults( arg0);

		arg0.put( "textHighlight", getMenuSelectedBackground());
	    
		arg0.put( "textInactiveText", getInactiveSystemTextColor().darker());
	}
	 public boolean isNativeLookAndFeel() {
		    return false;
	 }

	 public boolean isSupportedLookAndFeel() {
	    return true;
	 }
	 public boolean getSupportsWindowDecorations() {
		    return false;
	 }
	@Override
	protected void initClassDefaults(UIDefaults table) {
		// TODO Auto-generated method stub
		super.initClassDefaults(table);
		table.put( "ButtonUI", "com.mkfree.blog.frame.ui.ButtonUI");
		table.put( "ComboBoxUI", "com.mkfree.blog.frame.ui.ComboBoxUI");
		table.put( "SplitPaneUI", "com.mkfree.blog.frame.ui.SplitPaneUI");
	}
}
