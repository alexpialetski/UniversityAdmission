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

        </div>
        <input type="button" id="subject_change" class="btn"
               value="<fmt:message key="profile.view_jsp.button.change"/>">
        <input type="button" id="subject_submit" class="btn" style="display: none"
               value="<fmt:message key="profile.view_jsp.button.submit"/>">
        <input type="button" id="subject_cancel" class="btn" style="display: none"
               value="<fmt:message key="profile.view_jsp.button.cancel"/>">

        <div class="info">
            <div id="addSubject">
                <%--<form id="subject" class="form-input" method="POST" action="controller"--%>
                <%--style="border: solid 5pt rgb(100, 100, 100); display: none">--%>
                <%--<input type="hidden" name="command" value="addSubject">--%>
                <%--<input type="text" class="input-field" name="nameEng"--%>
                <%--placeholder="Subject name"--%>
                <%--required="required">--%>
                <%--<input type="text" class="input-field" name="nameRu"--%>
                <%--placeholder="Subject nameru"--%>
                <%--required="required">--%>
                <%--<input type="submit" class="btn">--%>
                <%--</form>--%>
            </div>
            <input type="button" id="subject_add" class="btn"
                   value="<fmt:message key="subject.list_jsp.button.add"/>">
            <input type="button" id="subject_add_submit" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.submit"/>" style="display: none">
            <input type="button" id="subject_add_cancel" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.cancel"/>" style="display: none">
        </div>
        <div class="info">
            <input type="button" id="subject_delete" class="btn"
                   value="<fmt:message key="subject.list_jsp.button.delete"/>">
            <input type="button" id="subject_delete_submit" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.delete.submit"/>" style="display: none">
            <input type="button" id="subject_delete_cancel" class="btn"
                   value="<fmt:message key="profile.view_jsp.button.delete.cancel"/>" style="display: none">
            <%--<a href="controller?command=addSubject"><fmt:message--%>
            <%--key="subject.list_jsp.button.add"/></a>--%>
        </div>
    </div>
    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</div>
</div>
</body>
<script>
    $(document).ready(function () {
        updateSubjects();

        function updateSubjects() {
            // let parent = document.querySelector('.info:first');
            let parent = $(".info:first");
            let table = document.createElement('table');
            table.id = 't01';
            $(table).append("<tr>" +
                "        <th class=\"hidden\" style=\"display:none\">Delete</th> " +
                "        <th>Subject id</th>\n" +
                "        <th>Subject ru</th>\n" +
                "        <th>Subject eng</th>\n" +
                "    </tr>");
            let subjectsJson = ${subjectsGson};
            for (let i = 0; i < subjectsJson.length; ++i) {
                $(table).append("<tr>" +
                    "           <td class=\"hidden\" style=\"display:none\"><input  id=\"" + subjectsJson[i].id + "\" type=\"checkbox\"></td>" +
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

        $("#subject_change").click(subject_change_click);

        function subject_change_click() {
            alert("Dont subject_change_click!!");
            $("#subject_submit").show();
            $("#subject_cancel").show();
            $("#subject_change").hide();
            $("input[type=text]").attr("disabled", false);
        }

        $("#subject_cancel").click(subject_cancel_click);

        function subject_cancel_click() {
            alert("Dont subject_cancel_click!!");
            // let parent = document.querySelector('.info:first');
            $('.info:first').empty();
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
            $("#subject_add_submit").show();
            $("#subject_add_cancel").show();
            $("#subject_add").hide();
            let parent = document.getElementById("addSubject");
            $("#subject").show();
            $(parent).prepend('<form class="form-input" method="POST" action="controller">' +
                '<h4>Fill all fields, pls</h4>' +
                '<input type=\"hidden\" name=\"command\" value=\"addSubject\">' +
                '<input class=\"input-field\" type=\"text\" name=\"nameEng\" \n' +
                '                    placeholder=\"Subject name\" required=\"required\">' +
                '<input class=\"input-field\" type=\"text\" name=\"nameRu\"\n' +
                '                    placeholder=\"Имя предмета\" required=\"required\">' +
                // '<input type=\"submit\" class=\"btn\" value="Click">' +
                '</form>');
        }

        $("#subject_add_submit").click(subject_add_submit_click);

        function subject_add_submit_click() {
            document.querySelector("#addSubject form").submit();
        }

        $("#subject_add_cancel").click(subject_add_cancel_click);

        function subject_add_cancel_click() {
            $('#addSubject').empty();
            $("#subject_add_submit").hide();
            $("#subject_add_cancel").hide();
            $("#subject_add").show();
        }

        $("#subject_delete").click(subject_delete_click);

        function subject_delete_click() {
            $("#subject_delete_submit").show();
            $("#subject_delete_cancel").show();
            $("#subject_delete").hide();
            let hiddenElements = $('.hidden');

            for (let i = 0; i < hiddenElements.length; i++) {
                $(hiddenElements[i]).css("display", "block");
            }
        }

        $("#subject_delete_submit").click(subject_delete_submit_click);

        function subject_delete_submit_click() {
            let checkBoxes = $('.hidden input[type=checkbox]');
            let mas = [];
            for (let i = 0; i < checkBoxes.length; i++) {
                if ($(checkBoxes[i]).is(':checked')) {
                    mas.push($(checkBoxes[i]).attr("id"));
                }
            }
            if (mas.length === 0) {
                subject_cancel_click();
            } else {
                let masString = JSON.stringify(mas);
                $.ajax({
                    url: 'controller',
                    type: 'get',
                    data: {command: "deleteSubject", type: "AJAX", subjects: masString},
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

        $("#subject_delete_cancel").click(subject_delete_cancel_click);

        function subject_delete_cancel_click() {
            $("#subject_delete_submit").hide();
            $("#subject_delete_cancel").hide();
            $("#subject_delete").show();
            let checkBoxes = $('.hidden input[type=checkbox]');
            for (let i = 0; i < checkBoxes.length; i++) {
                $(checkBoxes[i]).attr("checked", "unchecked");
            }

            let elements = $('.hidden');
            for (let i = 0; i < elements.length; i++) {
                $(elements[i]).css("display", "none");
            }

        }


        function appendColumn() {
            var tbl = document.getElementById('my-table'), // table reference
                i;
            tbl.rows.item()
            // open loop for each row and append cell
            for (i = 0; i < tbl.rows.length; i++) {
                createCell(tbl.rows[i].insertCell(tbl.rows[i].cells.length), i, 'col');
            }
        }

        function createCell(cell, text, style) {
            var div = document.createElement('div'), // create DIV element
                txt = document.createTextNode(text); // create text node
            div.appendChild(txt);                    // append text node to the DIV
            div.setAttribute('class', style);        // set DIV class attribute
            div.setAttribute('className', style);    // set DIV class attribute for IE (?!)
            cell.appendChild(div);                   // append DIV to the table cell
        }

        function makeJsonOfSubjects(subjects) {
            let counter = 1;
            let obj = [];

            let subject = {};
            for (let i = 0; i < subjects.length; i++) {
                // let subjectInput = subjectsRows[i].innerHTML;
                let value = getValueOfInput(subjects[i].innerHTML);
                if (counter === 1) {
                    subject.id = value;
                } else if (counter === 2) {
                    subject.nameRu = value;
                } else {
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