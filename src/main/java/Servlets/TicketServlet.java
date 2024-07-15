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

@WebServlet(urlPatterns = {"/tickets", "/createTicket", "/updateTicket", "/deleteTicket", "/getTicket"})
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
            getServletContext().setAttribute("ticketDAO", ticketDAO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();

        switch (servletPath) {
            case "/tickets":
                getAllTickets(request, response);
                break;
            case "/getTicket":
                getTicketById(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();
        switch (servletPath) {
            case "/createTicket":
                createTicket(request, response);
                break;
            case "/updateTicket":
                updateTicket(request, response);
                break;
            case "/deleteTicket":
                deleteTicket(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
                break;
        }
    }

    protected void getAllTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Obteniendo todos los tickets...");
            List<Ticket> tickets = ticketDAO.getAllTickets(); // Obteniendo la lista de tickets desde la base de datos
            System.out.println("Tickets obtenidos: " + tickets);

            request.setAttribute("tickets", tickets); // Estableciendo la lista de tickets como atributo de solicitud

            // Redirigiendo al JSP para mostrar la lista de tickets
            request.getRequestDispatcher("/listAndCreateTickets.jsp").forward(request, response);
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
            } else {
                request.setAttribute("errorMessage", "Ticket no encontrado");
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error al obtener el ticket: " + e.getMessage());
        }
        request.getRequestDispatcher("/viewTicket.jsp").forward(request, response);
    }


    protected void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int userId = Integer.parseInt(request.getParameter("userId")); // Assuming userId is provided in the request
        int statusId = 1; // Assuming a default status ID for new tickets

        Ticket newTicket = new Ticket(userId, title, description, statusId, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));

        try {
            ticketDAO.createTicket(newTicket);
            response.sendRedirect(request.getContextPath() + "/tickets"); // Redirect to list tickets page after creation
        } catch (SQLException e) {
            e.printStackTrace();
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
            response.sendRedirect(request.getContextPath() + "/tickets"); // Redirect to list tickets page after update
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el ticket: " + e.getMessage());
        }
    }

    protected void deleteTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        try {
            ticketDAO.deleteTicket(ticketId);
            response.sendRedirect(request.getContextPath() + "/tickets"); // Redirect to list tickets page after delete
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al eliminar el ticket: " + e.getMessage());
        }
    }
}