<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="title.faculty"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

<div id="container">
    <div class="content">
        <div class="info">
            <div><h2><fmt:message key="faculty.text.faculty"/></h2></div>
            <form id="faculty" class="form-input" method="POST" action="controller">
                <input type="hidden" name="command" value="editFaculty">
                <input type="hidden" name="facultyId"
                       value="<c:out value="${requestScope.facultyInfo.faculty.id}"></c:out>">

                <input type="text" class="input-field" name="nameRu"
                       placeholder="<fmt:message key="faculty.ph.facultyRu"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.nameRu}"></c:out>" required="required"
                       disabled>
                <input type="text" class="input-field" name="nameEng"
                       placeholder="<fmt:message key="faculty.ph.facultyEng"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.nameEng}"></c:out>" required="required"
                       disabled>
                <input type="text" class="input-field" name="totalSeats"
                       placeholder="<fmt:message key="faculty.ph.totalSeats"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.totalSeats}"></c:out>"
                       required="required" disabled>
                <input type="text" class="input-field" name="budgetSeats"
                       placeholder="<fmt:message key="faculty.ph.budgetSeats"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.budgetSeats}"></c:out>"
                       required="required" disabled>
                <textarea name="infoRu" style="width: 100%;" disabled><c:out
                        value="${requestScope.facultyInfo.faculty.infoRu}"></c:out></textarea>
                <textarea name="infoEng" style="width: 100%;" disabled><c:out
                        value="${requestScope.facultyInfo.faculty.infoEng}"></c:out></textarea>
                <button type="submit" id="faculty_submit" class="btn" style="display: none"><fmt:message
                        key="registration.button.submit"/></button>
                <button type="button" id="faculty_change" class="btn"><fmt:message
                        key="profile.view_jsp.button.change"/></button>
            </form>
        </div>

        <div class="info">
            <h2><fmt:message key="profile.view_jsp.label.subjects"/></h2>
            <div id="faculty_subjects">
            </div>
            <div id="all_subjects"></div>
            <c:if test="${empty facultyInfo.subjects}">
                <div class="noSubjects">
                    <h1><b><fmt:message key="faculty.view_jsp.label.no_subjects_msg"/></b></h1>
                </div>
            </c:if>
            <input type="button" id="subject_cancel" class="btn" style="display: none"
                   value="<fmt:message key="button.cancel"/>">
            <input type="button" id="subject_submit" class="btn" style="display: none"
                   value="<fmt:message key="button.submit"/>">
            <input type="button" id="subject_change" class="btn"
                   value="<fmt:message key="button.change"/>">
        </div>

        <div class="info">
            <div id="facultyUsers"></div>
            <input id="viewUsers" type="button" class="btn" value="Show faculty users">
        </div>

    </div>

    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</div>
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

        fillSubjects();

        function fillSubjects() {
            let subjectsJson = ${subjects};
            let parent = $("#faculty_subjects");
            for (let i = 0; i < subjectsJson.length; i++) {
                $(parent).append("<div class='subject-field'" +
                    "                             id='" + subjectsJson[i].id + "' style='display:flex;justify-content:space-between;'>" +
                    "                            <input type='hidden' class='id' id='" + subjectsJson[i].id + "'>" +
                    "                            <h4>" +
                    <c:choose>
                    <c:when test="${sessionScope.lang eq 'ru'}">subjectsJson[i].nameRu + </c:when>
                    <c:otherwise> subjectsJson[i].nameEng + </c:otherwise>
                    </c:choose>
                    "                            </h4>" +
                    "                        </div>");
            }
            if (subjectsJson.length === 0) {
                $(parent).append("<div class=\"noSubjects\">\n" +
                    "                    <h1><b><fmt:message key="faculty.view_jsp.label.no_subjects_msg"/></b></h1>" +
                    "                </div>");
            }
        }

        $("#viewUsers").click(viewUsers_click);

        function viewUsers_click() {
            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "getFacultyUsers", type: "AJAX", facultyId: ${requestScope.facultyInfo.faculty.id}},
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (data) {
                    let users = JSON.parse(data);
                    if (users.length !== 0) {
                        let table = document.createElement("table");
                        table.id = "t01";
                        $(table).append("<tr>\n" +
                            "                <th><fmt:message key="result.text.first_name"/></th>\n" +
                            "                <th><fmt:message key="result.text.last_name"/></th>\n" +
                            "                <th><fmt:message key="result.text.email"/></th>\n" +
                            "                <th><fmt:message key="result.text.preliminary_sum"/></th>\n" +
                            "                <th><fmt:message key="result.text.diploma_mark"/></th>\n" +
                            "                <th><fmt:message key="result.text.total_sum"/></th>\n" +
                            "            </tr>");
                        for (let i = 0; i < users.length; i++) {
                            $(table).append("<tr>\n" +
                                "                    <td>" + users[i].firstName + "</td>\n" +
                                "                    <td>" + users[i].lastName + "</td>\n" +
                                "                    <td>" + users[i].email + "</td>\n" +
                                "                    <td>" + users[i].preliminarySum + "</td>\n" +
                                "                    <td>" + users[i].diplomaMark + "</td>\n" +
                                "                    <td>" + users[i].totalSum + "</td>\n" +
                                "                </tr>");
                        }
                        $("#facultyUsers").append(table);
                    } else {
                        $("#facultyUsers").append("<fmt:message key="faculty.view_jsp.label.no_faculty_entrants_msg"/>");
                    }
                    $("#viewUsers").hide();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
                }
            });
        }

        $("#faculty_change").click(faculty_change_click);

        function faculty_change_click() {
            $("#faculty_submit").show();
            $("#faculty_change").hide();
            $("#faculty input[type=text]").attr("disabled", false);
            $("#faculty textarea").attr("disabled", false);
        }

        $("#subject_change").click(subject_change_click);

        function subject_change_click() {
            $("#subject_submit").show();
            $("#subject_cancel").show();
            $("#subject_change").hide();
            $(".subject-field input").attr("disabled", false);

            if ($('.noSubjects').length) {
                $('.noSubjects').hide();
            }

            let entrant_subjects = $(".subject-field");
            for (let i = 0; i < entrant_subjects.length; i++) {
                let button_delete = document.createElement("input");
                $(button_delete)
                    .attr("type", "button")
                    .attr("class", "subject_delete_button")
                    .attr("id", $(entrant_subjects[i].getElementsByClassName("id")).attr("id"));
                button_delete.addEventListener("click", subject_delete_button_click);
                button_delete.value = "Delete";
                $(entrant_subjects[i]).append(button_delete);
            }

            $.ajax({
                url: 'controller',
                type: 'get',
                data: {
                    command: "getOtherSubjects",
                    type: "AJAX",
                    facultyId: ${requestScope.facultyInfo.faculty.id},
                    target: "faculty"
                },
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
                    for (let i = 0; i < clientSubjects.length; i++) {
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
                    window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
                }
            });
        }

        $("#subject_add_button").click(subject_add_button_click);

        function subject_add_button_click() {
            let button = event.target;
            let subject_div = $(button).parent();

            button.removeEventListener("click", subject_add_button_click);
            button.addEventListener("click", subject_delete_button_click);

            $(button)
                .removeAttr("class")
                .attr("class", "subject_delete_button")
                .val("Delete");

            $(subject_div).remove();
            $("div #faculty_subjects").append(subject_div);
        }

        $("#subject_delete_button").click(subject_delete_button_click);

        function subject_delete_button_click() {
            let button = event.target;
            let subject_div = $(button).parent();

            button.removeEventListener("click", subject_delete_button_click);
            button.addEventListener("click", subject_add_button_click);

            $(button)
                .removeAttr("class")
                .attr("class", "subject_add_button")
                .val("Add");

            $(subject_div).remove();
            $("div #all_subjects").append(subject_div);
        }

        $("#subject_cancel").click(subject_cancel_click);

        function subject_cancel_click() {
            if($('.noSubjects').length)
            {
                $("div #faculty_subjects").empty();
                $("div #all_subjects").empty();
                $("div #faculty_subjects").append("<div class=\"noSubjects\">\n" +
                    "                               <h1><b><fmt:message key="profile.view_jsp.label.no_subjects_message"/></b></h1>\n" +
                    "                           </div>");
                $("#subject_cancel").hide();
                $("#subject_submit").hide();
                $("#subject_change").show();
            }
        else
            {
                $("div #faculty_subjects").empty();
                $("div #all_subjects").empty();
                fillSubjects();
                $("#subject_cancel").hide();
                $("#subject_submit").hide();
                $("#subject_change").show();
            }
        }

        $("#subject_submit").click(subject_submit_click);

        function subject_submit_click() {
            let childs = document.getElementById("faculty_subjects").getElementsByTagName('div');
            if (childs.length > 3) {
                createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="message.mark.tooMuch"/>");
            } else if (childs.length < 3) {
                createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="message.mark.notEnough"/>");
            } else {
                let s = ${subjects};
                if (compareArrays(childs, s)) {
                    subject_cancel_click();
                } else {
                    let jsonString = makeJsonOfSubjects(childs);
                    $.ajax({
                        url: 'controller',
                        type: 'get',
                        data: {
                            command: "changeFacultySubjects",
                            type: "AJAX",
                            subjects: jsonString,
                            facultyId: ${requestScope.facultyInfo.faculty.id}
                        },
                        headers: {
                            Accept: "application/json; charset=utf-8",
                            "Content-Type": "application/json; charset=utf-8"
                        },
                        success: function (data) {
                            let error = JSON.parse(data);
                            if (error.errorEng === "none") {
                                document.location.reload(true)
                            } else {
                                <c:choose>
                                <c:when test="${sessionScope.lang eq 'ru'}">
                                createElement("warning", "<fmt:message key="message.warning"/>", error.errorRu);
                                </c:when>
                                <c:otherwise>
                                createElement("warning", "<fmt:message key="message.warning"/>", error.errorEng);
                                </c:otherwise>
                                </c:choose>
                            }
                        },
                        error: function (xhr, ajaxOptions, thrownError) {
                            window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
                        }
                    });
                }
            }
        }

        function getValueOfInput(element) {
            if ($(element).val() === '') {
                return $(element).attr('placeholder');
            } else {
                return $(element).val();
            }
        }

        function makeJsonOfSubjects(subjects) {
            let subject_id = ${requestScope.subjects};
            let lengthOfMarks = subject_id.length;
            let obj = [];
            for (let i = 0; i < subjects.length; i++) {
                let facultySubject = {};
                if (lengthOfMarks > 0) {
                    facultySubject.id = subject_id[i].id;
                } else {
                    facultySubject.id = -1;
                }
                facultySubject.subjectId = parseInt(subjects[i].getAttribute('id'));
                facultySubject.facultyId = "?";
                obj.push(facultySubject);
            }
            return JSON.stringify(obj);
        }

        function compareArrays(array1, array2) {
            if (!(array1.length === array2.length)) {
                return false;
            }
            for (let i = 0; i < array1.length; i++) {
                let subjectid1 = parseInt(array1[i].getAttribute('id'));
                let subjectid2 = array2[i].id;
                if (subjectid1 !== subjectid2) {
                    return false
                }
            }
            return true;
        }
    });
</script>
</html>