<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Servlets.EstadoTicketServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Estado de Tickets</title>
    <style>
        .message {
            margin-top: 20px;
            padding: 10px;
            font-weight: bold;
        }
        .success {
            color: green;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <h1>Gestión de Estado de Tickets</h1>

    <%-- Mensaje de confirmación o error --%>
    <% String message = (String) request.getAttribute("message"); %>
    <% Boolean success = (Boolean) request.getAttribute("success"); %>
    <% if (message != null && success != null) { %>
        <div class="message <%= success ? "success" : "error" %>">
            <%= message %>
        </div>
    <% } %>

    <h2>Actualizar Estado de Ticket</h2>
    <form action="estadoticket" method="post">
        <fieldset>
            <legend>Actualizar Estado</legend>
            <label for="ticketId">ID de Ticket:</label>
            <input type="text" id="ticketId" name="ticketId" required><br><br>
            <label for="newStatusId">Nuevo ID de Estado:</label>
            <input type="text" id="newStatusId" name="newStatusId" required><br><br>
            <button type="submit">Actualizar Estado</button>
        </fieldset>
        <input type="hidden" name="action" value="updateStatus">
    </form>

    <hr>

    <h2>Consultar Estado de Ticket</h2>
    <form action="estadoticket" method="get">
        <fieldset>
            <legend>Consultar Estado</legend>
            <label for="ticketIdStatus">ID de Ticket:</label>
            <input type="text" id="ticketIdStatus" name="ticketId" required><br><br>
            <button type="submit">Consultar Estado</button>
        </fieldset>
        <input type="hidden" name="action" value="getStatus">
    </form>

    <%-- Mostrar el estado del ticket si se obtiene --%>
    <% String ticketStatus = (String) request.getAttribute("ticketStatus"); %>
    <% if (ticketStatus != null) { %>
        <div class="message">
            Estado actual del ticket: <%= ticketStatus %>
        </div>
    <% } %>

</body>
</html>
