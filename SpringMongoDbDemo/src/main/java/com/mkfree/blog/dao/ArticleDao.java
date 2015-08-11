package com.mkfree.blog.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mkfree.blog.domain.Article;
import com.mkfree.blog.domain.Cost;
import com.mkfree.framework.common.mongodb.MongodbBaseDao;
import com.mkfree.framework.common.page.Pagination;

/**
 * ＤＡＯ层操作类
 * 
 * @author oyhk
 * 
 *         2013-1-21下午1:57:14
 */
@SuppressWarnings("static-access")
@Repository("ArticleDao")
public class ArticleDao extends MongodbBaseDao<Article> {

	public Pagination<Article> getPageArticle(int pageNo, int pageSize) {
		Query query = new Query();
		return this.getPage(pageNo, pageSize, query);
	}

	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public Article findOne(Map<String, String> params) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.where("id").is(params.get("id"));
		query.addCriteria(criteria);
		return super.findOne(query);
	}

	/**
	 * 暂时通过ＩＤ去修改title
	 * 
	 * @param id
	 * @param params
	 */
	public void updateEntity(String id, Map<String, String> params) {
		super.updateFirst(Query.query(Criteria.where("id").is(id)), Update.update("title", params.get("title")));
	}

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<Article> getEntityClass() {
		return Article.class;
	}
}
