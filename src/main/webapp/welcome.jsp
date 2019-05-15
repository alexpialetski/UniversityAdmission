<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html  lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="css/login-example.css">
    <link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
<img src="images/bg-login.jpg" class="bg-image">
<ul>
    <li><a href="?sessionLocale=en"><fmt:message key="language.en" /></a></li>
    <li><a href="?sessionLocale=ru"><fmt:message key="language.ru" /></a></li>
</ul>
<%--<c:if test="${not empty param.sessionLocale}">--%>
    <%--<h1>${sessionScope.lang}</h1>--%>
<%--</c:if>--%>
<div class="main-container">
    <img src="images/login.png" id="overlay">
    <h1 style="margin-bottom: 0; margin-top: 8%"><fmt:message key="welcome_jsp.label.login" /></h1>
    <form action="controller" method="POST" class="form-container">
        <input type="hidden" name="command" value="login">
        <input type="hidden" name="type" value="POST">
        <div class="input-form">
            <input type="text" name="email" placeholder="<fmt:message key="profile.view_jsp.label.email"/>" class="input-field" required>
        </div>
        <div class="input-form">
            <input type="password" name="password" placeholder="<fmt:message key="welcome_jsp.label.password" />" class="input-field" required>
        </div>
        <input type="submit" class="btn" style="background-color:#4e9af1" value="<fmt:message key="welcome_jsp.button.login" />">
        <c:if test="${not empty errorMessage}">
            <h3>${errorMessage}</h3>
        </c:if>
    </form>
    <p><fmt:message key="welcome_jsp.label.not_registered_msg" />
        <a href="controller?command=client_registration"><fmt:message key="welcome_jsp.label.register_here_msg" />!</a>
    </p>
</div>
</body>
</html>