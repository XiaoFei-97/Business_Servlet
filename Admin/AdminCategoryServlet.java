package com.jzfblog.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.Category;
import com.jzfblog.store.service.CategoryService;
import com.jzfblog.store.service.impl.CategoryServiceImpl;
import com.jzfblog.store.utils.UUIDUtils;
import com.jzfblog.store.web.base.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取全部分类信息
		CategoryService CategoryService = new CategoryServiceImpl();
		List<Category> list = CategoryService.getAllCats();
		// 放入request
		request.setAttribute("allCats", list);
		// 转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";
	}
	
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		return "/admin/category/add.jsp";
	}
	
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取客户端提交分类名称
		String cname = request.getParameter("cname");
		// 创建分类id
		String cid = UUIDUtils.getId();
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		// 调用业务层：添加分类
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.addCategory(category);
		// 重定向到查询全部分类信息
		response.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
		return null;
	}
	
	public String editCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 获取客户端提交的cid
		String cid = request.getParameter("cid");
		// 调用业务层：获取分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		Category category = categoryService.findCategoryByCid(cid);
		// 将category放入到request域中
		request.setAttribute("category", category);
		System.out.println(category);
		// 转发到/admin/category/edit.jsp
		return "/admin/category/edit.jsp";
	}
	
	public String updateCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		// 获取客户端提交的cid与cname
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		Category category = new Category(cid, cname);
		// 调用业务层：修改分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.updateCategory(category);
		// 转发到/admin/category/list.jsp
		return "admin/category/list.jsp";
	}
}
