package com.lcy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcy.mapper.UserMapper;
import com.lcy.pojo.User;
import com.lcy.service.UserService;
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Override
	public List<User> findUsers(Integer id) {
		return userMapper.findUsers(id);
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		userMapper.deleteUser(id);
	}
	
	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.insertUser(user);
	}

	@Override
	public void updateUser(User user) {
		userMapper.updateUser(user);
		
	}

	@Override
	public List<Map<String,Object>> findUser() {
		// TODO Auto-generated method stub
		return userMapper.findUser();
	}

	@Override
	public void importUser(List<Map<String,Object>> list) {
		// TODO Auto-generated method stub
	    userMapper.importUser(list);
	}

}
