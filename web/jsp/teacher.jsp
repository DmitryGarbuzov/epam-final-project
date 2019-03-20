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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
    <script src="../script/teacher.js"></script>
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
                            <a href="#" onclick="document.getElementById('find_journal${entry.key.gradeId}${subject.subjectId}').submit(); return false;">${subject}</a>
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
            <a href="#"><fmt:message key="grade_button" bundle="${i18n}"/></a>
            <ul class="submenu">
                <li>
                    <a href="#"
                       onclick="document.getElementById('find_all_grade').submit(); return false;"><fmt:message key="adding_button" bundle="${i18n}"/></a>
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
                <input type="hidden" name="gradeId" value="${gradeId}">
                <input type="hidden" name="subjectId" value="${subjectId}">
                <div class="container">
                    <label><fmt:message key='date_selection' bundle='${i18n}'/></label>
                    <input type="date" name="date" required>
                    <button type="submit" class="register"><fmt:message key="add_button" bundle="${i18n}"/></button>
                    <button type="button" onclick="document.getElementById('add_lesson_modal').style.display='none'"
                            class="cancelbtn"><fmt:message key="cancel_button" bundle="${i18n}"/></button>
                </div>
            </form>
        </div>
    </div>
    <div id="delete_lesson_modal" class="modal">
        <span onclick="document.getElementById('delete_lesson_modal').style.display='none'"
              class="close" title="Close Modal">&times;</span>
        <div id="delete_lesson_block">
            <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="timetable_deletion">
                <input type="hidden" name="gradeId" value="${gradeId}">
                <input type="hidden" name="subjectId" value="${subjectId}">
                <div class="container">
                    <label><fmt:message key='date_selection' bundle='${i18n}'/></label>
                    <input type="date" name="date" required>
                    <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
                    <button type="button" onclick="document.getElementById('delete_lesson_modal').style.display='none'"
                            class="cancelbtn"><fmt:message key="cancel_button" bundle="${i18n}"/></button>
                </div>
            </form>
        </div>
    </div>
    <div id="add_mark_modal" class="modal">
        <span onclick="document.getElementById('add_mark_modal').style.display='none'"
              class="close" title="Close Modal">&times;</span>
        <div id="add_mark_block">
            <form name="mark_form" class="content" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="mark_add">
                <input type="hidden" name="studentId">
                <input type="hidden" name="dateId">
                <div class="container">
                    <label><fmt:message key='mark_selection' bundle='${i18n}'/></label>
                    <select class="select2" name="mark" required style="width: 50px;">
                        <option value="-1">н</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                        <option value="9">9</option>
                        <option value="10">10</option>
                    </select>
                    <button type="submit" class="register">OK</button>
                    <button type="button" onclick="document.getElementById('add_mark_modal').style.display='none'"
                            class="cancelbtn"><fmt:message key="cancel_button" bundle="${i18n}"/></button>
                </div>
            </form>
        </div>
    </div>
    <div id="add_homework_modal" class="modal">
        <span onclick="document.getElementById('add_homework_modal').style.display='none'"
              class="close" title="Close Modal">&times;</span>
        <div id="add_homework_block">
            <form name="homework_form" class="content" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="homework_add">
                <input type="hidden" name="dateId">
                <div class="container">
                    <label><fmt:message key='homework' bundle='${i18n}'/></label>
                    <textarea id="text_for_work" readonly></textarea>
                    <label><fmt:message key='new_homework' bundle='${i18n}'/></label>
                    <textarea name="homework" maxlength="199" required></textarea>
                    <button type="submit" class="register">OK</button>
                    <button type="button" onclick="document.getElementById('add_homework_modal').style.display='none'"
                            class="cancelbtn"><fmt:message key="cancel_button" bundle="${i18n}"/></button>
                </div>
            </form>
        </div>
    </div>
    <div id="journal_block">
        <div class="content">
            <input class="add_lesson" type="button" value="<fmt:message key='add_lesson_button' bundle='${i18n}'/>"
                   onclick="document.getElementById('add_lesson_modal').style.display='block'">
            <input class="delete_lesson" type="button" value="<fmt:message key='delete_lesson_button' bundle='${i18n}'/>"
                   onclick="document.getElementById('delete_lesson_modal').style.display='block'">
            <br>
            <table id="student_table">
                <tr id="button_row"><td>
                    <div class="center">
                        <c:if test="${fn:length(dateList)==13 || lastCommand=='forward'}">
                            <form id="backbtn" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="back_journal">
                                <input type="hidden" name="date" value="${dateList[0].dateId}">
                                <input type="submit" value="❮">
                            </form>
                        </c:if>
                        <input id="currentbtn" type="button" onclick="document.getElementById('find_journal${gradeId}${subjectId}').submit();" value="•">
                        <c:if test="${fn:length(dateList)==13 || lastCommand=='back'}">
                            <form id="forwardbtn" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="forward_journal">
                                <input type="hidden" name="date" value="${dateList[fn:length(dateList) - 1].dateId}">
                                <input type="submit" value="❯">
                            </form>
                        </c:if>
                    </div>
                </td></tr>
                <c:forEach var="student" items="${studentList}" varStatus="loop">
                    <tr><td>${loop.index+1} ${student}</td></tr>
                </c:forEach>
                <tr class="spacer"></tr>
                <tr><td><fmt:message key='homework' bundle='${i18n}'/></td></tr>
                <tr class="spacer"></tr>
            </table><table>
                <tr id="data_row">
                    <c:forEach var="date" items="${dateList}">
                        <td><p style="writing-mode:vertical-rl">${date}</p></td>
                    </c:forEach>
                </tr>
                <c:forEach var="list" items="${studentsMarks}" varStatus="loop1">
                    <tr>
                        <c:forEach var="mark" items="${list}" varStatus="loop2">
                            <td class="mark">
                                <a onclick="document.getElementById('add_mark_modal').style.display='block';
                                            document.mark_form.studentId.value = '${studentList[loop1.index].studentId}';
                                            document.mark_form.dateId.value = '${dateList[loop2.index].dateId}';">
                                    <div class="mark_div">${mark}</div>
                                </a>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                <tr class="spacer"></tr>
                <tr>
                    <c:forEach var="homework" items="${homeworkList}" varStatus="loop">
                        <td class="homework">
                            <a onclick="document.getElementById('add_homework_modal').style.display='block';
                                        document.homework_form.dateId.value='${dateList[loop.index].dateId}';
                                        document.getElementById('text_for_work').value='${homework}';">
                                <div class="homework_div">${homework}</div>
                            </a>
                        </td>
                    </c:forEach>
                </tr>
                <tr class="spacer"></tr>
            </table>
        </div>
    </div>
    <div id="modal">
        <div id="modal-content">
            <p id="message-text"></p>
        </div>
    </div>
    <c:if test="${sessionScope.message != null}">
        <c:choose>
            <c:when test="${sessionScope.message=='has_no_active_subject'}">
                <script>
                    document.getElementById('message-text').innerText = '<fmt:message key="${sessionScope.message}" bundle="${i18n}"/>';
                    document.getElementById('modal').style.display = 'block';
                </script>
            </c:when>
            <c:when test="${sessionScope.message=='show_add_grade_block'}">
                <script>document.getElementById('add_grade_block').style.display = 'block';</script>
            </c:when>
            <c:when test="${sessionScope.message=='show_journal_block'}">
                <script>
                    document.getElementById('journal_block').style.display = 'block';
                    scroll();
                </script>
            </c:when>
            <c:when test="${sessionScope.message=='find_journal'}">
                <script>document.getElementById('find_journal${gradeId}${subjectId}').submit();</script>
            </c:when>
            <c:when test="${sessionScope.message=='incorrect_add_date' || sessionScope.message=='incorrect_delete_date'}">
                <script>
                    document.getElementById('message-text').innerText = '<fmt:message key="${sessionScope.message}" bundle="${i18n}"/>';
                    document.getElementById('modal').style.display = 'block';
                    setTimeout(function(){
                        document.getElementById('find_journal${gradeId}${subjectId}').submit();
                        document.getElementById('modal').style.display = 'none';
                    }, 2000);
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
</div>
<doskort:footer/>
</body>
</html>
