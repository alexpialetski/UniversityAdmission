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

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>

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
                                <th><fmt:message key="result.text.email"/>Email</th>
                                <th><fmt:message key="result.text.diploma_mark"/>Diploma mark</th>
                                <th><fmt:message key="result.text.preliminary_sum"/>Preliminary summary</th>
                                <th><fmt:message key="result.text.total_sum"/>Total summary</th>
                                <th><fmt:message key="result.text.form_of_education"/>Form of education</th>
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
<%--<ctg:footer/>--%>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
</html>