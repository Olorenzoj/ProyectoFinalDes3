package DAO;

import Modelos.Usuario;
import ConexionDB.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() throws SQLException {
        connection = ConexionDB.getConnection();
    }

    // Hashear contraseña
    public String hashPassword(String password) {
        try {
            // Crear una instancia del algoritmo de hashing SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Calcular el hash de la contraseña
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Convertir el hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            // Devolver el hash en formato hexadecimal
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción en caso de que el algoritmo SHA-256 no esté disponible
            throw new RuntimeException(e);
        }
    }

    public void insertUser(Usuario newUser) throws SQLException {
        String query = "INSERT INTO Usuarios (username, password_hash, email, role_id, created_at, updated_at) " +
                "VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPasswordHash());
            statement.setString(3, newUser.getEmail());
            statement.setInt(4, newUser.getRoleId());
            statement.setDate(5, newUser.getCreatedAt());
            statement.setDate(6, newUser.getUpdatedAt());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al ingresar nuevo usuario a la bd: " + e.getMessage());
            throw e; // Propagar la excepción para que sea manejada fuera del método
        }
    }

    public static void main(String[] args) throws IOException {
        // Prueba para verificar si funciona el método creado y si se hashean las contraseñas en la base de datos
        String userName, contra, email, passwordHash;
        int roleid;
        Date createdAt, updatedAt;
        System.out.println("Prueba de base de datos:");
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingrese los datos del usuario: ");
        System.out.println("Nombre:");
        userName = rd.readLine();
        System.out.println("Contraseña:");
        contra = rd.readLine();
        System.out.println("Correo electrónico:");
        email = rd.readLine();
        System.out.println("Rol:");
        roleid = 3;
        Date fecha = new Date(System.currentTimeMillis());
        createdAt = fecha;
        updatedAt = fecha;

        // Crear una instancia de UsuarioDAO y hashear la contraseña
        UsuarioDAO usuarioDAO;
        try {
            usuarioDAO = new UsuarioDAO();
            passwordHash = usuarioDAO.hashPassword(contra);
            Usuario newUser = new Usuario(userName, passwordHash, email, roleid, createdAt, updatedAt);

            // Insertar el nuevo usuario en la base de datos
            try {
                usuarioDAO.insertUser(newUser);
                System.out.println("Usuario insertado correctamente con ID: " + newUser.getUserId());
            } catch (SQLException e) {
                System.err.println("Error al insertar usuario: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión a la base de datos: " + e.getMessage());
        }
    }
}
