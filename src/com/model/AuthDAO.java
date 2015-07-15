package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AuthDAO {

	public static DatabaseConnection dc = null;
	private static Connection conn = null;
	public static int user_id = 0;
	public AuthDAO() {
		dc = new DatabaseConnection();
		conn = dc.getConnection();
	}

	public static boolean isUserNameAvailable(String userName) {

		String sql = "SELECT user_id FROM user WHERE username = '" + userName
				+ "'";
		ResultSet result;

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			result = ps.executeQuery();
			return result.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println("Stack trace");
			e.printStackTrace();
			return false;
		}
	}

	public static int checkUserPass(String userName, String password) {

		String sql = "SELECT user_id FROM user WHERE username = '" + userName
				+ "' AND pw ='" + password + "'"+" AND active ='true'";
		ResultSet result;

		// System.out.println("In checkUserPass");

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			result = ps.executeQuery();

			if (result.next()) {
				return result.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			//System.out.println("Stack trace");
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public static User getUserById(int userId) {

		String sql = "select * from user_profile where user_id = " + userId;

		ResultSet result;
		User user = new User();

		try {

			PreparedStatement ps = conn.prepareStatement(sql);
			result = ps.executeQuery();
			if (result.next()) {
				user.setFirstName(result.getString("first_name"));
				user.setLastName(result.getString("last_name"));
				user.setMiddleName(result.getString("middle_name"));
				user.setEmail(result.getString("email"));
				user.setAddress1(result.getString("address1"));
				user.setAddress2(result.getString("address2"));
				user.setCity(result.getString("city"));
				user.setState(result.getString("state"));
				user.setZip(result.getString("zipcode"));
				user.setPhone(result.getString("phone"));
			}
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println("Stack trace");
			e.printStackTrace();
			return null;
		}
	}

	public static String getUserPrivilege(int userId) {
		String userPrivilege = null;
		String sql = "select user_privilege from user where user_id = "
				+ userId;

		ResultSet result = null;

		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			result = ps.executeQuery();

			if (result.next()) {
				userPrivilege = result.getString("user_privilege");
				return userPrivilege;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println("Stack trace");
			e.printStackTrace();
			return null;
		}

		return userPrivilege;
	}
	
	public static boolean deleteUser(int user_id) {
		String sql = "DELETE FROM USER WHERE user_id = " + user_id;
		
		PreparedStatement pStatement = null;
		try {
			pStatement = conn.prepareStatement(sql);
//			System.out.println("Inside enterUserName:  pStatement = "
//					+ pStatement);
			int returnValue = pStatement.executeUpdate();
			if (returnValue == 1)
				return true;
		} catch (SQLException e) {
			System.out.println("Class Not Found : " + e.getMessage());
			return false;
		}
		return false;
	}

	public static boolean enterUserName(int userId, String firstName,
			String middleName, String lastName, String email, String address1,
			String address2, String city, String state, String zipcode,
			String phone) {

		String sql = "INSERT INTO user_profile VALUES (" + userId + ",'"
				+ firstName + "','" + middleName + "','" + lastName + "','"
				+ email + "','" + address1 + "','" + address2 + "','" + city
				+ "','" + state + "','" + zipcode + "','" + phone + "')";

		// System.out.println("Inside enterUserName:  SQL = " + sql);

		PreparedStatement pStatement = null;
		try {
			pStatement = conn.prepareStatement(sql);
//			System.out.println("Inside enterUserName:  pStatement = "
//					+ pStatement);
			int returnValue = pStatement.executeUpdate();
			if (returnValue == 1)
				return true;
		} catch (SQLException e) {
			System.out.println("Class Not Found : " + e.getMessage());
			return false;
		}
		return false;
	}

	public static int enterNewUser(String userName, String password,
			String userPrivilege) {
		String sql=null;
if(userPrivilege.equals("buyer")){
		 sql = "INSERT INTO user (username, pw, user_privilege, active) VALUES('"
				+ userName
				+ "','"
				+ password
				+ "','"
				+ userPrivilege
				+ "','"
				+ "true" + "')";
}
else{
	 sql = "INSERT INTO user (username, pw, user_privilege, active) VALUES('"
			+ userName
			+ "','"
			+ password
			+ "','"
			+ userPrivilege
			+ "','"
			+ "false" + "')";
	
	 
}

		String sql2 = "SELECT user_id FROM user WHERE username = '" + userName
				+ "'";
		// System.out.println("Inside enterNewUser:  SQL = " + sql);
		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			pStatement = (PreparedStatement) conn.prepareStatement(sql);
			int k = pStatement.executeUpdate();
			if (k == 1) {
//				System.out.println("Inside enterNewUser:  SQL2 = " + sql2);
				pStatement = conn.prepareStatement(sql2);
				result = pStatement.executeQuery();
				while (result.next()) {
					if(userPrivilege.equals("seller")){
						entersellerApproval(result.getInt(1));
					return result.getInt(1);
					}
					else{
						return result.getInt(1);
					}
				}
			}
			pStatement.close();

		} catch (SQLException e) {
			return -1;
		}
		return -1;
	}
	
	public boolean updateProfile(int userId, String firstName,
			String middleName, String lastName, String email, String address1,
			String address2, String city, String state, String zipcode,
			String phone) {

		String updateSql = "UPDATE user_profile SET first_name='" + firstName + "', middle_name='" + middleName	+ "', last_name='" + lastName
				+ "', email='" + email + "', address1='" + address1	+ "', address2='" + address2
				+ "', city='" + city + "', state='" + state + "', zipcode='" + zipcode + "', phone='" + phone 
				+ "' WHERE user_id=" + userId + ";";
		
		PreparedStatement pStatement = null;
		
		try {
			
			pStatement = conn.prepareStatement(updateSql);
			int returnValue = pStatement.executeUpdate();
		
			if (returnValue == 1)
				return true;
			
		} catch (SQLException e) {
			System.out.println("Class Not Found : " + e.getMessage());
			return false;
		}
		
		return false;
	}
	
	public boolean updatePassword(int userId, String password) {

		String updateSql = "UPDATE user SET pw='" + password + "' WHERE user_id=" + userId + ";";
		
		PreparedStatement pStatement = null;
		System.out.println(updateSql);
		try {
			
			pStatement = conn.prepareStatement(updateSql);
			int returnValue = pStatement.executeUpdate();
		
			if (returnValue == 1)
				return true;
			
		} catch (SQLException e) {
			System.out.println("Class Not Found : " + e.getMessage());
			return false;
		}
		
		return false;
	}


	public String checkApproval(int userID)
	{
		String status = null;
		String sql = "select status from approval where seller_id = '"
				+ userID+"'";

		ResultSet result = null;
		System.out.println("Reached Checkapproval");
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			result = ps.executeQuery();

			if (result.next()) {
				status = result.getString("status");
				System.out.print(status);
				return status;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.out.println("Stack trace");
			e.printStackTrace();
			return null;
		}

		return status;
	}
	
	
	public static void DB_Close() throws Throwable {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
  
	public static boolean entersellerApproval(int userid)
	{
		System.out.println("entersellerApproval");
		
		
		String sql = "INSERT INTO approval VALUES ('"+userid+"','waiting','waiting')";

		
System.out.println(""+sql);
		PreparedStatement pStatement = null;
		try {
			pStatement = conn.prepareStatement(sql);

			int returnValue = pStatement.executeUpdate();
			if (returnValue == 1)
				return true;
		} catch (SQLException e) {
			System.out.println("Class Not Found : " + e.getMessage());
			return false;
		}
		return false;
	}
	public static int getUserId(String username) {

		String sql = "SELECT user_id FROM user WHERE username = '" + username
				+ "'";

		PreparedStatement pStatement = null;
		ResultSet result = null;
		try {
			pStatement = conn.prepareStatement(sql);
			result = pStatement.executeQuery();
			while (result.next()) {
				return result.getInt("user_id");
			}

			pStatement.close();

		} catch (SQLException e) {
			return -1;
		}
		return -1;
	}
	public ArrayList<User> getAllBuyers(int userId) {
		// TODO Auto-generated method stub
		String sql = "";
		if (userId == -1)
			sql = "select * from user, user_profile where user.user_id = user_profile.user_id and user.user_privilege = \"buyer\"";
		else
			sql = "select * from user, user_profile where user.user_id = user_profile.user_id and user.user_id = " + userId;
		ArrayList<User> userList = new ArrayList<User>();
		ResultSet result;

		try {
			// System.out.println("SQL : "+sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			result = ps.executeQuery();

			for (int i = 0; result.next(); i++) {

				User user = new User();
				user.setUserName(result.getString("username"));
				user.setFirstName(result.getString("first_name"));
				user.setMiddleName(result.getString("middle_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				user.setAddress1(result.getString("address1"));
				user.setAddress2(result.getString("address2"));
				user.setCity(result.getString("city"));
				user.setState(result.getString("state"));
				user.setZip(result.getString("zipcode"));
				user.setPhone(result.getString("phone"));
				userList.add(i, user);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;

	}
	
	public ArrayList<User> getAllSellers(int userId) {
		// TODO Auto-generated method stub
		String sql = "";
		if (userId == -1)
			sql = "select * from user, user_profile where user.user_id = user_profile.user_id and user.user_privilege = \"seller\"";
		else
			sql = "select * from user, user_profile where user.user_id = user_profile.user_id and user.user_id = " + userId;
		ArrayList<User> userList = new ArrayList<User>();
		ResultSet result;

		try {
			// System.out.println("SQL : "+sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			result = ps.executeQuery();

			for (int i = 0; result.next(); i++) {

				User user = new User();
				user.setUserName(result.getString("username"));
				user.setFirstName(result.getString("first_name"));
				user.setMiddleName(result.getString("middle_name"));
				user.setLastName(result.getString("last_name"));
				user.setEmail(result.getString("email"));
				user.setAddress1(result.getString("address1"));
				user.setAddress2(result.getString("address2"));
				user.setCity(result.getString("city"));
				user.setState(result.getString("state"));
				user.setZip(result.getString("zipcode"));
				user.setPhone(result.getString("phone"));
				userList.add(i, user);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userList;

	}
	
}
