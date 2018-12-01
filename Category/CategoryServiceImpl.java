package com.jzfblog.store.service.impl;

import java.util.List;

import com.jzfblog.store.dao.CategoryDao;
import com.jzfblog.store.dao.impl.CategoryDaoImpl;
import com.jzfblog.store.domain.Category;
import com.jzfblog.store.service.CategoryService;
import com.jzfblog.store.utils.BeanFactory;
import com.jzfblog.store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	/**
	 * 返回分类下的所有信息
	 */
	public List<Category> getAllCats() throws Exception {
		CategoryDao CategoryDao = (CategoryDao)BeanFactory.createObject("CategoryDao");
		return CategoryDao.getAllCats();
	}

	/**
	 * 添加分类
	 */
	public void addCategory(Category category) throws Exception {

		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.addCategory(category);
		// 更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

	/**
	 * 获取分类信息
	 */
	public Category findCategoryByCid(String cid) throws Exception {
		
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.findCategoryByCid(cid);
	}

	/**
	 * 更新分类信息
	 */
	public void updateCategory(Category category) throws Exception {

		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.updateCategory(category);
		
		// 更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
