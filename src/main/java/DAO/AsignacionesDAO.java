package DAO;

import Modelos.Asignaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AsignacionesDAO {
    private Connection connection;

    public AsignacionesDAO(Connection connection) {
        this.connection = connection;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public boolean insertAsignaciones(Asignaciones asignacion) throws SQLException {
        String query = "INSERT INTO Asignaciones (ticket_id, operator_id, assigned_at) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, asignacion.getTicketId());
            statement.setInt(2, asignacion.getOperatorId());
            statement.setTimestamp(3, new java.sql.Timestamp(asignacion.getAssignedAt().getTime()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        asignacion.setAssignmentId(generatedKeys.getInt(1));
                        return true;
                    } else {
                        throw new SQLException("Creating asignacion failed, no ID obtained.");
                    }
                }
            } else {
                return false;
            }
        }
    }

    public List<Asignaciones> allAsignaciones() throws SQLException {
        List<Asignaciones> allAsignaciones = new ArrayList<>();
        String query = "SELECT * FROM Asignaciones";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Asignaciones asignacion = new Asignaciones(
                        resultSet.getInt("assignment_id"),
                        resultSet.getInt("ticket_id"),
                        resultSet.getInt("operator_id"),
                        resultSet.getTimestamp("assigned_at")
                );
                allAsignaciones.add(asignacion);
            }
            return allAsignaciones;
        }
    }

    public boolean deleteAsignacion(Asignaciones asignacion) throws SQLException {
        String query = "DELETE FROM Asignaciones WHERE assignment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getAssignmentId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean updateAsignacion(Asignaciones asignacion) throws SQLException {
        String query = "UPDATE Asignaciones SET ticket_id = ?, operator_id = ?, assigned_at = ? WHERE assignment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getTicketId());
            statement.setInt(2, asignacion.getOperatorId());
            statement.setTimestamp(3, new java.sql.Timestamp(asignacion.getAssignedAt().getTime()));
            statement.setInt(4, asignacion.getAssignmentId());
            return statement.executeUpdate() > 0;
        }
    }

    public Asignaciones getAsignacion(int id) throws SQLException {
        String query = "SELECT * FROM Asignaciones WHERE assignment_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int ticketId = resultSet.getInt("ticket_id");
                    int operatorId = resultSet.getInt("operator_id");
                    java.sql.Timestamp assignedAtTimestamp = resultSet.getTimestamp("assigned_at");
                    Date assignedAt = assignedAtTimestamp != null ? new Date(assignedAtTimestamp.getTime()) : null;
                    return new Asignaciones(id, ticketId, operatorId, assignedAt);
                }
            }
        }
        return null;
    }
}
