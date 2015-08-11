package com.mkfree.blog.frame.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mkfree.blog.domain.Cost;
import com.mkfree.blog.service.CostService;
import com.mkfree.frame.chart.BarChart;
import com.mkfree.frame.chart.PieChart;
import com.mkfree.frame.utils.NoTileDrag;
import com.mkfree.framework.common.page.Pagination;

public class FrameMain extends JFrame {
	public static LookAndFeel feel;
	public static Theme theme;
	public JPanel title;
	public JSplitPane pView;
	public JComboBox cb;

	private static ApplicationContext app;
	private static CostService costService;

	private String level;
	private Vector<Object> vData;
	private Vector<Vector<Object>> CostData;
	private Vector<Vector<Object>> ctData;

	FrameMain() {

		AddTitle();
		addBody();

		getContentPane().add(title, BorderLayout.NORTH);
		getContentPane().add(pView, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);// 隐藏标题栏
		setSize(1024, 768);
		setVisible(true);
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	public void AddTitle() {
		final DefaultPieDataset dataset = new DefaultPieDataset();
		final DefaultCategoryDataset data = new DefaultCategoryDataset();

		title = new JPanel();
		title.setPreferredSize(new Dimension(1024, 110));
		title.setLayout(null);
		title.setBackground(Color.white);
		JLabel text1 = new JLabel("选择使用的清单号，管控水电气费用（JH）");
		text1.setFont(new Font("宋体", Font.BOLD, 14));
		text1.setBounds(10, 10, 700, 40);

		JLabel text2 = new JLabel("选择未使用的清单号，在选择一个用水电气，添加、修改、删除费用组成");
		text2.setBounds(30, 60, 700, 40);

		JButton btn = new JButton();
		try {
			Image image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/037.png")));
			image = image
					.getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(image);
			btn.setIcon(icon);
			btn.setFocusable(false);
			btn.setBounds(720, 10, 90, 90);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title.add(text1, null);
		title.add(text2, null);
		title.add(btn, null);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Cost> page = costService.findAll();
				for (Cost c : page) {
					vData.add(c.getName());
					vData.add(c.getPrice());
					/**********圆饼数据***********/
					dataset.setValue(c.getName(), c.getPrice());
					data.setValue(c.getPrice(), c.getName(), c.getName());
					/****************************/
					CostData.add(vData);
				}
				
				
				PieChart pie = new PieChart();
				pie.getPieChart(dataset);
				BarChart b = new BarChart();
				b.getBarChart(data);

				
				JFrame frame1 = new JFrame("总消费图表分析");
				frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame1.setLayout(new GridLayout(1,2,10,10));
				//p.add(new PieChart().getChartPanel());
				frame1.add(pie.getChartPanel());
				frame1.add(b.getChartPanel());
				//frame1.setBounds(50, 50, 800, 600);
				frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);
				//frame1.setUndecorated(true);
//				NoTileDrag n = new NoTileDrag();
//		        n.setCanDraged(frame1); //让没有标题栏的窗体可以拖动
		        
		        frame1.setResizable(true);
				frame1.setVisible(true);
			}
		});
		
	}

	public void addBody() {
		final String[] namestooltip = { "阶梯级别[Cost：Level]", "名称[Cost：name]",
				"编号[Cost：number]", "价格[Cost：price]", "比例[Cost：scale]",
				"说明[Cost：show]" };
		final DefaultPieDataset dataset = new DefaultPieDataset();
		
		// 左
		final JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

		final JLabel titlel = new JLabel("选择未使用的清单号:");

		/*
		 * List 转换为 数组
		 */
		// ArrayList<String> list=new ArrayList<String>();
		// String strings[]=new String[list.size()];
		// for(int i=0,j=list.size();i<j;i++){
		// strings[i]=list.get(i);
		// }

		Vector v = new Vector();

		List<Cost> cl = costService.findAll();
		for (Cost c : cl) {
			v.add(c.getLevel());
		}

		cb = new JComboBox(v);
		cb.setMaximumSize(new Dimension(400, cb.getPreferredSize().height));

		final Vector colums = new Vector<>();
		colums.add("名称");
		colums.add("价格[￥]");
        
		final JTable tabla = new JTable();
		
		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("Selected: " + cb.getSelectedItem());
				// System.out.println(", Position: " + cb.getSelectedIndex());
				dataset.clear();
				ctData = new Vector<Vector<Object>>();
				level = cb.getSelectedItem().toString();
				
				// System.out.println("Selected: " + cb.getSelectedItem());
				double sum = 0;
				double f1 = 0;
				List<Cost> cost = costService.find(level);
				for (Cost c : cost) {
					vData = new Vector<Object>();
					vData.add(c.getName());
					vData.add(c.getPrice());
					/**********圆饼数据***********/
					dataset.setValue(c.getName(), c.getPrice());
					/****************************/
					BigDecimal b = c.getPrice();
					f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP)
							.doubleValue(); // 保留5位小数
					sum = sum+f1;
					ctData.add(vData);
				}
				BigDecimal s = new BigDecimal(sum);
				sum = s.setScale(5,BigDecimal.ROUND_HALF_UP).doubleValue();
				
				Vector<Object> sData = new Vector<Object>();
				sData.add("汇总：");
				sData.add(sum);
				ctData.add(sData);
				
				DefaultTableModel model = (DefaultTableModel) tabla.getModel();
				model.setDataVector(ctData, colums);
				tabla.setModel(model);
			}
		});
		
		
		
		JScrollPane js = new JScrollPane(tabla);
		left.add(titlel, BorderLayout.PAGE_START);
		left.add(cb, BorderLayout.NORTH);
		left.add(js, BorderLayout.SOUTH);

		// 右
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout(0, 13));// new
													// BoxLayout(right,BoxLayout.Y_AXIS));
		JLabel titler = new JLabel("费用组成：");
		right.add(titler, BorderLayout.PAGE_START);

		Vector columNames = new Vector();
		columNames.add("阶梯级别[L+日期+流水好]");
		columNames.add("名称[别名+‘-’+日期+流水号]");
		columNames.add("编号[N00+流水号]");
		columNames.add("价格[￥]");
		columNames.add("比例[%]");
		columNames.add("说明");

		CostData = new Vector<Vector<Object>>();

		// Pagination<Cost> page = costService.getPageCost(1, 81);
		List<Cost> page = costService.findAll();
		for (Cost c : page) {
			vData = new Vector<Object>();
			vData.add(c.getLevel());
			vData.add(c.getName());
			vData.add(c.getNumber());
			vData.add(c.getPrice());
			vData.add(c.getScale());
			vData.add(c.getShow());
			CostData.add(vData);
		}

		// 创建table，鼠标悬停table表头有文本提示
		final JTable table = new JTable(CostData, columNames) {
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					public String getToolTipText(MouseEvent e) {
						String tip = null;
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index)
								.getModelIndex();
						return namestooltip[realIndex];
					}
				};
			}
		};
		JScrollPane jsp = new JScrollPane(table);
		jsp.setPreferredSize(new Dimension(200, 200));
		right.add(jsp, BorderLayout.CENTER);

		colorTableRender colorRender = new colorTableRender();
		table.getColumn("价格[￥]").setCellRenderer(colorRender);

		JPanel edit = new JPanel();
		edit.setLayout(null);
		JLabel titler1 = new JLabel("费用信息：");
		titler.setForeground(Color.blue);
		edit.add(titler1, null);
		JLabel t2 = new JLabel("阶梯级别");
		t2.setBounds(0, 60, 80, 25);
		final JTextField tf = new JTextField();
		tf.setPreferredSize(new Dimension(100, 25));
		tf.setBounds(110, 60, 100, 25);
		edit.add(t2, null);
		edit.add(tf, null);
		tf.setEditable(false);
		tf.setBackground(tf.getBackground().lightGray);

		JLabel t3 = new JLabel("名称");
		t3.setBounds(215, 60, 80, 25);
		final JTextField tf1 = new JTextField();
		tf1.setPreferredSize(new Dimension(100, 25));
		tf1.setBounds(300, 60, 100, 25);
		edit.add(t3, null);
		edit.add(tf1, null);

		JLabel t7 = new JLabel("编号");
		t7.setBounds(0, 30, 80, 25);
		final JTextField tf5 = new JTextField();
		tf5.setPreferredSize(new Dimension(100, 25));
		tf5.setBounds(110, 30, 140, 25);
		edit.add(t7, null);
		edit.add(tf5, null);
		// t7.setVisible(false);
		// tf5.setVisible(false);

		JLabel t4 = new JLabel("价格");
		t4.setBounds(0, 90, 80, 25);
		final JTextField tf2 = new JTextField();
		tf2.setPreferredSize(new Dimension(100, 25));
		tf2.setBounds(110, 90, 100, 25);
		edit.add(t4, null);
		edit.add(tf2, null);

		JLabel t5 = new JLabel("价格比例");
		t5.setBounds(215, 90, 80, 25);
		final JTextField tf3 = new JTextField();
		tf3.setPreferredSize(new Dimension(100, 60));
		tf3.setBounds(300, 90, 100, 25);
		edit.add(t5, null);
		edit.add(tf3, null);

		JLabel t6 = new JLabel("描述");
		t6.setBounds(0, 120, 80, 25);
		final JTextField tf4 = new JTextField();
		tf4.setPreferredSize(new Dimension(100, 25));
		tf4.setBounds(110, 120, 400, 25);
		edit.add(t6, null);
		edit.add(tf4, null);

		final JLabel t8 = new JLabel("");
		t8.setBounds(300, 170, 400, 25);
		edit.add(t8, null);
		
		PieChart pie = new PieChart();
		pie.getPieChart(dataset);
		
		Panel p = new Panel();
		p.setLayout(new GridLayout(1,1,10,10));
		//p.add(new PieChart().getChartPanel());
		p.add(pie.getChartPanel());
		p.setBounds(732, 390, 400, 260);
		right.add(p,BorderLayout.SOUTH);
		
		right.add(edit, BorderLayout.SOUTH);
		JSplitPane ppView = new JSplitPane(JSplitPane.VERTICAL_SPLIT, jsp, edit);
		ppView.setDividerLocation(350);
		right.add(ppView, BorderLayout.CENTER);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
		table.addMouseListener(new MouseAdapter() { // 鼠标事件
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow(); // 获得选中行索引
				Object Level = table.getValueAt(selectedRow, 0);
				Object name = table.getValueAt(selectedRow, 1);
				Object Number = table.getValueAt(selectedRow, 2);
				Object price = table.getValueAt(selectedRow, 3);
				Object scale = table.getValueAt(selectedRow, 4);
				Object show = table.getValueAt(selectedRow, 5);
				tf.setText(Level.toString()); // 给文本框赋值
				tf1.setText(Number.toString());
				tf2.setText(price.toString());
				tf3.setText(scale.toString());
				tf4.setText(show.toString());
				tf5.setText(name.toString());
			}
		});

		// 悬浮提示单元格的值
		table.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				if (row > -1 && col > -1) {
					Object value = table.getValueAt(row, col);
					if (null != value && !"".equals(value))
						table.setToolTipText(value.toString());// 悬浮显示单元格内容
					else
						table.setToolTipText(null);// 关闭提示
				}
			}
		});

		JToolBar toolBar = new JToolBar();
		Image image;
		try {
			image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/014.png")));
			image = image
					.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			ImageIcon icon = new ImageIcon(image);
			final JButton b0 = new JButton(icon);
			b0.setText("添加");
			b0.setToolTipText("添加");

			image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/070.png")));
			image = image
					.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			JButton b1 = new JButton(icon);
			b1.setText("修改");
			b1.setToolTipText("修改");

			image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/017.png")));
			image = image
					.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			JButton b2 = new JButton(icon);
			b2.setText("删除");
			b2.setToolTipText("删除");

			image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/003.png")));
			image = image
					.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			JButton b3 = new JButton(icon);
			b3.setText("重置");
			b3.setToolTipText("重置");

			image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/034.png")));
			image = image
					.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			JButton b4 = new JButton(icon);
			b4.setText("刷新");
			b4.setToolTipText("刷新");

			image = Toolkit
					.getDefaultToolkit()
					.createImage(
							Utils.readStream(LookAndFeel.class
									.getResourceAsStream("/com/mkfree/frame/icons/030.png")));
			image = image
					.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			icon = new ImageIcon(image);
			JButton b5 = new JButton(icon);
			b5.setText("退出");
			b5.setToolTipText("退出");

			b0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Cost c = new Cost();
					c.setLevel(tf.getText());
					c.setName(tf5.getText());
					c.setNumber(tf1.getText());
					BigDecimal b = new BigDecimal(tf2.getText());
					double f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP)
							.doubleValue(); // 保留5位小数
					c.setPrice(new BigDecimal(Double.toString(f1)));
					c.setScale(tf3.getText());
					c.setShow(tf4.getText());

					costService.save(c);
					t8.setText("insert Execute successfully!");

				}
			});
			// 编辑区域和表格互联修改编辑区表格数据变更，数据库数据变更
			b1.addActionListener(new ActionListener() {// 添加事件
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedRow();// 获得选中行的索引
					if (selectedRow != -1) // 是否存在选中行
					{
						// 修改指定的值：
						table.setValueAt(tf.getText(), selectedRow, 0);
						table.setValueAt(tf5.getText(), selectedRow, 1);
						table.setValueAt(tf1.getText(), selectedRow, 2);
						table.setValueAt(tf2.getText(), selectedRow, 3);
						table.setValueAt(tf3.getText(), selectedRow, 4);
						table.setValueAt(tf4.getText(), selectedRow, 5);

						// 将文本框的值赋给Cost对象实体类
						Cost c = new Cost();
						c.setLevel(tf.getText());
						c.setName(tf5.getText());
						c.setNumber(tf1.getText());
						BigDecimal b = new BigDecimal(tf2.getText());
						double f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP)
								.doubleValue(); // 保留5位小数
						c.setPrice(new BigDecimal(Double.toString(f1)));
						c.setScale(tf3.getText());
						c.setShow(tf4.getText());
						// 做更新用户资料
						costService.updatFirst(c.getLevel(), c);
						t8.setText("update Execute successfully!");
						// 刷新
						table.repaint();
						table.updateUI();
					}
				}
			});

			// delete
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Cost c = new Cost();
					c.setLevel(tf.getText());
					c.setName(tf5.getText());
					c.setNumber(tf1.getText());
					if (tf2.getText().trim() != ""
							&& tf2.getText().length() != 0) {
						BigDecimal b = new BigDecimal(tf2.getText());
						double f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP)
								.doubleValue(); // 保留5位小数
						c.setPrice(new BigDecimal(Double.toString(f1)));
					}
					c.setScale(tf3.getText());
					c.setShow(tf4.getText());
					System.out.println(tf.getText());
					if (tf.getText().trim() != "" && tf.getText().length() != 0
							&& tf1.getText().trim() != ""
							&& tf1.getText().length() != 0
							&& tf2.getText().trim() != ""
							&& tf2.getText().length() != 0
							&& tf3.getText().trim() != ""
							&& tf3.getText().length() != 0
							&& tf4.getText().trim() != ""
							&& tf4.getText().length() != 0
							&& tf5.getText().trim() != ""
							&& tf5.getText().length() != 0) {
						costService.delete(tf.getText(), c);
						t8.setText("del Execute successfully!");
					} else {
						t8.setText("Text is not Empty!");
					}

					table.repaint();
					table.updateUI();
				}
			});

			b3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tf.setText("");
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					tf4.setText("");
					tf5.setText("");
				}
			});

			// 刷新
			b4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					table.repaint();
					table.updateUI();
					t8.setText("refresh Execute successfully!");
				}
			});

			b5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			toolBar.addSeparator();
			toolBar.add(b0);
			toolBar.addSeparator();
			toolBar.add(b1);
			toolBar.addSeparator();
			toolBar.add(b2);
			toolBar.addSeparator();
			toolBar.add(b3);
			toolBar.addSeparator();
			toolBar.add(b4);
			toolBar.addSeparator();
			toolBar.add(b5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		right.add(toolBar, BorderLayout.PAGE_END);

		this.pView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		this.pView.setDividerLocation(200);

	}

	/*
	 * 鼠标滑动修改单元格背景颜色
	 */
	public class colorTableRender extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component cell = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);
			// if (row == 3 && cell.isBackgroundSet()) //这里设置行数
			// cell.setBackground(Color.red);
			// else
			// cell.setBackground(new Color(0xee,0xee,0xee));

			super.setForeground(cell.getBackground().red);
			if (isSelected) {
				// 设置选定单元格的背景色。单元格渲染器可以使用此颜色填充选定单元格。

				// 返回选定单元格的背景色。
				super.setBackground(table.getBackground().green);
				super.setForeground(cell.getBackground().black);
			} else {
				this.setBackground(table.getBackground().white);
			}
			return cell;

		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			feel = new LookAndFeel();
			theme = new Theme();
			LookAndFeel.setCurrentTheme(theme);
			UIManager.setLookAndFeel(feel);

			app = new ClassPathXmlApplicationContext(new String[] {
					"classpath:spring/framework-context.xml",
					"classpath:spring/mongodb.xml" });
			costService = (CostService) app.getBean("costService");
		} catch (Exception e) {
			System.out.println(e);
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		new FrameMain();

	}

}
