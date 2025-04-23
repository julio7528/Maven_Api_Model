<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Cadastro de Carros</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #f5f5f5;
        }
        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }
        .form-signin .form-floating:focus-within {
            z-index: 2;
        }
        .form-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
</head>
<body>
    <main class="form-signin w-100 m-auto">
        <form method="post" action="<c:url value='/login'/>">
            <h1 class="h3 mb-3 fw-normal">Faça o login</h1>

            <c:if test="${param.error != null}">
                <div class="alert alert-danger" role="alert">
                    Usuário ou senha inválidos.
                </div>
            </c:if>
            <c:if test="${param.logout != null}">
                <div class="alert alert-success" role="alert">
                    Logout realizado com sucesso.
                </div>
            </c:if>

            <div class="form-floating">
                <input type="text" class="form-control" id="username" name="username" placeholder="Usuário" required autofocus>
                <label for="username">Usuário</label>
            </div>
            <div class="form-floating">
                <input type="password" class="form-control" id="password" name="password" placeholder="Senha" required>
                <label for="password">Senha</label>
            </div>

            <!-- Campo oculto para o token CSRF -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="w-100 btn btn-lg btn-primary" type="submit">Entrar</button>
            <p class="mt-5 mb-3 text-muted">&copy; 2024</p>
        </form>
    </main>
</body>
</html>