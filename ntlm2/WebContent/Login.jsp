<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<title>Jespa Examples: Login</title>
</head>
<a href="./">Back</a>
<%
HttpSession ssn = request.getSession();
if (ssn != null) {
    String message = (String)ssn.getAttribute("my.message");
    if (message != null) {
        ssn.removeAttribute("my.message");
        out.println("<i style='display: block; color: #00f;'>");
        out.println(message);
        out.println("</i>");
    }
}
%>
<p/>
<form method="POST" action="./">
<table border="0">
<tr><td>Username:</td><td><input type="text" name="username"/></td></tr>
<tr><td>Password:</td><td><input type="password" name="password"/></td></tr>
<tr><td></td><td><input type="submit" value="Login"/></td></tr>
<tr><td></td><td style="height: 3em;">or</td></tr>
<tr><td></td><td><a href="./?anon=1">Become Anonymous</a></td></tr>
</table>
</form>

<div style="width: 800;">
<small>
Notes:
<p/>
To "Become Anonymous" you must first logout if you are not already.
<p/>
Parameter-based logins should always be encrypted with HTTPS but for demonstration purposes this example is not protected - passwords will be transmitted in plain-text.
<p/>
This login form implementation requires that the http.parameter.username.name init-param is set to "username" and the http.parameter.password.name init-param is set to "password".
</small>
</div>
