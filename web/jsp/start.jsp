<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="doskort" uri="/WEB-INF/footer.tld" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Doskort</title>
  <link href="../image/logo.png" rel="shortcut icon" type="image/png">
  <link href="../css/doskort.css" rel="stylesheet" type="text/css">
  <link href="../css/start.css" rel="stylesheet" type="text/css">
  <link href="../css/owl.carousel.min.css" rel="stylesheet" type="text/css">
</head>
<body>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="i18n.messages" var="i18n"/>
  <div class="page__cell">
    <div class="Slider">
      <div class="owl-carousel">
        <div class="image"><img src="../image/1.jpg"></div>
        <div class="image"><img src="../image/2.jpg"></div>
        <div class="image"><img src="../image/3.jpg"></div>
        <div class="image"><img src="../image/4.jpg"></div>
        <div class="image"><img src="../image/5.jpg"></div>
        <div class="image"><img src="../image/6.jpg"></div>
      </div>
    </div>
    <header class="header">
      <div class="header__logo">
        <img src="../image/logo.png">
      </div>
      <a href="" class="name">Doskort</a>
      <button onclick="document.getElementById('id01').style.display='block'" class="signup"><fmt:message key="sign_up_button" bundle="${i18n}"/></button>
    </header>
    <div id="language">
      <p><fmt:message key="language" bundle="${i18n}"/></p>
      <form class="lan_form" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="language_switch">
        <input type="hidden" name="language" value="ru">
        <input  class="lan" type="image" src="../image/ru.png" alt="submit">
      </form>
      <form class="lan_form" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="language_switch">
        <input type="hidden" name="language" value="en">
        <input  class="lan" type="image" src="../image/en.png" alt="submit">
      </form>
    </div>
    <div id="id01" class="modal">
    <span onclick="document.getElementById('id01').style.display='none'"
          class="close" title="Close Modal">&times;</span>
      <form class="modal-content animate" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="sign_up">
        <div class="imgcontainer">
          <img src="../image/img_avatar.jpg" alt="Avatar" class="avatar">
        </div>
        <div class="container">
          <c:if test="${sessionScope.message != null}">
            <script>document.getElementById('id01').style.display='block';</script>
            <span class="message"><fmt:message key="${sessionScope.message}" bundle="${i18n}"/></span>
            <br>
            <c:remove var="message" scope="session"/>
          </c:if>
          <label><b>Email</b></label>
          <input type="text" name="email" class="email" pattern="[a-z0-9._]{2,20}@[a-z0-9.-]{1,10}\.[a-z]{2,5}"
                 title="Данные в формате email" required >
          <label><b><fmt:message key="password" bundle="${i18n}"/></b></label>
          <input type="password" name="password" pattern="[\w]{8,19}"
                 title="Пароль может содержать символы латинского алфавита и цифры. Длина пароля минимум 8 символов"
                 required>
          <button type="submit" class="signupbtn"><fmt:message key="sign_up_button" bundle="${i18n}"/></button>
        </div>
        <div class="container">
          <button type="button" onclick="document.getElementById('id01').style.display='none'"
                  class="cancelbtn"><fmt:message key="cancel_button" bundle="${i18n}"/></button>
        </div>
      </form>
    </div>
    <div class="aboutus">
      <div class="first-block"><h1><fmt:message key="about_doskort_1" bundle="${i18n}"/></h1></div>
      <div class="about-doskort">
        <div class="second-block"><p><fmt:message key="about_doskort_2" bundle="${i18n}"/></p></div>
        <div class="first-block"><p><fmt:message key="about_doskort_3" bundle="${i18n}"/></p></div>
        <div  class="second-block"><p><fmt:message key="about_doskort_4" bundle="${i18n}"/></p></div>
      </div>
    </div>
  </div>
  <doskort:footer/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="../script/owl.carousel.min.js"></script>
  <script src="../script/start.js"></script>
</body>
</html>
