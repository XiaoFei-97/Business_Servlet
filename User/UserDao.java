package com.jzfblog.store.dao;

import java.sql.SQLException;

import com.jzfblog.store.domain.User;

public interface UserDao {

	/**
	 * 用户注册
	 * @param user
	 */
	public void userRegister(User user) throws SQLException;
	
	/**
	 * 激活用户验证码
	 * @param code 激活码
 	 * @return 该激活码用户
	 * @throws SQLException
	 */
	public User userActive(String code) throws SQLException;
	
	/**
	 * 更新用户状态，清除激活码
	 * @param user
	 * @throws SQLException
	 */
	public void updateUser(User user) throws SQLException;
	
	/**
	 * 实现用户登录
	 * @param user 用户
	 * @return 用户信息
	 * @throws SQLException
	 */
	public User userLogin(User user) throws SQLException;
}
