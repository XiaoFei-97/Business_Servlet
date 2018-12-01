package com.jzfblog.store.service.impl;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import com.jzfblog.store.dao.UserDao;
import com.jzfblog.store.dao.impl.UserDaoImpl;
import com.jzfblog.store.domain.User;
import com.jzfblog.store.service.UserService;
import com.jzfblog.store.utils.BeanFactory;

public class UserServiceImpl implements UserService{

	/**
	 * 实现用户注册功能
	 * @throws SQLException 
	 */
	public void userRegister(User user) throws SQLException {
		
		UserDao UserDao = (UserDao)BeanFactory.createObject("UserDao");
		UserDao.userRegister(user);
	}
	
	/**
	 * 实现用户码的激活
	 */
	public boolean userActive(String code) throws SQLException {
		
		UserDao UserDao = new UserDaoImpl();
		// 对DB发送一个命令selcet * from user whrere code = ?
		User user = UserDao.userActive(code);
		if(null != user) {
			// 可通过激活码查询用户  修改用户的状态，清除激活码
			user.setState(1);
			user.setCode(null);
			// 对数据库执行一次真实的更新操作
			UserDao.updateUser(user);
			return true;
		}else {
			// 不可以通过激活码查询用户
			return false;
		}
	}

	/**
	 * 实现用户登录功能
	 */
	public User userLogin(User user) throws SQLException {
		
		UserDao UserDao = new UserDaoImpl();
		User uu = UserDao.userLogin(user);
		if(null == uu) {
			throw new RuntimeException("密码有误!");
		}else if(uu.getState() == 0){
			throw new RuntimeException("用户未激活!");
		}else {
			return uu;
		}
		
	}

}
