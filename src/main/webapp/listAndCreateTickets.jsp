<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="Servlets.TicketServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tickets</title>
        <link rel="stylesheet" type="text/css" href="CSS/Tickets.css">
    <title>Tickets Seccion</title>
</head>
<body>
    <h1>Tickets</h1>

    <h2>Crear Nuevo Ticket</h2>
    <form action="createTicket" method="post">
        <fieldset>
            <legend>Datos del Ticket</legend>
            <label for="userId">ID de Usuario:</label>
            <input type="text" id="userId" name="userId" required><br><br>
            <label for="title">Título:</label>
            <input type="text" id="title" name="title" required><br><br>
            <label for="description">Descripción:</label><br>
            <textarea id="description" name="description" rows="4" cols="50" required></textarea><br><br>
            <label for="statusId">ID de Estado:</label>
            <input type="text" id="statusId" name="statusId" required><br><br>
            <button type="submit">Crear</button>
        </fieldset>
    </form>

    <%-- Mostrar mensaje de confirmación --%>
    <%
        String message = (String) request.getAttribute("message");
        String messageType = (String) request.getAttribute("messageType");
        if (message != null) {
    %>
        <div class="message <%= messageType %>"><%= message %></div>
    <%
        }
    %>

    <h2>Buscar Tickets</h2>
    <%-- Formulario para buscar ticket por ID --%>
    <form action="getTicket" method="get">
        <label for="ticketId">Buscar Ticket por ID:</label>
        <input type="text" id="ticketId" name="ticketId" required>
        <button type="submit">Buscar</button>
    </form>

    <%-- Obtener la lista de tickets desde el atributo de la solicitud --%>
    <%
        List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");

        if (tickets != null && !tickets.isEmpty()) {
    %>
    <h2>Tickets Creados</h2>
    <div>
    <%
            for (Ticket ticket : tickets) {
    %>
        <div class="ticket-details">
            <p>Titulo: <%= ticket.getTitle() %></p>
            <p>Descripcion: <%= ticket.getDescription() %></p>
            <p>Creado el: <%= ticket.getCreateAtTicket() %></p>
            <p>Actualizado el: <%= ticket.getUpdatedAtTicket() %></p>
        </div>
    <%
            }
        } else {
    %>
        <div class="no-tickets">
            <p>No hay tickets disponibles.</p>
        </div>
    <%
        }
    %>

    <h2>Eliminar Tickets</h2>
    <%-- Formulario para eliminar ticket por ID --%>
    <form action="deleteTicket.jsp" method="post">
        <fieldset>
            <legend>Eliminar Ticket por ID:</legend>
            <label for="deleteTicketId">ID del Ticket:</label>
            <input type="text" id="deleteTicketId" name="ticketId" required>
            <button type="submit">Eliminar Ticket</button>
        </fieldset>
    </form>

    <hr>
    <br>
    <a href="index.jsp">Inicio</a>
</body>
</html>
