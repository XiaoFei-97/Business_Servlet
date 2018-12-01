package com.jzfblog.store.service;

import java.sql.SQLException;

import com.jzfblog.store.domain.User;

public interface UserService {

	/**
	 * 注册用户
	 * @param user 用户
	 */
	public void userRegister(User user) throws SQLException;
	
	/**
	 * 激活用户码
	 * @param code 激活码
	 * @return true：激活成功  false：激活失败
	 * @throws SQLException
	 */
	public boolean userActive(String code) throws SQLException;
	
	/**
	 * 登录用户
	 * @param user 用户
	 * @return 用户信息
	 * @throws SQLException
	 */
	public User userLogin(User user) throws SQLException;
}