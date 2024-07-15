<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelos.Ticket" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ver Ticket</title>
    <link rel="stylesheet" type="text/css" href="../CSS/viewTickets.css">
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
    <div class="ticket-details">
        <p><strong>ID:</strong> <%= ticket.getTicketId() %></p>
        <p><strong>Título:</strong> <%= ticket.getTitle() %></p>
        <p><strong>Descripción:</strong> <%= ticket.getDescription() %></p>
        <form action="updateTicket" method="post">
            <label for="statusId">Estado:</label>
            <select id="statusId" name="statusId">
                <option value="1" <%= ticket.getStatusId() == 1 ? "selected" : "" %>>En proceso</option>
                <option value="2" <%= ticket.getStatusId() == 2 ? "selected" : "" %>>Completado</option>
                <option value="3" <%= ticket.getStatusId() == 3 ? "selected" : "" %>>Cancelado</option>
            </select>
            <input type="hidden" name="ticketId" value="<%= ticket.getTicketId() %>">
            <button type="submit">Guardar Cambios</button>
        </form>
        <p><strong>Fecha de Creación:</strong> <%= ticket.getCreateAtTicket() %></p>
        <p><strong>Fecha de Actualización:</strong> <%= ticket.getUpdatedAtTicket() %></p>
    </div>
    <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
    <%
        } else if (request.getAttribute("errorMessage") == null) {
    %>
    <p>El ticket no fue encontrado.</p>
    <a href="listAndCreateTickets.jsp">Volver a la Lista de Tickets</a>
    <%
        }
    %>
</body>
</html>
