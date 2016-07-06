<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ScrumBoard - Projetos</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
        <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
        <link href="<c:url value="/resources/css/normalize.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="row title">
                <div class="col-md-2"><h1>Projetos</h1></div>
                <div class="col-md-8">&nbsp;</div>
                <c:if test="${not empty projectList}">
                    <form action="create">
                        <div class="col-md-2">
                            <input type="submit" value="+ Novo Projeto" class="btn btn-lg bt-bkg">
                        </div>
                    </form>
                </c:if>
            </div>
            <c:if test="${empty projectList}">
                <div class="row">
                    <div class="col-md-12">
                            <p>Nenhum projeto foi encontrado, deseja cadastrar um novo?</p>
                            <br>
                            <form action="create">
                                <input value="Criar novo projeto" type="submit" class="btn btn-success btn-lg">
                            </form>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty projectList}">  
                <div class="row">
                    <form action="searchUser">
                    <div class="col-md-10"><input type='text' placeholder='Buscar projeto...' name='searchName' id='searchName'/></div>
                    <div class="col-md-2"><button type='submit' class="btn btn-warning btn-lg"><span class="glyphicon glyphicon-search"></span></button></div>
                    </form>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <c:forEach items="${projectList}" var="p">
                            <div class="card">
                                <h3 class="card-header"><c:out value="${p.name}"/></h3>
                                <div class="card-block">
                                    <div class="card-text"><c:out value="${p.description}"/></div>
                                    <div class="card-text"><b>Start Date : </b><c:out value="${p.startDate}"/></div>
                                </div>
                                <div class="card-block">
                                    <button class="btn btn-primary"><span class="glyphicon glyphicon-eye-open"></span></button>
                                    <a href="edit?id=<c:out value='${p.id}'/>"><button class="btn btn-success"><span class="glyphicon glyphicon-pencil"></span></button></a>
                                    <a href="delete?id=<c:out value='${p.id}'/>"><button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button></a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>    
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </body>
</html>