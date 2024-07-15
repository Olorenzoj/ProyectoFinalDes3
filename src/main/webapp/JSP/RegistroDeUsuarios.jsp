<%@ page import="Modelos.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Usuarios</title>
    <style>
        /* Estilos para mensajes */
        .message { padding: 10px; margin-bottom: 15px; border-radius: 5px; }
        .message.success { background-color: lightgreen; color: green; }
        .message.error { background-color: lightcoral; color: red; }
    </style>
</head>
<body>

<h2>Lista de Usuarios</h2>

<form action="users" method="get">
    <label for="ticket">Obtener los usuarios:</label>
    <button type="submit" id="ticket">Buscar</button>
</form>

<%-- Verificación y visualización de la lista de usuarios --%>
<% List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    if (usuarios != null && !usuarios.isEmpty()) { %>
<table>
    <tr>
        <th>ID</th>
        <th>Nombre de Usuario</th>
        <th>Email</th>
        <th>Rol</th>
    </tr>
    <% for (Usuario usuario : usuarios) { %>
    <tr>
        <td><%= usuario.getUserId() %></td>
        <td><%= usuario.getUsername() %></td>
        <td><%= usuario.getEmail() %></td>
        <td><%= usuario.getRoleId() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No hay usuarios registrados.</p>
<% } %>

<a href="JSP/RegistroDeUsuarios.jsp">Registrar Usuario</a>
<a href="JSP/logIn.jsp">Log in</a>
</body>
</html>
