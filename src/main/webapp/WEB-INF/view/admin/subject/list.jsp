<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <title><fmt:message key="title.subjects"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>
<div id="container">
    <div class="content">
        <h2><fmt:message key="subjects.text.subjects"/></h2>
        <div class="info"></div>

        <input type="button" id="subject_change" class="btn"
               value="<fmt:message key="button.change"/>">
        <input type="button" id="subject_submit" class="btn" style="display: none"
               value="<fmt:message key="button.submit"/>">
        <input type="button" id="subject_cancel" class="btn" style="display: none"
               value="<fmt:message key="button.cancel"/>">

        <div class="info">
            <div id="addSubject">
            </div>
            <div class="info">
                <input type="button" id="subject_add" class="btn"
                       value="<fmt:message key="button.add"/>">
                <input type="button" id="subject_add_submit" class="btn"
                       value="<fmt:message key="button.submit"/>" style="display: none">
                <input type="button" id="subject_add_cancel" class="btn"
                       value="<fmt:message key="button.cancel"/>" style="display: none">
            </div>
        </div>
        <div class="info">
            <input type="button" id="subject_delete" class="btn"
                   value="<fmt:message key="button.delete"/>">
            <input type="button" id="subject_delete_submit" class="btn"
                   value="<fmt:message key="button.delete.submit"/>" style="display: none">
            <input type="button" id="subject_delete_cancel" class="btn"
                   value="<fmt:message key="button.delete.cancel"/>" style="display: none">
        </div>
    </div>
    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</div>
</div>
</body>
<script>
    loadMessages();
    $(document).ready(function () {
        updateSubjects();

        function updateSubjects() {
            let parent = $(".info:first");
            let table = document.createElement('table');
            table.id = 't01';
            $(table).append("<tr>" +
                "        <th><fmt:message key="subjects.text.subject_id"/></th>\n" +
                "        <th><fmt:message key="subjects.text.subject_ru"/></th>\n" +
                "        <th><fmt:message key="subjects.text.subject_eng"/></th>\n" +
                "        <th class=\"hidden\" style=\"display:none\"><fmt:message key="subjects.text.delete"/></th> " +
                "    </tr>");
            let subjectsJson = ${subjectsGson};
            for (let i = 0; i < subjectsJson.length; ++i) {
                $(table).append("<tr>" +
                    "            <td><input class=\"no-spinners\" type=\"number\" value=\"" + subjectsJson[i].id + "\"\n" +
                    "                       disabled>\n" +
                    "            </td>\n" +
                    "            <td><input class=\"input-field\" type=\"text\"\n" +
                    "                       placeholder=\"" + subjectsJson[i].nameRu + "\"\n" +
                    "                       disabled></td>\n" +
                    "            <td><input class=\"input-field\" type=\"text\"\n" +
                    "                       placeholder=\"" + subjectsJson[i].nameEng + "\"\n" +
                    "                       disabled></td>\n" +
                    "           <td class=\"hidden\" style=\"display:none\"><input  id=\"" + subjectsJson[i].id + "\" type=\"checkbox\"></td>" +
                    "        </tr>");
            }
            $(parent).prepend(table);
        }

        $("#subject_change").click(subject_change_click);

        function subject_change_click() {
            $("#subject_submit").show();
            $("#subject_cancel").show();
            $("#subject_change").hide();
            $("input[type=text]").attr("disabled", false);
        }

        $("#subject_cancel").click(subject_cancel_click);

        function subject_cancel_click() {
            $('.info:first').empty();
            updateSubjects();
            $("#subject_submit").hide();
            $("#subject_cancel").hide();
            $("#subject_change").show();
        }

        $("#subject_submit").click(subject_submit_click);

        function subject_submit_click() {
            let childs = $('td input');
            let jsonString = makeJsonOfSubjects(childs);

            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "updateSubjects", type: "AJAX", subjects: jsonString},
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (data) {
                    let error = JSON.parse(data);
                    if (error.errorEng === "none") {
                        document.location.reload(true)
                    } else {
                        stopLoader();
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

        $("#subject_add").click(subject_add_click);

        function subject_add_click() {
            $("#subject_add_submit").show();
            $("#subject_add_cancel").show();
            $("#subject_add").hide();
            let parent = document.getElementById("addSubject");
            $("#subject").show();
            $(parent).prepend('<form class="form-input" method="POST" action="controller">' +
                '<h4><fmt:message key="subjects.text.fill_fields"/></h4>' +
                '<input type=\"hidden\" name=\"command\" value=\"addSubject\">' +
                '<input class=\"input-field\" type=\"text\" name=\"nameEng\" \n' +
                '                    placeholder=\"<fmt:message key="subjects.text.subject_eng"/>\" required=\"required\">' +
                '<input class=\"input-field\" type=\"text\" name=\"nameRu\"\n' +
                '                    placeholder=\"<fmt:message key="subjects.text.subject_ru"/>\" required=\"required\">' +
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

        function makeJsonOfSubjects(subjects) {
            let counter = 1;
            let obj = [];

            let subject = {};
            for (let i = 0; i < subjects.length; i++) {
                let value = getValueOfInput(subjects[i]);
                if (counter === 1) {
                    subject.id = value;
                } else if (counter === 2) {
                    subject.nameRu = value;
                } else if (counter === 3) {
                    subject.nameEng = value;
                    obj.push(subject);
                    subject = {};
                } else {
                    counter = 0;
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
    });
</script>

</html>