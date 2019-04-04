package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.UserDAO;
import db.DBCon;

public class UserDAOImpl implements UserDAO {
	private  String insertUser = "insert into user_info"
			+ "(ui_num,ui_name,ui_id,ui_pwd,ui_email)"
			+ " values (seq_ui_num.nextval,?,?,?,?)";  
	private String userLogin = "select * from user_info where ui_id=? and ui_pwd=?";
	
	@Override
	public int insertUser(Map<String, String> user) {
		try {
			PreparedStatement ps = DBCon.getCon().prepareStatement(insertUser);
			ps.setString(1, user.get("ui_name"));
			ps.setString(2, user.get("ui_id"));
			ps.setString(3, user.get("ui_pwd"));
			ps.setString(4, user.get("ui_email"));			
			return ps.executeUpdate();
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			DBCon.close();
		}
		return 0;
	}
	
	@Override
	public Map<String, String> userLogin(String userId,String userPwd) {
		try {
			PreparedStatement ps = DBCon.getCon().prepareStatement(userLogin);
			ps.setString(1,userId);	
			ps.setString(2, userPwd);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Map<String,String> u = new HashMap<>();				
				u.put("ui_name",rs.getString("ui_name"));
				u.put("ui_id",rs.getString("ui_id"));
				u.put("ui_pwd",rs.getString("ui_pwd"));
				u.put("ui_email",rs.getString("ui_email"));
				return u;				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBCon.close();
		}
		
		
		return null;
	}
}
