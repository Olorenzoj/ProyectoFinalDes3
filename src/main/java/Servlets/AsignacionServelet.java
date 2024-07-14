package Servlets;

import DAO.AsignacionDAO;
import Modelos.Asignacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/asignaciones")
public class AsignacionServelet extends HttpServlet {
    private AsignacionDAO asignacionDAO;

    @Override
    public void init() throws ServletException {
        String jdbcURL = "jdbc:mysql://localhost:3306/your_database"; // Reemplaza 'your_database' con tu nombre de base de datos
        String jdbcUsername = "root";
        String jdbcPassword = "password";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            asignacionDAO = new AsignacionDAO(connection);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertAsignacion(request, response);
                    break;
                case "delete":
                    deleteAsignacion(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateAsignacion(request, response);
                    break;
                default:
                    listAsignacion(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Asignacion> listAsignacion = asignacionDAO.allAsignacion();
        request.setAttribute("listAsignacion", listAsignacion);
        request.getRequestDispatcher("asignacion-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("asignacion-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Asignacion existingAsignacion = asignacionDAO.getAsignacion(id);
        request.setAttribute("asignacion", existingAsignacion);
        request.getRequestDispatcher("asignacion-form.jsp").forward(request, response);
    }

    private void insertAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        Date assignedAt = new Date();

        Asignacion newAsignacion = new Asignacion(0, ticketId, operatorId, assignedAt);
        asignacionDAO.insertAsignacion(newAsignacion);
        response.sendRedirect("asignaciones");
    }

    private void updateAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        Date assignedAt = new Date();

        Asignacion asignacion = new Asignacion(id, ticketId, operatorId, assignedAt);
        asignacionDAO.updateAsignacion(asignacion);
        response.sendRedirect("asignaciones");
    }

    private void deleteAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Asignacion asignacion = new Asignacion(id, 0, 0, null);
        asignacionDAO.deleteAsignacion(asignacion);
        response.sendRedirect("asignaciones");
    }
}


