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
    <script>
        function toggleSidebar() {
            document.getElementById("sidebar").classList.toggle('active');
        }

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
</head>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<ctg:navbar/>
<div id="container">
    <div class="content">
        <h1><fmt:message key="faculty.label.faculties"/></h1>
        <c:forEach var="facultyInfo" items="${requestScope.facultiesInfo}">
            <div class="info">
                <div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">
                    <img src="images/faculty-image.png" class="photo">
                    <div class="greeting" style=" text-align: center;">
                        <h1>
                            <c:if test="${sessionScope.lang eq 'ru'}">
                                ${facultyInfo.faculty.nameRu}
                            </c:if>
                            <c:if test="${sessionScope.lang eq 'en'}">
                                ${facultyInfo.faculty.nameEng}
                            </c:if></h1><br>
                        <h1>
                                <%--Information--%>
                            <fmt:message key="faculty.label.information"/>
                        </h1>
                    </div>
                </div>
                <div>
                    <fmt:message key="faculty.label.temp"/>
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
                    <c:forEach var="faculty_subject" items="${facultyInfo.subjects}">
                        <div class="subject-field"
                             id=${faculty_subject.id} style="display:flex;justify-content:space-between;">
                            <input type="hidden" class="id" id=${faculty_subject.id}>
                            <h4>
                                <c:if test="${sessionScope.lang eq 'ru'}">
                                    ${faculty_subject.nameRu}
                                </c:if>
                                <c:if test="${sessionScope.lang eq 'en'}">
                                    ${faculty_subject.nameEng}
                                </c:if>
                            </h4>
                        </div>
                    </c:forEach>
                </div>
                <c:if test="${role eq client}">
                    <form action="/controller" type="post">
                        <input class="btn" type="submit" value="<fmt:message key="faculty.button.apply"/>">
                    </form>
                </c:if>
            </div>
        </c:forEach>
        <%--<div class="info">--%>
        <%--<div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">--%>
        <%--<img src="images/faculty-image.png" class="photo">--%>
        <%--<div class="greeting" style=" text-align: center;">--%>
        <%--<h1>Faculty name1</h1><br>--%>
        <%--<h1>--%>
        <%--Information--%>
        <%--</h1>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--Text text text textText text text textText text text textText text text textText text text textText text--%>
        <%--text text--%>
        <%--</div>--%>
        <%--<div id="entrant_subjects" style="border: solid 2pt rgb(0, 255, 21);">--%>
        <%--&lt;%&ndash;<c:forEach var="mark_subject" items="${marks}">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="subject-field"&ndash;%&gt;--%>
        <%--&lt;%&ndash;id=${mark_subject.subject.id} style="display:flex;justify-content:space-between;">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=${mark_subject.subject.id}>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<h4>${mark_subject.subject.nameEng}</h4>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<input class="mark no-spinners" type="number" placeholder="${mark_subject.mark.mark}"&ndash;%&gt;--%>
        <%--&lt;%&ndash;disabled>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
        <%--<div class="subject-field"--%>
        <%--id=1 style="display:flex;justify-content:space-between;">--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=1>&ndash;%&gt;--%>
        <%--<input type="hidden" class="id" id=1>--%>
        <%--<h4>Subject1</h4>--%>
        <%--</div>--%>
        <%--<div class="subject-field"--%>
        <%--id=2 style="display:flex;justify-content:space-between;">--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=2>&ndash;%&gt;--%>
        <%--<input type="hidden" class="id" id=2>--%>
        <%--<h4>Subject1</h4>--%>
        <%--</div>--%>
        <%--<div class="subject-field"--%>
        <%--id=3 style="display:flex;justify-content:space-between;">--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=3>&ndash;%&gt;--%>
        <%--<input type="hidden" class="id" id=3>--%>
        <%--<h4>Subject1</h4>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<c:if test="${role eq client}">--%>
        <%--<form action="/controller" type="post">--%>
        <%--<input class="btn" type="submit" value="Apply on faculty">--%>
        <%--</form>--%>
        <%--</c:if>--%>
        <%--</div>--%>
        <%--<div class="info">--%>
        <%--<div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">--%>
        <%--<img src="images/faculty-image.png" class="photo">--%>
        <%--<div class="greeting" style=" text-align: center;">--%>
        <%--<h1>Faculty name2</h1><br>--%>
        <%--<h1>--%>
        <%--Information2--%>
        <%--</h1>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--Text text text textText text text textText text text textText text text textText text text textText text--%>
        <%--text text--%>
        <%--Text text text textText text text textText text text textText text text textText text text textText text--%>
        <%--text text--%>
        <%--</div>--%>
        <%--<div id="entrant_subjects" style="border: solid 2pt rgb(0, 255, 21);">--%>
        <%--<div class="subject-field"--%>
        <%--id=4 style="display:flex;justify-content:space-between;">--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=1>&ndash;%&gt;--%>
        <%--<input type="hidden" class="id" id=4>--%>
        <%--<h4>Subject1</h4>--%>
        <%--</div>--%>
        <%--<div class="subject-field"--%>
        <%--id=5 style="display:flex;justify-content:space-between;">--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=2>&ndash;%&gt;--%>
        <%--<input type="hidden" class="id" id=5>--%>
        <%--<h4>Subject1</h4>--%>
        <%--</div>--%>
        <%--<div class="subject-field"--%>
        <%--id=6 style="display:flex;justify-content:space-between;">--%>
        <%--&lt;%&ndash;<input type="hidden" class="id" id=3>&ndash;%&gt;--%>
        <%--<input type="hidden" class="id" id=6>--%>
        <%--<h4>Subject1</h4>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<c:if test="${role eq client}">--%>
        <%--<form action="/controller" type="post">--%>
        <%--<input class="btn" type="submit" value="Apply on faculty">--%>
        <%--</form>--%>
        <%--</c:if>--%>
        <%--</div>--%>
    </div>
</div>


<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
</html>
