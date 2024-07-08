import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PRUEBAHASH {

    // Método para hashear la contraseña utilizando SHA-256
    public static String hashPassword(String password) {
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

    // Método para verificar la contraseña comparando el hash de la contraseña original con el hash almacenado
    public static boolean verifyPassword(String originalPassword, String storedHash) {
        // Hashear la contraseña original proporcionada por el usuario
        String hashedPassword = hashPassword(originalPassword);
        // Comparar el hash calculado con el hash almacenado
        return hashedPassword.equals(storedHash);
    }

    public static void main(String[] args) {
        // Contraseña a ser hasheada
        String password = "miContraseñaSegura";
        // Calcular el hash de la contraseña
        String hashedPassword = hashPassword(password);
        System.out.println("Hashed password: " + hashedPassword);

        // Verificar si la contraseña proporcionada coincide con el hash almacenado
        boolean isPasswordCorrect = verifyPassword("miContraseñaSegura", hashedPassword);
        System.out.println("Is password correct: " + isPasswordCorrect);

        // Intentar verificar con una contraseña incorrecta
        boolean isPasswordIncorrect = verifyPassword("contraseñaIncorrecta", hashedPassword);
        System.out.println("Is password correct with incorrect password: " + isPasswordIncorrect);
    }
}
