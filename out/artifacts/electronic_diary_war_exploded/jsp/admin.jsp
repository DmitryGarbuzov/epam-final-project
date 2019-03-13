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
    <link href="../css/owl.carousel.min.css" rel="stylesheet" type="text/css">
    <link href="../css/doskort.css" rel="stylesheet" type="text/css">
    <link href="../css/admin.css" rel="stylesheet" type="text/css">
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
              <a href="#"><fmt:message key="user_registration_button" bundle="${i18n}"/></a>
              <ul class="submenu">
                  <li>
                      <a href="#" id="add_parent"
                         onclick="document.getElementById('find_all_student').submit(); return false"><fmt:message key="parent_button" bundle="${i18n}"/></a>
                      <form id="find_all_student" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_student">
                          <input type="hidden" name="target" value="for_adding">
                      </form>
                  </li>
                  <li>
                      <a href="#" id="add_teacher"
                         onclick="document.getElementById('find_all_subject').submit(); return false"><fmt:message key="teacher_button" bundle="${i18n}"/></a>
                      <form id="find_all_subject" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_subject">
                          <input type="hidden" name="target" value="for_adding">
                      </form>
                  </li>
              </ul>
          </li>
          <li>
              <a href="#"><fmt:message key="user_deletion_button" bundle="${i18n}"/></a>
              <ul class="submenu">
                  <li><a href="#" id="delete_parent"
                         onclick="document.getElementById('find_all_parent').submit(); return false"><fmt:message key="parent_button" bundle="${i18n}"/></a>
                      <form id="find_all_parent" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_parent">
                      </form>
                  </li>
                  <li><a href="#" id="delete_teacher"
                         onclick="document.getElementById('find_all_teacher').submit(); return false"><fmt:message key="teacher_button" bundle="${i18n}"/></a>
                      <form id="find_all_teacher" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_teacher">
                      </form>
                  </li>
              </ul>
          </li>
          <li>
              <a href="#"><fmt:message key="adding_button" bundle="${i18n}"/></a>
              <ul class="submenu">
                  <li>
                      <a href="#" id="add_student"
                         onclick="document.getElementById('find_all_grade').submit(); return false"><fmt:message key="student_button" bundle="${i18n}"/></a>
                      <form id="find_all_grade" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_grade">
                          <input type="hidden" name="target" value="for_adding">
                      </form>
                  </li>
                  <li><a href="#" id="add_grade"><fmt:message key="grade_button" bundle="${i18n}"/></a></li>
                  <li><a href="#" id="add_subject"><fmt:message key="subject_button" bundle="${i18n}"/></a></li>
              </ul>
          </li>
          <li>
              <a href="#"><fmt:message key="deletion_button" bundle="${i18n}"/></a>
              <ul class="submenu">
                  <li>
                      <a href="#"
                         onclick="document.getElementById('all_student').submit(); return false"><fmt:message key="student_button" bundle="${i18n}"/></a>
                      <form id="all_student" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_student">
                          <input type="hidden" name="target" value="for_removing">
                      </form>
                  </li>
                  <li>
                      <a href="#"
                         onclick="document.getElementById('all_grade').submit(); return false"><fmt:message key="grade_button" bundle="${i18n}"/></a>
                      <form id="all_grade" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_grade">
                          <input type="hidden" name="target" value="for_removing">
                      </form>
                  </li>
                  <li>
                      <a href="#"
                         onclick="document.getElementById('all_subject').submit(); return false"><fmt:message key="subject_button" bundle="${i18n}"/></a>
                      <form id="all_subject" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_subject">
                          <input type="hidden" name="target" value="for_removing">
                      </form>
                  </li>
              </ul>
          </li>
          <li>
              <a href="#"><fmt:message key="recovery_button" bundle="${i18n}"/></a>
              <ul class="submenu">
                  <li>
                      <a href="#"
                         onclick="document.getElementById('all_inactive_student').submit(); return false"><fmt:message key="student_button" bundle="${i18n}"/></a>
                      <form id="all_inactive_student" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_student">
                          <input type="hidden" name="target" value="for_recovery">
                      </form>
                  </li>
                  <li>
                      <a href="#"
                         onclick="document.getElementById('all_inactive_grade').submit(); return false"><fmt:message key="grade_button" bundle="${i18n}"/></a>
                      <form id="all_inactive_grade" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_grade">
                          <input type="hidden" name="target" value="for_recovery">
                      </form>
                  </li>
                  <li>
                      <a href="#"
                         onclick="document.getElementById('all_inactive_subject').submit(); return false"><fmt:message key="subject_button" bundle="${i18n}"/></a>
                      <form id="all_inactive_subject" action="${pageContext.request.contextPath}/controller" method="post">
                          <input type="hidden" name="command" value="find_all_subject">
                          <input type="hidden" name="target" value="for_recovery">
                      </form>
                  </li>
              </ul>
          </li>
      </ul>
      <div id="add_parent_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="parent_registration">
              <div class="container">
                  <label>Email</label>
                  <input type="text" name="email" class="email" pattern="[a-z0-9._]{2,20}@[a-z0-9.-]{1,10}\.[a-z]{2,5}"
                         title="Данные в формате email" required>
                  <label><fmt:message key="name" bundle="${i18n}"/></label>
                  <input type="text" name="first_name" pattern="[a-zA-za-яА-яЁё]{1,20}"
                         title="Имя может содержать только буквы. Длина имени максимум 20 символов" required>
                  <label><fmt:message key="surname" bundle="${i18n}"/></label>
                  <input type="text" name="last_name" pattern="[a-zA-za-яА-яЁё]{1,20}"
                         title="Фамилия может содержать только буквы. Длина фамилии максимум 20 символов" required>
                  <label><fmt:message key="child" bundle="${i18n}"/></label>
                  <select class="select2" name="students[]" multiple="multiple" required>
                      <c:forEach var="student" items="${sessionScope.studentList}">
                          <option value="${student.studentId}">${student}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="register_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="add_teacher_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="teacher_registration">
              <div class="container">
                  <label>Email</label>
                  <input id="23" type="text" name="email" class="email" pattern="[a-z0-9._]{2,20}@[a-z0-9.-]{1,10}\.[a-z]{2,5}"
                         title="Данные в формате email" required>
                  <label><fmt:message key="name" bundle="${i18n}"/></label>
                  <input type="text" name="first_name" pattern="[a-zA-za-яА-яЁё]{1,20}"
                         title="Имя может содержать только буквы. Длина имени максимум 20 символов" required>
                  <label><fmt:message key="surname" bundle="${i18n}"/></label>
                  <input type="text" name="last_name" pattern="[a-zA-za-яА-яЁё]{1,20}"
                         title="Фамилия может содержать только буквы. Длина фамилии максимум 20 символов" required>
                  <label><fmt:message key="subject" bundle="${i18n}"/></label>
                  <select id="subject_select" class="select2" name="subjects[]" multiple="multiple" required>
                      <c:forEach var="subject" items="${sessionScope.subjectList}">
                          <option value="${subject.subjectId}">${subject}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="register_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="delete_parent_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="parent_deletion">
              <div class="container">
                  <label><fmt:message key="parent_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="parents[]" multiple="multiple" required>
                      <c:forEach var="parent" items="${sessionScope.parentList}">
                          <option value="${parent.id}">${parent}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="delete_teacher_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="teacher_deletion">
              <div class="container">
                  <label><fmt:message key="teacher_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="teachers[]" multiple="multiple" required>
                      <c:forEach var="teacher" items="${sessionScope.teacherList}">
                          <option value="${teacher.id}">${teacher}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="add_subject_block">
          <form id="subject_form" class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="subject_add">
              <div class="container">
                  <label><fmt:message key="subject_name" bundle="${i18n}"/></label>
                  <input type="text" name="name" class="subject" pattern="[A-zА-яЁ][a-zA-za-яА-яЁё ]{1,25}"
                         title="Название предмета должно начинаться с заглавной буквы и содержать только буквы. Длина названия максимум 25 символов"
                         required>
                  <button type="submit" class="register"><fmt:message key="add_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="add_grade_block">
          <form id="grade_form" class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="grade_add">
              <div class="container">
                  <label><fmt:message key="number" bundle="${i18n}"/></label>
                  <select name="number">
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
                      <option value="11">11</option>
                  </select>
                  <label><fmt:message key="letter" bundle="${i18n}"/></label>
                  <select name="letter">
                      <option value="А">А</option>
                      <option value="Б">Б</option>
                      <option value="В">В</option>
                      <option value="Г">Г</option>
                  </select>
                  <button type="submit" class="register"><fmt:message key="add_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="add_student_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="student_add">
              <div class="container">
                  <label><fmt:message key="name" bundle="${i18n}"/></label>
                  <input type="text" name="first_name" pattern="[a-zA-za-яА-яЁё]{1,20}"
                         title="Имя может содержать только буквы. Длина имени максимум 20 символов" required>
                  <label><fmt:message key="surname" bundle="${i18n}"/></label>
                  <input type="text" name="last_name" pattern="[a-zA-za-яА-яЁё]{1,20}"
                         title="Фамилия может содержать только буквы. Длина фамилии максимум 20 символов" required>
                  <label><fmt:message key="grade" bundle="${i18n}"/></label>
                  <select class="select2" name="grade">
                      <c:forEach var="grade" items="${sessionScope.gradeList}">
                          <option value="${grade.gradeId}">${grade}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="add_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="delete_student_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="student_update">
              <input type="hidden" name="target" value="delete">
              <div class="container">
                  <label><fmt:message key="student_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="students[]" multiple="multiple" required>
                      <c:forEach var="student" items="${sessionScope.studentList}">
                          <option value="${student.studentId}">${student}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="delete_grade_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="grade_update">
              <input type="hidden" name="target" value="delete">
              <div class="container">
                  <label><fmt:message key="grade_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="grades[]" multiple="multiple" required>
                      <c:forEach var="grade" items="${sessionScope.gradeList}">
                          <option value="${grade.gradeId}">${grade}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="delete_subject_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="subject_update">
              <input type="hidden" name="target" value="delete">
              <div class="container">
                  <label><fmt:message key="subject_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="subjects[]" multiple="multiple" required>
                      <c:forEach var="subject" items="${sessionScope.subjectList}">
                          <option value="${subject.subjectId}">${subject}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="remove_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="recover_student_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="student_update">
              <input type="hidden" name="target" value="restore">
              <div class="container">
                  <label><fmt:message key="student_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="students[]" multiple="multiple" required>
                      <c:forEach var="student" items="${sessionScope.studentList}">
                          <option value="${student.studentId}">${student}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="restore_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="recover_grade_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="grade_update">
              <input type="hidden" name="target" value="restore">
              <div class="container">
                  <label><fmt:message key="grade_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="grades[]" multiple="multiple" required>
                      <c:forEach var="grade" items="${sessionScope.gradeList}">
                          <option value="${grade.gradeId}">${grade}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="restore_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <div id="recover_subject_block">
          <form class="content" action="${pageContext.request.contextPath}/controller" method="post">
              <input type="hidden" name="command" value="subject_update">
              <input type="hidden" name="target" value="restore">
              <div class="container">
                  <label><fmt:message key="subject_selection" bundle="${i18n}"/></label>
                  <select class="select2" name="subjects[]" multiple="multiple" required>
                      <c:forEach var="subject" items="${sessionScope.subjectList}">
                          <option value="${subject.subjectId}">${subject}</option>
                      </c:forEach>
                  </select>
                  <button type="submit" class="register"><fmt:message key="restore_button" bundle="${i18n}"/></button>
              </div>
          </form>
      </div>
      <c:if test="${sessionScope.message != null}">
          <c:choose>
              <c:when test="${sessionScope.message=='show_add_student_block'}">
                  <script>document.getElementById('add_student_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_add_parent_block'}">
                  <script>document.getElementById('add_parent_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_add_teacher_block'}">
                  <script>document.getElementById('add_teacher_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_delete_teacher_block'}">
                  <script>document.getElementById('delete_teacher_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_delete_parent_block'}">
                  <script>document.getElementById('delete_parent_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_delete_student_block'}">
                  <script>document.getElementById('delete_student_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_delete_grade_block'}">
                  <script>document.getElementById('delete_grade_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_delete_subject_block'}">
                  <script>document.getElementById('delete_subject_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_recover_subject_block'}">
                  <script>document.getElementById('recover_subject_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_recover_grade_block'}">
                  <script>document.getElementById('recover_grade_block').style.display = 'block';</script>
              </c:when>
              <c:when test="${sessionScope.message=='show_recover_student_block'}">
                  <script>document.getElementById('recover_student_block').style.display = 'block';</script>
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
  <script src="../script/admin.js"></script>
</body>
</html>
