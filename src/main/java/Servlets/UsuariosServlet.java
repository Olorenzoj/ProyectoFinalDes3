package Servlets;

import ConexionDB.ConexionDB;
import DAO.UsuarioDAO;
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

        switch (servletPath.toLowerCase()) {
            case "/users" -> getUsers(request, response);
            default -> response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
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

            // mensaje en consola para revisar que los datos fueron traidos correctamente
            if (usuarios != null && !usuarios.isEmpty()) {
                System.out.println("Fetched " + usuarios.size() + " users.");
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario);
                }
            } else {
                System.err.println("No users found.");
            }

            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener la lista de usuarios: " + e.getMessage());
        }
    }

    protected void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Validación de entrada (ejemplo simplificado)
        if (!request.getParameterMap().containsKey("username") || !request.getParameterMap().containsKey("password") || !request.getParameterMap().containsKey("email")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan campos obligatorios.");
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Date fecha = new Date(System.currentTimeMillis());
        try {
            int role = Integer.parseInt(request.getParameter("role"));
            String hashedPassword = usuarioDAO.hashPassword(password);

            Usuario usuario = new Usuario(username, hashedPassword, email, role, fecha, fecha);

// Insertar en la base de datos
            usuarioDAO.insertUser(usuario);

// Redireccionar en caso de éxito
            response.setContentType("text/html");
            PrintWriter printWriter = response.getWriter();
            printWriter.print("<html>");
            printWriter.print("<body>");
            printWriter.print("<h1 style=color: green> Registro Realizado exitosamente</h1>");
            printWriter.print("</body>");
            printWriter.print("</html>");
            printWriter.close();
        } catch (SQLException e) {
// Manejo de excepciones (registrar el error, mostrar mensaje al usuario, etc.)
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al crear el usuario.");
        }
    }

    protected void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletPath = request.getServletPath();
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