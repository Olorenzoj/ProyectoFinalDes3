<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Roles</title>
</head>
<body>
    <h1>Gestión de Roles</h1>

    <h2>Obtener Nombre del Rol</h2>
    <form action="roles" method="get">
        <input type="hidden" name="action" value="getRoleName">
        <label for="roleId">ID del Rol:</label>
        <input type="text" id="roleId" name="roleId" required>
        <input type="submit" value="Obtener Nombre del Rol">
    </form>
    <hr>

    <h2>Reasignar Rol de Usuario</h2>
    <form action="roles" method="post">
        <input type="hidden" name="action" value="reassignUserRole">
        <label for="userId">ID del Usuario:</label>
        <input type="text" id="userId" name="userId" required>
        <label for="newRoleId">Nuevo ID del Rol:</label>
        <input type="text" id="newRoleId" name="newRoleId" required>
        <input type="submit" value="Reasignar Rol de Usuario">
    </form>
    <hr>

    <h2>Imprimir Roles de Usuarios</h2>
    <form action="roles" method="get">
        <input type="hidden" name="action" value="printUserRoles">
        <input type="submit" value="Imprimir Roles de Usuarios">
    </form>
</body>
</html>
