package com.jzfblog.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.jzfblog.store.dao.UserDao;
import com.jzfblog.store.domain.User;
import com.jzfblog.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	/**
	 * 用户注册的dao实现
	 * @throws SQLException 
	 */
	public void userRegister(User user) throws SQLException {
		String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		// 将参数拼凑成数组类型
		Object[] params = {user.getUid(), user.getUsername(), 
				user.getPassword(), user.getName(), 
				user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), 
				user.getState(), user.getCode()};
		query.update(sql, params);
	}

	/**
	 * 用户验证码的激活
	 */
	public User userActive(String code) throws SQLException {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "SELECT * FROM USER WHERE code = ?";
		User user = query.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	/**
	 * 更新用户状态，清除用户激活码
	 */
	public void updateUser(User user) throws SQLException {
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username=?, "
				+ "password=?, name=?, email=?, telephone=?, "
				+ "birthday=?, sex=?, state=?, code=? where uid=?";
		Object[] params = {user.getUsername(), user.getPassword(), 
				user.getName(), user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid()};
		query.update(sql, params);
	}

	public User userLogin(User user) throws SQLException {
		
		QueryRunner query = new QueryRunner(JDBCUtils.getDataSource());
		// 查询数据库是否存在该用户名与密码
		String sql = "select * from user where username=? and password=?";
		return query.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
	}
	
}
