<%-- 
    Document   : userList
    Created on : 27/06/2016, 21:38:30
    Author     : Vinicius
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Usuários</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    </head>
    <body class=".container-fluid">
        <div class="container myrow-container">
            <div class="panel panel-success">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <div align="left"><b>Lista de Usuários</b> </div>
                        <div align="right"><a href="create">Adicionar usuário</a></div>
                    </h3>
                </div>
                <div class="panel-body">
                    <c:if test="${empty userList}">
                        Não exitem usuários cadastrados!
                    </c:if>
                    <c:if test="${not empty userList}">   

                        <form action="searchUser">
                            <div class="row">
                                <div class="col-md-4">Busca por nome: <input type='text' name='searchName' id='searchName'/> </div>
                                <div class="col-md-4"><input class="btn btn-success" type='submit' value='Search'/></div>
                            </div>
                        </form>         	

                        <table class="table table-striped table-bordered">
                            <thead>
                                <tr>
                                    <th>Email</th>
                                    <th>Name</th>
                                    <th>Password</th>
                                    <th>Edit</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${userList}" var="us">
                                    <tr>
                                        <th><c:out value="${us.email}"/></th>
                                        <th><c:out value="${us.name}"/></th>
                                        <th><c:out value="${us.password}"/></th> 
                                        <th><a href="edit?email=<c:out value='${us.email}'/>">Edit</a></th>
                                        <th><a href="deleteUser?email=<c:out value='${us.email}'/>">Delete</a></th>                         	
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:if>
                </div>
            </div>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>    
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </body>
</html>