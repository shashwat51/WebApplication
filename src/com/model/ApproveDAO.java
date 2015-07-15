package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

public class ApproveDAO {
	public static DatabaseConnection dc = null;
	private static Connection conn = null;

	public ApproveDAO() {
		dc = new DatabaseConnection();
		conn = dc.getConnection();
	}

	@SuppressWarnings("resource")
	public static ArrayList<Approve> getSeller() {
		ArrayList<Approve> app_arraylist = new ArrayList<Approve>();
		/*
		 * String sql =
		 * "select book_id, seller_id, quantity from cart_details where user_id = "
		 * + AuthDAO.user_id;
		 */

		String sql = "SELECT user_id,username,pw,user_privilege,active "
				+ "FROM user "
				+ "WHERE user_privilege='seller' and active='false' "
				;

		// System.out.println("SQL : " + sql);
		ResultSet result;
		PreparedStatement pStatement = null;
		try {
			// System.out.println("I am here 1");
			pStatement = (PreparedStatement) conn.prepareStatement(sql);
			result = pStatement.executeQuery();
			for (int i = 0; result.next(); i++) {
				Approve app = new Approve();
				app.setuserid(result.getString(1));
				app.setusername(result.getString(2));
				app.setpw(result.getString(3));
				app.setprivilege(result.getString(4));
				app.setaction(result.getString(5));
				// System.out.println("Count : " + total_quantity);
				app_arraylist.add(i, app);
				// System.out.println("Size : " + cart_arraylist.size());
			}
			return app_arraylist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Class Not Found : " + e.getMessage());
			return null;
		}
	}

	public static List<Approve> getSellers() {
		ArrayList<Approve> app_arraylist = new ArrayList<Approve>();
		/*
		 * String sql =
		 * "select book_id, seller_id, quantity from cart_details where user_id = "
		 * + AuthDAO.user_id;
		 */

		String sql = "SELECT user_id,username,pw,user_privilege,active "
				+ "FROM user "
				+ "WHERE user_privilege='seller' and active='true' "
				;

		// System.out.println("SQL : " + sql);
		ResultSet result;
		PreparedStatement pStatement = null;
		try {
			// System.out.println("I am here 1");
			pStatement = (PreparedStatement) conn.prepareStatement(sql);
			result = pStatement.executeQuery();
			for (int i = 0; result.next(); i++) {
				Approve app = new Approve();
				app.setuserid(result.getString(1));
				app.setusername(result.getString(2));
				app.setpw(result.getString(3));
				app.setprivilege(result.getString(4));
				app.setaction(result.getString(5));
				// System.out.println("Count : " + total_quantity);
				app_arraylist.add(i, app);
				// System.out.println("Size : " + cart_arraylist.size());
			}
			return app_arraylist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Class Not Found : " + e.getMessage());
			return null;
		}
	
}}