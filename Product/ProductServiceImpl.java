package com.jzfblog.store.service.impl;

import java.util.List;

import com.jzfblog.store.dao.ProductDao;
import com.jzfblog.store.dao.impl.ProductDaoImpl;
import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.Product;
import com.jzfblog.store.service.ProductService;
import com.jzfblog.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService{

	/**
	 * 查询最热商品
	 */
	public List<Product> findHots() throws Exception {
		ProductDao ProductDao = (ProductDao) BeanFactory.createObject("ProductDao");
		return ProductDao.findHots();
	}

	/**
	 * 查询最新商品
	 */
	public List<Product> findNews() throws Exception {
		ProductDao ProductDao = new ProductDaoImpl();
		return ProductDao.findNews();
	}

	/**
	 * 根据pid查询商品
	 */
	public Product findProductByPid(String pid) throws Exception {
		ProductDao ProductDao = new ProductDaoImpl();
		return ProductDao.findProductByPid(pid);
	}

	/**
	 * 分页显示某分类下的产品
	 */
	public PageModel findProductByCidWithPage(String cid, int currentPage) throws Exception {
		ProductDao ProductDao = new ProductDaoImpl();
		// 统计某分类产品的总记录数
		int totalRecords = ProductDao.findTotalRecords(cid);
		PageModel pageModel = new PageModel(currentPage, totalRecords, 12);
		
		// 得出当页的产品集合
		List records = ProductDao.findProductByCidWithPage(cid, pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setRecords(records);
		pageModel.setUrl("ProductServlet?method=findProductByCidWithPage&cid="+cid);
		return pageModel;
	}

	/**
	 * 分页显示所有商品
	 */
	public PageModel findAllProductsWithPage(int currentPage) throws Exception {

		ProductDao productDao = new ProductDaoImpl();
		// 统计全部商品的记录数
		int totalRecords = productDao.findAllRecords();
		// 创建PageModel
		PageModel pageModel = new PageModel(currentPage, totalRecords, 10);
		// 得出当页所有商品集合
		List<Product> list = productDao.findAllProductsWithPage(pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setRecords(list);
		pageModel.setUrl("");
		return pageModel;
	}

	/**
	 * 保存商品信息
	 */
	public void saveProduct(Product product) throws Exception {

		ProductDao productDao = new ProductDaoImpl();
		productDao.saveProduct(product);
	}
	
}
