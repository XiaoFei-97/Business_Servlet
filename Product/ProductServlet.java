package com.jzfblog.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.Product;
import com.jzfblog.store.service.ProductService;
import com.jzfblog.store.service.impl.ProductServiceImpl;
import com.jzfblog.store.web.base.BaseServlet;


public class ProductServlet extends BaseServlet {
	
	/**
	 * 通过pid查找商品
	 * @param request
	 * @param response
	 * @return /jsp/product_info.jsp
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 获取商品pid
		String pid = request.getParameter("pid");
		// 根据商品pid查询商品信息
		ProductService ProductService = new ProductServiceImpl();
		Product product = null;
		try {
			product = ProductService.findProductByPid(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将获取到的商品放入request
		request.setAttribute("product", product);
		// 转发到/jsp/product_info.jsp
		
		return "/jsp/product_info.jsp";
	}

	/**
	 * 分页显示某分类下的商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findProductByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取分类的cid及当前页currentPage
		String cid = request.getParameter("cid");
		int currentPage = Integer.parseInt(request.getParameter("num"));
		// 调用业务层功能：实现分页形式查询当前类别的商品信息
		ProductService ProductService = new ProductServiceImpl();
		PageModel pageModel = null;
		try {
			pageModel = ProductService.findProductByCidWithPage(cid, currentPage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将pageBean对象放置到request上
		request.setAttribute("page", pageModel);
		// 转发到/jsp/product_list.jsp上
		return "/jsp/product_list.jsp";
	}
	
}
