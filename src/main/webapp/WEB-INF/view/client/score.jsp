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
    <link rel="stylesheet" type="text/css" href="css/table.css">
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
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>

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
    });

</script>
</html>