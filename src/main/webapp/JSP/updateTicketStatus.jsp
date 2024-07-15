<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.SQLException" %>
<%@ page import="DAO.EstadoTicketDAO" %>
<%@ page import="ConexionDB.ConexionDB" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actualizar Estado de Ticket</title>
</head>
<body>
    <h2>Actualizar Estado de Ticket</h2>

    <%-- Java code to handle database connection and update --%>
    <%@ page trimDirectiveWhitespaces="true" %>
    <%@ page import="java.util.*, java.io.*, java.sql.*" %>
    <%@ page import="Modelos.EstadoTickets" %>
    <%@ page errorPage="error.jsp" %>

    <%
        Connection connection = null;
        try {
            if (request.getMethod().equals("POST") && request.getParameter("updateStatus") != null) {
                String ticketIdParam = request.getParameter("ticketId");
                String newStatusIdParam = request.getParameter("newStatusId");

                // Validar que se hayan proporcionado ambos parámetros
                if (ticketIdParam != null && newStatusIdParam != null) {
                    int ticketId = Integer.parseInt(ticketIdParam);
                    int newStatusId = Integer.parseInt(newStatusIdParam);

                    connection = ConexionDB.getConnection();
                    EstadoTicketDAO estadoTicketDAO = new EstadoTicketDAO(connection);
                    Boolean updated = null;
                    // Actualizar el estado del ticket
                    try {
                        estadoTicketDAO.updateTicketStatus(ticketId, newStatusId);
                        updated = true;
                    }catch (SQLException ignored){

                    }

                    if (updated) {
                        System.out.println("<p>Estado del ticket actualizado correctamente.</p>");
                    } else {
                        System.out.println("<p>Error al actualizar el estado del ticket.</p>");
                    }

                    estadoTicketDAO.close();
                } else {
                    System.out.println("<p>Debe proporcionar ambos ID de ticket y nuevo estado ID.</p>");
                }
            }
        } catch (SQLException e) {
            System.out.println("<p>Error en la conexión o consulta SQL: " + e.getMessage() + "</p>");
        } catch (NumberFormatException e) {
            System.out.println("<p>Debe ingresar números válidos para el ID del ticket y el nuevo estado ID.</p>");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("<p>Error al cerrar la conexión: " + e.getMessage() + "</p>");
                }
            }
        }
    %>

    <form action="" method="post">
        <label for="ticketId">ID del Ticket:</label>
        <input type="text" id="ticketId" name="ticketId"><br><br>

        <label for="newStatusId">Nuevo Estado ID:</label>
        <input type="text" id="newStatusId" name="newStatusId"><br><br>

        <input type="submit" name="updateStatus" value="Actualizar Estado">
    </form>

    <br>
    <p><a href="../index.jsp">Volver al Inicio</a></p>
</body>
</html>
