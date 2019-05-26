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
<style>
    .pag {
        display: inline-block;
    }

    .pag a {
        color: black;
        float: left;
        padding: 8px 16px;
        text-decoration: none;
        transition: background-color .3s;
    }

    .pag a.active {
        background-color: #4CAF50;
        color: white;
    }

    .pag a:hover:not(.active) {
        background-color: #ddd;
    }
</style>
<body>
<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>
<%@ include file="/WEB-INF/view/jspf/sideBar.jspf" %>
<%--<ctg:navbar/>--%>

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
                    // $(element).append("<div class=\"info\">\n" +
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
                        "                <div id=\"faculty-label\">" +
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
                    $(info).append(subjects);//
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
                        // $(element).prepend(info);
                        // $(info).insertAfter("#facultyLabel");
                        faculties.unshift(info);
                    } else {
                        // $(element).append(info);
                        faculties.push(info);
                    }
                    </c:when>
                    <c:otherwise>
                    // $(element).append(info);
                    // $(element).append(info);
                    faculties.push(info);
                    </c:otherwise>
                    </c:choose>
                    </c:if>
                    </c:when>
                    <c:otherwise>
                    // $(element).append(info);
                    faculties.push(info);
                    </c:otherwise>
                    </c:choose>

                    <c:if test="${sessionScope.userRole eq 'admin'}">
                    $(info).append("<form method='GET' action='controller'>" +
                        "<input type='submit' class='btn' value='Change'>" +
                        "<input type='hidden' name='command' value='viewFaculty'>" +
                        "<input type='hidden' name='facultyId' value='" + facultiesGson[i].faculty.id + "'>" +
                        "</form>");
                    // $(element).append(info);
                    faculties.push(info);
                    </c:if>
                }
                loadPagination();
            },
            error: function (xhr, ajaxOptions, thrownError) {
                <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
                var errorMsg = 'Ajax request failed: ' + xhr.responseText;
                $('#content').html(errorMsg);
            }
        });

        function apply_button_click() {
            <c:choose>
            <c:when test="${not empty requestScope.applied}">
            <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
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
                        $("#facultiesHeader").click();
                    } else {
                        <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
                        alert("Cant apply:" + dataJson.error);
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
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
                        <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
                        alert("cant unapply");
                    } else {
                        <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
                        $("#facultiesHeader").click();
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    <%--ALlllerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrt--%>
                    $('#content').html(errorMsg);
                }
            });
        }
    });

    function addPaginationButtons(pagination){
        let count = 0;
        // if (faculties.length % 3 === 0) {
        //     count = faculties.length / 3;
        // } else {
        //     count = Math.floor(faculties.length / 3) + 1;
        // }
        (faculties.length%3===0) ? count = faculties.length / 3 : count = Math.floor(faculties.length / 3) + 1;
        let pagBar = document.createElement("div");
        pagBar.classList.add("pag");
        for(let i=0; i<count; i++){
            let button = document.createElement("span");
            button.addEventListener(reloadPagination());
            $(pagBar).append(button);
        }
    }

    function loadPagination() {
        let pagination = document.getElementById("pagination");
        pagination.innerHTML = "";
        addPaginationButtons(pagination);
        for (let i = 0; i < 3; i++) {
            if (faculties[i].style.display === "flex") {
                $(pagination).append(faculties[i]);
            }
        }
    }

    function reloadPagination() {
        let button = event.target;
        alert(button.innerHTML);
    }

    function searchFor(value) {
        // let elements = document.getElementsByClassName("info");
        let hasOne = false;
        // for (let i = 0; i < elements.length; i++) {
        for (let i = 0; i < faculties.length; i++) {
            // let greeting = (elements[i]).getElementsByClassName("greeting")[0];
            // let name = greeting.getElementsByTagName("h2")[0].innerHTML;
            let name = (faculties[i]).getElementsByClassName("greeting")[0].getElementsByTagName("h2")[0].innerHTML;
            if ((name.toUpperCase()).search((value.toUpperCase())) === -1) {
                // elements[i].style.display = "none";
                faculties[i].style.display = "none";
            } else {
                // elements[i].style.display = "flex";
                faculties[i].style.display = "flex";
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
        loadPagination();
    }
</script>
</html>
