package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO {
    private Connection connection;

    public RoleDAO(Connection connection) {
        this.connection = connection;
    }

    public String getRoleName(int roleId) throws SQLException {
        String roleName = null;
        String query = "SELECT role_name FROM roles WHERE role_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, roleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                roleName = rs.getString("role_name");
            }
        }
        return roleName;
    }

    public boolean reassignUserRole(int userId, int newRoleId) throws SQLException {
        String query = "UPDATE usuarios SET role_id = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, newRoleId);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public void printUserRoles() throws SQLException {
        String query = "SELECT u.user_id, r.role_name FROM usuarios u JOIN roles r ON u.role_id = r.role_id";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String roleName = rs.getString("role_name");
                System.out.println("UserID: " + userId + ", RoleName: " + roleName);
            }
        }
    }
}
