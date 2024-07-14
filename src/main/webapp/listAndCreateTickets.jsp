<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="Servlets.TicketServlet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado y Creación de Tickets</title>
</head>
<body>
    <h1>Listado de Tickets</h1>

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
            for (Ticket ticket : tickets) {
    %>
    <div>
        <h2><%= ticket.getTitle() %></h2>
        <p><%= ticket.getDescription() %></p>
        <p>Creado el: <%= ticket.getCreateAtTicket() %></p>
        <p>Actualizado el: <%= ticket.getUpdatedAtTicket() %></p>

        <%-- Formulario para eliminar ticket por ID --%>
            <form action="deleteTicket.jsp" method="post">
                <fieldset>
                    <legend>Eliminar Ticket por ID:</legend>
                    <label for="deleteTicketId">ID del Ticket:</label>
                    <input type="text" id="deleteTicketId" name="ticketId" required>
                    <button type="submit">Eliminar Ticket</button>
                </fieldset>
            </form>

    </div>
    <%
            }
        } else {
    %>
    <div>
        <p>No hay tickets disponibles.</p>
    </div>
    <%
        }
    %>

    <hr>
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
    <br>
    <a href="tickets">Volver a la Lista de Tickets</a>
</body>
</html>


