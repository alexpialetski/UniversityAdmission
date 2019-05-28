<%@ include file="/WEB-INF/view/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/view/jspf/directive/taglib.jspf" %>

<fmt:setLocale value="${sessionScope.lang}"/>

<html lang="${sessionScope.lang}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="title.login"/></title>
    <%@ include file="/WEB-INF/view/jspf/head.jspf" %>
    <link rel="stylesheet" type="text/css" href="css/login.css">
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
<%@ include file="/WEB-INF/view/jspf/message.jspf" %>

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
            <p style="font-size: 18px;"><fmt:message key="welcome_jsp.label.not_registered_msg"/>
                <%--<a href="controller?command=view_registration"><fmt:message--%>
                        <%--key="welcome_jsp.label.register_here_msg"/>!</a>--%>
            </p>
            <form class="form" action="controller" method="GET">
                <input type="hidden" name="command" value="view_registration">
                <input style="color: black;text-align: center;padding: 4px 10px;font-size: 18px;line-height: 25px;border-radius: 10px;background-color: rgb(115, 212, 167);box-shadow: 0 4px 8px 0 rgba(197, 197, 197, 0.2), 0 6px 20px 0 rgba(197, 197, 197, 0.2);border-color: rgba(255,255,255,1);" type="submit" value="<fmt:message key="welcome_jsp.label.register_here_msg"/>">
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/view/jspf/footer.jspf" %>
</body>
<script src="js/messages.js"></script>
<script>
    loadMessages();
</script>
</html>