package com.jzfblog.store.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.jzfblog.store.dao.OrderDao;
import com.jzfblog.store.domain.Order;
import com.jzfblog.store.domain.OrderItem;
import com.jzfblog.store.domain.Product;
import com.jzfblog.store.domain.User;
import com.jzfblog.store.utils.JDBCUtils;
import com.mchange.v2.codegen.CodegenUtils;

public class OrderDaoImpl implements OrderDao {

	/**
	 * 保存订单
	 * @throws SQLException 
	 */
	public void saveOrder(Order order) throws SQLException {
		
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(), 
				order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid()};
		query.update(sql, params);
	}

	/**
	 * 保存订单项
	 * @throws SQLException 
	 */
	public void saveOrderItem(OrderItem item) throws SQLException {
		
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] params = {item.getItemid(), item.getQuantity(), 
				item.getTotal(), item.getProduct().getPid(), item.getOrder().getOid()};
		query.update(sql, params);
	}

	/**
	 * 获取全部记录数
	 */
	public int getTotalRecords(User user) throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid=?";
		Long num = (Long) query.query(sql, new ScalarHandler(), user.getUid());
		return num.intValue();
	}

	/**
	 * 获取用户分页订单
	 */
	public List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid=? limit ?, ?";
		List<Order> list = query.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);
		
		// 遍历所有订单
		for (Order order : list) {
			// 获取到每笔订单oid，查询每笔单下的订单项以及订单项对应的商品信息
			
			// 由于返回的数据是多个表，多行数据MapListHandler封装返回的数据
			String oid = order.getOid();
			sql = "select * from orderItem o, product p where o.pid=p.pid and oid=?";
			List<Map<String , Object>> list02 = query.query(sql, new MapListHandler(), oid);
			
			// 遍历list
			for (Map<String, Object> map : list02) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
				
				// 由于BeanUtils将字符串"1992-3-3"的setBirthday参数传递有问题
				// 1.创建时间类型的转换器	
				DateConverter dt = new DateConverter();
				// 2.设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3.注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				// 将map中属于orderItem数据自动填充到orderItem对象上
				BeanUtils.populate(orderItem, map);
				// 将map中属于product数据自动填充到product对象上
				BeanUtils.populate(product, map);
				
				// 让每个订单项与商品发生联系
				orderItem.setProduct(product);
				// 让每个订单项和订单项的集合发生关系
				order.getList().add(orderItem);
			}
		}
		
		return list;
	}
	
	/**
	 * 根据订单编号查找订单
	 */
	public Order findOrderByOid(String oid) throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid=?";
		
		Order order = query.query(sql, new BeanHandler<Order>(Order.class), oid);
		
		// 根据订单id查询订单下所有的订单项对应的商品信息
		sql = "select * from orderItem o, product p where o.pid=p.pid and oid=?";
		List<Map<String , Object>> list02 = query.query(sql, new MapListHandler(), oid);
		
		// 遍历list
		for (Map<String, Object> map : list02) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			
			// 由于BeanUtils将字符串"1992-3-3"的setBirthday参数传递有问题
			// 1.创建时间类型的转换器	
			DateConverter dt = new DateConverter();
			// 2.设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3.注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			// 将map中属于orderItem数据自动填充到orderItem对象上
			BeanUtils.populate(orderItem, map);
			// 将map中属于product数据自动填充到product对象上
			BeanUtils.populate(product, map);
			
			// 让每个订单项与商品发生联系
			orderItem.setProduct(product);
			// 让每个订单项和订单项的集合发生关系
			order.getList().add(orderItem);

		}
		return order;
	}

	/**
	 * 更新订单
	 */
	public void updateOrder(Order order) throws Exception {
		
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update orders set ordertime=?, total=?, state=?, address=?, name=?, telephone=? where oid=?";
		Object[] params = {order.getOrdertime(), order.getTotal(), 
				order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid()};
		query.update(sql, params);
	}

	/**
	 * 查询所有订单
	 */
	public List<Order> findAllOrders() throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders";
		return query.query(sql, new BeanListHandler<Order>(Order.class));
	}

	/**
	 * 查询带有状态的订单
	 */
	public List<Order> findAllOrders(String state) throws Exception {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where state=?";
		return query.query(sql, new BeanListHandler<Order>(Order.class), state);
	}
	
}
