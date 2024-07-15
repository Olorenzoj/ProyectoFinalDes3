package Servlets;

import ConexionDB.ConexionDB;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import DAO.RoleDAO;
import Modelos.Role;
import Modelos.Usuario;

@WebServlet("/roles")
public class RoleServlet extends HttpServlet {
    private RoleDAO roleDAO;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = ConexionDB.getConnection();
            roleDAO = new RoleDAO(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null) {
                // Obtener todos los usuarios con sus roles y enviarlos al JSP
                List<Usuario> usuariosConRoles = roleDAO.getAllUsersWithRoles();
                List<Role> roles = roleDAO.getAllRoles(); // Obtener todos los roles disponibles
                request.setAttribute("usuariosConRoles", usuariosConRoles);
                request.setAttribute("roles", roles); // Agregar la lista de roles a la solicitud

                request.getRequestDispatcher("roles.jsp").forward(request, response);
                return;
            }

            switch (action) {
                default:
                    response.sendRedirect("roles.jsp");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error al obtener los roles desde la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "reassignUserRole":
                reassignUserRole(request, response);
                break;
            default:
                response.sendRedirect("roles.jsp");
                break;
        }
    }

    private void reassignUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int newRoleId = Integer.parseInt(request.getParameter("newRoleId"));

        try {
            boolean success = roleDAO.reassignUserRole(userId, newRoleId);

            if (success) {
                request.setAttribute("success", true);
                request.setAttribute("message", "Rol de usuario reasignado correctamente.");
            } else {
                request.setAttribute("success", false);
                request.setAttribute("message", "Error al reasignar el rol de usuario.");
            }

        } catch (SQLServerException e) {
            // Capturar la excepción específica de base de datos (ej. SQLServerException)
            String errorMessage = "Error al reasignar el rol de usuario: " + e.getMessage(); // Mensaje de error específico
            request.setAttribute("success", false);
            request.setAttribute("message", errorMessage);

        } catch (SQLException e) {
            throw new ServletException("Error al reasignar el rol de usuario", e);
        }

        try {
            // Recargar los datos necesarios después de la reasignación
            List<Usuario> usuariosConRoles = roleDAO.getAllUsersWithRoles();
            List<Role> roles = roleDAO.getAllRoles();

            request.setAttribute("usuariosConRoles", usuariosConRoles);
            request.setAttribute("roles", roles); // Actualizar la lista de roles

        } catch (SQLException e) {
            throw new ServletException("Error al obtener los usuarios con roles desde la base de datos", e);
        }

        // Mantener los valores en el formulario
        request.setAttribute("userId", userId);
        request.setAttribute("newRoleId", newRoleId);

        request.getRequestDispatcher("roles.jsp").forward(request, response);
    }


    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
