package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.AuthDAO;
import com.model.Cart;
import com.model.CartDao;
import com.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
        String privilege="";
		String url = "/Login.jsp";
		HttpSession session;
		User user;
		
		String message = "";
		String userPrivilege = "";
		int userId = -1;
		
		AuthDAO auth;
		
		if(userName.length()==0 && password.length()==0)	
			message = "Enter UserName and Password";							
		else if(password.length()==0)
				message = "Password not filled in";
		else if(userName.length()==0)
				message = "UserName not filled in";
		else	{
			auth = new AuthDAO();

			userId = auth.checkUserPass(userName, password);
			//privilege=auth.getUserPrivilege(userId);
			userPrivilege = auth.getUserPrivilege(userId); 
			
			if(userId == 0){
				message = "UserName/Password Not valid";
			}
			else if(userPrivilege.equals("seller"))
			{
				String approval="";
				System.out.println("Reached here");
				approval=auth.checkApproval(userId);
				if(approval.equals("approved"))
				{

					user = auth.getUserById(userId);
					userPrivilege = auth.getUserPrivilege(userId); 
					
					user.setUser_id(userId);
					user.setUserName(userName);

					session = request.getSession();
					session.setAttribute("user", user);
					session.setAttribute("loggedIn","true");
					session.setAttribute("userPrivilege", userPrivilege);
					session.setAttribute("userId", userId);
					AuthDAO.user_id = userId;
					// using session because if we use request it wont store if we access the page randomly
					CartDao cartd = new CartDao();
					List<Cart> result = null;
					result = cartd.getCart();
					session = request.getSession();
					session.setAttribute("cartList", result);
					request.setAttribute("success","Login Successful");
//					request.setAttribute("loggedIn","true");
					url = "/Welcome.jsp";
					pageNavigation(request,response,url);	
					try {
						AuthDAO.DB_Close();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					return;
					
				}				
				else if(userPrivilege.equals("waiting")){
					message = "UserNot approved so far by admin";
				}
				else{
					message = "User declined admin";
				}
			}
			else	{		
				
			//	user = AuthDAO.getUserById(userId);
				
				user = auth.getUserById(userId);
				userPrivilege = auth.getUserPrivilege(userId); 
				
				user.setUser_id(userId);
				user.setUserName(userName);
				
				session = request.getSession();
				session.setAttribute("user", user);
				session.setAttribute("loggedIn","true");
				session.setAttribute("userPrivilege", userPrivilege);
				session.setAttribute("userId", userId);
				AuthDAO.user_id = userId;
				// using session because if we use request it wont store if we access the page randomly
				CartDao cartd = new CartDao();
				List<Cart> result = null;
				result = cartd.getCart();
				session = request.getSession();
				session.setAttribute("cartList", result);
				request.setAttribute("success","Login Successful");
//				request.setAttribute("loggedIn","true");
				url = "/Welcome.jsp";
				pageNavigation(request,response,url);										
				try {
					AuthDAO.DB_Close();
				} catch (Throwable e) {
					e.printStackTrace();
				}
				return;
			}
		}

		request.setAttribute("message", message);
		pageNavigation(request,response,url);						
		
		try {
			AuthDAO.DB_Close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}	
	
	private void pageNavigation(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException{
		RequestDispatcher rDispatch = getServletContext().getRequestDispatcher(url);
		rDispatch.forward(request,response);
	}

}

