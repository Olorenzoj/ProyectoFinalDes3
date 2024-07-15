package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Modelos.Role;
import Modelos.Usuario;

public class RoleDAO {
    private Connection connection;

    public RoleDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Role> getAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT role_id, role_name FROM roles";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");
                Role role = new Role(roleId, roleName);
                roles.add(role);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los roles: " + e.getMessage());
            throw e; // Lanzar la excepción para manejarla en el servlet
        }

        return roles;
    }

    public boolean reassignUserRole(int userId, int newRoleId) throws SQLException {
        String query = "UPDATE Usuarios SET role_id = ? WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newRoleId);
            stmt.setInt(2, userId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println("Error al reasignar el rol de usuario: " + e.getMessage());
            throw e; // Lanzar la excepción para manejarla en el servlet
        }
    }

    public List<Usuario> getAllUsersWithRoles() throws SQLException {
        List<Usuario> usuariosConRoles = new ArrayList<>();
        String query = "SELECT u.user_id, u.username, u.role_id, r.role_name " +
                "FROM Usuarios u " +
                "JOIN Roles r ON u.role_id = r.role_id";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");

                Usuario usuario = new Usuario(userId, username, roleId, roleName);
                usuariosConRoles.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios con roles: " + e.getMessage());
            throw e; // Lanzar la excepción para manejarla en el servlet
        }

        return usuariosConRoles;
    }
}
