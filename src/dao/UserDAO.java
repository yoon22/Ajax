package dao;


import java.util.Map;

public interface UserDAO {
	public int insertUser(Map<String,String> user);
	public Map<String,String> userLogin(String userId,String userPwd);
}
