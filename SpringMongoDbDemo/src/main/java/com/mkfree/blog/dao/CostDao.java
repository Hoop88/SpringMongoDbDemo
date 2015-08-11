package com.mkfree.blog.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

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
@Repository("CostDao")
public class CostDao extends MongodbBaseDao<Cost> {

	public Pagination<Cost> getPageCost(int pageNo, int pageSize) {
		Query query = new Query();
		return this.getPage(pageNo, pageSize, query);
	}

	/**
	 * 通过条件去查询
	 * 
	 * @return
	 */
	public Cost findOne(Map<String, String> params) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.where("id").is(params.get("id"));
		query.addCriteria(criteria);
		return super.findOne(query);
	}
	
	public List<Cost> findAll() {  
        return mongoTemplate.findAll(Cost.class);  
    } 
	
	public List<Cost> find(String Level){
		return mongoTemplate.find(new Query(Criteria.where("level")  
	       		 .is(Level)), Cost.class);
	}
	
	
	public void delete(String Level,Cost entity){
		Cost c =mongoTemplate.findOne(new Query(Criteria.where("level")  
       		 .is(Level)), Cost.class);
		mongoTemplate.remove(Query.query(Criteria.where("id").is(c.getId())), Cost.class);
	}

	/**
	 * 暂时通过ＩＤ去修改title
	 * 
	 * @param id
	 * @param params
	 */
	public void updateEntity(String id, Map<String, String> params) {
		super.updateFirst(Query.query(Criteria.where("id").is(id)), Update.update("show", params.get("show")));
	}

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<Cost> getEntityClass() {
		return Cost.class;
	}
	
	
	/*
	 * 根据level 查找id ，id是uuid唯一的
	 * 更新用户修改的资料
	 */
	public void updateall(String Level,Cost entity) {  
        Cost c =mongoTemplate.findOne(new Query(Criteria.where("level")  
        		 .is(Level)), Cost.class);
        super.updateFirst(Query.query(Criteria.where("id").is(c.getId())), Update.update("level", entity.getLevel()));
        super.updateFirst(Query.query(Criteria.where("id").is(c.getId())), Update.update("name", entity.getName()));
        super.updateFirst(Query.query(Criteria.where("id").is(c.getId())), Update.update("number", entity.getNumber()));
        super.updateFirst(Query.query(Criteria.where("id").is(c.getId())), Update.update("price", entity.getPrice()));
        super.updateFirst(Query.query(Criteria.where("id").is(c.getId())), Update.update("scale", entity.getScale()));
        super.updateFirst(Query.query(Criteria.where("id").is(c.getId())), Update.update("show", entity.getShow()));  
    }
}
