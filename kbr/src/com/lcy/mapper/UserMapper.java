package com.lcy.mapper;

import java.util.List;
import java.util.Map;

import com.lcy.pojo.User;


public interface UserMapper {
	public List<User> findUsers(Integer id);
	public void deleteUser(Integer id);
	public int insertUser(User user);
	public void updateUser(User user);
	public List<Map<String,Object>> findUser();
	public void importUser(List<Map<String,Object>>list);
}
