<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html  lang="${sessionScope.lang}">
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="css/registration-example.css">
    <link rel="stylesheet" type="text/css" href="css/general.css">
    <link rel="stylesheet" type="text/css" href="css/dialog.css">
</head>
<script src="js/dialog.js"></script>
<script src="js/jquery-1.11.2.min.js"></script>
<script>
    function fun() {
        alert("Send confirm");
        let first_name = $('input[name="first_name"]').val();
        let last_name = $('input[name="last_name"]').val();
        let email  = $('input[name="email"]').val();
        let password = $('input[name="password"]').val();
        let lang = "${sessionScope.lang}";
        $.ajax({
            url: 'controller',
            type: 'get',
            data: {command: "sendConfirmation", type: "AJAX",
                first_name: first_name, last_name: last_name, email: email, password: password, lang: lang},
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (data) {
                alert(data);
                $("button").click();

            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert("error");
                var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                $('#content').html(errorMsg);
            }
        });
    }

    function sendConfirmation(val){
        alert("confirm");
        // let key = $('#prompt_value1').val();
        let key = val;
        let email  = $('input[name="email"]').val();
        let password = $('input[name="password"]').val();
        $.ajax({
            url: 'controller',
            type: 'get',
            data: {command: "confirm", type: "AJAX",
                email: email, password: password, key: key},
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (data) {
                if(data === "okay"){
                    $("form").submit();
                }else{
                    alert("someting gone wrong, check your key")
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                alert("error");
                var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                $('#content').html(errorMsg);
            }
        });
    }
</script>
<body>
<img src="images/bg-login.jpg" class="bg-image">
<ul>
    <li><a href="?command=view_registration&sessionLocale=en"><fmt:message key="language.en" /></a></li>
    <li><a href="?command=view_registration&sessionLocale=ru"><fmt:message key="language.ru" /></a></li>
</ul>
<div id="dialogoverlay"></div>
<div id="dialogbox">
    <div>
        <div id="dialogboxhead"></div>
        <div id="dialogboxbody"></div>
        <div id="dialogboxfoot"></div>
    </div>
</div>
<h1 id="status">Default Text</h1>

<button onclick="Prompt.render('Check your email and insert key:','sendConfirmation')">Change Text</button>

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
        <%--<input type="submit" class="btn" style="background-color:#4e9af1" value="<fmt:message key="registration.button.submit"/>">--%>
        <input type="button" onclick="fun()" class="btn" style="background-color:#4e9af1" value="<fmt:message key="registration.button.submit"/>">
    </form>
    <p><fmt:message key="registration.label.alredy_registered_msg" />
        <a href="welcome.jsp"><fmt:message key="registration.label.login_here_msg" />!</a>
    </p>
</div>
</body>
</html>
