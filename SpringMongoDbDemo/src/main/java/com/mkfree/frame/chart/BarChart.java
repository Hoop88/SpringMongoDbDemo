package com.mkfree.frame.chart;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class BarChart {
  ChartPanel frame1;
  public  BarChart(){
      CategoryDataset dataset = getDataSet();
      JFreeChart chart = ChartFactory.createBarChart3D(
                           "总消费清单分析柱状图", // 图表标题
                          "水电消费种类", // 目录轴的显示标签
                          "金额", // 数值轴的显示标签
                          dataset, // 数据集
                          PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                          true,           // 是否显示图例(对于简单的柱状图必须是false)
                          false,          // 是否生成工具
                          false           // 是否生成URL链接
                          );
       
      //从这里开始
      CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
      CategoryAxis domainAxis=plot.getDomainAxis();         //水平底部列表
       domainAxis.setLabelFont(new Font("黑体",Font.BOLD,14));         //水平底部标题
       domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));  //垂直标题
       ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
       rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,15));
        chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
         
        //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题
         
       frame1=new ChartPanel(chart,true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame
        
  }
     private static CategoryDataset getDataSet() {
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
         dataset.addValue(100, "北京", "苹果");
         dataset.addValue(100, "上海", "苹果");
         dataset.addValue(100, "广州", "苹果");
         dataset.addValue(200, "北京", "梨子");
         dataset.addValue(200, "上海", "梨子");
         dataset.addValue(200, "广州", "梨子");
         dataset.addValue(300, "北京", "葡萄");
         dataset.addValue(300, "上海", "葡萄");
         dataset.addValue(300, "广州", "葡萄");
         dataset.addValue(400, "北京", "香蕉");
         dataset.addValue(400, "上海", "香蕉");
         dataset.addValue(400, "广州", "香蕉");
         dataset.addValue(500, "北京", "荔枝");
         dataset.addValue(500, "上海", "荔枝");
         dataset.addValue(500, "广州", "荔枝");
		 return dataset;
	}
     
     public void getBarChart(CategoryDataset data){
         //CategoryDataset dataset = getDataSet();
         JFreeChart chart = ChartFactory.createBarChart3D(
                              "总消费清单分析柱状图", // 图表标题
                             "水电消费种类", // 目录轴的显示标签
                             "金额", // 数值轴的显示标签
                             data, // 数据集
                             PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                             true,           // 是否显示图例(对于简单的柱状图必须是false)
                             false,          // 是否生成工具
                             false           // 是否生成URL链接
                             );
          
         //从这里开始
         CategoryPlot plot=chart.getCategoryPlot();//获取图表区域对象
         CategoryAxis domainAxis=plot.getDomainAxis();         //水平底部列表
          domainAxis.setLabelFont(new Font("黑体",Font.BOLD,8));         //水平底部标题
          domainAxis.setTickLabelFont(new Font("宋体",Font.BOLD,8));  //垂直标题
          ValueAxis rangeAxis=plot.getRangeAxis();//获取柱状
          rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,8));
           chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 8));
           chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
            
           //到这里结束，虽然代码有点多，但只为一个目的，解决汉字乱码问题
            
          frame1=new ChartPanel(chart,true);        //这里也可以用chartFrame,可以直接生成一个独立的Frame
           
     }

	public ChartPanel getChartPanel() {
		return frame1;

	}
}