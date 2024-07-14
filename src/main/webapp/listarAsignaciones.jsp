<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>

<%@ page import="java.sql.Connection, java.sql.SQLException, java.util.List, DAO.AsignacionesDAO, Modelos.Asignaciones, ConexionDB.ConexionDB"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listar Asignaciones</title>
</head>
<body>
    <h1>Listar Asignaciones</h1>

    <%@ page errorPage="error.jsp" %>

    <%
        List<Asignaciones> asignacionesList = null;
        try {
            Connection connection = ConexionDB.getConnection();
            AsignacionesDAO asignacionDAO = new AsignacionesDAO(connection);

            asignacionesList = asignacionDAO.allAsignaciones(); // Corregido aquí

            connection.close();
        } catch (SQLException e) {
            out.println("<p>Error de SQL: " + e.getMessage() + "</p>");
        }
    %>

    <% if (asignacionesList != null) { %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>ID de Ticket</th>
                <th>ID de Operador</th>
                <th>Fecha y Hora de Asignación</th>
            </tr>
            <% for (Asignaciones asignacion : asignacionesList) { %>
                <tr>
                    <td><%= asignacion.getAssignmentId() %></td>
                    <td><%= asignacion.getTicketId() %></td>
                    <td><%= asignacion.getOperatorId() %></td>
                    <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(asignacion.getAssignedAt()) %></td>
                </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No se encontraron asignaciones.</p>
    <% } %>

    <br>
    <p><a href="index.jsp">Volver al Inicio</a></p>
</body>
</html>
