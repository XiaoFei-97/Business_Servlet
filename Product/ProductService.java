package com.jzfblog.store.service;

import java.util.List;

import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.Product;

public interface ProductService {
	
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
	 * 对某分类下的所有产品分页显示
	 * @param cid 分类cid
	 * @param currentPage 当前页
	 * @return pageModel
	 * @throws Exception
	 */
	PageModel findProductByCidWithPage(String cid, int currentPage) throws Exception;

	/**
	 * 对所有商品分页显示
	 * @param currentPage 当前页
	 * @return
	 */
	PageModel findAllProductsWithPage(int currentPage) throws Exception;

	/**
	 * 保存商品信息
	 * @param product 商品
	 */
	void saveProduct(Product product) throws Exception;
}
