package DAO;

import ConexionDB.ConexionDB;
import Modelos.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private Connection connection;

    public TicketDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTicket(Ticket ticket) throws SQLException {
        String query = "INSERT INTO Tickets (user_id, title, description_ticket, status_id, create_at_ticket, updated_at_ticket) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ticket.getUserId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setInt(4, ticket.getStatusId()); // Assuming a default initial status
            statement.setTimestamp(5, new Timestamp(ticket.getCreateAtTicket().getTime()));
            statement.setTimestamp(6, new Timestamp(ticket.getUpdatedAtTicket().getTime()));
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ticket.setTicketId(generatedKeys.getInt(1));
                }
            }
        }
    }
// new methods added
  /*  public List<Ticket> getAllTickets() throws SQLException {
        // ... (Similar to allUsers() method in UsuarioDAO)
    }

    public Ticket getTicketById(int ticketId) throws SQLException {
        // ... (Query to fetch a single ticket by ID)
    }

    public void updateTicket(Ticket ticket) throws SQLException {
        // ... (Query to update ticket details)
    }

    public void deleteTicket(int ticketId) throws SQLException {
        // ... (Query to delete a ticket)
    }*/
}
