package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAO.UsuarioDAO;
import Modelos.Usuario;
import ConexionDB.ConexionDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import DAO.UsuarioDAO;
import Modelos.Usuario;

@WebServlet("/usuarios")
public class UsuariosServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Verificar que el metodo está siendo llamado
        System.out.println("doPost method called");
        // Validación de entrada (ejemplo simplificado)
        if (!request.getParameterMap().containsKey("username") || !request.getParameterMap().containsKey("password") ||
                !request.getParameterMap().containsKey("email")) {
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
        } catch (Exception e) {
            // Manejo de excepciones (registrar el error, mostrar mensaje al usuario, etc.)
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al crear el usuario.");
        }
    }

}
