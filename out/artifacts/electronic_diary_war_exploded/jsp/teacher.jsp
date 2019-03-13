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
    <link href="../css/teacher.css" rel="stylesheet" type="text/css">
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
        <c:forEach var="entry" items="${grades}">
            <li>
                <a href="#">${entry.key}</a>
                <ul class="submenu">
                    <c:forEach var="subject" items="${entry.value}">
                        <li>
                            <a href="#" onclick="document.getElementById('find_journal${entry.key.gradeId}${subject.subjectId}').submit();">${subject}</a>
                            <form id="find_journal${entry.key.gradeId}${subject.subjectId}" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="teacherId" value="${user.teacherId}">
                                <input type="hidden" name="gradeId" value="${entry.key.gradeId}">
                                <input type="hidden" name="subjectId" value="${subject.subjectId}">
                                <input type="hidden" name="command" value="find_journal">
                            </form>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:forEach>
        <li>
            <a href="#">Класс</a>
            <ul class="submenu">
                <li>
                    <a href="#"
                       onclick="document.getElementById('find_all_grade').submit(); return false"><fmt:message key="adding_button" bundle="${i18n}"/></a>
                    <form id="find_all_grade" action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="teacherId" value="${user.teacherId}">
                        <input type="hidden" name="command" value="grade_for_teacher">
                    </form>
                </li>
                <li>
                    <a href="#" id="lesson_deletion"><fmt:message key="deletion_button" bundle="${i18n}"/></a>
                </li>
            </ul>
        </li>
    </ul>
    <div id="add_grade_block">
        <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="lesson_add">
            <input type="hidden" name="teacherId" value="${user.teacherId}">
            <div class="container">
                <label><fmt:message key="grade" bundle="${i18n}"/></label>
                <select class="select2" name="grade" required>
                    <c:forEach var="grade" items="${gradeList}">
                        <option value="${grade.gradeId}">${grade}</option>
                    </c:forEach>
                </select><br><br>
                <label><fmt:message key="subject_selection" bundle="${i18n}"/></label>
                <select class="select2" name="subjects[]" multiple="multiple" required>
                    <c:forEach var="subject" items="${user.subjectList}">
                        <option value="${subject.subjectId}">${subject}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="register"><fmt:message key="add_button" bundle="${i18n}"/></button>
            </div>
        </form>
    </div>
    <div id="delete_grade_block">
        <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="lesson_deletion">
            <input type="hidden" name="teacherId" value="${user.teacherId}">
            <div class="container">
                <label><fmt:message key="grade_selection" bundle="${i18n}"/></label>
                <select class="select2" name="grades[]" multiple="multiple" style="width: 100%" required>
                    <c:forEach var="entry" items="${grades}">
                        <option value="${entry.key.gradeId}">${entry.key}</option>
                    </c:forEach>
                </select>
                <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
            </div>
        </form>
    </div>
    <div id="add_lesson_modal" class="modal">
        <span onclick="document.getElementById('add_lesson_modal').style.display='none'"
              class="close" title="Close Modal">&times;</span>
        <div id="add_lesson_block">
            <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="timetable_add">
                <input type="hidden" name="lessonId" value="${lessonId}">
                <div class="container">
                    <label>Выберете дату занятия</label>
                    <input type="date" name="date" required>
                    <button type="submit" class="register"><fmt:message key="add_button" bundle="${i18n}"/></button>
                    <button type="button" onclick="document.getElementById('add_lesson_modal').style.display='none'"
                            class="cancelbtn"><fmt:message key="cancel_button" bundle="${i18n}"/></button>
                </div>
            </form>
        </div>
    </div>
    <div id="journal_block">
        <div class="content">
            <input class="add_lesson" type="button" value="Add lesson" onclick="document.getElementById('add_lesson_modal').style.display='block'">
            <input class="delete_lesson" type="button" value="Delete lesson" onclick="document.getElementById('delete_lesson_modal').style.display='block'">
            <input class="find_lesson" type="button" value="Find Lesson" onclick="document.getElementById('find_lesson_modal').style.display='block'">
            <br>
            <table>
                <c:forEach var="student" items="${studentList}">
                    <tr><td>${student}</td></tr>
                </c:forEach>
            </table><c:if test="${fn:length(dateList) gt 0}"><table>
                    <tr>
                        <c:forEach var="date" items="${dateList}">
                            <td><p style="writing-mode:vertical-rl">${date}</p></td>
                        </c:forEach>
                    </tr>
                <c:forEach var="list" items="${studentsMarks}">
                    <tr>
                        <c:forEach var="mark" items="${list}">
                            <td class="mark"><a href="#"><div class="mark_div">${mark}</div></a></td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table></c:if>
        </div>
    </div>
    <c:if test="${sessionScope.message != null}">
        <c:choose>
            <c:when test="${sessionScope.message=='show_add_grade_block'}">
                <script>document.getElementById('add_grade_block').style.display = 'block';</script>
            </c:when>
            <c:otherwise>
                <div id="modal">
                    <div id="modal-content">
                        <p><fmt:message key="${sessionScope.message}" bundle="${i18n}"/></p>
                    </div>
                </div>
                <script>
                    setTimeout(function(){
                        document.getElementById('modal').style.display = 'none';
                    }, 2000);
                </script>
            </c:otherwise>
        </c:choose>
        <c:remove var="message" scope="session"/>
    </c:if>
</div>
<doskort:footer/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
<script src="../script/teacher.js"></script>
</body>
</html>
