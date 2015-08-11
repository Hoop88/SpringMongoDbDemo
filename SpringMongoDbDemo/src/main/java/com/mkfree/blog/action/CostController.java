package com.mkfree.blog.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mkfree.blog.domain.Cost;
import com.mkfree.blog.service.CostService;
import com.mkfree.framework.common.page.Pagination;

public class CostController {

	private static ApplicationContext app;
	private static CostService costService;
	private static MongoTemplate mongoTemplate;

//	@Test
//	public void listArticle() {
//		//分页 第2页的5条数据
//		Pagination<Cost> page = costService.getPageCost(2, 5);
//		for (Cost c : page.getDatas()) {
//			System.out.println(c.getLevel());
//		}
//	}

	@Test
	public void save() {
		double num=13.25;
		for (int i = 1; i < 81; i++) {
			Cost c = new Cost();
			c.setLevel("L20150610"+i);
			if(i%2==0){
			   c.setName("水费-FL20150610" + i);
			}else if(i%3==0){
				c.setName("电费-FL20150610" + i);
			}else if(i%5==0){
				c.setName("气费-FL20150610" + i);
			}else{
			    c.setName("水电气-FL20150610" + i);
			}
			c.setNumber("N0010000" + i);
			num = i+0.023+num+0.56;
			BigDecimal b = new BigDecimal(num);  
			double f1 = b.setScale(5,BigDecimal.ROUND_HALF_UP).doubleValue(); //保留5位小数 
			c.setPrice(new BigDecimal(Double.toString(f1)));
			c.setScale(i+"0"+":"+"1"+i);
			if(i%2==0){
			   c.setShow("水费支出明细:"+new BigDecimal(Double.toString(f1))+"$");
			}else if(i%3==0){
				c.setShow("电费支出明细:"+new BigDecimal(Double.toString(f1))+"$");
			}else if(i%5==0){
				c.setShow("气费支出明细:"+new BigDecimal(Double.toString(f1))+"$");
			}else{
			    c.setShow("水电气总支出费用："+new BigDecimal(Double.toString(f1))+"$");
			}
			
			costService.save(c);
		}
	}
	
	
//	@Test
//	public void update(){
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("show", "sdfasfasdfasdfasdf");
//		costService.update("55794eba232f65229ec0d471", params);
        
//        mongoTemplate.updateFirst(new Query(Criteria.where("id").is("55794ff9232f63203e5f9400")),
//        Update.update("show", "12312312"), Cost.class);
//	}

//	@Test
//	public void findArticle() {
//		Article a = articleService.findByid("555d38dfbb470474f2018a93");
//		System.out.println(a.getTitle());
//	}
//
//	@Test
//	public void update() {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("title", "修改内容...");
//		articleService.update("555d38dfbb470474f2018a93", params);
//	}

	@BeforeClass
	public static void initSpring() {
		app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml",
				"classpath:spring/mongodb.xml" });
		costService = (CostService) app.getBean("costService");
	}
}
