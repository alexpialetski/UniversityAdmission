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
    <link rel="stylesheet" type="text/css" href="css/general.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/scrollButton.js"></script>
    <script src="js/sideBar.js"></script>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>

<div id="container">
    <div class="content">
        <div class="image-greeting">
            <div class="greeting">
                <h1><fmt:message key="profile.text.greeting"/></h1><br>
                <h1>
                    <c:out value="${requestScope.first_name}"></c:out>
                    <c:out value="${requestScope.last_name}"></c:out>
                </h1>
            </div>
        </div>
        <div class="info">
            <div><h2><fmt:message key="profile.text.profile"/></h2></div>
            <form id="profile" class="form-input" method="POST" action="controller">
                <input type="hidden" name="command" value="editProfile">
                <input type="text" class="input-field" name="first-name"
                       placeholder="<fmt:message key="profile.text.ph.first_name"/>"
                       value="<c:out value="${requestScope.first_name}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="last-name"
                       placeholder="<fmt:message key="profile.text.ph.last_name"/>"
                       value="<c:out value="${requestScope.last_name}"></c:out>" required="required" disabled>
                <input type="email" class="input-field" name="email"
                       placeholder="<fmt:message key="profile.text.ph.email"/>"
                       value="<c:out value="${requestScope.email}"></c:out>" required="required" disabled>
                <button type="submit" id="profile_submit" class="btn" style="display: none"><fmt:message
                        key="button.submit"/></button>
                <button type="button" id="profile_cancel" class="btn" style="display: none"><fmt:message
                        key="button.cancel"/></button>
                <button type="button" id="profile_change" class="btn"><fmt:message
                        key="button.change"/></button>
            </form>
        </div>

        <div class="info">
            <form id="register" class="form-input" method="POST" action="controller" style="display:none;">
                <div><h2><fmt:message key="admin.text.register"/></h2></div>
                <input type="hidden" name="command" value="adminRegistration">
                <input type="text" class="input-field" name="first-name"
                       placeholder="<fmt:message key="profile.text.ph.first_name"/>"
                       required="required" disabled>
                <input type="text" class="input-field" name="last-name"
                       placeholder="<fmt:message key="profile.text.ph.last_name"/>"
                       required="required" disabled>
                <input type="email" class="input-field" name="email"
                       placeholder="<fmt:message key="profile.text.ph.email"/>"
                       required="required" disabled>
                <button type="submit" id="validationButton" class="btn" style="display: none"></button>
            </form>
            <input type="button" id="admin_register" class="btn" onclick="registerAdmin()" value="<fmt:message
                        key="admin.button.registerAdmin"/>">
            <input type="button" id="admin_submit" class="btn" onclick="submitRegister()" value="<fmt:message
                        key="button.submit"/>" style="display: none">
            <input type="button" id="admin_cancel" class="btn" onclick="cancelRegisterAdmin()" value="<fmt:message
                        key="button.cancel"/>" style="display: none">
        </div>
        <c:choose>
            <c:when test="${requestScope.results eq false}">
                <div class="info">
                    <form onsubmit="confirm('<fmt:message key="message.confirm"/>')" method="POST" action="controller">
                        <input type="hidden" name="command" value="makeResult">
                        <button type="submit" class="btn"><fmt:message key="admin.button.end_applying"/></button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="info">
                    <form onsubmit="confirm('<fmt:message key="message.confirm"/>')" method="POST" action="controller">
                        <input type="hidden" name="command" value="deleteResults">
                        <button type="submit" class="btn"><fmt:message key="admin.button.start_applying"/></button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
<script>
    $(document).ready(function () {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 10) {
                $('#scrollButton').addClass('show');
            } else {
                $('#scrollButton').removeClass('show');
            }
        });

        $('#scrollButton').on('click', function (e) {
            e.preventDefault();
            $('html, body').animate({scrollTop: 0}, '300');
        });

        $("#profile_change").click(profile_change_click);

        function profile_change_click() {
            alert("Dont profile_change_click!!");
            $("#profile_submit").show();
            $("#profile_cancel").show();
            $("#profile_change").hide();
            $("#profile input[type=text]").attr("disabled", false);
        }

        $("#profile_cancel").click(profile_cancel_click);

        function profile_cancel_click() {
            alert("Dont profile_change_click!!");
            $("#profile_cancel").hide();
            $("#profile_submit").hide();
            $("#profile_change").show();
            $("#profile input[type=text]").attr("disabled", true);
        }

        $("#register_change").click(register_change_click);

        function register_change_click() {
            alert("Dont profile_change_click!!");
            $("#profile_submit").show();
            $("#profile_cancel").show();
            $("#profile_change").hide();
            $("#profile input[type=text]").attr("disabled", false);
        }

        $("#register_cancel").click(register_cancel_click);

        function register_cancel_click() {
            alert("Dont profile_change_click!!");
            $("#profile_cancel").hide();
            $("#profile_submit").hide();
            $("#profile_change").show();
            $("#profile input[type=text]").attr("disabled", true);
        }
    });

    function registerAdmin() {
        $("#register").show();
        $("#admin_submit").show();
        $("#admin_cancel").show();
        $("#admin_register").hide();
        $("#register input").attr("disabled", false);
    }

    function cancelRegisterAdmin() {
        $("#register").hide();
        $("#admin_submit").hide();
        $("#admin_cancel").hide();
        $("#admin_register").show();
        $("#register input").attr("disabled", true);
    }

    function submitRegister() {
        if (!$("#register").checkValidity()) {
            $("#validationButton").click();
        } else {
            $("#register").submit();
        }
    }
</script>
</html>