package com.jzfblog.store.dao;

import java.util.List;

import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.Product;

public interface ProductDao {
	
	/**
	 * 查询最热商品
	 * @return
	 * @throws Exception
	 */
	List<Product> findHots() throws Exception;
	
	/**
	 * 查询最新商品
	 * @return
	 * @throws Exception
	 */
	List<Product> findNews() throws Exception;
	
	/**
	 * 根据商品的pid查询商品
	 * @return
	 * @throws Exception
	 */
	Product findProductByPid(String pid) throws Exception;
	
	/**
	 * 查询某分类的总记录数
	 * @param cid 分类cid
	 * @return
	 * @throws Exception
	 */
	int findTotalRecords(String cid) throws Exception;
	
	/**
	 * 分页显示某分类的产品
	 * @param cid
	 * @param startIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List findProductByCidWithPage(String cid, int startIndex, int pageSize) throws Exception;

	/**
	 * 查询所有商品的记录数
	 * @return
	 */
	int findAllRecords() throws Exception;

	/**
	 * 分页显示所有商品
	 * @param startIndex 起始页
	 * @param pageSize 一页记录数
	 * @return
	 */
	List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception;

	/**
	 * 保存商品信息
	 * @param product 商品
	 */
	void saveProduct(Product product) throws Exception;
}
