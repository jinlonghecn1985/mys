package com.jing.system.user.service.impl;

import org.springframework.stereotype.Service;

import com.jing.system.user.model.User;
import com.jing.system.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Override
	public User login(String userName, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setEnabled(true);
		return user;
	}

}
