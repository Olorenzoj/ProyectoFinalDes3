<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error.jsp" %>

<%@ page import="java.sql.Connection, java.sql.SQLException, DAO.AsignacionesDAO, Modelos.Asignaciones, ConexionDB.ConexionDB"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Eliminar Asignación</title>
</head>
<body>
    <h1>Eliminar Asignación</h1>

    <%@ page errorPage="error.jsp" %>

    <%
        try {
            Connection connection = ConexionDB.getConnection();
            AsignacionesDAO asignacionDAO = new AsignacionesDAO(connection);

            if (request.getMethod().equals("POST")) {
                int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
                Asignaciones asignacion = new Asignaciones();
                asignacion.setAssignmentId(assignmentId);
                boolean deleted = asignacionDAO.deleteAsignacion(asignacion);

                if (deleted) {
                    out.println("<p>Asignación eliminada correctamente.</p>");
                } else {
                    out.println("<p>Error al eliminar la asignación.</p>");
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
        <input type="submit" value="Eliminar Asignación">
    </form>

    <br>
    <p><a href="../index.jsp">Volver al Inicio</a></p>
</body>
</html>
