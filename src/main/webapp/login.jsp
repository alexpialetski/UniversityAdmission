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
    <%--<link rel="stylesheet" type="text/css" href="css/loader.css">--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <%--<link rel="stylesheet" type="text/css" href="css/login-example1.css">--%>
    <link rel="stylesheet" type="text/css" href="css/general.css">
    <script src="js/jquery-1.11.2.min.js"></script>
    <script src="js/scrollButton.js"></script>
    <script src="js/sideBar.js"></script>
</head>
<style>
    .circle {
        width: 100px;
        height: 100px;
        position: absolute;
        z-index: 1200;
        transform: translate(0%, -195%);
    }
    .circle .border {
        /* content: ''; */
        position: absolute;
        top: -5px;
        bottom: 0;
        left: -3px;
        right: 0;
        background: transparent;
        border-radius: 50%;
        border: 13px solid #f3f3f3;
        border-right: 13px solid #00f903;
        width: 110px;
        height: 110px;
        -webkit-animation-name: Rotate;
        -webkit-animation-duration: 2s;
        -webkit-animation-iteration-count: infinite;
        -webkit-animation-timing-function: linear;
        -moz-animation-name: Rotate;
        -moz-animation-duration: 2s;
        -moz-animation-iteration-count: infinite;
        -moz-animation-timing-function: linear;
        -ms-animation-name: Rotate;
        -ms-animation-duration: 2s;
        -ms-animation-iteration-count: infinite;
        -ms-animation-timing-function: linear;
    }
    @-webkit-keyframes Rotate {
        from {
            -webkit-transform: rotate(0deg);
        }
        to {
            -webkit-transform: rotate(360deg);
        }
    }
    @-moz-keyframes Rotate {
        from {
            -moz-transform: rotate(0deg);
        }
        to {
            -moz-transform: rotate(360deg);
        }
    }
    @-ms-keyframes Rotate {
        from {
            -ms-transform: rotate(0deg);
        }
        to {
            -ms-transform: rotate(360deg);
        }
    }
</style>
<body>

<%@ include file="/WEB-INF/view/jspf/header.jspf" %>
<a id="scrollButton"></a>

<div id="container">
    <div class="content">
        <div class="main-container">
            <div class="circle">
                <div class="border"></div>
            </div>
            <img class="overlay" src="images/login.png">
            <h1 style="margin-bottom: 0; margin-top: 8%"><fmt:message key="welcome_jsp.label.login"/></h1>
            <form action="controller" method="POST" class="form-container">
                <input type="hidden" name="command" value="login">
                <input type="hidden" name="type" value="POST">
                <div class="input-form" style="margin: 20px 15px;">
                    <input type="email" name="email" placeholder="<fmt:message key="profile.view_jsp.label.email"/>"
                           class="input-field" required>
                </div>
                <div class="input-form" style="margin: 20px 15px;">
                    <input type="password" name="password"
                           placeholder="<fmt:message key="welcome_jsp.label.password" />" class="input-field" required>
                </div>
                <input type="submit" class="btn" style="background-color:#4e9af1"
                       value="<fmt:message key="welcome_jsp.button.login" />">
                <c:if test="${not empty message}">
                    <h3>${message}</h3>
                </c:if>
            </form>
            <p><fmt:message key="welcome_jsp.label.not_registered_msg"/>
                <a href="controller?command=view_registration"><fmt:message
                        key="welcome_jsp.label.register_here_msg"/>!</a>
            </p>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
</html>