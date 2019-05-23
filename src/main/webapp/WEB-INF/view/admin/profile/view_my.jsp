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
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/scrollButton.js"></script>
    <script src="js/sideBar.js"></script>
</head>
<body>

<%--<ctg:header company="University admission"/>--%>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%--<ctg:navbar/>--%>
<%@ include file="/WEB-INF/view/jspf/navbar.jspf" %>

<div id="container">
    <div class="content">
        <div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">
            <img src="images/profile/photo-human.png" class="photo">
            <div class="greeting" style=" text-align: center;">
                <h1><fmt:message key="profile.view_jsp.label.greeting"/></h1><br>
                <h1>
                    <c:out value="${requestScope.first_name}"></c:out>
                    <c:out value="${requestScope.last_name}"></c:out>
                </h1>
            </div>
        </div>
        <div class="info" style="border: solid 2pt rgb(255, 0, 200);">
            <div><h2><fmt:message key="profile.view_jsp.label.profile"/></h2></div>
            <form id="profile" class="form-input" method="POST" action="controller"
                  style="border: solid 5pt rgb(100, 100, 100);">
                <input type="hidden" name="command" value="editProfile">
                <%--<input type="hidden" name="type" value="post">--%>
                <input type="hidden" name="email" value="${requestScope.email}">

                <input type="text" class="input-field" name="first-name"
                       placeholder="<fmt:message key="registration.label.first_name"/>"
                       value="<c:out value="${requestScope.first_name}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="last-name"
                       placeholder="<fmt:message key="registration.label.last_name"/>"
                       value="<c:out value="${requestScope.last_name}"></c:out>" required="required" disabled>
                <input type="email" class="input-field" name="email"
                       placeholder="<fmt:message key="registration.label.email"/>"
                       value="<c:out value="${requestScope.email}"></c:out>" required="required" disabled>
                <button type="submit" id="profile_submit" class="btn" style="display: none"><fmt:message
                        key="registration.button.submit"/></button>
                <button type="button" id="profile_change" class="btn"><fmt:message
                        key="profile.view_jsp.button.change"/></button>
            </form>
        </div>
        <%--<input type="button" class="btn" onclick="makeResult()" value="Make results" >--%>

        <div class="info" style="border: solid 2pt rgb(255, 0, 200);">
            <form id="admin" class="form-input" method="POST" action="controller"
                  style="display:none; border: solid 5pt rgb(100, 100, 100);">
                <%--<div><h2><fmt:message key="profile.view_jsp.label.profile"/></h2></div>--%>
                <input type="hidden" name="command" value="admin_registration">
                <%--<input type="hidden" name="type" value="post">--%>
                <input type="hidden" name="email" value="${requestScope.email}">

                <input type="text" class="input-field" name="first-name"
                       placeholder="<fmt:message key="registration.label.first_name"/>"
                       required="required" disabled>
                <input type="text" class="input-field" name="last-name"
                       placeholder="<fmt:message key="registration.label.last_name"/>"
                       required="required" disabled>
                <input type="email" class="input-field" name="email"
                       placeholder="<fmt:message key="registration.label.email"/>"
                       required="required" disabled>
                <button type="submit" id="profile_submit" class="btn" style="display: none"><fmt:message
                        key="registration.button.submit"/></button>
                <button type="button" id="profile_change" class="btn"><fmt:message
                        key="profile.view_jsp.button.change"/></button>
            </form>
            <input type="button" id="admin_register" class="btn" onclick="registerAdmin()" value="Register another admin" >
            <input type="submit" id="admin_submit" class="btn" value="Submit" style="display: none" >
            <input type="button" id="admin_cancel" class="btn" onclick="cancelRegisterAdmin()" style="display: none" value="Cancel" >
        </div>
        <c:choose>
            <c:when test="${requestScope.results eq false}">
                <div class="info">
                    <form onsubmit="confirm('Are you sure?')" method="POST" action="controller">
                        <input type="hidden" name="command" value="makeResult">
                        <button type="submit" class="btn">End applying</button>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="info">
                    <form onsubmit="confirm('Are you sure?')" method="POST" action="controller">
                        <input type="hidden" name="command" value="deleteResults">
                        <button type="submit" class="btn">Start applying</button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%--<ctg:footer/>--%>
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
            $("#profile_change").hide();
            $("#profile input[type=text]").attr("disabled", false);
        }

        function registerAdmin() {
            $("#admin").show();
            $("#admin_submit").show();
            $("#admin_cancel").show();
            $("#admin_register").hide();
            $("#admin input").attr("disabled", false);
        }
        
        function cancelRegisterAdmin() {
            $("#admin").hide();
            $("#admin_submit").hide();
            $("#admin_cancel").hide();
            $("#admin_register").show();
            $("#admin input").attr("disabled", true);
        }
    });


</script>
</html>