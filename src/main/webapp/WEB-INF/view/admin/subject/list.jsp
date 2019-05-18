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
    <style>
        table {
            width: 100%;
        }

        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }

        th, td {
            padding: 15px;
            text-align: left;
        }

        table#t01 tr:nth-child(even) {
            background-color: #eee;
        }

        table#t01 tr:nth-child(odd) {
            background-color: #fff;
        }

        table#t01 th {
            background-color: black;
            color: white;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>

<%@ include file="/WEB-INF/view/jspf/navbar.jspf" %>
<%--<ctg:navbar/>--%>
<div id="container">
    <div class="content">
        <div class="header">
            <fmt:message key="subject.list_jsp.label.subjects_list"/>
        </div>






        <div class="info">

            <input type="button" id="subject_cancel" class="btn" style="display: none"
                   value="<fmt:message key="profile.view_jsp.button.cancel"/>">
            <input type="button" id="subject_submit" class="btn" style="display: none"
                   value="<fmt:message key="profile.view_jsp.button.submit"/>">
            <input type="button" id="subject_change" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.change"/>">
        </div>
        <div class="info">
            <div id="addSubject"></div>
            <input type="button" id="subject_add" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.logout"/>">
            <input type="button" id="subject_add_cancel" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.cancel"/>">
            <input type="button" id="subject_add_submit" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.logout"/>">
            <a href="controller?command=addSubject"><fmt:message
                    key="subject.list_jsp.button.add"/></a>
        </div>
        <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
    </div>
</div>
<%--<table id="t01">--%>
<%--<tr>--%>
<%--<th>Subject id</th>--%>
<%--<th>Subject ru</th>--%>
<%--<th>Subject eng</th>--%>
<%--</tr>--%>
<%--<c:forEach var="subject" items="${allSubjects}">--%>
<%--<tr>--%>
<%--<td><input class="no-spinners" type="number" value="<c:out value="${subject.id}"></c:out>"--%>
<%--disabled>--%>
<%--</td>--%>
<%--<td><input class="input-field" type="text"--%>
<%--palceholder="<c:out value="${subject.nameRu}"></c:out>"--%>
<%--disabled></td>--%>
<%--<td><input class="input-field" type="text"--%>
<%--placeholder="<c:out value="${subject.nameEng}"></c:out>"--%>
<%--disabled></td>--%>
<%--</tr>--%>
<%--</c:forEach>--%>
<%--</table>--%>

</body>
<script>
    $(document).ready(function () {
        updateSubjects();

        $("#subject_change").click(subject_change_click);

        function updateSubjects() {
            // let parent = document.querySelector('.info:first');
            let parent = $(".info:first");
            let table = document.createElement('table');
            table.id = 't01';
            $(table).append("<tr>\n" +
                "        <th>Subject id</th>\n" +
                "        <th>Subject ru</th>\n" +
                "        <th>Subject eng</th>\n" +
                "    </tr>");
            let subjectsJson = ${subjectsGson};
            for (let i = 0; i < subjectsJson.length; ++i) {
                $(table).append("<tr>\n" +
                    "            <td><input class=\"no-spinners\" type=\"number\" value=\"" + subjectsJson[i].id + "\"\n" +
                    "                       disabled>\n" +
                    "            </td>\n" +
                    "            <td><input class=\"input-field\" type=\"text\"\n" +
                    "                       placeholder=\"" + subjectsJson[i].nameRu + "\"\n" +
                    "                       disabled></td>\n" +
                    "            <td><input class=\"input-field\" type=\"text\"\n" +
                    "                       placeholder=\"" + subjectsJson[i].nameEng + "\"\n" +
                    "                       disabled></td>\n" +
                    "        </tr>");
            }
            $(parent).prepend(table);
        }


        function subject_change_click() {
            alert("Dont subject_change_click!!");
            $("#subject_submit").show();
            $("#subject_change").hide();
            $("input[type=text]").attr("disabled", false);
        }

        $("#subject_cancel").click(subject_cancel_click);

        function subject_cancel_click() {
            alert("Dont subject_cancel_click!!");
            let parent = document.querySelector('.info:first');
            parent.innerHTML = "";
            updateSubjects();
            $("#subject_submit").hide();
            $("#subject_cancel").hide();
            $("#subject_change").show();
        }

        $("#subject_submit").click(subject_submit_click);

        function subject_submit_click() {
            <%--let s = ${subjectsGson};--%>

            let childs = document.getElementsByTagName("td");
            let jsonString = makeJsonOfSubjects(childs);

            alert("new");
            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "updateSubjects", type: "AJAX", subjects: jsonString},
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

        $("#subject_add").click(subject_add_click);

        function subject_add_click() {
            let parent = document.getElementById("addSubject");

            let ruInput = document.createElement("input");

            let engInput = document.createElement("input");


        }


        function makeJsonOfSubjects(subjects) {
            let counter = 1;
            let obj = [];

            let subject = {};
            for (let i = 0; i < subjects.length; i++) {
                // let subjectInput = subjectsRows[i].innerHTML;
                let value = getValueOfInput(subjects[i].innerHTML);
                if(counter === 1){
                    subject.id = value;
                }else if(counter===2){
                    subject.nameRu = value;
                }else{
                    subject.nameEng = value;
                    obj.push(subject);
                    subject = {};
                    counter = 0
                }
                ++counter;
            }
            return JSON.stringify(obj);
        }

        function getValueOfInput(element) {
            if ($(element).val() === '') {
                return $(element).attr('placeholder');
            } else {
                return $(element).val();
            }
        }

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

    });
</script>

</html>