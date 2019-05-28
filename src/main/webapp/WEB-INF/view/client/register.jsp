<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="title.registration"/></title>
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
    <link rel="stylesheet" type="text/css" href="css/dialog.css">
    <link rel="stylesheet" type="text/css" href="css/loader.css">
    <script src="js/dialog.js"></script>
    <script src="js/loader.js"></script>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

<div id="dialogoverlay"></div>
<div id="dialogbox">
    <div>
        <div id="dialogboxhead"></div>
        <div id="dialogboxbody"></div>
        <div id="dialogboxfoot"></div>
    </div>
</div>

<div class="loader" style="display:none"></div>

<div id="container">
    <div class="content">
        <div class="main-container">
            <h1><fmt:message key="header.register"/></h1>
            <form id="registration" action="controller" method="POST" class="form-container">
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
                <%--<a href="?command=viewLogin"><fmt:message key="registration.label.login_here_msg"/>!</a>--%>
            </p>
            <form style="font-size: 18px;" class="form" action="controller" method="GET">
                <input type="hidden" name="command" value="viewLogin">
                <input style="color: black;text-align: center;padding: 4px 10px;font-size: 18px;line-height: 25px;border-radius: 10px;background-color: rgb(115, 212, 167);box-shadow: 0 4px 8px 0 rgba(197, 197, 197, 0.2), 0 6px 20px 0 rgba(197, 197, 197, 0.2);border-color: rgba(255,255,255,1);"
                       type="submit" value="<fmt:message key="registration.label.login_here_msg"/>">
            </form>
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
        let inputs = $("input");
        for (let i = 0; i < inputs.length; ++i) {
            if (!validateScript(inputs[i].value)) {
                createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="error.script"/>");
                return false;
            }
        }
        let passwords = $("input[type='password']");
        if (passwords[0].value.length < 4) {
            createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="error.passwordLength"/>");
            return false;
        }
        if (passwords[0].value !== passwords[1].value) {
            createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="error.passwordNotSame"/>");
            return false;
        }
        runLoader();
        fun();
    }

    function fun() {
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
                let error = JSON.parse(data);
                if (error.errorEng === "none") {
                    Prompt.render('<fmt:message key="message.email"/>', 'checkKey');
                } else {
                    <c:choose>
                    <c:when test="${sessionScope.lang eq 'ru'}">
                    createElement("warning", "<fmt:message key="message.warning"/>", error.errorRu);
                    </c:when>
                    <c:otherwise>
                    createElement("warning", "<fmt:message key="message.warning"/>", error.errorEng);
                    </c:otherwise>
                    </c:choose>
                }
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
                let error = JSON.parse(data);
                if (error.errorEng === "none") {
                    $("#registration").submit();
                } else {
                    stopLoader();
                    <c:choose>
                    <c:when test="${sessionScope.lang eq 'ru'}">
                    createElement("warning", "<fmt:message key="message.warning"/>", error.errorRu);
                    </c:when>
                    <c:otherwise>
                    createElement("warning", "<fmt:message key="message.warning"/>", error.errorEng);
                    </c:otherwise>
                    </c:choose>
                }
            },
            error: function (xhr, ajaxOptions, thrownError) {
                window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
            }
        });
    }
</script>
</html>