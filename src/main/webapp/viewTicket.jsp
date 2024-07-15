<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tickets</title>
    <link rel="stylesheet" type="text/css" href="CSS/viewTickets.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            var errorMessage = "<%= request.getAttribute("errorMessage") %>";
            if (errorMessage) {
                showError(errorMessage);
            }
        });

        function showError(message) {
            $('#error-message').text(message);
            $('#error-message').show();
        }
    </script>
</head>
<body>
    <h1>Ver Ticket</h1>
    <div id="error-message" style="display: none; color: red; border: 1px solid red; padding: 10px; margin-bottom: 20px;"></div>
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
        } else if (request.getAttribute("errorMessage") == null) {
    %>
    <p>El ticket no fue encontrado.</p>
    <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>

    <%
        }
    %>
    <a href="estadoticket.jsp">Gestion de Tickets</a>
</body>
</html>
