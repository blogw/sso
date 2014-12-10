<%@ page import="jespa.http.HttpSecurityServletRequest" %>
<%@ page import="jespa.ntlm.NtlmSecurityProvider" %>
<%@ page import="jespa.security.Account" %>
<%
HttpSecurityServletRequest req = (HttpSecurityServletRequest)request;
NtlmSecurityProvider provider = (NtlmSecurityProvider)req.getSecurityProvider();
Account account = provider.getAccount(null, null);
out.println("objectSid: " + account.get("objectSid"));
// output:
// objectSid: S-1-5-21-2779991729-652803122-3494501911-13031 
%>
