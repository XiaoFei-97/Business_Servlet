package com.jzfblog.store.service;

import java.util.List;

import com.jzfblog.store.domain.Order;
import com.jzfblog.store.domain.PageModel;
import com.jzfblog.store.domain.User;

public interface OrderService {

	/**
	 * 保存订单下
	 * @param order 订单
	 * @throws Exception
	 */
	void saveOrder(Order order);
	
	/**
	 * 查询用户订单
	 * @param user 用户
	 * @param currentPage 当前页
	 * @return 订单列表
	 * @throws Exception 异常
	 */
	PageModel findMyOrderWithPage(User user, int currentPage) throws Exception;

	/**
	 * 根据订单oid查找订单
	 * @param oid 订单编号
	 * @return
	 */
	Order findOrderByOid(String oid) throws Exception;

	/**
	 * 更新订单
	 * @param order
	 */
	void updateOrder(Order order) throws Exception;

	/**
	 * 查询所有订单
	 * @return 订单列表
	 */
	List<Order> findAllOrders() throws Exception;

	/**
	 * 查询带有状态的订单
	 * @param state 状态
	 * @return
	 */
	List<Order> findAllOrders(String state) throws Exception;
}
