package service.impl;

import java.util.HashMap;
import java.util.Map;

import dao.UserDAO;
import dao.impl.UserDAOImpl;
import service.UserService;

public class UserServiceImpl implements UserService {
	private UserDAO udao = new UserDAOImpl();
	
	public int insertUser(Map<String, String> user) {
	
		return udao.insertUser(user);
	}
	
	public static void main(String[] args) {
		UserService us = new UserServiceImpl();
		Map<String,String> user = new HashMap<>();
		user.put("ui_name","서비스테스트");
		user.put("ui_id","sy");
		user.put("ui_pwd","5588");
		user.put("ui_email","sy@gmail.com");
		System.out.println(us.insertUser(user));
	}

	@Override
	public Map<String, String> userLogin(String userid) {
	
		return udao.userLogin(userid);
	}
}
