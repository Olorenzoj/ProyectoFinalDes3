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

    public String getTicketStatus(int ticketId) throws SQLException {
        String status = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT status_name FROM Estados_Tickets WHERE statud_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ticketId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                status = rs.getString("status_name");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return status;
    }

    public void updateTicketStatus(int ticketId, int newStatusId) throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Tickets SET status_id = ? WHERE ticket_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newStatusId);
            stmt.setInt(2, ticketId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
