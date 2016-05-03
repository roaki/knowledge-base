<%@ tag pageEncoding="UTF-8" %>
<%@ attribute name="model" type="java.lang.String" required="true" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<spring:hasBindErrors name="${model}">
  <div class="am-panel am-panel-danger">
    <div class="am-panel-hd">
      <h3 class="am-panel-title">校验错误</h3>
    </div>
    <div class="am-panel-bd">
      <c:if test="${errors.fieldErrorCount > 0}">
        <c:forEach items="${errors.fieldErrors}" var="error">
          <spring:message var="message" code="${error.code}" arguments="${error.arguments}"
                          text="${error.defaultMessage}"/>
          <p class="am-text-danger">${error.field}:${message}</p>
        </c:forEach>
      </c:if>

      <c:if test="${errors.globalErrorCount > 0}">
        <c:forEach items="${errors.globalErrors}" var="error">
          <spring:message var="message" code="${error.code}" arguments="${error.arguments}"
                          text="${error.defaultMessage}"/>
          <c:if test="${not empty message}">
            <p class="am-text-danger">${message}</p>
          </c:if>
        </c:forEach>
      </c:if>
    </div>
  </div>
</spring:hasBindErrors>
