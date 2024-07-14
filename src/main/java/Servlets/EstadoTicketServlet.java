package Servlets;

import ConexionDB.ConexionDB;
import DAO.EstadoTicketDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/ticketStatus")
public class EstadoTicketServlet extends HttpServlet {
    private EstadoTicketDAO estadoTicketDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = null;
        try {
            connection = ConexionDB.getConnection();
            estadoTicketDAO = new EstadoTicketDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error de conexión a la base de datos", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'action' requerido");
            return;
        }

        switch (action) {
            case "updateStatus":
                handleUpdateStatus(request, response);
                break;
            case "getStatus":
                handleGetStatus(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    private void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int newStatusId = Integer.parseInt(request.getParameter("newStatusId"));

        try {
            boolean updated = estadoTicketDAO.updateTicketStatus(ticketId, newStatusId);
            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Estado del ticket actualizado correctamente");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo actualizar el estado del ticket");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el estado del ticket: " + e.getMessage());
        }
    }

    private void handleGetStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        try {
            String status = estadoTicketDAO.getTicketStatus(ticketId);
            if (status != null) {
                response.getWriter().write("Estado actual del ticket: " + status);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el estado del ticket");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el estado del ticket: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        try {
            estadoTicketDAO.close(); // Método close() necesario si existe en EstadoTicketDAO para cerrar la conexión
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
