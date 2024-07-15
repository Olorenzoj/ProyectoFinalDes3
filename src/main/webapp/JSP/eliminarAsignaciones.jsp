<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Modelos.Asignaciones" %>
<%@ page import="DAO.AsignacionesDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Eliminar Asignaciones</title>
    <link rel="stylesheet" type="text/css" href="../CSS/asigElim.css">
</head>
<body>
    <div class="container">
        <h1>Elimina una Asignación</h1>

        <form method="post">
            <label for="assignmentId">Selecciona el Assignment ID a eliminar:</label>
            <select name="assignmentId" id="assignmentId">
                <%
                    AsignacionesDAO asignacionDAO = new AsignacionesDAO(ConexionDB.ConexionDB.getConnection());
                    List<Asignaciones> asignacionesList = asignacionDAO.allAsignaciones();
                    for (Asignaciones asignacion : asignacionesList) {
                %>
                    <option value="<%= asignacion.getAssignmentId() %>"><%= asignacion.getAssignmentId() %></option>
                <% } %>
            </select>
            <br>
            <button type="submit">Eliminar</button>
        </form>

        <%-- Procesamiento del formulario --%>
        <% if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
                Asignaciones asignacion = new Asignaciones(assignmentId, 0, 0, new Date());
                boolean deleted = asignacionDAO.deleteAsignacion(asignacion);
        %>
                <div class="message">
                    <% if (deleted) { %>
                        <p>Asignación con ID <%= assignmentId %> eliminada correctamente.</p>
                    <% } else { %>
                        <p>Ocurrió un error al intentar eliminar la asignación con ID <%= assignmentId %> .</p>
                    <% } %>
                </div>
        <%
            } catch (NumberFormatException e) {
        %>
                <div class="error-message">
                    <p>Error: Debes seleccionar un Assignment ID válido.</p>
                </div>
        <%
            } finally {
                asignacionDAO.close();
            }
        }
        %>

        <p><a class="button" href="menuAsignaciones.jsp">Volver al Inicio</a></p>
    </div>
</body>
</html>
