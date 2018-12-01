package com.jzfblog.store.dao;

import java.util.List;

import com.jzfblog.store.domain.Category;

public interface CategoryDao {
 
	/**
	 * 返回分类信息
	 * @return
	 * @throws Exception
	 */
	List<Category> getAllCats() throws Exception;

	/**
	 * 添加分类
	 */
	void addCategory(Category category) throws Exception;

	/**
	 * 获取分类信息
	 * @param cid 分类cid
	 * @return
	 * @throws Exception
	 */
	Category findCategoryByCid(String cid) throws Exception;

	/**
	 * 修改分类信息
	 * @param category
	 */
	void updateCategory(Category category) throws Exception;
}
