<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.SQLException, DAO.AsignacionesDAO, Modelos.Asignaciones, ConexionDB.ConexionDB"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Asignación</title>
    <link rel="stylesheet" type="text/css" href="../CSS/AsigIn.css">
</head>
<body>
    <h1>Ingresa una Asignación</h1>

    <%@ page errorPage="error.jsp" %>

    <%
        try {
            if (request.getMethod().equals("POST")) {
                // Obtener los datos del formulario
                int ticketId = Integer.parseInt(request.getParameter("ticketId"));
                int operatorId = Integer.parseInt(request.getParameter("operatorId"));
                java.sql.Timestamp assignedAtTimestamp = java.sql.Timestamp.valueOf(request.getParameter("assignedAt"));

                // Crear una asignación con los datos proporcionados
                Asignaciones asignacion = new Asignaciones(0, ticketId, operatorId, new java.util.Date(assignedAtTimestamp.getTime()));

                // Obtener conexión a la base de datos
                Connection connection = ConexionDB.getConnection();

                // Crear un objeto AsignacionesDAO con la conexión establecida
                AsignacionesDAO asignacionDAO = new AsignacionesDAO(connection);

                // Insertar la asignación
                boolean inserted = asignacionDAO.insertAsignaciones(asignacion); // Corregido aquí

                // Mostrar mensaje de éxito o error
                if (inserted) {
                    out.println("<p>Asignación insertada correctamente.</p>");
                } else {
                    out.println("<p>Error al insertar la asignación.</p>");
                }

                // Cerrar conexión
                connection.close();
            }
        } catch (SQLException e) {
            out.println("<p>Error de SQL: " + e.getMessage() + "</p>");
        }
    %>

    <form action="" method="post">
        <label for="ticketId">ID de Ticket:</label>
        <input type="text" id="ticketId" name="ticketId" required><br><br>

        <label for="operatorId">ID de Operador:</label>
        <input type="text" id="operatorId" name="operatorId" required><br><br>

        <label for="assignedAt">Fecha y Hora de Asignación (AAAA-MM-DD HH:MM:SS):</label>
        <input type="text" id="assignedAt" name="assignedAt" required><br><br>

        <input type="submit" value="Ingresar Asignacion">
    </form>

    <br>
    <p><a href="menuAsignaciones.jsp">Volver al Menú Principal</a></p>
</body>
</html>
