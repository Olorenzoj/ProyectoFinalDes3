<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.SQLException, DAO.AsignacionesDAO, Modelos.Asignaciones, ConexionDB.ConexionDB"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ver Asignación</title>
</head>
<body>
    <h1>Ver Asignación</h1>

    <%@ page errorPage="error.jsp" %>

    <%
        Asignaciones asignacion = null;
        try {
            Connection connection = ConexionDB.getConnection();
            AsignacionesDAO asignacionDAO = new AsignacionesDAO(connection);

            if (request.getMethod().equals("POST")) {
                int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
                asignacion = asignacionDAO.getAsignacion(assignmentId);

                if (asignacion == null) {
                    out.println("<p>No se encontró la asignación con ID: " + assignmentId + ".</p>");
                }
            }

            connection.close();
        } catch (SQLException e) {
            out.println("<p>Error de SQL: " + e.getMessage() + "</p>");
        }
    %>

    <form action="" method="post">
        <label for="assignmentId">ID de Asignación:</label>
        <input type="text" id="assignmentId" name="assignmentId" required><br><br>
        <input type="submit" value="Ver Asignación">
    </form>

    <% if (asignacion != null) { %>
        <h2>Detalles de la Asignación</h2>
        <p>ID: <%= asignacion.getAssignmentId() %></p>
        <p>ID de Ticket: <%= asignacion.getTicketId() %></p>
        <p>ID de Operador: <%= asignacion.getOperatorId() %></p>
        <p>Fecha y Hora de Asignación: <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(asignacion.getAssignedAt()) %></p>
    <% } %>

    <br>
    <p><a href="index.jsp">Volver al Inicio</a></p>
</body>
</html>
