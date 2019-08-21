<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="title.score"/></title>
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

<div id="container">
    <div class="content">
        <table id="t01">
            <tr>
                <th><fmt:message key="score.text.name"/></th>
                <th><fmt:message key="score.text.budget_pass"/></th>
                <th><fmt:message key="score.text.paid_pass"/></th>
            </tr>
            <c:forEach var="score" items="${requestScope.scores}">
                <tr>
                    <td class='<c:out value="${score.faculty.id}"></c:out>'><c:out value="${score.faculty.nameRu}"></c:out></td>
                    <td class='<c:out value="${score.faculty.id}"></c:out>' rowspan="2"><c:out value="${score.budgetScore}"></c:out></td>
                    <td class='<c:out value="${score.faculty.id}"></c:out>' rowspan="2"><c:out value="${score.notBudgetScore}"></c:out></td>
                </tr>
                <tr>
                    <td>${score.faculty.nameEng}</td>
                </tr>
            </c:forEach>
        </table>
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
    });

    var socket = new WebSocket("ws://"+document.location.host+"/UniversityAdmission/score");
    socket.onopen = function () {
    };

    socket.onclose = function (event) {
    };

    socket.onmessage = function (event) {
        let scoreChanges = JSON.parse(event.data);
        let facultyInfo = document.getElementsByClassName("" + scoreChanges.faculty.id);
        facultyInfo[1].innerHTML = scoreChanges.budgetScore;
        facultyInfo[2].innerHTML = scoreChanges.notBudgetScore;
        facultyInfo[1].style.color = 'red';
        facultyInfo[2].style.color = 'red';
        setTimeout(function () {
            facultyInfo[1].style.color = 'black';
            facultyInfo[2].style.color = 'black';
        }, 3000);
    };

    socket.onerror = function (error) {
    };
</script>
</html>