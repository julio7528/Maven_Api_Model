<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Importa a biblioteca JSTL Core --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Carros Cadastrados</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<h1>Lista de Carros</h1>

<c:choose>
    <c:when test="${not empty carros}"> <%-- Verifica se a lista 'carros' (enviada pelo controller) não está vazia --%>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Ano</th>
                    <%-- Adicione mais colunas conforme os atributos da sua classe Carro --%>
                </tr>
            </thead>
            <tbody>
                <%-- Itera sobre cada 'carro' na lista 'carros' --%>
                <c:forEach items="${carros}" var="carro">
                    <tr>
                        <td>${carro.id}</td> <%-- Assume que Carro tem um getId() --%>
                        <td>${carro.marca}</td> <%-- Assume que Carro tem um getMarca() --%>
                        <td>${carro.modelo}</td> <%-- Assume que Carro tem um getModelo() --%>
                        <td>${carro.ano}</td> <%-- Assume que Carro tem um getAno() --%>
                        <%-- Adicione mais células conforme os atributos --%>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Nenhum carro cadastrado ainda.</p> <%-- Mensagem se a lista estiver vazia --%>
    </c:otherwise>
</c:choose>

<br/>
<a href="/carros/novo">Cadastrar Novo Carro</a> <%-- Link para o formulário de cadastro (se existir) --%>
<br/>
<a href="/">Voltar para Home</a> <%-- Link para a página inicial --%>

</body>
</html>