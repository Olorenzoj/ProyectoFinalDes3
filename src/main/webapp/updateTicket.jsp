<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="DAO.TicketDAO" %>
<%@ page import="Modelos.Ticket" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<head>
    <title>Actualizar Ticket</title>
</head>
<body>
    <h1>Actualizar Ticket</h1>
    <%
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        TicketDAO ticketDAO = null;
        Connection connection = null;
        try {
            connection = getConnection(); // Obtener la conexión usando el método getConnection()
            ticketDAO = new TicketDAO(connection);
            Ticket ticket = ticketDAO.getTicketById(ticketId);

            if (ticket != null) {
    %>
    <form action="TicketServlet?action=updateTicket" method="post">
        <input type="hidden" name="ticketId" value="<%= ticket.getTicketId() %>">
        <label for="userId">ID de Usuario:</label>
        <input type="text" id="userId" name="userId" value="<%= ticket.getUserId() %>" required><br>
        <label for="title">Título:</label>
        <input type="text" id="title" name="title" value="<%= ticket.getTitle() %>" required><br>
        <label for="description">Descripción:</label>
        <textarea id="description" name="description" required><%= ticket.getDescription() %></textarea><br>
        <label for="statusId">ID de Estado:</label>
        <input type="text" id="statusId" name="statusId" value="<%= ticket.getStatusId() %>" required><br>
        <button type="submit">Actualizar</button>
    </form>
    <%
            } else {
    %>
    <p>El ticket no fue encontrado.</p>
    <%
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close(); // Cerrar la conexión al finalizar
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    %>
    <a href="TicketServlet?action=list">Volver a la Lista de Tickets</a>
</body>
</html>
