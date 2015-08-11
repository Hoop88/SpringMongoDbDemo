package com.mkfree.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mkfree.blog.dao.CostDao;
import com.mkfree.blog.domain.Cost;
import com.mkfree.framework.common.page.Pagination;

@Service("costService")
public class CostService {

	@Autowired
	private CostDao CostDao;

	public Cost findByid(String id) {
		return (Cost) CostDao.findById(id);
	}

	public Cost findOne(Map<String, String> params) {
		return this.CostDao.findOne(params);
	}

	public Cost save(Cost bean) {
		return (Cost) CostDao.save(bean);
	}

	public Pagination<Cost> getPageCost(int pageNo, int pageSize) {
		return CostDao.getPageCost(pageNo, pageSize);
	}
	
	public List<Cost> findAll() {  
        return CostDao.findAll();  
    }
	
	public List<Cost> find(String Level){
		return CostDao.find(Level);
	}
	
	public void delete(String Level,Cost entity){
		CostDao.delete(Level, entity);
	}

	/**
	 * 暂时只是固定去修改
	 */
	public void update(String id, Map<String, String> params) {
		CostDao.updateEntity(id, params);
	}
	
	public void updatFirst(String Level,Cost c){
		CostDao.updateall(Level,c);
	}
}
