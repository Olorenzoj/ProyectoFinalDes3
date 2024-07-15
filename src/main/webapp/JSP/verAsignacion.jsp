<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.SQLException, java.util.List, DAO.AsignacionesDAO, Modelos.Asignaciones, ConexionDB.ConexionDB"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ver Asignación</title>
    <link rel="stylesheet" type="text/css" href="CSS/asigView.css">
</head>
<body>
    <div class="container">
        <h1>Ver Asignación</h1>

        <%@ page errorPage="error.jsp" %>

        <%
            Asignaciones asignacion = null;
            List<Asignaciones> asignacionesList = null;
            try {
                Connection connection = ConexionDB.getConnection();
                AsignacionesDAO asignacionDAO = new AsignacionesDAO(connection);

                asignacionesList = asignacionDAO.allAsignaciones(); // Obtener todas las asignaciones disponibles

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
            <label for="assignmentId">Selecciona el ID de la Asignación:</label>
            <select name="assignmentId" id="assignmentId">
                <% for (Asignaciones asign : asignacionesList) { %>
                    <option value="<%= asign.getAssignmentId() %>"><%= asign.getAssignmentId() %></option>
                <% } %>
            </select>
            <br><br>
            <button type="submit">Ver Asignacion</button>
        </form>


        <% if (asignacion != null) { %>
            <div class="asignacion-detalle">
                <h2>Detalles de la Asignación</h2>
                <p>ID: <%= asignacion.getAssignmentId() %></p>
                <p>ID de Ticket: <%= asignacion.getTicketId() %></p>
                <p>ID de Operador: <%= asignacion.getOperatorId() %></p>
                <p>Fecha y Hora de Asignación: <%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(asignacion.getAssignedAt()) %></p>

                <div class="centered">
                    <p><a href="index.jsp" class="azul">Volver al Inicio</a></p>
                </div>
            </div>
        <% } %>
    </div>
</body>
</html>
