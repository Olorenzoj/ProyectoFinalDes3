package Servlets;

import ConexionDB.ConexionDB;
import DAO.TicketDAO;
import Modelos.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/tickets", "/createTicket", "/updateTicket", "/deleteTicket"})
public class TicketServlet extends HttpServlet {
    private TicketDAO ticketDAO;
    private String servletPath = null;

    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = null;
        try {
            connection = ConexionDB.getConnection();
            ticketDAO = new TicketDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();

        switch (servletPath) {
            case "/tickets" -> getAllTickets(request, response);
            case "/getTicket" -> getTicketById(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();
        switch (servletPath) {
            case "/createTicket" -> createTicket(request, response);
            case "/updateTicket" -> updateTicket(request, response);
            case "/deleteTicket" -> deleteTicket(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
        }
    }

    protected void getAllTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Ticket> tickets = ticketDAO.getAllTickets();

            request.setAttribute("tickets", tickets);
            request.getRequestDispatcher("/listTickets.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener la lista de tickets: " + e.getMessage());
        }
    }

    protected void getTicketById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        try {
            Ticket ticket = ticketDAO.getTicketById(ticketId);
            if (ticket != null) {
                request.setAttribute("ticket", ticket);
                request.getRequestDispatcher("/viewTicket.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ticket no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el ticket: " + e.getMessage());
        }
    }

    protected void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        Date createAtTicket = new Date(System.currentTimeMillis());
        Date updatedAtTicket = new Date(System.currentTimeMillis());

        Ticket ticket = new Ticket(userId, title, description, statusId, createAtTicket, updatedAtTicket);

        try {
            ticketDAO.createTicket(ticket);
            response.sendRedirect("TicketServlet?action=list");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al crear el ticket: " + e.getMessage());
        }
    }

    protected void updateTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int statusId = Integer.parseInt(request.getParameter("statusId"));
        Date updatedAtTicket = new Date(System.currentTimeMillis());

        Ticket ticket = new Ticket(userId, title, description, statusId, null, updatedAtTicket);
        ticket.setTicketId(ticketId);

        try {
            ticketDAO.updateTicket(ticket);
            response.sendRedirect("TicketServlet?action=list");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el ticket: " + e.getMessage());
        }
    }

    protected void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        try {
            ticketDAO.deleteTicket(ticketId);
            response.sendRedirect("TicketServlet?action=list");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el ticket: " + e.getMessage());
        }
    }
}