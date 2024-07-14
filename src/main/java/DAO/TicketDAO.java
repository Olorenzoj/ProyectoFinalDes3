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
public List<Ticket> getAllTickets() throws SQLException {
    List<Ticket> tickets = new ArrayList<>();
    String query = "SELECT * FROM Tickets";

    try (PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            Ticket ticket = new Ticket(
                    resultSet.getInt("ticket_id"),
                    resultSet.getString("title"),
                    resultSet.getString("description_ticket"),
                    resultSet.getInt("status_id"),
                    resultSet.getDate("create_at_ticket"),
                    resultSet.getDate("updated_at_ticket")
            );
            System.out.println("Ticket obtenido: " + ticket);
            tickets.add(ticket);
        }
    } catch (SQLException e) {
        System.out.println("Error al buscar la lista de tickets: " + e.getMessage());
        throw new SQLException();
    }
    return tickets;
}


    public Ticket getTicketById(int ticketId) throws SQLException {
        String query = "SELECT * FROM Tickets WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, ticketId);

            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return new Ticket(
                            resultSet.getInt("user_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description_ticket"),
                            resultSet.getInt("status_id"),
                            resultSet.getDate("create_at_ticket"),
                            resultSet.getDate("updated_at_ticket")
                    );
                } else {
                    return null; // Si no encuentra el ticket, se devuelve null
                }
            } catch (SQLException e){
                System.out.println("Error al buscar el ticket por ID: " + e.getMessage());
                throw new SQLException();
            }
        }
    }

    public void updateTicket(Ticket ticket) throws SQLException {
        // ... (Query to update ticket details)
        String query = "UPDATE Tickets SET user_id = ?, title = ?, description_ticket = ?, status_id = ?, updated_at_ticket = ? WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, ticket.getTicketId());
            statement.setString(2, ticket.getTitle());
            statement.setString(3, ticket.getDescription());
            statement.setInt(4, ticket.getStatusId());
            statement.setTimestamp(5, new Timestamp(ticket.getUpdatedAtTicket().getTime()));
            statement.setInt(6, ticket.getTicketId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0){
                throw new SQLException("No se encontro ningun ticket con el ID proporcionado.");
            }
        }catch (SQLException e){
            System.out.println("Error al actualizar el ticket: " + e.getMessage());
            throw new SQLException();
        }
    }

    public void deleteTicket(int ticketId) throws SQLException {
        // ... (Query to delete a ticket)
        String query = "DELETE FROM Tickets WHERE ticket_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, ticketId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0){
                throw new SQLException("No se encontro ningun ticket con el ID proporcionado.");
            }
        } catch (SQLException e){
            System.out.println("Error el eliminar el ticket: " + e.getMessage());
            throw new SQLException();
        }
    }
}