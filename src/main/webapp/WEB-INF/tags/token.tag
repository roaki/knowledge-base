<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="java.util.UUID" %>

<%
  String token = UUID.randomUUID().toString();
  request.getSession(false).setAttribute("token", token);
%>
<input type="hidden" name="token" value="<%=token%>" />