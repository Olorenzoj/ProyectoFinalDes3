<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ page import="java.sql.Connection, java.sql.SQLException, DAO.AsignacionesDAO, Modelos.Asignaciones, ConexionDB.ConexionDB"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actualizar Asignación</title>
    <link rel="stylesheet" type="text/css" href="CSS/asigActu.css">
</head>
<body>
    <div class="container">
        <div class="content">
            <h1>Actualizar Asignación</h1>

            <%@ page errorPage="error.jsp" %>

            <%
                Asignaciones asignacion = null;
                List<Asignaciones> asignacionesList = null;
                try {
                    Connection connection = ConexionDB.getConnection();
                    AsignacionesDAO asignacionDAO = new AsignacionesDAO(connection);

                    asignacionesList = asignacionDAO.allAsignaciones();

                    if (request.getMethod().equals("POST") && request.getParameter("selectAssignment") != null) {
                        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
                        asignacion = asignacionDAO.getAsignacion(assignmentId);
                        if (asignacion == null) {
                            out.println("<p>No se encontró la asignación con ID: " + assignmentId + ".</p>");
                        }
                    }

                    if (request.getMethod().equals("POST") && request.getParameter("updateAssignment") != null) {
                        int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
                        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
                        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
                        java.sql.Timestamp assignedAtTimestamp = java.sql.Timestamp.valueOf(request.getParameter("assignedAt"));
                        asignacion = new Asignaciones(assignmentId, ticketId, operatorId, new java.util.Date(assignedAtTimestamp.getTime()));
                        boolean updated = asignacionDAO.updateAsignacion(asignacion);

                        if (updated) {
                            out.println("<p>Asignación actualizada correctamente.</p>");
                        } else {
                            out.println("<p>Error al actualizar la asignación.</p>");
                        }
                    }

                    connection.close();
                } catch (SQLException e) {
                    out.println("<p>Error de SQL: " + e.getMessage() + "</p>");
                }
            %>

            <% if (asignacion == null) { %>
                <form action="" method="post">
                    <label for="assignmentId">Selecciona el ID de la Asignación:</label>
                    <select name="assignmentId" id="assignmentId">
                        <% for (Asignaciones asign : asignacionesList) { %>
                            <option value="<%= asign.getAssignmentId() %>"><%= asign.getAssignmentId() %></option>
                        <% } %>
                    </select>
                    <br><br>
                    <input type="submit" name="selectAssignment" value="Seleccionar Asignación">
                </form>
            <% } else { %>
                <form action="" method="post">
                    <input type="hidden" id="assignmentId" name="assignmentId" value="<%= asignacion.getAssignmentId() %>">
                    <label for="ticketId">ID de Ticket:</label>
                    <input type="text" id="ticketId" name="ticketId" value="<%= asignacion.getTicketId() %>" required><br><br>
                    <label for="operatorId">ID de Operador:</label>
                    <input type="text" id="operatorId" name="operatorId" value="<%= asignacion.getOperatorId() %>" required><br><br>
                    <label for="assignedAt">Fecha y Hora de Asignación (AAAA-MM-DD HH:MM:SS):</label>
                    <input type="text" id="assignedAt" name="assignedAt" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(asignacion.getAssignedAt()) %>" required><br><br>
                    <input type="submit" name="updateAssignment" value="Actualizar Asignación">
                </form>
                <div class="centered">
                    <p><a href="index.jsp">Volver al Inicio</a></p>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
