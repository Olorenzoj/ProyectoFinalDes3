<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAO.TicketDAO" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="java.sql.SQLException" %>
<%
    // Obtener el objeto TicketDAO desde el contexto de la aplicación
    TicketDAO ticketDAO = (TicketDAO) application.getAttribute("ticketDAO");

    try {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        Ticket ticket = ticketDAO.getTicketById(ticketId);

        if (ticket != null) {
            // Realizar la operación de eliminación
            ticketDAO.deleteTicket(ticketId);
%>
            <!DOCTYPE html>
            <html>
            <head>
                <title>Eliminar Ticket</title>
                        <link rel="stylesheet" type="text/css" href="CSS/deleteTicket.css">
                    <title>Eliminar Tickets</title>
            </head>
            <body>
                <h1>Eliminar Ticket</h1>
                <p>El ticket con ID <%= ticketId %> ha sido eliminado exitosamente.</p>
                <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
            </body>
            </html>
<%
        } else {
%>
            <!DOCTYPE html>
            <html>
            <head>
            <title>EliminarTicket</title>
                    <link rel="stylesheet" type="text/css" href="CSS/deleteTicket.css">
                <title>Eliminar Ticket</title>
            </head>
            <body>
                <h1>Eliminar Ticket</h1>
                <p>No se encontró ningún ticket con el ID <%= ticketId %>.</p>
                <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
            </body>
            </html>
<%
        }
    } catch (NumberFormatException e) {
        // Manejar errores de conversión de números
        e.printStackTrace();
%>
        <!DOCTYPE html>
        <html>
        <head>
            <title>Error</title>
                    <link rel="stylesheet" type="text/css" href="CSS/deleteTicket.css">
                <title>Error</title>
        </head>
        <body>
            <h1>Error</h1>
            <p>El ID del ticket proporcionado no es válido.</p>
            <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
        </body>
        </html>
<%
    } catch (SQLException e) {
        // Manejar errores de SQL
        e.printStackTrace();
%>
        <!DOCTYPE html>
        <html>
        <head>
        <title>Error</title>
                <link rel="stylesheet" type="text/css" href="CSS/deleteTicket.css">
            <title>Error</title>
        </head>
        <body>
            <h1>Error</h1>
            <p>Ocurrió un error al eliminar el ticket: <%= e.getMessage() %></p>
            <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
        </body>
        </html>
<%
    }
%>