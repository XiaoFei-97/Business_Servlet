package com.jzfblog.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.jzfblog.store.dao.CategoryDao;
import com.jzfblog.store.domain.Category;
import com.jzfblog.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	/**
	 * 返回分类下的信息
	 */
	public List<Category> getAllCats() throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		return query.query(sql, new BeanListHandler<Category>(Category.class));
	}

	/**
	 * 添加分类
	 */
	public void addCategory(Category category) throws Exception {

		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category values(?, ?)";
		Object[] params = {category.getCid(), category.getCname()};
		query.update(sql, params);
	}

	/**
	 * 获取分类信息
	 */
	public Category findCategoryByCid(String cid) throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid=?";
		return query.query(sql, new BeanHandler<Category>(Category.class), cid);
	}

	/**
	 * 修改分类信息
	 */
	public void updateCategory(Category category) throws Exception {

		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category cname=? where cid=?";
		query.update(sql, category.getCname(), category.getCid());
	}

	
}
