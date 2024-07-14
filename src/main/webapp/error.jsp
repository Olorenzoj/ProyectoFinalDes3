<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error en la aplicación</title>
</head>
<body>
    <h1>Error en la aplicación</h1>
    <p>Ocurrió un error: <%= exception.getMessage() %></p>

    <%-- Obtener el parámetro 'menu' para redirigir al menú correcto --%>
    <% String menu = request.getParameter("menu"); %>

    <%-- Redirigir de vuelta al menú correspondiente usando el parámetro --%>
    <% if (menu != null && !menu.isEmpty()) { %>
        <p><a href="<%= menu %>.jsp">Volver al Menú</a></p>
    <% } else { %>
        <p><a href="index.jsp">Volver al Menú Principal</a></p>
    <% } %>
</body>
</html>
