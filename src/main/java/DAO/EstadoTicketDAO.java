package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadoTicketDAO {
    private Connection connection;

    public EstadoTicketDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean updateTicketStatus(int ticketId, int newStatusId) throws SQLException {
        String query = "UPDATE Tickets SET status_id = ? WHERE ticket_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newStatusId);
            stmt.setInt(2, ticketId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public String getTicketStatus(int ticketId) throws SQLException {
        String status = null;
        String query = "SELECT status_name FROM Estados_Tickets WHERE statud_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, ticketId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                status = rs.getString("status_name");
            }
        }
        return status;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

