<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ver Ticket</title>
</head>
<body>
    <h1>Ver Ticket</h1>
    <%
        Ticket ticket = (Ticket) request.getAttribute("ticket");
        if (ticket != null) {
    %>
    <p><strong>ID:</strong> <%= ticket.getTicketId() %></p>
    <p><strong>Título:</strong> <%= ticket.getTitle() %></p>
    <p><strong>Descripción:</strong> <%= ticket.getDescription() %></p>
    <p><strong>Estado:</strong> <%= ticket.getStatusId() %></p>
    <p><strong>Fecha de Creación:</strong> <%= ticket.getCreateAtTicket() %></p>
    <p><strong>Fecha de Actualización:</strong> <%= ticket.getUpdatedAtTicket() %></p>
    <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
    <%
        } else {
    %>
    <p>El ticket no fue encontrado.</p>
    <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
    <%
        }
    %>
</body>
</html>

