<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body >
<table id="main-container">
    <tr>
        <td class="content">
            <h2 class="error">Something gone wrong</h2>
            <c:if test="${not empty errorMessage}">
                <p>
                    <c:out value="${errorMessage}"/>
                </p>
            </c:if>
                <a href="?command=viewWelcome">Welcome page</a>
        </td>
    </tr>
</table>
</body>
</html>