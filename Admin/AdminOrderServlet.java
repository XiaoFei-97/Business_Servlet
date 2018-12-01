package com.jzfblog.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzfblog.store.domain.Order;
import com.jzfblog.store.service.OrderService;
import com.jzfblog.store.service.impl.OrderServiceImpl;
import com.jzfblog.store.web.base.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
	
	public String findOrders(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String state = request.getParameter("state");
		OrderService orderService = new OrderServiceImpl();
		// 如果获取到state为null或者为空
		if(null==state || "".equals(state)) {
			// 获取到全部订单
			List<Order> list = orderService.findAllOrders();
			request.setAttribute("allOrders", list);
		}else{
			List<Order> list = orderService.findAllOrders(state);
			request.setAttribute("allOrders", list);
		}
		
		// 转发到/admin/order/list.jsp
		return "/admin/order/list.jsp";
	}
	
	public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		// 获取订单oid
		String oid = request.getParameter("oid");
		// 查询该订单
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		// 设置订单状态
		order.setState(3);
		orderService.updateOrder(order);
		// 重定向到查询已发货 订单
		response.sendRedirect("/store/AdminOrderServlet?method=findOrders&state=3");
		//重定向到/admin/AdminOrderServlet
		return null;
	}

}
