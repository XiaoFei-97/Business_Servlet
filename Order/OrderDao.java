package com.jzfblog.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.jzfblog.store.domain.Order;
import com.jzfblog.store.domain.OrderItem;
import com.jzfblog.store.domain.User;

public interface OrderDao {

	/**
	 * 保存订单
	 * @param order 订单
	 * @throws SQLException
	 */
	void saveOrder(Order order) throws SQLException;

	/**
	 * 保存订单项
	 * @param item 订单项
	 * @throws SQLException
	 */
	void saveOrderItem(OrderItem item) throws SQLException;
	
	/**
	 * 获取用户全部订单数
	 * @param user 用户
	 * @return 全部记录数
	 * @throws Exception
	 */
	int getTotalRecords(User user) throws Exception;
	
	/**
	 * 获取用户分页订单
	 * @param user 用户
	 * @param startIndex 起始页
	 * @param pageSize 一页记录数
	 * @return 
	 * @throws Exception
	 */
	List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception;

	/**
	 * 根据订单oid查找订单
	 * @param oid 订单编号
	 * @return 订单
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
	 * @param state 订单状态
	 * @return
	 */
	List<Order> findAllOrders(String state) throws Exception;
}
