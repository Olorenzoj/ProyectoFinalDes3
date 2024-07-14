package Servlets;

import ConexionDB.ConexionDB;
import DAO.RoleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/roles"})
public class RoleServlet extends HttpServlet {
    private RoleDAO roleDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Connection connection = ConexionDB.getConnection();
            roleDAO = new RoleDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("getRoleName".equals(action)) {
            getRoleName(request, response);
        } else if ("printUserRoles".equals(action)) {
            printUserRoles(response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("reassignUserRole".equals(action)) {
            reassignUserRole(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    private void getRoleName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        try {
            String roleName = roleDAO.getRoleName(roleId);
            if (roleName != null) {
                response.setContentType("text/plain");
                response.getWriter().write("Nombre del rol: " + roleName);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Rol no encontrado para el ID especificado");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el nombre del rol: " + e.getMessage());
        }
    }

    private void reassignUserRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int newRoleId = Integer.parseInt(request.getParameter("newRoleId"));

        try {
            boolean success = roleDAO.reassignUserRole(userId, newRoleId);
            if (success) {
                response.setContentType("text/plain");
                response.getWriter().write("Rol reasignado correctamente.");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado o error al reasignar rol.");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al reasignar el rol: " + e.getMessage());
        }
    }

    private void printUserRoles(HttpServletResponse response) throws IOException {
        try {
            roleDAO.printUserRoles();
            response.setContentType("text/plain");
            response.getWriter().write("Roles de usuarios impresos en la consola del servidor.");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al imprimir los roles de los usuarios: " + e.getMessage());
        }
    }
}
