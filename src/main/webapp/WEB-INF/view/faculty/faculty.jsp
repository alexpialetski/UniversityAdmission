<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/client-profile.css">
    <link rel="stylesheet" type="text/css" href="css/scrollButton.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/scrollButton.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<ctg:navbar/>
<div id="container">
    <div class="content">
        <h1>Faculties</h1>
        <div class="info">
            <div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">
                <img src="images/faculty-image.png" class="photo">
                <div class="greeting" style=" text-align: center;">
                    <h1>Faculty name1</h1><br>
                        <h1>
                            Information
                        </h1>
                </div>
            </div>
            <div>
                Text text text textText text text textText text text textText text text textText text text textText text text text
            </div>
            <div id="entrant_subjects" style="border: solid 2pt rgb(0, 255, 21);">
                <%--<c:forEach var="mark_subject" items="${marks}">--%>
                    <%--<div class="subject-field"--%>
                         <%--id=${mark_subject.subject.id} style="display:flex;justify-content:space-between;">--%>
                        <%--<input type="hidden" class="id" id=${mark_subject.subject.id}>--%>
                        <%--<h4>${mark_subject.subject.nameEng}</h4>--%>
                        <%--<input class="mark no-spinners" type="number" placeholder="${mark_subject.mark.mark}"--%>
                               <%--disabled>--%>
                    <%--</div>--%>
                <%--</c:forEach>--%>
                    <div class="subject-field"
                         id=1 style="display:flex;justify-content:space-between;">
                        <%--<input type="hidden" class="id" id=1>--%>
                        <input type="hidden" class="id" id=1>
                        <h4>Subject1</h4>
                    </div>
                    <div class="subject-field"
                         id=2 style="display:flex;justify-content:space-between;">
                        <%--<input type="hidden" class="id" id=2>--%>
                        <input type="hidden" class="id" id=2>
                        <h4>Subject1</h4>
                    </div>
                    <div class="subject-field"
                         id=3 style="display:flex;justify-content:space-between;">
                        <%--<input type="hidden" class="id" id=3>--%>
                        <input type="hidden" class="id" id=3>
                        <h4>Subject1</h4>
                    </div>
            </div>
            <c:if test="${role eq client}">
                <form action="/controller" type="post">
                    <input class="btn" type="submit" value="Apply on faculty">
                </form>
            </c:if>
        </div>
        <div class="info">
            <div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">
                <img src="images/faculty-image.png" class="photo">
                <div class="greeting" style=" text-align: center;">
                    <h1>Faculty name2</h1><br>
                    <h1>
                        Information2
                    </h1>
                </div>
            </div>
            <div>
                Text text text textText text text textText text text textText text text textText text text textText text text text
                Text text text textText text text textText text text textText text text textText text text textText text text text
            </div>
            <div id="entrant_subjects" style="border: solid 2pt rgb(0, 255, 21);">
                <div class="subject-field"
                     id=4 style="display:flex;justify-content:space-between;">
                    <%--<input type="hidden" class="id" id=1>--%>
                    <input type="hidden" class="id" id=4>
                    <h4>Subject1</h4>
                </div>
                <div class="subject-field"
                     id=5 style="display:flex;justify-content:space-between;">
                    <%--<input type="hidden" class="id" id=2>--%>
                    <input type="hidden" class="id" id=5>
                    <h4>Subject1</h4>
                </div>
                <div class="subject-field"
                     id=6 style="display:flex;justify-content:space-between;">
                    <%--<input type="hidden" class="id" id=3>--%>
                    <input type="hidden" class="id" id=6>
                    <h4>Subject1</h4>
                </div>
            </div>
            <c:if test="${role eq client}">
                <form action="/controller" type="post">
                    <input class="btn" type="submit" value="Apply on faculty">
                </form>
            </c:if>
        </div>
    </div>
</div>


<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
</html>
