<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>View Order</title>
<link href="styles/main.css" rel="stylesheet" type="text/css">
<!--The following script tag downloads a font from the Adobe Edge Web Fonts server for use within the web page. We recommend that you do not modify it.--><script>var __adobewebfontsappname__="dreamweaver"</script><script src="http://use.edgefonts.net/aclonica:n4:default.js" type="text/javascript"></script>
<style>
h2 {text-align:center;}
table.center {
    margin-left:auto; 
    margin-right:auto;
  }
  input.center {
    margin-left:auto; 
    margin-right:auto;
  }
</style>
</head>

<body>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.model.ViewOrder"%>
		<% String loggedIn = (String) session.getAttribute("loggedIn");
		String msgFromCheckout = (String) session.getAttribute("messageFromCheckoutServlet");
		//String error = (String) session.getAttribute("error");
		boolean noEntry = false;
		String userPrivilege = (String) session
				.getAttribute("userPrivilege");

		if (loggedIn == null)
			loggedIn = "";
		
		if (msgFromCheckout == null)
			msgFromCheckout = "";

		/*if (error == null)
			error = "";*/

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
	%>

<h2><span style="font-size: 36px">View Orders</span></h2>
<h4><% out.println(msgFromCheckout); %></h4>
<%
ArrayList<ViewOrder> result = new ArrayList<ViewOrder>();
ViewOrder order = new ViewOrder();
result = (ArrayList) request.getAttribute("orderList");
if(result == null || result.size()==0)
{
	//System.out.println("I am here 4");
	result = new ArrayList<ViewOrder>();
	//System.out.println("No entry");
	noEntry = true;
}
%>
<% if(noEntry == false){ 
	//System.out.println("I am here 3");
	int uniqueOrder = 0;
	 for (int i = 0; i < result.size(); i++) {
	    	order = result.get(i);
	    	if(i<1){
	    	%>
	    			<table class="center" width="100%">
	<tr>
		<td><span style="font-size: 16px"><b>Order Placed: </b> <% out.println(order.getOrder_time()); %></span></td>	
	</tr>
    <tr>
		<td><span style="font-size: 16px"><b>Order Number:</b> <% out.println(order.getOrder_id()); %></span></td>	
	</tr>
	<% if (userPrivilege.equalsIgnoreCase("admin")) { %>
	<tr>
		<td><span style="font-size: 16px"><b>User ID:</b> <% out.println(order.getBuyer_id()); %></span></td>	
	</tr>
    <% }
	if (userPrivilege.equalsIgnoreCase("buyer") || userPrivilege.equalsIgnoreCase("admin")) { %>
	<tr>
		<td><span style="font-size: 16px"><b>Total Quantity:</b> <% out.println(order.getTotal_quantity()); %></span></td>	
	</tr>
    <tr>
		<td><span style="font-size: 16px"><b>Order Total: </b>$<% out.println(String.format("%.2f", order.getTotal_price())); %></span></td>	
	</tr><% } %>
	<tr><% if(!order.getOrder_status().equalsIgnoreCase("completed")){%>
		<td><span style="font-size: 16px"><form action='EditOrderServlet'  method='post'>
		<input type="hidden" name="order_id" value="<%=order.getOrder_id()%>" />
 		<input align="center" type="submit" name="edit" value="Edit Order">
    </form></span></td>
    <% if (!userPrivilege.equalsIgnoreCase("buyer")) { %>
    <td><span style="font-size: 16px"><form action='EditOrderStatusServlet'  method='post'>
		<input type="hidden" name="order_id" value="<%=order.getOrder_id()%>" />
		<input type="hidden" name="book_id" value="<%=order.getBook_id() %>" />
		<input type="hidden" name="status" value="Select a status" />
 		<input align="center" type="submit" name="updateStatus" value="Update status">
    </form></span></td>	<%} }%>
	</tr>
    <tr>
    <td> </td>
    </tr>
    <tr bgcolor="#aaa">
    <% if (userPrivilege.equalsIgnoreCase("seller")) { %>
    <th width="200" align="center">User ID </th> <% } %>
    <% if (userPrivilege.equalsIgnoreCase("admin")) { %>
    <th width="200" align="center">Seller ID </th> <% } %>
		<th width="200" align="center">Items Ordered </th> 
                       	  	<th width="80" align="center">Quantity </th> 
                        	<th width="80" align="center">Price ($)</th> 
                        	<th width="60" align="center">Shipping cost ($)</th>
                        	<th width="60" align="center">Total </th> 	
                        	<% if (userPrivilege.equalsIgnoreCase("seller")||userPrivilege.equalsIgnoreCase("admin")||userPrivilege.equalsIgnoreCase("buyer")) { %>
                        	<th width="60" align="center">Status </th><%} %>	
                        	<% if (userPrivilege.equalsIgnoreCase("buyer")) { %>
                        	<th width="60" align="center">Seller ID </th> 	
                        	<th width="60" align="center">Seller Review </th>
                        	<th width="60" align="center">Book Review </th>	<%} %>
	</tr>
	    		<%}
	    	if(i>=1){
	    		if(result.get(i).getOrder_id() != result.get(i-1).getOrder_id()){
	    		%>
	    		<tr>
		<td><span style="font-size: 16px"><b>Order Placed: </b> <% out.println(order.getOrder_time()); %></span></td>	
	</tr>
    <tr>
		<td><span style="font-size: 16px"><b>Order Number:</b> <% out.println(order.getOrder_id()); %></span></td>	
	</tr><% if (userPrivilege.equalsIgnoreCase("admin")) { %>
	<tr>
		<td><span style="font-size: 16px"><b>User ID:</b> <% out.println(order.getBuyer_id()); %></span></td>	
	</tr>
    <% }
	if  (userPrivilege.equalsIgnoreCase("buyer") || userPrivilege.equalsIgnoreCase("admin")) { %>
	<tr>
		<td><span style="font-size: 16px"><b>Total Quantity:</b> <% out.println(order.getTotal_quantity()); %></span></td>	
	</tr>
    <tr>
		<td><span style="font-size: 16px"><b>Order Total: </b>$<% out.println(String.format("%.2f", order.getTotal_price())); %></span></td>	
	</tr><% } %>
	<tr>
	<% if(!order.getOrder_status().equalsIgnoreCase("completed")){%>
		<td><span style="font-size: 16px"><form action='EditOrderServlet'  method='post'>
		<input type="hidden" name="order_id" value="<%=order.getOrder_id()%>" />
 		<input align="center" type="submit" name="edit" value="Edit Order">
    </form></span></td>
    <% if (!userPrivilege.equalsIgnoreCase("buyer")) { %>
    <td><span style="font-size: 16px"><form action='EditOrderStatusServlet'  method='post'>
		<input type="hidden" name="order_id" value="<%=order.getOrder_id()%>" />
		<input type="hidden" name="book_id" value="-1" />
 		<input align="center" type="submit" name="status" value="Update status">
    </form></span></td>	
    <%} }%>
	</tr>
	
    <tr>
    <td> </td>
    </tr>
    <tr bgcolor="#aaa">
    <% if (userPrivilege.equalsIgnoreCase("seller")) { %>
    <th width="200" align="center">User ID </th> <% } %>
    <% if (userPrivilege.equalsIgnoreCase("admin")) { %>
    <th width="200" align="center">Seller ID </th> <% } %>
		<th width="200" align="center">Items Ordered </th> 
                       	  	<th width="80" align="center">Quantity </th> 
                        	<th width="80" align="center">Price ($)</th> 
                        	<th width="60" align="center">Shipping cost ($)</th>
                        	<th width="60" align="center">Total </th> 
                        	<% if (userPrivilege.equalsIgnoreCase("seller")||userPrivilege.equalsIgnoreCase("admin")||userPrivilege.equalsIgnoreCase("buyer")) { %>
                        	<th width="60" align="center">Status </th><%} %>
                        	<% if (userPrivilege.equalsIgnoreCase("buyer")) { %>	
                        	<th width="60" align="center">Seller ID </th>
                        	<th width="60" align="center">Seller Review </th>
                        	<th width="60" align="center">Book Review </th>	<%} %>
	</tr>
	    		<% } 
	    		
	    	}
%>
<tr bgcolor="#ddd">
<% if (userPrivilege.equalsIgnoreCase("seller")) { %>
    <th width="200" align="center"><% out.println(order.getBuyer_id()); %></th> <% } %>
    <% if (userPrivilege.equalsIgnoreCase("admin")) { %>
    <th width="200" align="center"><% out.println(order.getSellerName()); %></th> <% } %>
	    		<td width="200" align="center"><% out.println(order.getBookTitle()); %> </td> 
	                           	  	<td width="80" align="center"><% out.println(order.getQuantity()); %></td> 
	                            	<td width="80" align="center"><% out.println(order.getPrice()); %></td> 
	                            	<td width="80" align="center"><% out.println(order.getShippingCost()); %></td>
	                            	<td width="60" align="center"><% out.println(String.format("%.2f",order.getPrice() * order.getQuantity() + order.getQuantity() * order.getShippingCost())); %> </td>
	                            	<% if (userPrivilege.equalsIgnoreCase("seller")||userPrivilege.equalsIgnoreCase("admin")||userPrivilege.equalsIgnoreCase("buyer")) { %>
                        	<td width="60" align="center"><% out.println(order.getProduct_status()); %> </td><%} %>
	                            	<% if (userPrivilege.equalsIgnoreCase("buyer")) { %>
	                            	<td width="80" align="center"><% out.println(order.getSellerName()); %></td>
	                            	<td width="40" align="center"> 
	                            	<form action='SellerReviewServlet' method='get'>
	                            	 <input type="hidden" name="seller_id" value="<%=order.getSeller_id() %>" />
 		<input align="center" type="submit"  value="Review Seller">
    </form></td><td width="40" align="center">
    <form action='BookReviewServlet' method='get'>
    <input type="hidden" name="book_id" value="<%=order.getBook_id() %>" />
    	<input align="center" type="submit"  value="Review Book">
    </form> </td> 	<% } %>	
	    	</tr>
	    	<% } %>
    </table>
    <br><br>
    <%}
		else{
			out.println("<h4>No orders placed till date</h4>");
		}
		} else {
		out.println("<h4>Please log in to access this page</h4>");
		
		}
	%>
	<br><br><br><br>
	<%@include file="includes/footer.jsp"%>
</body>
</html>
