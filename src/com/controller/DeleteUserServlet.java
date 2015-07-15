package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.AuthDAO;
import com.model.DeleteUser;

/**
 * Servlet implementation class DeleteUserServlet
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "/Login.jsp";
		System.out.println("Delete user");
		HttpSession session;
		session = request.getSession();
		int user_id = (int) session.getAttribute("userId");
		AuthDAO auth=new AuthDAO();
		String privilege=auth.getUserPrivilege(user_id);
		
		boolean result=false;
		DeleteUser del=new DeleteUser();
		if(privilege.equals("buyer"))
		{
		result=del.deleteuser(user_id);
		}
		else
			result=del.disapproveuser(user_id);	
		if(result==true)
		{
			session.invalidate();
		}
		else{session.invalidate();}
		request.setAttribute("msgFromServlet", "You account has been deleted");
		pageNavigation(request, response, url);
	}
	private void pageNavigation(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException{
		RequestDispatcher rDispatch = getServletContext().getRequestDispatcher(url);
		rDispatch.forward(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = "/Welcome.jsp";
		System.out.println("Delete user post");
		HttpSession session;
		session = request.getSession();
		int user_id=Integer.parseInt(request.getParameter("user_idfordeletion"));
		String actiontoperform=request.getParameter("actiontotake");
		boolean result=false;
		DeleteUser del=new DeleteUser();
		//result=del.approveuser(user_id);
		System.out.print("user_id"+user_id);
		System.out.print("actiontoperform"+actiontoperform);
		if(actiontoperform.equals("approve"))
		{
			System.out.print("Approved Seller"+user_id);
			result=del.approveuser(user_id);
			pageNavigation(request, response, url);
		}
		else{
			result=del.disapproveuser(user_id);
			pageNavigation(request, response, url);
			}
			
		
	}

}
