<%@ page import="jespa.http.HttpSecurityServletRequest" %>
<%@ page import="jespa.ntlm.NtlmSecurityProvider" %>
<%@ page import="jespa.security.Domain" %>
<%@ page import="jespa.security.Account" %>
<%@ page import="jcifs.smb.SID" %>

<a href="./">Back</a>
<p/>
<pre>
<%
out.println("getRemoteUser: " + request.getRemoteUser());
out.println("getUserPrincipal: " + request.getUserPrincipal());
out.println("getAuthType: " + request.getAuthType());

HttpSecurityServletRequest req = (HttpSecurityServletRequest)request;
NtlmSecurityProvider provider = (NtlmSecurityProvider)req.getSecurityProvider();

out.println("NtlmSecurityProvider:");
%><small><%
out.println(provider);
%></small><%

out.println("Domain:");
Domain d = provider.getDomain(null, null);
%><small><%
out.println(d);
%></small><%

out.println("Account:");
Account a = provider.getAccount(null, null);
%><small><%
out.println(a);
%></small><%

%>
</pre>
