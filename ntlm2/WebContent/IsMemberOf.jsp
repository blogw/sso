<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.DecimalFormat" %>
<%response.setContentType("text/html;charset=UTF-8"); %>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<a href="./">Back</a>
<p/>
<%
out.println("getRemoteUser: " + request.getRemoteUser());
%>
<p/>
<small>
Enter a <i>semicolon</i> separated list of groups below and click "Check Groups". The list will be checked with <tt>isUserInRole</tt> and will print the total time required to check the list of groups as well as the results of each check.
</small>
<p/>
<form method="POST">
<textarea name="groups" cols="72" rows="10"><%

String[] defaultGroups = { "Domain Users", " Domain Admins" };
String p = request.getParameter("groups");
String[] groups = p == null ? defaultGroups : p.split(";");

for (int gi = 0; gi < groups.length; gi++) {
    if (gi > 0)
        out.print(";");
    out.print(groups[gi]);
}

%></textarea>
<p/>
<input type="submit" value="Check Groups"/>
</form>
<p/>
<%

if (groups != defaultGroups) {
    boolean[] results = new boolean[groups.length];

    long t0 = System.currentTimeMillis();
    for (int gi = 0; gi < groups.length; gi++) {
        groups[gi] = groups[gi].trim();
        results[gi] = request.isUserInRole(groups[gi]);
    }
    double seconds = (System.currentTimeMillis() - t0) / 1000.0;

    DecimalFormat df = new DecimalFormat("0.000#");
    out.println(groups.length + " groups checked in " + df.format(seconds) + " seconds");

%>
<p/>
<table border="1" cellspacing="0" cellpadding="5">
<%
    for (int gi = 0; gi < groups.length; gi++) {
%>
<tr><td><% out.println(groups[gi]); %></td><td><% out.println(results[gi] ? "Yes" : "No"); %></td></tr>
<%
    }
%>
</table>
<%
}
%>
<p/>
<small>
Account names may also be supplied and will result in true if it matches the identity of the caller. Names should be qualified with a domain like "EXAMPLE\Managers" or "example.com\managers" or "alice@example.com" and not just "Managers" or "alice" although any form of qualified or unqualified name will work. If a name cannot be resolved, the domain authority is queried and may cause a signficant delay if the name is unqualified or if the domain of the group or account cannot be reached. The total time should be 0.000 if all names can be resolved. An additional 0.005 to 0.007 seconds is required for each name that cannot be resolved. If the authority for a domain cannot be reached, the result may be unexpectedly "No" and a delay of several seconds may occur.
</small>
