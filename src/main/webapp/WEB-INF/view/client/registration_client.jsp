<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html  lang="${sessionScope.lang}">
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="css/registration-example.css">
    <link rel="stylesheet" type="text/css" href="css/general.css">
</head>
<body>
<img src="images/bg-login.jpg" class="bg-image">
<ul>
    <li><a href="?command=client_registration&sessionLocale=en"><fmt:message key="language.en" /></a></li>
    <li><a href="?command=client_registration&sessionLocale=ru"><fmt:message key="language.ru" /></a></li>
</ul>
<div class="main-container">
    <h1><fmt:message key="header.register" /></h1>
    <form  action="controller" method="POST" class="form-container">
        <input type="hidden" name="command" value="client_registration">
        <input type="hidden" name="lang" value="en">
        <div class="input-form">
            <input type="text" class="input-field" name="first_name" placeholder="<fmt:message key="registration.label.first_name"/>" required="required">
        </div>
        <div class="input-form">
            <input type="text" class="input-field" name="last_name" placeholder="<fmt:message key="registration.label.last_name"/>" required="required">
        </div>
        <div class="input-form">
            <input type="email" class="input-field" name="email" placeholder="<fmt:message key="registration.label.email"/>" required="required">
        </div>
        <div class="input-form">
            <input type="text" class="input-field" name="city" placeholder="<fmt:message key="registration.label.city"/>" required="required">
        </div>
        <div class="input-form">
            <input type="text" class="input-field" name="district" placeholder="<fmt:message key="registration.label.district"/>" required="required">
        </div>
        <div class="input-form">
            <input type="text" class="input-field" name="school" placeholder="<fmt:message key="registration.label.school"/>" required="required">
        </div>
        <div class="input-form">
            <input type="password" class="input-field" name="password" placeholder="<fmt:message key="registration.label.password"/>" required="required">
        </div>
        <div class="input-form">
            <input type="password" class="input-field" name="confirm_password" placeholder="<fmt:message key="registration.label.confirm_password"/>" required="required">
        </div>
        <input type="submit" class="btn" style="background-color:#4e9af1" value="<fmt:message key="registration.button.submit"/>">
    </form>
    <p><fmt:message key="registration.label.alredy_registered_msg" />
        <a href="welcome.jsp"><fmt:message key="registration.label.login_here_msg" />!</a>
    </p>
</div>
</body>
</html>
