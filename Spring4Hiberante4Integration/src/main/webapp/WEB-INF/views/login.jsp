<%-- 
    Document   : login
    Created on : 28/06/2016, 20:10:03
    Author     : Vinicius
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Scrumboard - Login</title>  
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
        <link href="<c:url value="resources/css/normalize.css" />" rel="stylesheet">
        <link href="<c:url value="resources/css/style.css" />" rel="stylesheet">
    </head>
    <body>
        <div class="form">
            <ul class="tab-group">
                <li class="tab active"><a href="#login">Login</a></li>
                <li class="tab"><a href="#signup">Cadastre-se</a></li>
            </ul>
            <div class="tab-content">
                <div id="login">   
                    <h1>SCRUMBOARD</h1>
                    <c:if test="${invalid}"><p style="color: red">Usuário inválido! Tente novamente!</p></c:if>
                    <form:form id="loginForm" modelAttribute="user" method="post" action="login">
                        <div class="field-wrap">
                            <label>Email</label>
                            <form:input path="email" value="${userObject.email}"/>
                        </div>
                        <div class="field-wrap">
                            <label>Senha</label>
                            <form:password path="password" value="${userObject.password}"/>
                        </div>
                        <button type="submit" id="btnLogin" class="button button-block"/>Entrar</button>
                    </form:form>
                </div>
                <div id="signup">
                    <form:form id="userRegisterForm" modelAttribute="user" method="post" action="save">
                        <h1>Cadastro</h1>
                        <div class="field-wrap">
                            <label>Nome</label>
                            <form:input path="name" value="${userObject.name}"/>
                        </div>
                        <div class="field-wrap">
                            <label>Email</label>
                            <form:input path="email" value="${userObject.email}"/>
                        </div>
                        <div class="field-wrap">
                            <label>Senha</label>
                            <form:password path="password" value="${userObject.password}"/>
                        </div>
                        <button type="submit" id="save" class="button button-block">Cadastrar</button>
                    </form:form>
                </div>
            </div>
        </div>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src="<c:url value="/resources/js/login.js"/>"></script>
    </body>
</html>