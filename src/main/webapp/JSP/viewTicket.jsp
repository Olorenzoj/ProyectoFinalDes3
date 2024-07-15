<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ticket</title>
    <link rel="stylesheet" type="text/css" href="../CSS/viewTickets.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            var errorMessage = "<%= request.getAttribute("errorMessage") %>";
            if (errorMessage) {
                showError(errorMessage);
            }
            var successMessage = "<%= request.getAttribute("message") %>";
            if (successMessage) {
                showSuccess(successMessage);
            }
        });

        function showError(message) {
            $('#error-message').text(message);
            $('#error-message').show();
        }

        function showSuccess(message) {
            $('#success-message').text(message);
            $('#success-message').show();
        }
    </script>
</head>
<body>
    <h1>Ver Ticket</h1>
    <div id="error-message" style="display:none;"></div>
    <div id="success-message" style="display:none;"></div>
    <%
        Ticket ticket = (Ticket) request.getAttribute("ticket");
        if (ticket != null) {
    %>
    <div class="ticket-details">
        <p><strong>ID:</strong> <%= ticket.getTicketId() %></p>
        <p><strong>Título:</strong> <%= ticket.getTitle() %></p>
        <p><strong>Descripción:</strong> <%= ticket.getDescription() %></p>
        <p><strong>Estado:</strong> <%= ticket.getStatusId() %></p>
        <p><strong>Fecha de Creación:</strong> <%= ticket.getCreateAtTicket() %></p>
        <p><strong>Fecha de Actualización:</strong> <%= ticket.getUpdatedAtTicket() %></p>
    </div>
    <form action="viewTickets" method="post">
        <input type="hidden" name="action" value="updateStatus">
        <input type="hidden" name="ticketId" value="<%= ticket.getTicketId() %>">
        <label for="newStatusId">Nuevo Estado:</label>
        <input type="number" name="newStatusId" required>
        <button type="submit">Actualizar Estado</button>
    </form>
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
