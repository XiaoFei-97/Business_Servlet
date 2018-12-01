package com.jzfblog.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.User;
import com.jzfblog.store.service.UserService;
import com.jzfblog.store.service.impl.UserServiceImpl;
import com.jzfblog.store.utils.MailUtils;
import com.jzfblog.store.utils.MyBeanUtils;
import com.jzfblog.store.utils.UUIDUtils;
import com.jzfblog.store.web.base.BaseServlet;


public class UserServlet extends BaseServlet {

	public String registerUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	public String userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收表单参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		// 为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		// 调用业务层注册功能
		UserService UserService = new UserServiceImpl();
		try {
			UserService.userRegister(user);
			// 注册成功，向用户邮箱发送信息，跳转到提示界面
			// 发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活！");
		} catch (Exception e) {
			// 注册失败，跳转到提示界面
			request.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		return "jsp/info.jsp";
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 获取激活码
		String code = request.getParameter("code");
		// 调用业务层的激活功能
		UserService UserService = new UserServiceImpl();
		boolean flag = UserService.userActive(code);
		if(flag == true) {
			// 用户激活成功，向request放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "jsp/login.jsp";
		}else {
			// 用户激活失败，向request放入提示信息，转发到提示页面
			request.setAttribute("msg", "用户激活失败，请重新激活！");
			return "jsp/info.jsp";
		}
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取用户数据
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		// 调用业务层的登录功能
		UserService UserService = new UserServiceImpl();
		User user02 = null;
		try {
			user02 = UserService.userLogin(user);
			// 用户登录成功，将用户信息放入session中
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("index.jsp");
			// 重定向使用null
			return null;
		} catch (Exception e) {
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}
	
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// 清除服务端的session
		request.getSession().invalidate();
		response.sendRedirect("index.jsp");
		return null;
	}
}
