<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="doskort" uri="/WEB-INF/footer.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=e    dge">
    <title>Doskort error</title>
    <link href="${pageContext.request.contextPath}/image/logo.png" rel="shortcut icon" type="image/png">
    <link href="${pageContext.request.contextPath}/css/doskort.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="page__cell">
        <div id="error_block">
            <div class="content">
                <a href="/jsp/start.jsp">
                <input class="backbtn" type="button" value="BACK">
                </a>
                <c:choose>
                    <c:when test="${error != null}">
                        <table>
                            <tr valign = "top">
                                <td width = "40%"><b>Error:</b></td>
                                <td>${error}</td>
                            </tr>

                            <tr valign = "top">
                                <td><b>Stack trace:</b></td>
                                <td>
                                    <c:forEach var = "trace"
                                               items = "${error.stackTrace}">
                                        <p>${trace}</p>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                        <c:remove var="error" scope="session"/>
                    </c:when>
                    <c:otherwise>
                        <table>
                            <tr valign = "top">
                                <td width = "40%"><b>Error:</b></td>
                                <td>${pageContext.exception}</td>
                            </tr>
                            <tr valign = "top">
                                <td><b>URI:</b></td>
                                <td>${pageContext.errorData.requestURI}</td>
                            </tr>
                            <tr valign = "top">
                                <td><b>Status code:</b></td>
                                <td>${pageContext.errorData.statusCode}</td>
                            </tr>
                            <tr valign = "top">
                                <td><b>Stack trace:</b></td>
                                <td>
                                    <c:forEach var = "trace"
                                               items = "${pageContext.exception.stackTrace}">
                                        <p>${trace}</p>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <doskort:footer/>
</body>
</html>
