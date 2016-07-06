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
        <title>Scrumboard - Projetos</title>  
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
        <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
        <link href="<c:url value="/resources/css/normalize.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="row title">
                <div class="col-md-4">
                    <h1>
                        <c:if test="${projectObject.id == null}">Novo</c:if>
                        <c:if test="${projectObject.id > 0}">Editar</c:if>
                        Projeto
                    </h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                   <form:form id="projectRegisterForm" cssClass="form-horizontal" modelAttribute="project" method="post" action="save">
                        <div class="form-group">
                            <form:label path="name" cssClass="control-label col-xs-3">Nome</form:label>
                                <div class="col-xs-6">
                                <form:hidden path="id" value="${projectObject.id}"></form:hidden>
                                <form:input path="name" value="${projectObject.name}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="description" cssClass="control-label col-xs-3">Description</form:label>
                            <div class="col-xs-6">
                                <form:textarea path="description" value="${projectObject.description}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <form:label path="startDate" cssClass="control-label col-xs-3">Start Date</form:label>
                                <div class="col-xs-6">
                                <form:input path="startDate" value="${projectObject.startDate}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-7">
                                </div>
                                <div class="col-xs-2">
                                    <input type="submit" id="btnSave" class="btn btn-warning" value="Salvar"/>
                                </div>
                            </div>
                        </div>

                    </form:form> 
                </div>
            </div>
        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </body>
</html>
