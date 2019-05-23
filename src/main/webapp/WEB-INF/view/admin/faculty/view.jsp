<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/client-profile.css">
    <link rel="stylesheet" type="text/css" href="css/scrollButton.css">
    <link rel="stylesheet" type="text/css" href="css/footer.css">
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <link rel="stylesheet" type="text/css" href="css/sidebar.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/scrollButton.js"></script>
    <script src="js/sideBar.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>

<%@ include file="/WEB-INF/view/jspf/navbar.jspf" %>
<%--<ctg:navbar/>--%>
<div id="container">
    <div class="content">
        <%--<div class="info">--%>
            <%--<div class="photo-greeting" style="border: solid 2pt rgb(0, 4, 255);">--%>
                <%--<img src="images/faculty-image.png" class="photo">--%>
                <%--<div class="greeting" style=" text-align: center;">--%>
                    <%--<h1><c:out--%>
                            <%--value="${lang eq 'ru' ? facultyInfo.faculty.nameRu : facultyInfo.faculty.nameEng}"></c:out></h1>--%>
                    <%--<h1><fmt:message key="faculty.label.information"/></h1>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div id=\"faculty-label\">--%>
                <%--<fmt:message key="faculty.label.temp"/>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="info" style="border: solid 2pt rgb(255, 0, 200);">
            <div><h2><fmt:message key="profile.view_jsp.label.profile"/></h2></div>
            <form id="faculty" class="form-input" method="POST" action="controller"
                  style="border: solid 5pt rgb(100, 100, 100);">
                <input type="hidden" name="command" value="editFaculty">
                <%--<input type="hidden" name="type" value="post">--%>
                <input type="hidden" name="facultyId" value="<c:out value="${requestScope.facultyInfo.faculty.id}"></c:out>">

                <input type="text" class="input-field" name="nameRu"
                       placeholder="<fmt:message key="registration.label.first_name"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.nameRu}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="nameEng"
                       placeholder="<fmt:message key="registration.label.last_name"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.nameEng}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="totalSeats"
                       placeholder="<fmt:message key="registration.label.email"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.totalSeats}"></c:out>" required="required" disabled>
                <input type="text" class="input-field" name="budgetSeats"
                       placeholder="<fmt:message key="registration.label.city"/>"
                       value="<c:out value="${requestScope.facultyInfo.faculty.budgetSeats}"></c:out>" required="required" disabled>
                <textarea name="infoRu" disabled><c:out value="${requestScope.facultyInfo.faculty.infoRu}"></c:out></textarea>
                <textarea name="infoEng" disabled><c:out value="${requestScope.facultyInfo.faculty.infoEng}"></c:out></textarea>
                <button type="submit" id="faculty_submit" class="btn" style="display: none"><fmt:message
                        key="registration.button.submit"/></button>
                <button type="button" id="faculty_change" class="btn"><fmt:message
                        key="profile.view_jsp.button.change"/></button>
            </form>
        </div>

        <div class="info" style="border: solid 2pt rgb(255, 0, 200);">
            <p><fmt:message
                    key="faculty.view_jsp.label.preliminary_subjects"/></p>
            <div id="seats">
                <div style="display: flex; justify-content: space-between">
                    <h4><fmt:message key="faculty.view_jsp.label.total_seats"/></h4>
                    <input id="totalSeats" type="number" class="no-spinners" placeholder=
                    <c:out value="${facultyInfo.faculty.totalSeats}"></c:out> disabled>
                </div>
                <div style="display: flex; justify-content: space-between">
                    <h4><fmt:message key="faculty.view_jsp.label.budget_seats"/></h4>
                    <input id="budgetSeats" type="number" class="no-spinners" placeholder=
                    <c:out value="${facultyInfo.faculty.budgetSeats}"></c:out> disabled>
                </div>
            </div>

            <input type="button" id="seats_cancel" class="btn" style="display: none"
                   value="<fmt:message key="profile.view_jsp.button.cancel"/>">
            <input type="button" id="seats_submit" class="btn" style="display: none"
                   value="<fmt:message key="profile.view_jsp.button.submit"/>">
            <input type="button" id="seats_change" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.change"/>">
            <h4 id="errorSeats" style="display: none"></h4>
        </div>

        <div class="info" style="border: solid 2pt rgb(255, 0, 200);">
            <h2><fmt:message key="profile.view_jsp.label.subjects"/></h2>
            <%--<c:if test="${not empty facultyInfo.subjects}">--%>
                <%--<input id="entrant_subjectss" type="hidden" value=${marks}>--%>
                <div id="faculty_subjects" style="border: solid 2pt rgb(0, 255, 21);">
                    <%--<c:forEach var="subject" items="${facultyInfo.subjects}">--%>
                        <%--<div class="subject-field"--%>
                             <%--id=${subject.id} style="display:flex;justify-content:space-between;">--%>
                            <%--<input type="hidden" class="id" id=${subject.id}>--%>
                            <%--<h4>--%>
                                    <%--${lang eq 'ru' ? subject.nameRu : subject.nameEng}--%>
                                <%--&lt;%&ndash;<c:if test="${sessionScope.lang eq 'ru'}">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;${subject.nameRu}&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<c:if test="${sessionScope.lang eq 'en'}">&ndash;%&gt;--%>
                                    <%--&lt;%&ndash;${subject.nameEng}&ndash;%&gt;--%>
                                <%--&lt;%&ndash;</c:if>&ndash;%&gt;--%>
                            <%--</h4>--%>
                        <%--</div>--%>
                    <%--</c:forEach>--%>
                </div>
                <div id="all_subjects"></div>
            <%--</c:if>--%>
            <c:if test="${empty facultyInfo.subjects}">
                <%--<div id="faculty_subjects" style="border: solid 2pt rgb(0, 255, 21);"></div>--%>
                <%--<div id="all_subjects"></div>--%>
                <div class="noSubjects">
                    <h1><b><fmt:message key="faculty.view_jsp.label.no_subjects_msg"/></b></h1>
                </div>
            </c:if>
            <input type="button" id="subject_cancel" class="btn" style="display: none"
                   value="<fmt:message key="profile.view_jsp.button.cancel"/>">
            <input type="button" id="subject_submit" class="btn" style="display: none"
                   value="<fmt:message key="profile.view_jsp.button.submit"/>">
            <input type="button" id="subject_change" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.change"/>">
            <h1 id="error"></h1>
        </div>

        <div class="info">
            <div id="facultyUsers">

            </div>
            <input id="viewUsers" type="button" class="btn" value="Show faculty users">
        </div>

    </div>

    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</div>
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

        // let subjectsJson = "";
        <%--$.ajax({--%>
            <%--url: 'controller',--%>
            <%--type: 'get',--%>
            <%--data: {command: "getFacultySubjects", type: "AJAX", facultyId: ${requestScope.facultyInfo.faculty.id}},--%>
            <%--headers: {--%>
                <%--Accept: "application/json; charset=utf-8",--%>
                <%--"Content-Type": "application/json; charset=utf-8"--%>
            <%--},--%>
            <%--success: function (data) {--%>
                <%--alert(data);--%>
                <%--subjectsJson = JSON.parse(data);--%>
                <%--alert(subjectsJson.length);--%>
            <%--},--%>
            <%--error: function (xhr, ajaxOptions, thrownError) {--%>
                <%--alert("error" + xhr);--%>
                <%--$("#errorDiploma").val("check your mark, pls!");--%>
            <%--}--%>
        <%--});--%>
        fillSubjects();
        function fillSubjects(){
            <%--let subjectsJson = JSON.parse("${subjects}");--%>
            <%--let subjectsJson = $.parseJSON(${subjects});--%>
            let subjectsJson = ${subjects};
            let parent = $("#faculty_subjects");
            for(let i=0; i<subjectsJson.length; i++){
                $(parent).append("<div class='subject-field'" +
                    "                             id='" + subjectsJson[i].id +"' style='display:flex;justify-content:space-between;'>" +
                    "                            <input type='hidden' class='id' id='" + subjectsJson[i].id+"'>" +
                    "                            <h4>" +
                    <c:choose>
                        <c:when test="${sessionScope.lang eq 'ru'}">subjectsJson[i].nameRu + </c:when>
                        <c:otherwise> subjectsJson[i].nameEng + </c:otherwise>
                    </c:choose>
                    "                            </h4>" +
                    "                        </div>");
            }
            if(subjectsJson.length === 0){
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
                            "                <th>First name</th>\n" +
                            "                <th>Last name</th>\n" +
                            "                <th>Email</th>\n" +
                            "                <th>Preliminary sum</th>\n" +
                            "                <th>Diploma mark</th>\n" +
                            "                <th>Total sum</th>\n" +
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
                    alert("error" + xhr);
                    $("#errorDiploma").val("check your mark, pls!");
                }
            });
        }

        $("#faculty_change").click(faculty_change_click);

        function faculty_change_click() {
            alert("Dont profile_change_click!!");
            $("#faculty_submit").show();
            $("#faculty_change").hide();
            $("#faculty input[type=text]").attr("disabled", false);
            $("#faculty textarea").attr("disabled", false);
        }
        // $("#seats_change").click(seats_change_click);
        //
        // function seats_change_click() {
        //     alert("Dont diploma_change_click!!");
        //     $("#seats_submit").show();
        //     $("#seats_cancel").show();
        //     $("#seats_change").hide();
        //     $("#seats input").attr("disabled", false);
        // }
        //
        // $("#seats_cancel").click(seats_cancel_click);
        //
        // function seats_cancel_click() {
        //     alert("Dont diploma_cancel_click!!");
        //     $("#seats_submit").hide();
        //     $("#seats_cancel").hide();
        //     $("#seats_change").show();
        //     $("#seats input")
        //         .val("")
        //         .attr("disabled", true);
        //     $("#errorSeats")
        //         .css("display", "none")
        //         .text("");
        // }
        //
        // $("#seats_submit").click(seats_submit_click);
        //
        // function seats_submit_click() {
        //     let budgetSeats = getValueOfInput($("#budgetSeats"));
        //     let totalSeats = getValueOfInput($("#totalSeats"));
        //     if (validateNumber(budgetSeats) && validateNumber(totalSeats) && budgetSeats.checkValidity() && totalSeats.checkValidity()) {
        //         alert("ajax");
        //         $.ajax({
        //             url: 'controller',
        //             type: 'get',
        //             data: {command: "changeSeats", type: "AJAX", budgetSeats: budgetSeats, totalSeats: totalSeats},
        //             headers: {
        //                 Accept: "application/json; charset=utf-8",
        //                 "Content-Type": "application/json; charset=utf-8"
        //             },
        //             success: function (data) {
        //                 var empt = $("#errorSeats").html();
        //                 if (empt != null || empt != "") {
        //                     $("#errorSeats").html("");
        //                 }
        //                 alert("success");
        //                 $("#seats_submit").hide();
        //                 $("#seats_cancel").hide();
        //                 $("#seats_change").show();
        //                 $("#budgetSeats")
        //                     .attr("placeholder", budgetSeats)
        //                     .attr("disabled", true)
        //                     .val("");
        //                 $("#totalSeats")
        //                     .attr("placeholder", totalSeats)
        //                     .attr("disabled", true)
        //                     .val("");
        //                 $("#errorDiploma")
        //                     .css("display", "none")
        //                     .text("");
        //             },
        //             error: function (xhr, ajaxOptions, thrownError) {
        //                 alert("error" + xhr);
        //                 $("#errorDiploma").val("check your mark, pls!");
        //             }
        //         });
        //     } else {
        //         $("#errorSeats")
        //             .css("display", "inline-block")
        //             .text("check your mark, pls!");
        //     }
        // }

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
                $(button_delete)
                    .attr("type", "button")
                    .css("border", "solid 2pt rgb(100, 100, 100)")
                    .attr("class", "subject_delete_button")
                    .attr("id", $(entrant_subjects[i].getElementsByClassName("id")).attr("id"));
                button_delete.addEventListener("click", subject_delete_button_click);
                button_delete.value = "Delete";
                $(entrant_subjects[i]).append(button_delete);
            }

            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "getOtherSubjects", type: "AJAX", facultyId: ${requestScope.facultyInfo.faculty.id}, target: "faculty"},
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (data) {

                    let subjectsGson = $.parseJSON(data);

                    let element = document.getElementById('all_subjects');

                    $(element).css("display", "flex");
                    $(element).css("flex-direction", "column");
                    $(element).css("border", "solid 2pt rgb(100, 100, 100)");

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

                    // element.appendChild(fragment);
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
            // let subject_id = $(button).attr("id");
            let subject_div = $(button).parent();

            // let input_number = document.createElement("input");
            // $(input_number)
            //     .attr("type", "number")
            //     .attr("id", subject_id)
            //     .attr("class", "mark no-spinners")
            //     .val(0);

            button.removeEventListener("click", subject_add_button_click);
            button.addEventListener("click", subject_delete_button_click);

            $(button)
                .removeAttr("class")
                .attr("class", "subject_delete_button")
                .val("Delete");

            // $($(subject_div).find("h4")).after(input_number);
            $(subject_div).remove();
            $("div #faculty_subjects").append(subject_div);
        }

        $("#subject_delete_button").click(subject_delete_button_click);

        function subject_delete_button_click() {
            alert("Dont subject_delete_button_click!!");
            let button = event.target;
            // let subject_id = $(button).attr("id");
            let subject_div = $(button).parent();

            button.removeEventListener("click", subject_delete_button_click);
            button.addEventListener("click", subject_add_button_click);

            $(button)
                .removeAttr("class")
                .attr("class", "subject_add_button")
                .val("Add");

            // $(subject_div).find(".mark").remove();
            $(subject_div).remove();
            $("div #all_subjects").append(subject_div);
        }

        $("#subject_cancel").click(subject_cancel_click);

        function subject_cancel_click() {
            alert("Dont subject_cancel_click!!");
            if ($('.noSubjects').length) {
                alert("element(s) found");
                $("div #faculty_subjects").empty();
                $("div #all_subjects").empty();
                $("div #faculty_subjects").append("<div class=\"noSubjects\">\n" +
                    "                               <h1><b><fmt:message key="profile.view_jsp.label.no_subjects_message"/></b></h1>\n" +
                    "                           </div>");
                $("#subject_cancel").hide();
                $("#subject_submit").hide();
                $("#subject_change").show();
            } else {
                alert("nothing found");
                <%--let subjects =--%>
                <%--${jsonMarks}.--%>
                <%--clientSubjects;--%>
                $("div #faculty_subjects").empty();
                $("div #all_subjects").empty();
                fillSubjects();
                <%--for (var i = 0; i < subjects.length; i++) {--%>
                    <%--let subjectName;--%>
                    <%--<c:if test="${sessionScope.lang eq 'ru'}">--%>
                    <%--subjectName = subjects[i].subject.nameRu;--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${sessionScope.lang eq 'en'}">--%>
                    <%--subjectName = subjects[i].subject.nameEng;--%>
                    <%--</c:if>--%>
                    <%--$("div #entrant_subjects").append(--%>
                        <%--"<div class=\"subject-field\" style=\"display:flex; justify-content: space-between;\">" +--%>
                        <%--"   <input type=\"hidden\" class=\"id\" id=\"" + subjects[i].subject.id + "\" disabled>" +--%>
                        <%--// "   <h4>" + subjects[i].subject.nameEng + "</h4>" +--%>
                        <%--"   <h4>" + subjectName + "</h4>" +--%>
                        <%--"   <input type=\"number\" class=\"mark no-spinners\" placeholder=\"" + subjects[i].mark.mark + "\" disabled>" +--%>
                        <%--"</div>"--%>
                    <%--);--%>
                <%--}--%>
                $("#subject_cancel").hide();
                $("#subject_submit").hide();
                $("#subject_change").show();
            }
        }

        $("#subject_submit").click(subject_submit_click);

        function subject_submit_click() {
            let childs = document.getElementById("faculty_subjects").getElementsByTagName('div');
            if (childs.length > 3) {
                $("#error").text("Too much subjects");
            } else if (childs.length < 3) {
                $("#error").text("Not enough subjects");
            }
            // else if (!validateMarks(childs)) {
            //     $("#error").text("Marks have to be between 0 and 100");
            // }
            else {
                // let s = subjectsJson;
                let s = ${subjects};
                <%--${jsonMarks}.--%>
                <%--clientSubjects;--%>
                if (compareArrays(childs, s)) {
                    alert("old");
                    subject_cancel_click();
                } else {
                    let jsonString = makeJsonOfSubjects(childs);
                    alert("new");
                    $.ajax({
                        url: 'controller',
                        type: 'get',
                        data: {command: "changeFacultySubjects", type: "AJAX", subjects: jsonString, facultyId: ${requestScope.facultyInfo.faculty.id}},
                        headers: {
                            Accept: "application/json; charset=utf-8",
                            "Content-Type": "application/json; charset=utf-8"
                        },
                        success: function (data) {
                            alert("success");
                            document.location.reload(true)
                            // subject_cancel_click();
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

        function validateNumber(number) {
            return number != null && number > 0 && /^[1-9]\d*$/.test(number);
        }

        function getValueOfInput(element) {
            if ($(element).val() === '') {
                return $(element).attr('placeholder');
            } else {
                return $(element).val();
            }
        }

        function makeJsonOfSubjects(subjects) {
            // let subject_id = subjectsJson;
            let subject_id = ${requestScope.subjects};
            <%--${jsonMarks}.--%>
            <%--clientSubjects--%>
            let lengthOfMarks = subject_id.length;
            let obj = [];
            for (let i = 0; i < subjects.length; i++) {
                let facultySubject = {};
                if(lengthOfMarks > 0) {
                    facultySubject.id = subject_id[i].id;
                }else{
                    facultySubject.id = -1;
                }
                facultySubject.subjectId = parseInt(subjects[i].getAttribute('id'));
                facultySubject.facultyId = "?";
                // facultySubject.mark = parseInt(getValueOfInput(subjects[i].getElementsByClassName('mark')));
                // marks.push(subject);
                obj.push(facultySubject);
            }
            // obj.marks = marks;
            return JSON.stringify(obj);
        }

        function compareArrays(array1, array2) {
            if (!(array1.length === array2.length)) {
                return false;
            }
            for (let i = 0; i < array1.length; i++) {
                // let mark1 = parseInt(getValueOfInput(array1[i].getElementsByClassName('mark')));
                // let mark2 = array2[i].mark.mark;
                let subjectid1 = parseInt(array1[i].getAttribute('id'));
                let subjectid2 = array2[i].id;
                // let b1 = mark1 !== mark2;
                // let b2 = subjectid1 !== subjectid2;
                // if (mark1 !== mark2 || subjectid1 !== subjectid2) {
                if (subjectid1 !== subjectid2) {
                    return false
                }
            }
            return true;
        }
    });
</script>
</html>