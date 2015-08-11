package com.mkfree.blog.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkfree.blog.dao.ArticleDao;
import com.mkfree.blog.domain.Article;
import com.mkfree.framework.common.page.Pagination;

@Service("articleService")
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;

	public Article findByid(String id) {
		return (Article) articleDao.findById(id);
	}

	public Article findOne(Map<String, String> params) {
		return this.articleDao.findOne(params);
	}

	public Article save(Article bean) {
		return (Article) articleDao.save(bean);
	}

	public Pagination<Article> getPageArticle(int pageNo, int pageSize) {
		return articleDao.getPageArticle(pageNo, pageSize);
	}

	/**
	 * 暂时只是固定去修改,会有下一篇博客,写高级修改...
	 */
	public void update(String id, Map<String, String> params) {
		this.articleDao.updateEntity(id, params);
	}
}
