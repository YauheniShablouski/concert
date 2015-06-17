<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>" type="text/css">
        <link rel="stylesheet" href="<c:url value='/resources/css/my.css'/>" type="text/css">
        <title>Concert Information</title>
    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                   <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                       <span class="sr-only">Toggle navigation</span>
                       <span class="icon-bar"></span>
                       <c:choose>
                           <c:when test="${pageContext.request.userPrincipal.name != null}">
                               <span class="icon-bar"></span>
                               <span class="icon-bar"></span>
                               <span class="icon-bar"></span>
                               <span class="icon-bar"></span>
                           </c:when>
                           <c:otherwise>
                               <span class="icon-bar"></span>
                           </c:otherwise>
                       </c:choose>
                       <span class="icon-bar"></span>
                       <span class="icon-bar"></span>
                   </button>
                   <a class="navbar-brand" href='<spring:url value="/index" ></spring:url>'>Home</a>
               </div>
            
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                       
                       <c:choose>
                           <c:when test="${pageContext.request.userPrincipal.name != null}">
                               <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <ul class="nav navbar-nav">
                                        <li><a href='<spring:url value="/addConcert" ></spring:url>'>Add Concert</a></li>
                                    </ul>
                               </sec:authorize>
                   
                               <ul class="nav navbar-nav navbar-right">
                                   <li><a href= "" >Hi, <c:out value="${pageContext.request.userPrincipal.name}"/></a></li>
                                   <li><a href='<spring:url value="/logout" ></spring:url>'>Logout</a></li>
                               </ul>
                           </c:when>
                           <c:otherwise>
                               <ul class="nav navbar-nav navbar-right">
                                   <li><a href='<spring:url value="/signIn" ></spring:url>'>Sign In</a></li>
                                   <li><a href='<spring:url value="/registration" ></spring:url>' >Sign Up</a></li>
                               </ul>
                           </c:otherwise>
                       </c:choose>
                    </ul>   
               </div>
            
        </div>        
    </nav>
                                   <br><br><br>
        
                
        <div class="container">    
            <form name='signIn' class="form-sign" role="form" action="<c:url value='/j_spring_security_check' />" method='POST'>
                        <input type = "text" class="form-control" name = "username" placeholder="Login"></input>

                        <input type = "password" class="form-control" name = "password" placeholder="Password"></input>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            </form>
                    <c:if test="${not empty error}">
                            <div class="error">${error}</div>
                    </c:if>
                    <c:if test="${not empty msg}">
                            <div class="msg">${msg}</div>
                    </c:if>
        
        </div>
                        
            <tr/>  
             
            <div class="footer">
                <div class="container">
                    <small><p class="text-muted">Created by Yauheni Shablouski</p></small>
                </div>
            </div> 
          
                        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    </body>
</html>
