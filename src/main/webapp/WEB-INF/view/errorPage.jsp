<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>
<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body style="background-color:#adf1ff;">
<table id="main-container">
    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <h2 class="error">The following error
                occurred</h2> <%-- this way we get the error information (error 404)--%>
            <%--<c:set var="code"--%>
            <%--value="${requestScope['javax.servlet.error.status_code']}"/> <c:set--%>
            <%--var="message"--%>
            <%--value="${requestScope['javax.servlet.error.message']}"/> <c:if--%>
            <%--test="${not empty code}">--%>
            <%--<h3>--%>
            <%--Error code:--%>
            <%--<c:out value="${code}"/>--%>
            <%--</h3>--%>
            <%--</c:if>--%>
            <%--<c:if test="${not empty message}">--%>
            <%--<h3>--%>
            <%--Error message:--%>
            <%--<c:out value="${message}"/>--%>
            <%--</h3>--%>
            <%--</c:if> &lt;%&ndash; if get this page using forward &ndash;%&gt;--%>
            <c:if test="${not empty errorMessage}">
                <p>
                    <c:out value="${errorMessage}"/>
                </p>
            </c:if>
                <a href="?command=viewWelcome">Welcom page</a>
                <%-- this way we print exception stack trace --%>
            <%--<pre>${pageContext.out.flush()}${requestScope['javax.servlet.error.exception'].printStackTrace(pageContext.response.writer)}</pre>--%>
        </td>
    </tr>
</table>
</body>
</html>