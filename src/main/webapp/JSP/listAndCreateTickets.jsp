<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelos.Ticket" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tickets</title>
    <link rel="stylesheet" type="text/css" href="../CSS/Tickets.css">
</head>
<body>
<div class="menu-container">
    <h1 style="color: white">Tickets</h1>
    <div class="button-menu">
        <button onclick="showSection('createTicketSection')">Crear Nuevo Ticket</button>
        <button onclick="showSection('viewTicketsSection')">Ver Tickets</button>
        <button onclick="showSection('deleteTicketSection')">Eliminar Ticket</button>
    </div>
</div>

<div id="createTicketSection" style="display: none;">
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
</div>

<div id="viewTicketsSection" style="display: none;">
    <h2>Buscar Tickets</h2>
    <form action="getTicket" method="get">
        <label for="ticketId">Buscar Ticket por ID:</label>
        <input type="text" id="ticketId" name="ticketId" required>
        <button type="submit">Buscar</button>
    </form>

    <h2>Tickets Creados</h2>
    <form action="tickets" method="get">
        <label for="ticket">Obtener los Tickets:</label>
        <button type="submit" id="ticket">Buscar</button>
    </form>
    <div>
        <%
            List<Ticket> tickets = (List<Ticket>) request.getAttribute("tickets");
            if (tickets != null && !tickets.isEmpty()) {
                for (Ticket ticket : tickets) {
        %>
        <div class="ticket-details">
            <p>Titulo: <%= ticket.getTitle() %></p>
            <p>ID: <%= ticket.getTicketId() %></p>
            <p>Asignando a: <%= ticket.getUserId()%></p>
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
    </div>
</div>

<div id="deleteTicketSection" style="display: none;">
    <h2>Eliminar Tickets</h2>
    <form action="deleteTicket" method="post">
        <fieldset>
            <legend>Eliminar Ticket por ID:</legend>
            <label for="deleteTicketId">ID del Ticket:</label>
            <input type="text" id="deleteTicketId" name="ticketId" required>
            <button type="submit">Eliminar Ticket</button>
        </fieldset>
    </form>
</div>

<script>
    function showSection(sectionId) {
        document.getElementById("createTicketSection").style.display = "none";
        document.getElementById("viewTicketsSection").style.display = "none";
        document.getElementById("deleteTicketSection").style.display = "none";
        document.getElementById(sectionId).style.display = "block";
    }
</script>
</body>
</html>
