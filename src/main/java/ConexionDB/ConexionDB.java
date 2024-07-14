package ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static Connection connection;public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = connectionStablish();
            //si no existe la conexion o esta cerrada, la creamos
        }
        return connection;
    }

    private static Connection connectionStablish() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=PseudoJira;TrustServerCertificate=true;user=sa;password=1234;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException a) {
            throw new SQLException("Error al cargar el driver: " + a.getMessage());
        }
    }



    public static void main(String[] args) {
        Connection connection = null;
        try {
            System.out.println("Intentando establecer la conexión a la base de datos...");
            connection = connectionStablish();
            System.out.println("Conexión establecida correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al establecer la conexión: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexión cerrada correctamente.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}

