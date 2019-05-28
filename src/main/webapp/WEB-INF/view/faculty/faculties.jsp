<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="title.faculties"/></title>
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
</head>
<style>
    .pag {
        display: inline-block;
    }

    .pag span {
        color: black;
        float: left;
        padding: 8px 16px;
        text-decoration: none;
        transition: background-color .3s;
    }

    .pag span.active {
        background-color: #4CAF50;
        color: white;
    }

    .pag span:hover:not(.active) {
        background-color: #ddd;
    }
</style>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

<div id="container">
    <div class="content">
        <div class="search">
            <h4><fmt:message key="faculties.text.search"/></h4>
            <input type="text" oninput="searchFor(this.value)" placeholder="<fmt:message key="faculties.ph.search"/>">
        </div>
        <h1 id="facultyLabel"><fmt:message key="faculties.text.faculties"/></h1>
        <div id="pagination" class="info" style="align-items: center;">
        </div>
        <h1 id="message"><fmt:message key="faculties.text.no_results"/></h1>
    </div>

    <%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</div>
</body>
<script>
    loadMessages();
    let faculties = [];
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
                let element = $('.content');
                for (let i = 0; i < facultiesGson.length; i++) {
                    let info = document.createElement("div");
                    $(info)
                        .attr("class", "info")
                        .attr("id", facultiesGson[i].faculty.id)
                        .css("display", "flex")
                        .css("width", "80%");
                    $(info).append(
                        "                <div class=\"image-greeting\">" +
                        "                    <div class=\"greeting\" style=\" text-align: center;\">" +
                        "                        <h2>" + <c:if test="${sessionScope.lang eq 'ru'}">
                        facultiesGson[i].faculty.nameRu +
                        </c:if>
                        <c:if test="${sessionScope.lang eq 'en'}">
                        facultiesGson[i].faculty.nameEng +
                        </c:if>
                        "                        </h2><br>" +
                        "<h3>" +
                        "                            <fmt:message key="faculties.text.information"/>" +
                        "                        </h3>" +
                        "                    </div>" +
                        "                </div>" +
                        "                <div class=\"facultyInformation\">" +
                        <c:if test="${sessionScope.lang eq 'ru'}">
                        facultiesGson[i].faculty.infoRu +
                        </c:if>
                        <c:if test="${sessionScope.lang eq 'en'}">
                        facultiesGson[i].faculty.infoEng +
                        </c:if>
                        "                </div>");
                    let subjects = document.createElement("div");
                    $(subjects).attr("id", "entrant_subjects");

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
                    $(info).append(subjects);
                    <c:choose>
                    <c:when test="${requestScope.results eq false}">
                    <c:if test="${sessionScope.userRole eq 'client'}">
                    <c:choose>
                    <c:when test="${not empty requestScope.applied}">
                    let facultyId = facultiesGson[i].faculty.id;
                    if (facultyId === ${requestScope.applied}) {
                        let button_apply = document.createElement("input");
                        $(button_apply).attr("type", "button");
                        $(button_apply).attr("class", "btn");
                        $(button_apply).val("<fmt:message key="faculties.button.pick_up_documents"/>");
                        button_apply.addEventListener("click", unapply_button_click);
                        $(info).append(button_apply);
                    } else {
                        let button_unapply = document.createElement("input");
                        $(button_unapply).attr("type", "button");
                        $(button_unapply).attr("class", "btn");
                        $(button_unapply).val("<fmt:message key="faculties.button.apply"/>");
                        button_unapply.addEventListener("click", apply_button_click);
                        $(info).append(button_unapply);
                    }
                    </c:when>
                    <c:otherwise>
                    let button_apply = document.createElement("input");
                    $(button_apply).attr("type", "button");
                    $(button_apply).attr("class", "btn");
                    $(button_apply).val("<fmt:message key="faculties.button.apply"/>");
                    button_apply.addEventListener("click", apply_button_click);
                    $(info).append(button_apply);
                    </c:otherwise>
                    </c:choose>
                    <c:choose>
                    <c:when test="${not empty applied}">
                    if (facultyId === ${applied}) {
                        faculties.unshift(info);
                    } else {
                        faculties.push(info);
                    }
                    </c:when>
                    <c:otherwise>
                    faculties.push(info);
                    </c:otherwise>
                    </c:choose>
                    </c:if>
                    </c:when>
                    <c:otherwise>
                    faculties.push(info);
                    </c:otherwise>
                    </c:choose>

                    <c:if test="${sessionScope.userRole eq 'admin'}">
                    $(info).append("<form method='GET' action='controller'>" +
                        "<input type='submit' class='btn' value='Change'>" +
                        "<input type='hidden' name='command' value='viewFaculty'>" +
                        "<input type='hidden' name='facultyId' value='" + facultiesGson[i].faculty.id + "'>" +
                        "</form>");
                    <c:if test="${requestScope.results eq false}">
                            faculties.push(info);
                        </c:if>
                    </c:if>
                }
                loadPagination();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                window.location.assign("/UniversityAdmission/WEB-INF/client/errorPage.jsp");
            }
        });

        function apply_button_click() {
            <c:choose>
            <c:when test="${not empty requestScope.applied}">
            createElement("warning", "<fmt:message key="message.warning"/>", "<fmt:message key="message.applied"/>");
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
                    let error = JSON.parse(data);
                    if (error.errorEng === "none") {
                        $("#facultiesHeader").click();
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
            </c:otherwise>
            </c:choose>
        }

        function unapply_button_click() {
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
                    let error = JSON.parse(data);
                    if (error.errorEng === "none") {
                        $("#facultiesHeader").click();
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
    });

    function addPaginationButtons(pagination, activeNum) {
        let count = 0;
        let flexObjects = countFlex(faculties);
        (flexObjects % 3 === 0) ? count = flexObjects / 3 : count = Math.floor(flexObjects / 3) + 1;
        let pagBar = document.createElement("div");
        pagBar.classList.add("pag");
        for (let i = 0; i < count; i++) {
            let button = document.createElement("span");
            button.innerText = (i + 1).toString();
            if (i === activeNum) {
                button.classList.add("active");
            }
            button.addEventListener("click", clickPaginationButton);
            $(pagBar).append(button);
        }
        $(pagination).append(pagBar);
    }

    function loadPagination() {
        let pagination = document.getElementById("pagination");
        pagination.innerHTML = "";
        addPaginationButtons(pagination, 0);
        fillPagination(0, 3);
        addPaginationButtons(pagination, 0);
    }

    function fillPagination(left, toDisplay) {
        for (let i = 0; i < faculties.length; i++) {
            if (faculties[i].style.display === "flex") {
                if (left === 0 && toDisplay !== 0) {
                    $(pagination).append(faculties[i]);
                    --toDisplay;
                } else {
                    --left;
                }
            }
        }
    }

    function countFlex(obj) {
        let counter = 0;
        for (let i = 0; i < obj.length; i++) {
            if (faculties[i].style.display === "flex") {
                ++counter;
            }
        }
        return counter;
    }

    function clickPaginationButton() {
        let pagination = document.getElementById("pagination");
        pagination.innerHTML = "";
        let button = event.target;
        let num = button.innerHTML;
        addPaginationButtons(pagination, parseInt(num-1));
        let left = (num - 1) * 3;
        let toDisplay = 3;
        if ((faculties.length - left) < 3) {
            toDisplay = faculties.length - left;
        }
        fillPagination(left, toDisplay);
        addPaginationButtons(pagination, parseInt(num-1));
    }

    function searchFor(value) {
        let hasOne = false;
        for (let i = 0; i < faculties.length; i++) {
            let name = (faculties[i]).getElementsByClassName("greeting")[0].getElementsByTagName("h2")[0].innerHTML;
            if ((name.toUpperCase()).search((value.toUpperCase())) === -1) {
                faculties[i].style.display = "none";
            } else {
                faculties[i].style.display = "flex";
                hasOne = true;
            }
        }
        let pagination = $("#pagination");
        if (!hasOne) {
            $(pagination).hide();
            $("#message").css("display", "inline-block");
        } else {
            if((pagination).is(":hidden")){
                $(pagination).show();
            }
            loadPagination();
            $("#message").css("display", "none");
        }
    }
</script>
</html>
