package com.jzfblog.store.service;

import java.util.List;

import com.jzfblog.store.domain.Category;

public interface CategoryService {

	/**
	 * 返回分类下的所有信息
	 * @return
	 * @throws Exception
	 */
	public List<Category> getAllCats() throws Exception;

	/**
	 * 添加分类
	 * @param category 分类
	 */
	public void addCategory(Category category) throws Exception;

	/**
	 * 返回当前分类信息
	 * @param cid
	 * @return
	 */
	public Category findCategoryByCid(String cid) throws Exception;

	/**
	 * 修改分类信息
	 * @param category
	 */
	public void updateCategory(Category category) throws Exception;
}
