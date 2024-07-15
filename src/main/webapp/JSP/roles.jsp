<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelos.Role" %>
<%@ page import="Modelos.Usuario" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Roles</title>
    <link rel="stylesheet" type="text/css" href="../CSS/roles.css">
</head>
<body>
    <h1>Gestión de Roles</h1>

    <%-- Imprimir mensaje de confirmación si existe --%>
    <% String message = (String) request.getAttribute("message"); %>
    <% Boolean success = (Boolean) request.getAttribute("success"); %>
    <% if (message != null && success != null) { %>
        <div class="message <%= success ? "success" : "error" %>">
            <%= message %>
        </div>
    <% } %>

    <h2>Roles</h2>

    <table class="role-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre del Rol</th>
            </tr>
        </thead>
        <tbody>
            <% List<Role> roles = (List<Role>) request.getAttribute("roles"); %>
            <% if (roles != null && !roles.isEmpty()) { %>
                <% for (Role role : roles) { %>
                    <tr>
                        <td><%= role.getRoleId() %></td>
                        <td><%= role.getRoleName() %></td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="2">No hay roles disponibles.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <hr>

    <h2>Reasignar Rol de Usuario</h2>
    <form action="roles" method="post">
        <fieldset>
            <legend>Datos de Reasignación</legend>
            <label for="userId">ID de Usuario:</label>
            <input type="text" id="userId" name="userId" required><br><br>
            <label for="newRoleId">Nuevo ID de Rol:</label>
            <input type="text" id="newRoleId" name="newRoleId" required><br><br>
            <button type="submit">Reasignar Rol</button>
        </fieldset>
        <input type="hidden" name="action" value="reassignUserRole">
    </form>

    <%-- Mostrar usuarios con roles si están disponibles --%>
    <h2>Usuarios con Roles</h2>
    <table class="user-role-table">
        <thead>
            <tr>
                <th>ID Usuario</th>
                <th>Nombre de Usuario</th>
                <th>Rol</th>
                <th>Nombre del Rol</th>
            </tr>
        </thead>
        <tbody>
            <% List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuariosConRoles"); %>
            <% if (usuarios != null && !usuarios.isEmpty()) { %>
                <% for (Usuario usuario : usuarios) { %>
                    <tr>
                        <td><%= usuario.getUserId() %></td>
                        <td><%= usuario.getUsername() %></td>
                        <td><%= usuario.getRoleId() %></td>
                        <td><%= usuario.getRoleName() %></td>
                    </tr>
                <% } %>
            <% } else { %>
                <tr>
                    <td colspan="4">No hay usuarios con roles disponibles.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
