package com.mkfree.blog.frame.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

public class Main extends JFrame {
	public static LookAndFeel feel;
	public static Theme theme;
	private JToolBar toolBar;
	private JSplitPane pView;
	public JMenuBar menuBar;
	Main(){
		super("demo");
		
		menuBar = new JMenuBar();
		InsertMenuItem();
		//setJMenuBar(menuBar);
		
		addToolbar();
		addPanel();
		
		getContentPane().add( toolBar, BorderLayout.PAGE_START);
		getContentPane().add( pView, BorderLayout.CENTER);
		 
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
	    
	    setSize( 1024,768);
	    setVisible( true);
	    setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	private void InsertMenuItem(){
		JMenu menuFiles = new JMenu("文件");
		menuBar.add(menuFiles);
		
		JMenuItem menuItem = new JMenuItem("新建");
		menuFiles.add(menuItem);
		
		menuItem = new JMenuItem("打开");
		menuFiles.add(menuItem);
		
		JMenu menuEdit = new JMenu("编辑");
		menuBar.add(menuEdit);
		
		menuItem = new JMenuItem("剪切");
		menuEdit.add(menuItem);
		
		menuItem = new JMenuItem("粘贴");
		menuEdit.add(menuItem);
	}
	private void addToolbar(){
		class DemoAction  extends AbstractAction {
			public DemoAction(String str) {
				super.putValue(Action.NAME,
                           str);
			}

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog( (Component) e.getSource(),
                                          super.getValue(Action.NAME));
			}
		}
		toolBar = new JToolBar();
	    
		Image image;
		try {
			JPopupMenu popup = new JPopupMenu("PopupMenu");
	        popup.add(new DemoAction("新增费用"));
	        popup.add(new DemoAction("银行联网代收"));
	        popup.add(new DemoAction("数据导出"));
			image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/021.png")));
			image = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(image);
			JPopupButton b0 = new JPopupButton(
		            JPopupButton.TYPE_WITH_RIGHT_TOGGLE,
		            "", icon,popup);
			b0.setPreferredSize(new Dimension(60,40));
			b0.setSize(new Dimension(60, 40));
			b0.setMaximumSize(new Dimension(60,40));
			image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/005.png")));
			image = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		    JButton b1 = new JButton(icon);
		    
		    image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/017.png")));
			image = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		    JButton b2 = new JButton(icon);

		    image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/049.png")));
			image = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		    JButton b3 = new JButton(icon);
		    
		    image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/050.png")));
			image = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
		    JButton b4 = new JButton(icon);
		    
		    toolBar.addSeparator();
		    toolBar.add(b0);
		    toolBar.addSeparator();
		    toolBar.add( b1);
		    toolBar.addSeparator();
		    toolBar.add( b2);
		    toolBar.addSeparator();
		    toolBar.add( b3);
		    toolBar.addSeparator();
		    toolBar.add( b4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void addPanel(){
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("福州营业收费系统");
		DefaultMutableTreeNode next = new DefaultMutableTreeNode( "费用组成");
		root.add(next);
		//DefaultMutableTreeNode node1 = new DefaultMutableTreeNode( "费用组成");
		//node1.
		next.add( new DefaultMutableTreeNode( "费用组成"));
		next.add( new DefaultMutableTreeNode( "价格管理"));
	    
	    next = new DefaultMutableTreeNode( "银行联网代收");
	    root.add( next);
	    
	    next.add( new DefaultMutableTreeNode( "银行管理"));
	    next.add( new DefaultMutableTreeNode( "银行对账"));
	    
	    next = new DefaultMutableTreeNode( "数据导出");
	    root.add( next);
	    
	    next.add( new DefaultMutableTreeNode( "帐务数据导出"));
	    next.add( new DefaultMutableTreeNode( "用户数据导出"));
	    
	    JTree arb = new JTree( root);
	    
	    JScrollPane treeView = new JScrollPane( arb); 
	    JPanel panel = new JPanel(new BorderLayout( 0,13));
	    JLabel title = new JLabel("福州营业收费系统-新增功能工具");
	    title.setForeground(Color.blue);
	    JPanel titlepanel = new JPanel();
	    titlepanel.add(title);
	    titlepanel.setPreferredSize(new Dimension(200,20));
	    panel.add(titlepanel,BorderLayout.NORTH);
	    panel.add(treeView,BorderLayout.CENTER);
	    //右侧
	    JPanel scrPan = new JPanel();
	    scrPan.setLayout(null);
	    ImageIcon icon;
		try {
			//1
			Image image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/008.png")));
			image = image.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH); 
			icon = new ImageIcon(image);
			JButton b1 = new JButton(icon);
			b1.setBounds(10, 10, 50, 50);
			b1.setFocusable(false);
			scrPan.add(b1,null);
			JLabel title1 = new JLabel("新增费用");
			title1.setFont(new Font("宋体",1,14));
			title1.setForeground(Color.blue);
			title1.setBounds(70, 10, 100, 25);
			scrPan.add(title1,null);
			JLabel context1 = new JLabel("可以满足根据费用组成的灵活性要求，自由添加费用组成");
			context1.setBounds(70, 35, 500, 25);
			scrPan.add(context1,null);
			
			//2
			image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/007.png")));
			image = image.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH); 
			icon = new ImageIcon(image);
			JButton b2 = new JButton(icon);
			b2.setBounds(10, 80, 50, 50);
			b2.setFocusable(false);
			scrPan.add(b2,null);
			title1 = new JLabel("银行联网代收");
			title1.setFont(new Font("宋体",1,14));
			title1.setForeground(Color.blue);
			title1.setBounds(70, 80, 100, 25);
			scrPan.add(title1,null);
			context1 = new JLabel("实现与银行，电信实施收费");
			context1.setBounds(70, 105, 500, 25);
			scrPan.add(context1,null);
			//3
			image = Toolkit.getDefaultToolkit().createImage( Utils.readStream(LookAndFeel.class.getResourceAsStream("/com/mkfree/frame/icons/024.png")));
			image = image.getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH); 
			icon = new ImageIcon(image);
			JButton b3 = new JButton(icon);
			b3.setBounds(10, 150, 50, 50);
			b3.setFocusable(false);
			scrPan.add(b3,null);
			title1 = new JLabel("数据导出");
			title1.setFont(new Font("宋体",1,14));
			title1.setForeground(Color.blue);
			title1.setBounds(70, 150, 100, 25);
			scrPan.add(title1,null);
			context1 = new JLabel("比如：外网、财政局等");
			context1.setBounds(70, 175, 500, 25);
			scrPan.add(context1,null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    this.pView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel,scrPan);
	    this.pView.setDividerLocation(200);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			feel = new LookAndFeel();
			theme = new Theme();
			LookAndFeel.setCurrentTheme(theme);
			UIManager.setLookAndFeel(feel);
		}catch(Exception e){
			System.out.println(e);
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		new Main();
	}

}