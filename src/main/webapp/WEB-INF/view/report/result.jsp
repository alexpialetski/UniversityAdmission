<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="title.result"/></title>
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
</head>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

<div id="container">
    <div class="content">
        <h1>Results</h1>
        <c:forEach var="facultyResult" items="${requestScope.facultyResults}">
            <div class="info">
                <h3>
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'en'}">
                            <c:out value="${facultyResult.faculty.nameEng}"></c:out>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${facultyResult.faculty.nameRu}"></c:out>
                        </c:otherwise>
                    </c:choose>
                </h3>
                <c:choose>
                    <c:when test="${empty facultyResult.entrantResults}">
                        <h2><fmt:message key="result.text.no_entrants"/></h2>
                    </c:when>
                    <c:otherwise>
                        <table id="t01">
                            <tr>
                                <th><fmt:message key="result.text.first_name"/></th>
                                <th><fmt:message key="result.text.last_name"/></th>
                                <th><fmt:message key="result.text.email"/></th>
                                <th><fmt:message key="result.text.diploma_mark"/></th>
                                <th><fmt:message key="result.text.preliminary_sum"/></th>
                                <th><fmt:message key="result.text.total_sum"/></th>
                                <th><fmt:message key="result.text.form_of_education"/></th>
                            </tr>
                            <c:forEach var="entrantResult" items="${facultyResult.entrantResults}">
                                <tr>
                                    <td><c:out value="${entrantResult.user.firstName}"></c:out></td>
                                    <td><c:out value="${entrantResult.user.lastName}"></c:out></td>
                                    <td><c:out value="${entrantResult.user.email}"></c:out></td>
                                    <td><c:out value="${entrantResult.marksSum.diplomaMark}"></c:out></td>
                                    <td><c:out value="${entrantResult.marksSum.preliminarySum}"></c:out></td>
                                    <td><c:out value="${entrantResult.marksSum.totalSum}"></c:out></td>
                                    <td><c:choose>
                                        <c:when test="${sessionScope.lang eq 'en'}">
                                            <c:out value="${entrantResult.formOfEducation.formEng}"></c:out>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${entrantResult.formOfEducation.formRu}"></c:out>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
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