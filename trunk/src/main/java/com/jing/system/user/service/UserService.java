package com.jing.system.user.service;

import com.jing.system.user.model.User;

/**
 * 用户操作接口
 * @author Administrator
 *
 */
public interface UserService {

	User login(String userName,String password);
}
