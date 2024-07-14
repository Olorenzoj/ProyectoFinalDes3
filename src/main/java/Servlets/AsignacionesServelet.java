package Servlets;

import DAO.AsignacionesDAO;
import Modelos.Asignaciones;
import ConexionDB.ConexionDB;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@WebServlet("/asignaciones")
public class AsignacionesServelet extends HttpServlet {
    private AsignacionesDAO asignacionesDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConexionDB.getConnection();
            asignacionesDAO = new AsignacionesDAO(connection);
        } catch (SQLException e) {
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
                    listAsignaciones(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listAsignaciones(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Asignaciones> listAsignaciones = asignacionesDAO.allAsignaciones();
        request.setAttribute("listAsignaciones", listAsignaciones);
        request.getRequestDispatcher("asignacion-list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("asignacion-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Asignaciones existingAsignacion = asignacionesDAO.getAsignacion(id);
        request.setAttribute("asignacion", existingAsignacion);
        request.getRequestDispatcher("asignacion-form.jsp").forward(request, response);
    }

    private void insertAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        Date assignedAt = new Date();

        Asignaciones newAsignacion = new Asignaciones(0, ticketId, operatorId, assignedAt);
        asignacionesDAO.insertAsignaciones(newAsignacion);
        response.sendRedirect("asignaciones");
    }

    private void updateAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("assignmentId"));
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));
        int operatorId = Integer.parseInt(request.getParameter("operatorId"));
        Date assignedAt = new Date();

        Asignaciones asignacion = new Asignaciones(id, ticketId, operatorId, assignedAt);
        asignacionesDAO.updateAsignacion(asignacion);
        response.sendRedirect("asignaciones");
    }

    private void deleteAsignacion(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Asignaciones asignacion = new Asignaciones(id, 0, 0, null);
        asignacionesDAO.deleteAsignacion(asignacion);
        response.sendRedirect("asignaciones");
    }

    @Override
    public void destroy() {
        try {
            asignacionesDAO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
