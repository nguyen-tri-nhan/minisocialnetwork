<%-- 
    Document   : default
    Created on : Sep 18, 2020, 12:25:35 AM
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trang chủ</title>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <c:if test="${empty requestScope.LIST_ARTICLE}">
            <c:redirect url="ListArticleController"></c:redirect>
        </c:if>
        <link rel="stylesheet" type="text/css" href="css/bs/bootstrap-grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/bs/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/defaultlayout.css"/>
    </head>
    <body>

        <header class="navbar-expand-lg navbar-light bg-light fixed-top">
            <nav>
                <ul style="list-style-type:none;">
                    <li style="float: left"><a href="default.jsp">Home</a></li>
                    <li style="float: right"><a href="MainController?btnAction=Logout">Logout</a></li>
                    <li style="float: right">Welcome, <a href="#"><b>${sessionScope.USER.lastname}</b></a></li>
                </ul>
            </nav>
            <form action="MainController">
                <input type="text"
                       placeholder="🔍"
                       name="txtSearchArticle"
                       />
                <input type="submit"
                       name="btnAction"
                       value="Search"
                       />
            </form>
            <p>

            </p>
        </header>
        <div>
            <b>
                space
            </b>
            <p>
                another space
            </p>

        </div>
        <div class="row">
            <div class="col-md-3">
                Advertisement

            </div>
            <div class="col-md-6">
                <div>
                    <form method="post" action="MainController" enctype="multipart/form-data">
                        <input type="text" name="txtPostTitle" placeholder="Title" required="" /></br>
                        <textarea name="txtPostDescription"
                                  maxlength="1000"
                                  placeholder="What do you think?"
                                  cols="100"
                                  rows="3"></textarea></br>
                        <input type="file" name="fileIMG" accept="image/*" required=""></br>
                        <button type="submit" name="btnAction" value="NewArticle">Post</button>
                        <button type="reset">Cancel</button>
                    </form>
                </div>
                <h2>News feed</h2>
                <c:forEach var="articles" items="${requestScope.LIST_ARTICLE}">
                    <c:url var="upvote" value="MainController">
                        <c:param name="btnAction" value="Vote"/>
                        <c:param name="txtArticleID" value="${articles.articleID}"/>
                        <c:param name="location" value="outside"/>
                        <c:param name="vote" value="up"/>
                    </c:url>
                    <c:url var="downvote" value="MainController">
                        <c:param name="btnAction" value="Vote"/>
                        <c:param name="txtArticleID" value="${articles.articleID}"/>
                        <c:param name="location" value="outside"/>
                        <c:param name="vote" value="down"/>
                    </c:url>
                    <section>
                        <form action="MainController" method="POST">
                            <c:url var="details" value="MainController">
                                <c:param name="btnAction" value="ArticleDetail"></c:param>
                                <c:param name="txtArticleID" value="${articles.articleID}"></c:param>
                            </c:url>
                            <h2><a href="${details}">${articles.title}</a></h2>
                            <a href="${details}"><img src="${articles.imgAddress}" alt="${articles.title}" class="img-thumbnail" width="100%" height="100%"/></a>
                            <p>${articles.description}</p>
                            <p><a href="${upvote}">👍 </a>${articles.upvote} <a href="${downvote}">👎</a> ${articles.downvote}</p> 
                            <p>Posted by <a href="#">${articles.fullName}</a> in ${articles.createDate}</p>
                        </form>
                    </section>
                </c:forEach>
            </div>
            <div class="col-md-3 noti" id="sticky1">
                <h2 style="text-align: center;">Notification</h2>
                <c:forEach var="notifies" items="${sessionScope.NOTI}">
                    <ul>
                        <c:url var="details" value="MainController">
                            <c:param name="btnAction" value="ArticleDetail"></c:param>
                            <c:param name="txtArticleID" value="${notifies.articleID}"></c:param>
                        </c:url>
                        <c:if test="${notifies.type == false}">
                            <li>
                                <a href="${details}"><strong>${notifies.fullName}</strong> has <font color="red">voted</font> on your Article</a>
                            </li>
                        </c:if>

                        <c:if test="${notifies.type == true}">
                            <li>
                                <a href="${details}"><strong>${notifies.fullName}</strong> has <font color="red">commented</font> on your Article</a>
                            </li>
                        </c:if>
                    </ul>
                </c:forEach>
            </div>
        </div>
        <nav>
            <ul class="pagination justify-content-center">

                <c:forEach var="count" begin="1" end="${requestScope.PAGE}">
                    <c:url var="page" value="MainController">
                        <c:param name="btnAction" value="Paging"></c:param>
                        <c:param name="pagenum" value="${count}"></c:param>
                    </c:url>
                    <li class="page-item">
                        <a class="page-link" href="${page}">${count}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </body>
</html>
