<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Usuario</title>
</head>
<body>
<h2>Registro de Usuario</h2>
<form action="registro" method="post">
    <label for="username">Nombre de usuario:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Contraseña:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="email">Correo electrónico:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="role">Rol:</label>
    <select id="role" name="role">
        <option value="1">Administrador</option>
        <option value="2">Operador</option>
        <option value="3">Cliente</option>
    </select><br><br>

    <input type="hidden" name="createdAt" value=<%= new java.util.Date() %> />
    <input type="hidden" name="updatedAt" value=<%= new java.util.Date() %> />

    <input type="submit" value="Registrar">
</form>
</body>
</html>