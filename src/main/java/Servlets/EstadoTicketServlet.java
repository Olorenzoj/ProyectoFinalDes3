package Servlets;

import DAO.EstadoTicketDAO;
import ConexionDB.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name = "EstadoTicketServlet", urlPatterns = {"/JSP/estadoticket"})
public class EstadoTicketServlet extends HttpServlet {

    private EstadoTicketDAO estadoTicketDAO;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        try {
            connection = ConexionDB.getConnection();
            estadoTicketDAO = new EstadoTicketDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Error al establecer la conexión con la base de datos", e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "getStatus":
                    handleGetStatus(request, response);
                    break;
                case "updateStatus":
                    handleUpdateStatus(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'action' requerido");
        }
    }

    protected void handleGetStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ticketIdStr = request.getParameter("ticketId");
        if (ticketIdStr != null && !ticketIdStr.isEmpty()) {
            try {
                int ticketId = Integer.parseInt(ticketIdStr);
                String ticketStatus = estadoTicketDAO.getTicketStatus(ticketId);
                request.setAttribute("ticketStatus", ticketStatus);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de ticket no válido");
                return;
            } catch (SQLException e) {
                throw new ServletException("Error al obtener el estado del ticket", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'ticketId' requerido");
            return;
        }

        request.getRequestDispatcher("/JSP/estadoticket.jsp").forward(request, response);
    }

    protected void handleUpdateStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ticketIdStr = request.getParameter("ticketId");
        String newStatusIdStr = request.getParameter("newStatusId");

        if (ticketIdStr != null && !ticketIdStr.isEmpty() && newStatusIdStr != null && !newStatusIdStr.isEmpty()) {
            try {
                int ticketId = Integer.parseInt(ticketIdStr);
                int newStatusId = Integer.parseInt(newStatusIdStr);
                estadoTicketDAO.updateTicketStatus(ticketId, newStatusId);
                request.setAttribute("message", "Estado de ticket actualizado correctamente");
                request.setAttribute("success", true);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de ticket o estado no válido");
                return;
            } catch (SQLException e) {
                throw new ServletException("Error al actualizar el estado del ticket", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros 'ticketId' y 'newStatusId' requeridos");
            return;
        }

        request.getRequestDispatcher("/JSP/estadoticket.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        try {
            estadoTicketDAO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
