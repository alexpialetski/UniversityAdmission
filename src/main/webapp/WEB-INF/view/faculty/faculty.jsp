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
        <div class="search" style="display: flex; justify-content: center; left: 100%;">
            <h4>Search:</h4>
            <input type="text" oninput="searchFor(this.value)" placeholder="Search faculty"
                   style=" background: #4e9af1;display: inline-block;width: 50%;margin-left: 5%;">
            <input type="button" value="search!" style="width: 10%;">
        </div>
        <h1 id="facultyLabel"><fmt:message key="faculty.label.faculties"/></h1>
        <h1 id="message" style="color: red; margin-top: 25%; display: none;">No results</h1>
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

        $.ajax({
            url: 'controller',
            type: 'get',
            data: {command: "getAllFaculties", type: "AJAX"},
            headers: {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            },
            success: function (data) {

                let facultiesGson = $.parseJSON(data);

                // let element = document.getElementById('all_subjects');
                let element = $('.content');
                for (let i = 0; i < facultiesGson.length; i++) {
                    let info = document.createElement("div");
                    $(info).attr("class", "info");
                    $(info).attr("id", facultiesGson[i].faculty.id);
                    // $(element).append("<div class=\"info\">\n" +
                    $(info).append(
                        "                <div class=\"photo-greeting\" style=\"border: solid 2pt rgb(0, 4, 255);\">" +
                        "                    <img src=\"images/faculty-image.png\" class=\"photo\">" +
                        "                    <div class=\"greeting\" style=\" text-align: center;\">" +
                        "                        <h1>" + <c:if test="${sessionScope.lang eq 'ru'}">
                        facultiesGson[i].faculty.nameRu +
                        </c:if>
                        <c:if test="${sessionScope.lang eq 'en'}">
                        facultiesGson[i].faculty.nameEng +
                        </c:if>
                        "                        </h1><br>" +
                        "<h1>" +
                        "                            <fmt:message key="faculty.label.information"/>" +
                        "                        </h1>" +
                        "                    </div>" +
                        "                </div>" +
                        "                <div id=\"faculty-label\">" +
                        "                    <fmt:message key="faculty.label.temp"/>" +
                        "                </div>");
                    let subjects = document.createElement("div");
                    $(subjects).attr("id", "entrant_subjects");
                    $(subjects).css("border", "solid 2pt rgb(0, 255, 21)");

                    for (let j = 0; j < facultiesGson[i].subjects.length; j++) {
                        $(subjects).append("<div class=\"subject-field\"\n" +
                            "                             id=" + facultiesGson[i].subjects[j].id + " style=\"display:flex;justify-content:space-between;\">" +
                            "                            <input type=\"hidden\" class=\"id\" id=" + facultiesGson[i].subjects[j].id + ">" +
                            "                            <h4>" +
                            <c:if test="${sessionScope.lang eq 'ru'}">
                            facultiesGson[i].subjects[j].nameRu +
                            </c:if>
                            <c:if test="${sessionScope.lang eq 'en'}">
                            facultiesGson[i].subjects[j].nameEng +
                            </c:if>
                            "                            </h4>" +
                            "                        </div>");
                    }
                    $(info).append(subjects);//
                    // $(element).append( subjects);
                    // $(element).append(
                    <c:if test="${userRole eq 'client'}">
                    <c:choose>
                    <c:when test="${not empty applied}">
                    let facultyId = facultiesGson[i].faculty.id;
                    if (facultyId === ${applied}) {
                        let button_apply = document.createElement("input");
                        $(button_apply).attr("type", "button");
                        $(button_apply).attr("class", "btn");
                        $(button_apply).val("<fmt:message key="faculty.button.pick_up_documents"/>");
                        button_apply.addEventListener("click", unapply_button_click);
                        $(info).append(button_apply);
                    } else {
                        let button_unapply = document.createElement("input");
                        $(button_unapply).attr("type", "button");
                        $(button_unapply).attr("class", "btn");
                        $(button_unapply).val("<fmt:message key="faculty.view_jsp.button.apply"/>");
                        button_unapply.addEventListener("click", apply_button_click);
                        $(info).append(button_unapply);
                    }
                    </c:when>
                    <c:otherwise>
                    let button_apply = document.createElement("input");
                    $(button_apply).attr("type", "button");
                    $(button_apply).attr("class", "btn");
                    $(button_apply).val("<fmt:message key="faculty.view_jsp.button.apply"/>");
                    button_apply.addEventListener("click", apply_button_click);
                    $(info).append(button_apply);
                    </c:otherwise>
                    </c:choose>

                    <c:choose>
                    <c:when test="${not empty applied}">
                    if (facultyId === ${applied}) {
                        // $(element).prepend(info);
                        $(info).insertAfter("#facultyLabel");
                    } else {
                        $(element).append(info);
                    }
                    </c:when>
                    <c:otherwise>
                    $(element).append(info);
                    </c:otherwise>
                    </c:choose>
                    </c:if>

                    <c:if test="${userRole eq 'admin' or userRole eq 'client'}">
                    $(info).append("<form type='GET' action='controller'>" +
                        "<input type='submit' class='btn' value='Change'>" +
                        "<input type='hidden' name='command' value='viewFaculty'>" +
                        "<input type='hidden' name='facultyId' value='" + facultiesGson[i].faculty.id + "'>  " +
                        "</form>");
                    // $(info).append("<a href='controller?command=viewFaculty&facultyId'>");
                    </c:if>
                }


            },
            error: function (xhr, ajaxOptions, thrownError) {
                var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                $('#content').html(errorMsg);
            }
        });


        function apply_button_click() {
            alert("apply_button_click!!");
            <c:choose>
            <c:when test="${not empty applied}">
            alert("you are already applied");
            </c:when>
            <c:otherwise>
            let button = event.target;
            let subject_div = $(button).parent();
            let facultyId = $(subject_div).attr("id");
            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "applyOnFaculty", type: "AJAX", facultyId: facultyId},
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (data) {
                    let dataJson = JSON.parse(data);
                    if (dataJson.error === 'none') {
                        alert("successful");
                        $("#facultiesHeader").click();
                    } else {
                        alert("Cant apply:" + dataJson.error);
                        // document.location.reload(true);
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                    $('#content').html(errorMsg);
                }
            });
            </c:otherwise>
            </c:choose>
        }

        function unapply_button_click() {
            alert("unapply_button_click!!");
            let button = event.target;
            let subject_div = $(button).parent();
            let facultyId = $(subject_div).attr("id");
            $.ajax({
                url: 'controller',
                type: 'get',
                data: {command: "unApplyOnFaculty", type: "AJAX", facultyId: facultyId},
                headers: {
                    Accept: "application/json; charset=utf-8",
                    "Content-Type": "application/json; charset=utf-8"
                },
                success: function (data) {
                    if (data === 'error') {
                        alert("cant apply");
                    } else {
                        alert("successful");
                        $("#facultiesHeader").click();
                        // document.location.reload(true);
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                    $('#content').html(errorMsg);
                }
            });
        }
    });

    function searchFor(value) {
        let elements = document.getElementsByClassName("info");
        let hasOne = false;
        for (let i = 0; i < elements.length; i++) {
            let greeting = (elements[i]).getElementsByClassName("greeting")[0];
            let name = greeting.getElementsByTagName("h1")[0].innerHTML;
            if ((name.toUpperCase()).search((value.toUpperCase())) === -1) {
                elements[i].style.display = "none";
            } else {
                elements[i].style.display = "flex";
                hasOne = true;
            }
        }
        if (!hasOne) {
            // let facultyLabel = document.getElementById("facultyLabel");
            // let message = document.createElement("h1");
            // $(message).css("color", "red");
            // $(message).css("margin-top", "50vh");
            // $(message).attr("id", "message");
            // $(message).innerHTML = "No results";
            // $(message).insertAfter(facultyLabel);
            $("#message").css("display", "inline-block");
        } else {
            $("#message").css("display", "none");
        }
    }
</script>
</html>
