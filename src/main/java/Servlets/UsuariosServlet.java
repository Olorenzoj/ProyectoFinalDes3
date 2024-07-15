package Servlets;

import ConexionDB.ConexionDB;
import DAO.UsuarioDAO;
import Modelos.Ticket;
import Modelos.Usuario;
import jakarta.servlet.Servlet;
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

@WebServlet(urlPatterns = {"/users", "/registro", "/logIn"})
public class UsuariosServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private String servletPath = null;


    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = null;
        try {
            connection = ConexionDB.getConnection();
            usuarioDAO = new UsuarioDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();

        if (servletPath.equals("/JSP/users")) {
            getUsers(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();
        switch (servletPath) {
            case "/registro" -> handleRegister(request, response);
            case "/login" -> handleLogin(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
        }
    }

    protected void getUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Usuario> usuarios = usuarioDAO.allUsers();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/JSP/RegistroDeUsuarios.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener la lista de tickets: " + e.getMessage());
        }
    }

    protected void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recopilación de datos del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Validación de datos (puedes agregar más validaciones según tus requisitos)
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            request.setAttribute("message", "Todos los campos son obligatorios.");
            request.setAttribute("messageType", "error");
            response.sendRedirect(request.getContextPath() + "/JSP/RegistroDeUsuarios.jsp");
            return;
        }

        try {
            int role = Integer.parseInt(request.getParameter("role"));
            String hashedPassword = usuarioDAO.hashPassword(password); // Hash de la contraseña
            Date fecha = new Date(System.currentTimeMillis());

            // Creación del objeto Usuario
            Usuario usuario = new Usuario(username, hashedPassword, email, role, fecha, fecha);

            // Registro del usuario en la base de datos
            usuarioDAO.insertUser(usuario);
            request.setAttribute("message", "Registro exitoso.");
            request.setAttribute("messageType", "success");
            response.sendRedirect(request.getContextPath() + "/JSP/RegistroDeUsuarios.jsp");
            return;
        } catch (NumberFormatException e) {
            // Captura de excepcion al convertir el role
            request.setAttribute("message", "Error en el formato del campo de role.");
            request.setAttribute("messageType", "error");
        } catch (SQLException e) {
            // Captura de errores de base de datos (ej. duplicación de usuario)
            e.printStackTrace();
            request.setAttribute("message", "Error al crear el usuario: " + e.getMessage());
            request.setAttribute("messageType", "error");
        }

        // En caso de error redirigir de vuelta al formulario
        response.sendRedirect(request.getContextPath() + "/JSP/RegistroDeUsuarios.jsp");
    }


    protected void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
        List<Usuario> userLogged = usuarioDAO.logIn(username, password);
        request.setAttribute("useLogged", userLogged);
        request.getRequestDispatcher("/index.jsp").forward(request, response);

    } catch (SQLException e) {
        response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "iNFO NO COMPATIBLE");
        throw new RuntimeException(e);
    }

}

}