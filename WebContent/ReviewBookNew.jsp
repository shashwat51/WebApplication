<%@ page import="com.model.User"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Review Book</title>
<link href="styles/main.css" rel="stylesheet" type="text/css">
<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.-->
<script>
	var __adobewebfontsappname__ = "dreamweaver"
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
	<%@ page import="java.util.*"%>
	<%@ page import="com.model.Books"%>
	<%@ page import="com.model.Approve"%>
	<%
		String loggedIn = (String) session.getAttribute("loggedIn");
			String userPrivilege = (String) session
		.getAttribute("userPrivilege");

			if (loggedIn == null)
		loggedIn = "";
			
			if (userPrivilege == null)
		userPrivilege = "";

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
			ArrayList<Books> result = new ArrayList<Books>();
			Books book = new Books();
			Approve approve=new Approve();
			List<Books> result1 = null;
			String book1=(String)session.getAttribute("book_id");
			result = (ArrayList) session.getAttribute("resultList");	
			result1=(List) session.getAttribute("resultList");
			User user = (User) session.getAttribute("user");
	%>
	<h2>
		<span style="font-size: 36px">Results</span>
	</h2>
	<table class="center" width="80%">

		<tr>
			<th width="100" align="center">BookID</th>
			

		</tr>
		<tr>
				
			</th>
			
					
			<th width="180" align="center">
				<form action='BookReviewServlet' method='post'>
					<tr>
				<td><span style="font-size: 24px">Rating: </span></td>
				<td><input type="radio" name="rating" value="1">1 
					<input type="radio" name="rating" value="2">2 
					<input type="radio" name="rating" value="3">3 
					<input type="radio" name="rating" value="4">4 
					<input type="radio" name="rating" value="5">5</td>
			</tr>
			<tr>
				<td><span style="font-size: 24px">Review: </span></td>
				<td><textarea name="review_text" rows="4" cols="50"
						maxlength="100"></textarea></td>
			</tr>
			<tr>
				<td><input type="hidden" name="Book_id"
						value="<%=book.getBook_id().toString()%>" />
						<input
						type="submit" name="submit" value="select" />
						</td>
			<%
				}
								
			
			%>
		</tr>
		<%
			
								
		%>
	</table>
	<%@include file="includes/footer.jsp"%>
</body>
</html>
