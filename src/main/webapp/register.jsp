<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>


<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Client-view</title>
    <link rel="stylesheet" type="text/css" href="css/client-profile.css">
    <link rel="stylesheet" type="text/css" href="css/scrollButton.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" type="text/css" href="css/sidebar.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="css/general.css">
    <link rel="stylesheet" type="text/css" href="css/messages.css">
    <link rel="stylesheet" type="text/css" href="css/dialog.css">
    <link rel="stylesheet" type="text/css" href="css/loader.css">
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/scrollButton.js"></script>
    <script src="js/sideBar.js"></script>
    <script src="js/validation.js"></script>
    <script src="js/messages.js"></script>
    <script src="js/dialog.js"></script>
    <script src="js/loader.js"></script>
</head>

<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<div id="dialogoverlay"></div>
<div id="dialogbox">
    <div>
        <div id="dialogboxhead"></div>
        <div id="dialogboxbody"></div>
        <div id="dialogboxfoot"></div>
    </div>
</div>

<div class="loader" style="display:none"></div>

<div class="alert-boxes">
    <c:if test="${not empty requestScope.errorMessage}">
        <div class="alert warning">
            <span class="closebtn">&times;</span>
            <strong>Warning!</strong> <c:out value="${requestScope.errorMessage}"/>
        </div>
    </c:if>
</div>

<div id="container">
    <div class="content">
        <div class="main-container">
            <h1><fmt:message key="header.register"/></h1>
            <form action="controller" method="POST" class="form-container">
                <input type="hidden" name="command" value="client_registration">
                <input type="hidden" name="lang" value="en">
                <div class="input-form">
                    <input type="text" class="input-field" name="first_name"
                           placeholder="<fmt:message key="registration.label.first_name"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="text" class="input-field" name="last_name"
                           placeholder="<fmt:message key="registration.label.last_name"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="email" class="input-field" name="email"
                           placeholder="<fmt:message key="registration.label.email"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="text" class="input-field" name="city"
                           placeholder="<fmt:message key="registration.label.city"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="text" class="input-field" name="district"
                           placeholder="<fmt:message key="registration.label.district"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="text" class="input-field" name="school"
                           placeholder="<fmt:message key="registration.label.school"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="password" class="input-field" name="password"
                           placeholder="<fmt:message key="registration.label.password"/>" required="required">
                </div>
                <div class="input-form">
                    <input type="password" class="input-field" name="confirm_password"
                           placeholder="<fmt:message key="registration.label.confirm_password"/>" required="required">
                </div>
                <input type="button" onclick="validateInputs()" class="btn" style="background-color:#4e9af1"
                       value="<fmt:message key="registration.button.submit"/>">
                <input id="validationButton" type="submit" style="display: none">
            </form>
            <p><fmt:message key="registration.label.alredy_registered_msg"/>
                <a href="?command=viewLogin"><fmt:message key="registration.label.login_here_msg"/>!</a>
            </p>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
<script>
    $(document).ready(function () {
        loadMessages();
    });
    function validateInputs() {
        if (!$(".main-container form")[0].checkValidity()) {
            $("#validationButton").click();
            return false;
        }
        let inputs = $("input[type='text']");
        for (let i = 0; i < inputs.length; ++i) {
            if (!validateScript(inputs[i].value)) {
                createElement("warning", "Warning", "Scripts are not allowed");
                return false;
            }
        }
        let passwords = $("input[type='password']");
        if (passwords[0].value.length < 4) {
            createElement("warning", "Warning", "Password is less than 4 symbols");
            return false;
        }
        if (passwords[0].value !== passwords[1].value) {
            createElement("warning", "Warning", "Passwords are not the same");
            return false;
        }
        runLoader();
        fun();
    }

    function fun() {
        alert("Send confirm");
        let first_name = $('input[name="first_name"]').val();
        let last_name = $('input[name="last_name"]').val();
        let email = $('input[name="email"]').val();
        let password = $('input[name="password"]').val();
        let lang = "${sessionScope.lang}";
        $.ajax({
            url: 'controller',
            type: 'get',
            data: {
                command: "sendConfirmation", type: "AJAX",
                first_name: first_name, last_name: last_name, email: email, password: password, lang: lang
            },
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (data) {
                stopLoader();
                Prompt.render('Check your email and insert key:','checkKey');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
            }
        });
    }

    function checkKey(val) {
        let key = val;
        let email = $('input[name="email"]').val();
        let password = $('input[name="password"]').val();
        $.ajax({
            url: 'controller',
            type: 'get',
            data: {
                command: "confirm", type: "AJAX",
                email: email, password: password, key: key
            },
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (data) {
                if (data === "okay") {
                    $("form").submit();
                } else {
                    stopLoader();
                    createElement("warning", "Warning", "Someting gone wrong, check your key");
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
            }
        });
    }
</script>
</html>