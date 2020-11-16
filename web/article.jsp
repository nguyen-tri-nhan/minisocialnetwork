<%-- 
    Document   : article
    Created on : Sep 24, 2020, 11:10:40 PM
    Author     : Lenovo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:set var="articledetail" value="${requestScope.ARTICLE}">
        </c:set>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${articledetail.userMail}</title>
        <c:if test="${sessionScope.USER == null}">
            <c:redirect url="login.jsp"/>
        </c:if>
        <link rel="stylesheet" type="text/css" href="css/bs/bootstrap-grid.css"/>
        <link rel="stylesheet" type="text/css" href="css/bs/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="css/artilcelayout.css"/>
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

        <form action="MainController" method="POST">
            <c:url var="upvote" value="MainController">
                <c:param name="btnAction" value="Vote"/>
                <c:param name="txtArticleID" value="${articledetail.articleID}"/>
                <c:param name="location" value="inside"/>
                <c:param name="vote" value="up"/>
            </c:url>
            <c:url var="downvote" value="MainController">
                <c:param name="btnAction" value="Vote"/>
                <c:param name="txtArticleID" value="${articledetail.articleID}"/>
                <c:param name="location" value="inside"/>
                <c:param name="vote" value="down"/>
            </c:url>
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6">
                    <div class="showbutton">
                        <h1><a href="ListArticleController">🔙</a>${articledetail.title}</h1>
                        <h5><a href="${upvote}">👍</a> ${articledetail.upvote} <a href="${downvote}">👎</a> ${articledetail.downvote}</h5>
                        <img src="${articledetail.imgAddress}" alt="${articledetail.title}" class="img-thumbnail" width="100%" height="100%"/>
                        <p>${articledetail.description}</p>
                        <p>
                            Posted by <a href="#">${articledetail.fullName}</a> in ${articledetail.createDate}
                            <c:if test="${sessionScope.USER.email == articledetail.userMail or sessionScope.USER.roleid == -1}">
                                <input type="hidden" name="txtArticleID" value="${articledetail.articleID}"/>
                                <input type="submit" class="button" name="btnAction" value="Delete Post" onclick="return confirm('Are you sure you want to delete this item?');"/>
                            </c:if>
                        </p>
                    </div>
                    <div>
                        <textarea
                            placeholder="Comment here"
                            name="txtComment"
                            maxlength="1000"
                            cols="100,05"
                            rows="3"></textarea>
                        <input type="hidden" name="txtArticleID" value="${articledetail.articleID}"/>
                        <input type="submit" name="btnAction" value="Comment"/>
                    </div>
                    <div>
                        <h3>Comment</h3>
                        <c:forEach var="comment" items="${requestScope.COMMENT}">
                            <div class="showbutton">
                                <h4>
                                    ${comment.fullname}
                                    <c:if test="${comment.userMail == sessionScope.USER.email}">
                                        <input type="hidden" name="txtCommentID" value="${comment.id}"/>
                                        <input type="submit" class="button" name="btnAction" value="Delete Comment" onclick="return confirm('Are you sure you want to delete this item?');"/>
                                    </c:if>
                                </h4>
                                <p>${comment.description}</p>
                            </div>

                        </c:forEach>
                    </div>
                </div>
                <div class="col-md-3">

                </div>
            </div>
            <div>

            </div>
        </form>

    </body>
</html>
