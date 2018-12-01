package com.jzfblog.store.service.impl;

import java.util.List;

import com.jzfblog.store.dao.OrderDao;
import com.jzfblog.store.dao.impl.OrderDaoImpl;
import com.jzfblog.store.domain.Order;
import com.jzfblog.store.domain.OrderItem;
import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.User;
import com.jzfblog.store.service.OrderService;
import com.jzfblog.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	/**
	 * 保存订单
	 */
	public void saveOrder(Order order){
		
		try {
			// 保存订单和订单下面所有的订单项（同时成功，失败）
			JDBCUtils.startTransaction();
			OrderDao orderDao = new OrderDaoImpl();
			orderDao.saveOrder(order);
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
		}
	}

	
	public PageModel findMyOrderWithPage(User user, int currentPage) throws Exception {
		
		// 创建pageModel，目的：携带分页参数
		OrderDao OrderDao = new OrderDaoImpl();
		int totalRecords = OrderDao.getTotalRecords(user);
		PageModel pageModel = new PageModel(currentPage, totalRecords, 3);
		// 关联集合
		List list = OrderDao.findMyOrderWithPage(user, pageModel.getStartIndex(), pageModel.getPageSize());;
		pageModel.setRecords(list);
		// 关联url
		pageModel.setUrl("OrderServlet?method=findMyOrderWithPage");
		return pageModel;
	}

	/**
	 * 根据订单编号差最后订单
	 */
	public Order findOrderByOid(String oid) throws Exception {
		
		// 调用dao层：根据订单编号查找订单信息
		OrderDao orderDao = new OrderDaoImpl();
		Order order = orderDao.findOrderByOid(oid);
		
		return order;
	}

	/**
	 * 更新订单
	 */
	public void updateOrder(Order order) throws Exception {
		
		OrderDao orderDao = new OrderDaoImpl();
		orderDao.updateOrder(order);
	}

	/**
	 * 查询所有订单
	 */
	public List<Order> findAllOrders() throws Exception {
		OrderDao orderDao = new OrderDaoImpl();
		return orderDao.findAllOrders();
	}


	/**
	 * 查询带有状态的订单
	 */
	public List<Order> findAllOrders(String state) throws Exception {
		OrderDao orderDao = new OrderDaoImpl();
		return orderDao.findAllOrders(state);
	}

}
