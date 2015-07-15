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

import com.model.Approve;
import com.model.ApproveDAO;
import com.model.AuthDAO;

/**
 * Servlet implementation class ApproveSevlet
 */
@WebServlet("/ApproveSevlet")
public class ApproveSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApproveSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session;
		session = request.getSession();

		ApproveDAO search = new ApproveDAO();
		List<Approve> result = null;
		String url = "/ApproveRegistration.jsp";
		result = search.getSeller();
		session = request.getSession();
		session.setAttribute("resultList", result);
		url = "/ApproveRegistration.jsp";
		pageNavigation(request, response, url);
		try {
			AuthDAO.DB_Close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	private void pageNavigation(HttpServletRequest request,
			HttpServletResponse response, String url) throws ServletException,
			IOException {
		RequestDispatcher rDispatch = getServletContext().getRequestDispatcher(
				url);
		rDispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String actiontotake = request.getParameter("actiontotake");
		//int user_id = (int) session.getAttribute("userId");
		String user = request.getParameter("user_idfordeletion");
		String url = "/ApproveRegistration.jsp";
		System.out.println("Approve user");
		HttpSession session;
		session = request.getSession();
		boolean result=false;
		int user_id = (int) session.getAttribute("userId");
		System.out.print(user_id);
		System.out.println("Action to take"+actiontotake);
		System.out.println("Userid from servlet"+actiontotake);
		if(actiontotake.equals("approve"))
		{
			//DeleteUser del=new DeleteUser();
			pageNavigation(request, response, url);
			//result=del.approveuser(Integer.parseInt(user));
			
		}
		request.setAttribute("msgFromServlet", "You account has been deleted");
		pageNavigation(request, response, url);
		
		
	}

}
