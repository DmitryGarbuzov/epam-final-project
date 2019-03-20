<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="doskort" uri="/WEB-INF/footer.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Doskort</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet" >
    <link href="../image/logo.png" rel="shortcut icon" type="image/png">
    <link href="../css/owl.carousel.min.css" rel="stylesheet" type="text/css">
    <link href="../css/doskort.css" rel="stylesheet" type="text/css">
    <link href="../css/parent.css" rel="stylesheet" type="text/css">
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
                <a href="#"><fmt:message key="diary" bundle="${i18n}"/></a>
                <ul class="submenu">
                    <c:forEach var="student" items="${user.studentList}">
                        <li>
                            <a href="#" onclick="document.getElementById('find_diary${student.studentId}').submit();return false;">${student}</a>
                            <form id="find_diary${student.studentId}" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="find_diary">
                                <input type="hidden" name="studentId" value="${student.studentId}">
                                <input type="hidden" name="gradeId" value="${student.grade.gradeId}">
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </li>
            <li>
                <a href="#"><fmt:message key="homework" bundle="${i18n}"/></a>
                <ul class="submenu">
                    <c:forEach var="student" items="${user.studentList}">
                        <li>
                            <a href="#" onclick="document.getElementById('find_homework${student.studentId}').submit();return false;">${student}</a>
                            <form id="find_homework${student.studentId}" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="find_homework">
                                <input type="hidden" name="studentId" value="${student.studentId}">
                                <input type="hidden" name="gradeId" value="${student.grade.gradeId}">
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </ul>
        <div id="diary_block">
            <div class="content">
                <table class="subject_table">
                    <tr id="button_row"><td>
                        <div class="center">
                            <c:if test="${fn:length(dateList)==13 || lastCommand=='forward'}">
                                <form id="backbtn" action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="back_diary">
                                    <input type="hidden" name="date" value="${dateList[0]}">
                                    <input type="submit" value="❮">
                                </form>
                            </c:if>
                            <input id="currentbtn" type="button" onclick="document.getElementById('find_diary${studentId}').submit();" value="•">
                            <c:if test="${fn:length(dateList)==13 || lastCommand=='back'}">
                                <form id="forwardbtn" action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="forward_diary">
                                    <input type="hidden" name="date" value="${dateList[fn:length(dateList) - 1]}">
                                    <input type="submit" value="❯">
                                </form>
                            </c:if>
                        </div>
                    </td></tr>
                    <c:forEach var="subject" items="${subjectList}">
                        <tr><td>${subject}</td></tr>
                    </c:forEach>
                </table><table>
                    <tr id="data_row">
                        <c:forEach var="date" items="${dateList}">
                            <td><p style="writing-mode:vertical-rl">${date}</p></td>
                        </c:forEach>
                    </tr>
                    <c:forEach var="list" items="${studentMarks}">
                        <tr>
                            <c:forEach var="mark" items="${list}">
                                <td class="mark"><div class="mark_div">${mark}</div></td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div id="homework_block">
            <div class="content">
                <table class="subject_table">
                    <tr id="button_row"><td>
                        <div class="center">
                            <c:if test="${fn:length(dateList)==13 || lastCommand=='forward'}">
                                <form id="backbtn" action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="back_homework">
                                    <input type="hidden" name="date" value="${dateList[0]}">
                                    <input type="submit" value="❮">
                                </form>
                            </c:if>
                            <input id="currentbtn" type="button" onclick="document.getElementById('find_homework${studentId}').submit();" value="•">
                            <c:if test="${fn:length(dateList)==13 || lastCommand=='back'}">
                                <form id="forwardbtn" action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="forward_homework">
                                    <input type="hidden" name="date" value="${dateList[fn:length(dateList) - 1]}">
                                    <input type="submit" value="❯">
                                </form>
                            </c:if>
                        </div>
                    </td></tr>
                    <c:forEach var="subject" items="${subjectList}">
                        <tr><td>${subject}</td></tr>
                    </c:forEach>
                </table><table>
                    <tr id="data_row">
                        <c:forEach var="date" items="${dateList}">
                            <td><p style="writing-mode:vertical-rl">${date}</p></td>
                        </c:forEach>
                    </tr>
                    <c:forEach var="list" items="${studentHomework}">
                        <tr>
                            <c:forEach var="homework" items="${list}">
                                <td class="homework">
                                    <a onclick="document.getElementById('homework_modal').style.display='block';
                                                document.getElementById('text_for_work').value='${homework}';">
                                        <div class="homework_div">${homework}</div>
                                    </a>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div id="homework_modal" class="modal">
            <span onclick="document.getElementById('homework_modal').style.display='none'"
                class="close" title="Close Modal">&times;</span>
                <div id="show_homework_block">
                    <form class="content">
                        <div class="container">
                            <label><fmt:message key='homework' bundle='${i18n}'/></label>
                            <textarea id="text_for_work" readonly></textarea>
                            <button type="button" onclick="document.getElementById('homework_modal').style.display='none'"
                                    class="register">OK</button>
                        </div>
                    </form>
                </div>
        </div>
        <div id="modal">
            <div id="modal-content">
                <p id="message-text"></p>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.message != null}">
        <c:choose>
            <c:when test="${sessionScope.message=='has_no_active_student'}">
                <script>
                    document.getElementById('message-text').innerText = '<fmt:message key="${sessionScope.message}" bundle="${i18n}"/>';
                    document.getElementById('modal').style.display = 'block';
                </script>
            </c:when>
            <c:when test="${sessionScope.message=='show_diary_block'}">
                <script>
                    document.getElementById('diary_block').style.display = 'block';
                </script>
            </c:when>
            <c:when test="${sessionScope.message=='show_homework_block'}">
                <script>
                    document.getElementById('homework_block').style.display = 'block';
                </script>
            </c:when>
            <c:otherwise>
                <script>
                    document.getElementById('message-text').innerText = '<fmt:message key="${sessionScope.message}" bundle="${i18n}"/>';
                    document.getElementById('modal').style.display = 'block';
                    setTimeout(function(){
                        document.getElementById('modal').style.display = 'none';
                    }, 2000);
                </script>
            </c:otherwise>
        </c:choose>
        <c:remove var="message" scope="session"/>
    </c:if>
    <doskort:footer/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
    <script src="../script/parent.js"></script>
</body>
</html>
