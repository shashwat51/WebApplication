<%@ page import="com.model.User"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
<link href="styles/main.css" rel="stylesheet" type="text/css">
<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->
<script>
	var __adobewebfontsappname__ = "dreamweaver";
</script>
<script src="http://use.edgefonts.net/aclonica:n4:default.js"
	type="text/javascript"></script>
<style>
h2 {
	text-align: center;
}

table.center {
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>

<body>
	<%
		User user = (User) session.getAttribute("user");
		String loggedIn = (String) session.getAttribute("loggedIn");
		String success = (String) session.getAttribute("success");
		String userPrivilege = (String) session
				.getAttribute("userPrivilege");

		if (user == null)
			user = new User();

		if (loggedIn == null)
			loggedIn = "";

		if (userPrivilege == null)
			userPrivilege = "";

		if (success == null)
			success = "";

		if (userPrivilege.equalsIgnoreCase("admin")) {
	%>
	<%@include file="includes/header_admin.jsp"%>
	<%
		} else if (userPrivilege.equalsIgnoreCase("seller")) {
	%>
	<%@include file="includes/header_seller.jsp"%>
	<%
		} else if (userPrivilege.equalsIgnoreCase("buyer")) {
	%>
	<%@include file="includes/header.jsp"%>
	<%
		} else {
	%>
	<%@include file="includes/header_login.jsp"%>
	<%
		}

		if (loggedIn.equals("true")) {
			if (userPrivilege.equalsIgnoreCase("buyer")) {
	%>
	<table class="center">
		<tr>
			<td>
				<h3 align='center'>
					<%
						out.println(success);
					%>
				</h3>
			</td>
		</tr>
		<tr>
			<td><p>
					<%
						out.println("Welcome " + user.getFirstName() + " "
										+ user.getLastName() + ",");
					%>
				</p></td>
		</tr>
		<tr>
			<td><h3 align="center">"A room without books is like a body
					without a soul."</h3>
		</tr>
		<tr>
			<td><h3 align="center">Marcus Tullius Cicero</h3></td>
		</tr>
		<tr>
			<td><h3 align="left">Suggested Books</h3></td>
		</tr>
		<tr>
			<td><h3 align="left">Harry Potter and the Prisoner of
					Azkaban</h3></td>
			<td><h3 align="left">Harry Potter and the Chamber of
					Secrets</h3></td>
		</tr>
	</table>

	<%
		} else if (userPrivilege.equalsIgnoreCase("seller")) {

				String msgFromServlet = (String) request
						.getAttribute("msgFromServlet");
				if (msgFromServlet == null)
					msgFromServlet = "";

				out.println(msgFromServlet);
	%>

	<table class="center">
		<tr>
			<td>
				<h3 align='center'>
					<%
						out.println(success);
					%>
				</h3>
			</td>
		</tr>
		<tr>
			<td><p>
					<%
						out.println("Welcome " + user.getFirstName() + " "
										+ user.getLastName() + ",");
					%>
				</p></td>
		</tr>
		<tr>
			<td><a href="${pageContext.request.contextPath}/ViewOrderServlet">View Orders</a></td>
		</tr>
		<tr>
			<td><a href="EditOrder.jsp">Edit Orders</a></td>
		</tr>
		<!-- <tr>
			<td><a href="#">Contact Seller</a></td>
		</tr> -->
		<tr>
			<td><a href="AddBook.jsp">Add Book</a></td>
		</tr>
		<tr>
			<td>
				<form id="editDelete" action="AddBookServlet" method="Post">
					<input type="hidden" name="editDelete"> <input
						type="hidden" name="seller_id" value="<%=user.getUser_id()%>">
					<a href="javascript:{}"
						onclick="document.getElementById('editDelete').submit(); return false;">Edit
						Book</a>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<form id="editDelete" action="AddBookServlet" method="Post">
					<input type="hidden" name="editDelete"> <input
						type="hidden" name="seller_id" value="<%=user.getUser_id()%>">
					<a href="javascript:{}"
						onclick="document.getElementById('editDelete').submit(); return false;">Delete
						Book</a>
				</form>
			</td>
		</tr>
		<tr>
			<td><a href="#">Update Order Status</a></td>
		</tr>
		
	</table>

	<%
		} else if (userPrivilege.equalsIgnoreCase("admin")) {
	%>

	<table class="center">
		<tr>
			<td>
				<h3 align='center'>
					<%
						out.println(success);
					%>
				</h3>
			</td>
		</tr>
		<tr>
			<td><p>
					<%
						out.println("Welcome " + user.getFirstName() + " "
										+ user.getLastName() + ",");
					%>
				</p></td>
		</tr>
		<tr>
			<td><a href="${pageContext.request.contextPath}/ApproveSevlet">Approve
					Seller Registration</a></td>
		</tr>
		<tr>
			<td><a href="${pageContext.request.contextPath}/ViewOrderServlet">View Orders</a></td>
		</tr>
		<tr>
			<td><a href="EditOrder.jsp">Edit Orders</a></td>
		</tr>
		<tr>
			<td><a href="ContactSeller.jsp">Contact Seller</a></td>
		</tr>
		<tr>
			<td><a href="ContactBuyer.jsp">Contact Buyer</a></td>
		</tr>
		<tr>
			<td><a href="AddBook.jsp">Add Book</a></td>
		</tr>
		<tr>
			<td><a href="Search.jsp">Edit Book</a></td>
			<%-- <td><form id="editDelete" action="AddBookServlet" method="Post">
					<input type="hidden" name="editDelete"> <input
						type="hidden" name="seller_id" value="<%=user.getUser_id()%>">
					<a href="javascript:{}"
						onclick="document.getElementById('editDelete').submit(); return false;">Edit
						Book</a>
				</form>
			</td> --%>
		</tr>
		<tr>
		<td><a href="Search.jsp">Delete Book</a></td>
			<%-- <td><form id="editDelete" action="AddBookServlet" method="Post">
					<input type="hidden" name="editDelete"> <input
						type="hidden" name="seller_id" value="<%=user.getUser_id()%>">
					<a href="javascript:{}"
						onclick="document.getElementById('editDelete').submit(); return false;">Delete
						Book</a>
				</form>
			</td> --%>
		</tr>
		<tr>
			<td><a href="${pageContext.request.contextPath}/AdmintoSellerReviewServlet">View Seller Reviews</a></td>
		</tr>
		<tr>
			<td><a href="${pageContext.request.contextPath}/AdmintoBookReviewServlet">View Book Reviews</a></td>
		</tr>
	</table>

	<%
		}
		} else {
			out.println("<p>Please log in to access this page/p>");

		}
	%>
	<%@include file="includes/footer.jsp"%>
</body>
</html>
