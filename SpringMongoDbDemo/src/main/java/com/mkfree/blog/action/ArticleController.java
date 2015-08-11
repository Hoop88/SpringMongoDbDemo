package com.mkfree.blog.action;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mkfree.blog.domain.Article;
import com.mkfree.blog.service.ArticleService;
import com.mkfree.framework.common.page.Pagination;

public class ArticleController {

	private static ApplicationContext app;
	private static ArticleService articleService;

//	@Test
//	public void listArticle() {
//		Pagination<Article> page = articleService.getPageArticle(2, 10);
//		System.out.println(page);
//	}
//
//	@Test
//	public void save() {
//		for (int i = 1; i < 21; i++) {
//			Article a = new Article();
//			a.setTitle("mongodb开始实战" + i);
//			a.setContent("mongodb开始实战..内容" + i);
//			articleService.save(a);
//		}
//	}
//
//	@Test
//	public void findArticle() {
//		Article a = articleService.findByid("50fd0c36bc40ceec1a44308b");
//		System.out.println(a);
//	}

	@Test
	public void update() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", "修改内容...");
		articleService.update("5577c313232f6566493d08f5", params);
	}

	@BeforeClass
	public static void initSpring() {
		app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml",
				"classpath:spring/mongodb.xml" });
		articleService = (ArticleService) app.getBean("articleService");
	}
}
