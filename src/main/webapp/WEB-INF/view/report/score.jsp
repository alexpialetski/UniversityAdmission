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
                    <td><c:out value="${score.faculty.nameRu}"></c:out></td>
                    <td rowspan="2"><c:out value="${score.budgetScore}"></c:out></td>
                    <td rowspan="2"><c:out value="${score.notBudgetScore}"></c:out></td>
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

</script>
</html>