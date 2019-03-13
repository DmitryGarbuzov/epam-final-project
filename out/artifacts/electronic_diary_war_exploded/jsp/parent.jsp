<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="doskort" uri="/WEB-INF/footer.tld" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Doskort</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" >
    <link href="../image/logo.png" rel="shortcut icon" type="image/png">
    <link href="../css/parent.css" rel="stylesheet" type="text/css">
    <link href="../css/owl.carousel.min.css" rel="stylesheet" type="text/css">
    <link href="../css/doskort.css" rel="stylesheet" type="text/css">
</head>
<body>
    <fmt:setLocale value="${sessionScope.language}"/>
    <fmt:setBundle basename="i18n.messages" var="i18n"/>
    <div class="page__cell">
        <header class="header">
            <div class="header__logo">
                <img src="../image/logo.png" alt="logo">
            </div>
            <a href="" class="name">Doskort</a>
            <a href="${pageContext.request.contextPath}/controller" class="log_out"><fmt:message key="log_out_button" bundle="${i18n}"/></a>
        </header>
        <div id="language">
            <p><fmt:message key="language" bundle="${i18n}"/></p>
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="language_switch">
                <input type="hidden" name="language" value="ru">
                <input type="image" src="../image/ru.png" alt="submit">
            </form>
            <form action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="language_switch">
                <input type="hidden" name="language" value="en">
                <input type="image" src="../image/en.png" alt="submit">
            </form>
        </div>
        <ul class="menu cf">
            <li>
                <a href="#">Дневник</a>
                <ul class="submenu"></ul>
            </li>
            <li>
                <a href="#">Домашнее задание</a>
                <ul class="submenu"></ul>
            </li>
            <li>
                <a href="#">Расписание</a>
                <ul class="submenu"></ul>
            </li>
        </ul>
    </div>
    <doskort:footer/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
    <script src="../script/parent.js"></script>
</body>
</html>
