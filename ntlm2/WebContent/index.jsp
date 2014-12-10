<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Jespa Examples</title>
</head>
<body>

<h2>Jespa Examples</h2>

Your current identity is:
<blockquote>
<i><% out.println(request.getRemoteUser()); %></i>
</blockquote>
<p/>
These examples are protected by the Jespa HttpSecurityFilter and should be accessible only to authorized clients.
<p/>
<table border="0">
<tr><td><a href="IsMemberOf.jsp">IsMemberOf</a></td><td>Check group membership of authenticated user</td></tr>
<tr><td><a href="Login.jsp">Login</a></td><td>Perform a manual form-based login.</td></tr>
<tr><td><a href="Login.jsp?logout=1">Logout</a></td><td>Delete the session state for the Jespa HttpSecurityFilter.</td></tr>
</table>

<div style="width: 800;">
<small>
Notes:
<p/>
The above Logout link requires that the http.parameter.logout.name init-param is set to "logout".
</small>
</div>

</body>
</html>
