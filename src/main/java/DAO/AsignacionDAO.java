package DAO;

import Modelos.Asignacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignacionDAO {
    private final Connection connection;

    public AsignacionDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertAsignacion(Asignacion asignacion) throws SQLException {
        String query = "INSERT INTO Asignacion (ticketId, operatorId, assignedAt) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getTicketId());
            statement.setInt(2, asignacion.getOperatorId());
            statement.setTimestamp(3, new Timestamp(asignacion.getAssignedAt().getTime()));

            statement.executeUpdate();
        }
    }

    public List<Asignacion> allAsignacion() throws SQLException {
        List<Asignacion> allAsignacion = new ArrayList<>();
        String query = "SELECT * FROM Asignacion";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Asignacion asignacion = new Asignacion(
                        resultSet.getInt("assignmentId"), // Corregido a assignmentId
                        resultSet.getInt("ticketId"),
                        resultSet.getInt("operatorId"),
                        resultSet.getTimestamp("assignedAt")
                );
                allAsignacion.add(asignacion);
            }
            return allAsignacion;
        } catch (SQLException e) {
            System.err.println("Error al buscar la lista de asignaciones: " + e.getMessage());
            throw e;
        }
    }

    public void deleteAsignacion(Asignacion asignacion) throws SQLException {
        String query = "DELETE FROM Asignacion WHERE assignmentId = ?"; // Corregido a assignmentId

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getAssigmentId()); // Corregido a assignmentId
            statement.executeUpdate();
        }
    }

    public void updateAsignacion(Asignacion asignacion) throws SQLException {
        String query = "UPDATE Asignacion SET ticketId = ?, operatorId = ?, assignedAt = ? WHERE assignmentId = ?"; // Corregido a assignmentId

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, asignacion.getTicketId());
            statement.setInt(2, asignacion.getOperatorId());
            statement.setTimestamp(3, new Timestamp(asignacion.getAssignedAt().getTime()));
            statement.setInt(4, asignacion.getAssigmentId()); // Corregido a assignmentId
            statement.executeUpdate();
        }
    }

    public Asignacion getAsignacion(int id) throws SQLException {
        String query = "SELECT * FROM Asignacion WHERE assignmentId = ?"; // Corregido a assignmentId

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int ticketId = resultSet.getInt("ticketId");
                    int operatorId = resultSet.getInt("operatorId");
                    Timestamp assignedAt = resultSet.getTimestamp("assignedAt");

                    return new Asignacion(id, ticketId, operatorId, assignedAt);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la asignación: " + e.getMessage());
            throw e;
        }

        return null;
    }
}

