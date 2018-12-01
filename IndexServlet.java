package com.jzfblog.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.Product;
import com.jzfblog.store.service.ProductService;
import com.jzfblog.store.service.impl.ProductServiceImpl;
import com.jzfblog.store.web.base.BaseServlet;


public class IndexServlet extends BaseServlet {
	
	// 方案1：缺点，每个页面转发都需要加查询
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 获取全部分类信息，返回集合
		// CategoryService CategoryService = new CategoryServiceImpl();
		// List<Category> list = null;
		// try {
		//	list = CategoryService.getAllCats();
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// }
		// 将返回的集合放入request
		// request.setAttribute("allCats", list);
		
		// 调用业务层
		ProductService ProductService = new ProductServiceImpl();
		// 查询最新、最热商品，返回两个集合
		List<Product> hots = null;
		List<Product> news = null;
		try {
			hots = ProductService.findHots();
			news = ProductService.findNews();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将两个集合放到真实的首页
		request.setAttribute("hots", hots);
		request.setAttribute("news", news);
		
		System.out.println(hots.toString());
		return "/jsp/index.jsp";
	}
	
}
