<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="title.adminProfile"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

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
                <button type="submit" id="profile_submit" onclick="validateInputs()" class="btn" style="display: none"><fmt:message
                        key="button.submit"/></button>
                <button type="button" id="profile_cancel" class="btn" style="display: none"><fmt:message
                        key="button.cancel"/></button>
                <button type="button" id="profile_change" class="btn"><fmt:message
                        key="button.change"/></button>
            </form>
        </div>
        <c:choose>
            <c:when test="${sessionScope.results eq false}">
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
    loadMessages();
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
            $("#profile_submit").show();
            $("#profile_cancel").show();
            $("#profile_change").hide();
            $("#profile input[type=text]").attr("disabled", false);
        }

        $("#profile_cancel").click(profile_cancel_click);

        function profile_cancel_click() {
            $("#profile_cancel").hide();
            $("#profile_submit").hide();
            $("#profile_change").show();
            $("#profile input[type=text]").attr("disabled", true);
        }
    });

    function validateInputs() {
        let inputs = $("input");
        for (let i = 0; i < inputs.length; ++i) {
            if (!validateScript(inputs[i].value)) {
                createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="error.script"/>");
                return false;
            }
        }
    }
</script>
</html>