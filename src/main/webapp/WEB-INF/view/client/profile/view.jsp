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
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js"></script>
    <script src="<c:url value="/js/scrollButton.js"/>"></script>
    <script src="<c:url value="/js/sideBar.js"/>"></script>
</head>
<style>
    h4 {
        text-align: left;
    }
</style>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>

<div id="container">
    <div class="content">
        <div class="image-greeting">
            <c:choose>
                <c:when test="${requestScope.results eq true}">
                    <c:choose>
                        <c:when test="${empty requestScope.formOfEducation}">
                            <c:if test="${not empty requestScope.faculty}">
                                <div class="status-info">
                                    <img src="${pageContext.request.contextPath}/images/not-passed.jpg"
                                         class="image-of-status"
                                         alt="<fmt:message key="profile.alt.image-status.not-passed"/>"
                                         title="<fmt:message key="profile.alt.image-status.not-passed"/>">
                                    <h4><fmt:message key="profile.text.status.not-passed"/>
                                        <c:choose>
                                            <c:when test="${sessionScope.lang eq 'en'}">
                                                <c:out value="${requestScope.faculty.nameEng}"></c:out>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${requestScope.faculty.nameRu}"></c:out>
                                            </c:otherwise>
                                        </c:choose>
                                        <fmt:message key="profile.text.status.faculty"/></h4>
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="status-info">
                                <img src="${pageContext.request.contextPath}/images/passed.jpg" class="image-of-status"
                                     alt="<fmt:message key="profile.alt.image-status.passed"/>"
                                     title="<fmt:message key="profile.alt.image-status.passed"/>">
                                <h4><fmt:message key="profile.text.status.passed"/>
                                    <c:choose>
                                        <c:when test="${sessionScope.lang eq 'en'}">
                                            <c:out value="${requestScope.formOfEducation.formEng}"></c:out>
                                            <fmt:message key="profile.text.status.toThe"/>
                                            <c:out value="${requestScope.faculty.nameEng}"></c:out>
                                            <fmt:message key="profile.text.status.faculty"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${requestScope.formOfEducation.formRu}"></c:out>
                                            <fmt:message key="profile.text.status.toThe"/>
                                            <fmt:message key="profile.text.status.faculty"/>
                                            <c:out value="${requestScope.faculty.nameRu}"></c:out>
                                        </c:otherwise>
                                    </c:choose>
                                </h4>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty requestScope.faculty}">
                            <div class="status-info">
                                <img src="${pageContext.request.contextPath}/images/applied.png" class="image-of-status"
                                     alt="<fmt:message key="profile.alt.image-status.applied"/>"
                                     title="<fmt:message key="profile.alt.image-status.applied"/>">
                                <h4><fmt:message key="profile.text.status.applied"/>
                                    <c:choose>
                                        <c:when test="${sessionScope.lang eq 'en'}">
                                            <fmt:message key="profile.text.status.toThe"/>
                                            <c:out value="${requestScope.faculty.nameEng}"></c:out>
                                            <fmt:message key="profile.text.status.faculty"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${requestScope.formOfEducation.formRu}"></c:out>
                                            <fmt:message key="profile.text.status.toThe"/>
                                            <fmt:message key="profile.text.status.faculty"/>
                                            <c:out value="${requestScope.faculty.nameRu}"></c:out>
                                        </c:otherwise>
                                    </c:choose></h4>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="status-info">
                                <img src="${pageContext.request.contextPath}/images/not-applied.png"
                                     class="image-of-status"
                                     alt="<fmt:message key="profile.alt.image-status.not-applied"/>"
                                     title="<fmt:message key="profile.alt.image-status.not-applied"/>">
                                <h4><fmt:message key="profile.text.status.not-applied"/></h4>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

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
                <input type="hidden" name="email" value="${requestScope.email}">
                <input type="text" class="input-field" name="first-name"
                       placeholder="<fmt:message key="profile.text.ph.first_name"/>"
                       value="<c:out value="${requestScope.first_name}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="last-name"
                       placeholder="<fmt:message key="profile.text.ph.last_name"/>"
                       value="<c:out value="${requestScope.last_name}"></c:out>" required="required" disabled>
                <input type="email" class="input-field" name="email"
                       placeholder="<fmt:message key="profile.text.ph.email"/>"
                       value="<c:out value="${requestScope.email}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="city"
                       placeholder="<fmt:message key="profile.text.ph.city"/>"
                       value="<c:out value="${requestScope.city}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="district"
                       placeholder="<fmt:message key="profile.text.ph.district"/>"
                       value="<c:out value="${requestScope.district}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="school"
                       placeholder="<fmt:message key="profile.text.ph.school"/>"
                       value="<c:out value="${requestScope.school}"></c:out>" required="required" disabled>
                <button type="submit" id="profile_submit" class="btn" style="display: none"><fmt:message
                        key="button.submit"/></button>
                <button type="button" id="profile_cancel" class="btn" style="display: none"><fmt:message
                        key="button.cancel"/></button>
                <button type="button" id="profile_change" class="btn"><fmt:message
                        key="button.change"/></button>
            </form>
        </div>

        <div class="info">
            <c:if test="${not empty requestScope.diploma}">
                <div id="diploma" style="display: flex; justify-content: space-around">
                    <h4><fmt:message key="profile.text.diploma_message"/></h4>
                    <input type="number" class="no-spinners" placeholder="<c:out value="${diploma}"></c:out>" disabled>
                </div>
            </c:if>
            <c:if test="${empty requestScope.faculty and requestScope.results eq false}">
                <input type="button" id="diploma_cancel" class="btn" style="display: none"
                       value="<fmt:message key="button.cancel"/>">
                <input type="button" id="diploma_submit" class="btn" style="display: none"
                       value="<fmt:message key="button.submit"/>">
                <input type="button" id="diploma_change" class="btn"
                       value="<fmt:message key="button.change"/>">
                <h4 id="errorDiploma" style="display: none"></h4>
            </c:if>
        </div>

        <div class="info">
            <h2><fmt:message key="profile.view_jsp.label.subjects"/></h2>
            <ctg:totalScore marks="${requestScope.jsonMarks}" lang="${sessionScope.lang}" diploma="${requestScope.diploma}"/>
            <c:if test="${not empty requestScope.marks}">
                <div id="entrant_subjects">
                    <c:forEach var="mark_subject" items="${requestScope.marks}">
                        <div class="subject-field" id=${mark_subject.subject.id}>
                            <input type="hidden" class="id" id=${mark_subject.subject.id}>
                            <h4>
                                <c:if test="${sessionScope.lang eq 'ru'}">
                                    ${mark_subject.subject.nameRu}
                                </c:if>
                                <c:if test="${sessionScope.lang eq 'en'}">
                                    ${mark_subject.subject.nameEng}
                                </c:if>
                            </h4>
                            <input class="mark no-spinners" type="number" placeholder="${mark_subject.mark.mark}"
                                   disabled>
                        </div>
                    </c:forEach>
                </div>
                <div id="all_subjects"></div>
            </c:if>
            <c:if test="${empty requestScope.marks}">
                <div id="entrant_subjects"></div>
                <div id="all_subjects"></div>
                <div class="noSubjects">
                    <h1><b><fmt:message key="profile.text.no_subjects_message"/></b></h1>
                </div>
            </c:if>
            <%--<c:if test="${requestScope.applied eq false or requestScope.results eq false}}">--%>
            <c:if test="${empty requestScope.faculty and requestScope.results eq false}">
                <input type="button" id="subject_cancel" class="btn" style="display: none"
                       value="<fmt:message key="button.cancel"/>">
                <input type="button" id="subject_submit" class="btn" style="display: none"
                       value="<fmt:message key="button.submit"/>">
                <input type="button" id="subject_change" class="btn"
                       value="<fmt:message key="button.change"/>">
                <h1 id="error"></h1>
            </c:if>
        </div>
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

        $("#diploma_change").click(diploma_change_click);

        function diploma_change_click() {
            alert("Dont diploma_change_click!!");
            $("#diploma_submit").show();
            $("#diploma_cancel").show();
            $("#diploma_change").hide();
            $("#diploma input").attr("disabled", false);
        }

        $("#diploma_cancel").click(diploma_cancel_click);

        function diploma_cancel_click() {
            alert("Dont diploma_cancel_click!!");
            $("#diploma_submit").hide();
            $("#diploma_cancel").hide();
            $("#diploma_change").show();
            $("#diploma input")
                .val("")
                .attr("disabled", true);
            $("#errorDiploma")
                .css("display", "none")
                .text("");
        }

        $("#diploma_submit").click(diploma_submit_click);

        function diploma_submit_click() {
            let input = $("#diploma input");
            let diploma = getValueOfInput(input);
            if (validateMark(diploma) && input[0].checkValidity()) {
                alert("ajax");
                $.ajax({
                    url: 'controller',
                    type: 'get',
                    data: {command: "changeDiploma", type: "AJAX", diploma: diploma},
                    headers: {
                        Accept: "application/json; charset=utf-8",
                        "Content-Type": "application/json; charset=utf-8"
                    },
                    success: function (data) {
                        var empt = $("#errorDiploma").html();
                        if (empt != null || empt != "") {
                            $("#errorDiploma").html("");
                        }
                        alert("success");
                        $("#diploma_submit").hide();
                        $("#diploma_cancel").hide();
                        $("#diploma_change").show();
                        $("#diploma input")
                            .attr("placeholder", diploma)
                            .attr("disabled", true)
                            .val("");
                        $("#errorDiploma")
                            .css("display", "none")
                            .text("");
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert("error" + xhr);
                        $("#errorDiploma").val("check your mark, pls!");
                    }
                });
            } else {
                $("#errorDiploma")
                    .css("display", "inline-block")
                    .text("check your mark, pls!");
            }
        }

        $("#subject_change").click(subject_change_click);

        function subject_change_click() {
            alert("Dont subject_change_click!!");
            $("#subject_submit").show();
            $("#subject_cancel").show();
            $("#subject_change").hide();
            $(".subject-field input").attr("disabled", false);

            if ($('.noSubjects').length) {
                alert("element(s) found");
                $('.noSubjects').hide();
            }

            let entrant_subjects = $(".subject-field");
            for (let i = 0; i < entrant_subjects.length; i++) {
                let subject = entrant_subjects[i].firstChild;
                let button_delete = document.createElement("input");
                $(button_delete).attr("type", "button");
                $(button_delete).attr("class", "subject_delete_button");
                $(button_delete).attr("id", $(entrant_subjects[i].getElementsByClassName("id")).attr("id"));
                button_delete.addEventListener("click", subject_delete_button_click);
                button_delete.value = "Delete";
                $(entrant_subjects[i]).append(button_delete);
                entrant_subjects[i].classList.remove("subject-field");
                entrant_subjects[i].classList.add("subject-mark-field");

            }

            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "getOtherSubjects", type: "AJAX", target: "entrant"},
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (data) {

                    let subjectsGson = $.parseJSON(data);

                    let element = document.getElementById('all_subjects');

                    $(element).css("display", "flex");
                    $(element).css("flex-direction", "column");

                    let clientSubjects = subjectsGson.subjects;
                    for (var i = 0; i < clientSubjects.length; i++) {
                        let parent = document.createElement('div');
                        parent.id = clientSubjects[i].id;
                        parent.classList.add("subject-field");

                        let subject = document.createElement('h4');
                        <c:if test="${sessionScope.lang eq 'ru'}">
                        $(subject).text(clientSubjects[i].nameRu);
                        </c:if>
                        <c:if test="${sessionScope.lang eq 'en'}">
                        $(subject).text(clientSubjects[i].nameEng);
                        </c:if>
                        subject.classList.add("subject");

                        let button_add = document.createElement("input");
                        button_add.type = "button";
                        button_add.value = "Add";
                        $(button_add).attr("id", clientSubjects[i].id);
                        $(button_add).attr("class", "subject_add_button");

                        button_add.addEventListener("click", subject_add_button_click);

                        parent.appendChild(subject);
                        parent.appendChild(button_add);

                        element.appendChild(parent);
                    }

                },
                error: function (xhr, ajaxOptions, thrownError) {
                    var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                    $('#content').html(errorMsg);
                }
            });
        }

        $("#subject_add_button").click(subject_add_button_click);

        function subject_add_button_click() {
            alert("Dont subject_add_button_click!!");
            let button = event.target;
            let subject_id = $(button).attr("id");
            let subject_div = $(button).parent();

            subject_div[0].classList.remove("subject-field");
            subject_div[0].classList.add("subject-mark-field");

            let input_number = document.createElement("input");
            $(input_number)
                .attr("type", "number")
                .attr("id", subject_id)
                .attr("class", "mark no-spinners")
                .val(0);

            button.removeEventListener("click", subject_add_button_click);
            button.addEventListener("click", subject_delete_button_click);

            $(button)
                .removeAttr("class")
                .attr("class", "subject_delete_button")
                .val("Delete");

            $($(subject_div).find("h4")).after(input_number);
            $(subject_div).remove();
            $("div #entrant_subjects").append(subject_div);
        }

        $("#subject_delete_button").click(subject_delete_button_click);

        function subject_delete_button_click() {
            alert("Dont subject_delete_button_click!!");
            let button = event.target;
            let subject_div = $(button).parent();

            subject_div[0].classList.remove("subject-mark-field");
            subject_div[0].classList.add("subject-field");

            button.removeEventListener("click", subject_delete_button_click);
            button.addEventListener("click", subject_add_button_click);

            $(button)
                .removeAttr("class")
                .attr("class", "subject_add_button")
                .val("Add");

            $(subject_div).find(".mark").remove();
            $(subject_div).remove();
            $("div #all_subjects").append(subject_div);
        }

        $("#subject_cancel").click(subject_cancel_click);

        function subject_cancel_click() {
            alert("Dont subject_cancel_click!!");
            if ($('.noSubjects').length) {
                alert("element(s) found");
                $("div #entrant_subjects").empty();
                $("div #all_subjects").empty();
                $("div #entrant_subjects").append("<div class=\"noSubjects\">\n" +
                    "                               <h1><b><fmt:message key="profile.text.no_subjects_message"/></b></h1>\n" +
                    "                           </div>");
                $("#subject_cancel").hide();
                $("#subject_submit").hide();
                $("#subject_change").show();
            } else {
                alert("nothing found");
                <%--let subjects =${jsonMarks}.clientSubjects;--%>
                let subjects =${jsonMarks};
                $("div #entrant_subjects").empty();
                $("div #all_subjects").empty();
                for (var i = 0; i < subjects.length; i++) {
                    let subjectName;
                    <c:if test="${sessionScope.lang eq 'ru'}">
                    subjectName = subjects[i].subject.nameRu;
                    </c:if>
                    <c:if test="${sessionScope.lang eq 'en'}">
                    subjectName = subjects[i].subject.nameEng;
                    </c:if>
                    $("div #entrant_subjects").append(
                        "<div class=\"subject-field\" style=\"display:flex; justify-content: space-between;\">" +
                        "   <input type=\"hidden\" class=\"id\" id=\"" + subjects[i].subject.id + "\" disabled>" +
                        "   <h4>" + subjectName + "</h4>" +
                        "   <input type=\"number\" class=\"mark no-spinners\" placeholder=\"" + subjects[i].mark.mark + "\" disabled>" +
                        "</div>"
                    );
                }
                $("div #all_subjects").hide();
                $("#subject_cancel").hide();
                $("#subject_submit").hide();
                $("#subject_change").show();
            }
        }


        $("#subject_submit").click(subject_submit_click);

        function subject_submit_click() {
            let childs = document.getElementById("entrant_subjects").getElementsByTagName('div');
            if (childs.length > 3) {
                $("#error").text("Too much subjects");
            } else if (childs.length < 3) {
                $("#error").text("Not enough subjects");
            } else if (!validateMarks(childs)) {
                $("#error").text("Marks have to be between 0 and 100");
            } else {
                <%--let s =${jsonMarks}.clientSubjects;--%>
                let s =${jsonMarks};
                if (compareArrays(childs, s)) {
                    alert("old");
                    subject_cancel_click();
                } else {
                    let jsonString = makeJsonOfSubjects(childs);
                    alert("new");
                    $.ajax({
                        url: 'controller',
                        type: 'get',
                        data: {command: "changeSubjects", type: "AJAX", subjects: jsonString},
                        headers: {
                            Accept: "application/json; charset=utf-8",
                            "Content-Type": "application/json; charset=utf-8"
                        },
                        success: function (data) {
                            alert("success");
                            document.location.reload(true)
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            alert("error");
                            var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                            $('#content').html(errorMsg);
                        }
                    });
                }
            }
        }
    });

    function compareArrays(array1, array2) {
        if (!(array1.length === array2.length)) {
            return false;
        }
        for (let i = 0; i < array1.length; i++) {
            let mark1 = parseInt(getValueOfInput(array1[i].getElementsByClassName('mark')));
            let mark2 = array2[i].mark.mark;
            let subjectid1 = parseInt(array1[i].getAttribute('id'));
            let subjectid2 = array2[i].subject.id;
            let b1 = mark1 !== mark2;
            let b2 = subjectid1 !== subjectid2;
            if (mark1 !== mark2 || subjectid1 !== subjectid2) {
                return false
            }
        }
        return true;
    }

    function makeJsonOfSubjects(subjects) {
        <%--let mark_id =${jsonMarks}.clientSubjects--%>
        let mark_id =${jsonMarks};
        let lengthOfMarks = mark_id.length;
        let obj = [];
        for (let i = 0; i < subjects.length; i++) {
            let mark = {};
            if (lengthOfMarks > 0) {
                mark.id = mark_id[i].mark.id;
            } else {
                mark.id = -1;
            }
            mark.subjectId = parseInt(subjects[i].getAttribute('id'));
            mark.entrantId = "?";
            mark.mark = parseInt(getValueOfInput(subjects[i].getElementsByClassName('mark')));
            obj.push(mark);
        }
        return JSON.stringify(obj);
    }

    function validateMark(mark) {
        return mark != null && mark > 0 && mark <= 100 && /^[1-9]\d*$/.test(mark);
    }

    function validateMarks(subjects) {
        let mark;
        for (let i = 0; i < subjects.length; i++) {
            mark = parseInt(getValueOfInput(subjects[i].getElementsByClassName('mark')));
            if (!validateMark(mark)) {
                return false;
            }
        }
        return true;
    }

    function getValueOfInput(element) {
        if ($(element).val() === '') {
            return $(element).attr('placeholder');
        } else {
            return $(element).val();
        }
    }
</script>
</html>