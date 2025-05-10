package seguridad;

import config.Configuracion;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase encargada de cifrar y descifrar el archivo que contiene los datos de
 * usuarios utilizando el algoritmo AES. Los datos cifrados se almacenan en el
 * archivo y pueden ser descifrados posteriormente.
 *
 * <p>
 * Utiliza una clave 'secreta' predefinida para cifrarArchivo y descifrarArchivo
 * los datos. El archivo con los datos se lee y escribe en el archivo definido
 * por la constante ARCHIVO_DE_DATOS_USUARIOS.</p>
 *
 * <p>
 * El algoritmo utilizado es AES con una clave de 16 bytes (128 bits).</p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class Cifradora {

    private static boolean archivoCifrado = true;

    public static boolean isCifrado() {
        return Cifradora.archivoCifrado;
    }

    /**
     * Cifra el contenido del archivo usuarios.txt. Los datos cifrados
     * sobrescriben el archivo original. Devuelve un booleano para controlar si
     * está cifrado o no.
     *
     * @throws Exception Si ocurre un error durante el proceso de cifrado, tiene
     * varias excepciones
     */
    public static synchronized void cifrarArchivo() throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(Configuracion.getAlgoritmo());
            SecretKey clave = new SecretKeySpec(Configuracion.getClaveCifrado().getBytes(), Configuracion.getAlgoritmo());

            cipher.init(Cipher.ENCRYPT_MODE, clave);

            byte[] datosDescifrados = Files.readAllBytes(Paths.get(Configuracion.getRutaArchivoUsuarios()));

            byte[] datosCifrados = cipher.doFinal(datosDescifrados);
            Files.write(Paths.get(Configuracion.getRutaArchivoUsuarios()), datosCifrados);
            archivoCifrado = true;

        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
            archivoCifrado = false;
        }
    }

    /**
     * Descifra el contenido del archivo usuarios.txt. Los datos cifrados
     * sobrescriben el archivo original. Devuelve un booleano para controlar si
     * está cifrado o no.
     *
     * @throws Exception Si ocurre un error durante el proceso de descifrado
     */
    public static synchronized void descifrarArchivo() throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(Configuracion.getAlgoritmo());
            SecretKey clave = new SecretKeySpec(Configuracion.getClaveCifrado().getBytes(), Configuracion.getAlgoritmo());

            cipher.init(Cipher.DECRYPT_MODE, clave);

            byte[] datosCifrados = Files.readAllBytes(Paths.get(Configuracion.getRutaArchivoUsuarios()));

            byte[] datosDescifrados = cipher.doFinal(datosCifrados);

            Files.write(Paths.get(Configuracion.getRutaArchivoUsuarios()), datosDescifrados);
            archivoCifrado = false;

        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
            archivoCifrado = true;
        }
    }

    /**
     * Cifra una contraseña utilizando el algoritmo BCrypt con un factor de
     * complejidad 12.
     *
     * <p>
     * Genera automáticamente una sal (salt) aleatoria y devuelve el hash de la
     * contraseña. Si ocurre un error durante el proceso de cifrado, se devuelve
     * {@code null}.
     * </p>
     *
     * @param pass Contraseña en texto plano que se desea cifrar.
     * @return Cadena hasheada de la contraseña, o {@code null} si ocurre un
     * error.
     */
    public static String cifrarPassword(String pass) {
        try {
            return BCrypt.hashpw(pass, BCrypt.gensalt(12));
        } catch (Exception e) {
            String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
            return null;
        }
    }

    /**
     * Verifica si la contraseña introducida coincide con el hash almacenado.
     *
     * <p>
     * Utiliza la función {@code BCrypt.checkpw()} para comparar una contraseña
     * en texto plano con su versión hasheada. Si el hash tiene un formato
     * inválido o ocurre algún error durante la comparación, el método devuelve
     * {@code false}.
     * </p>
     *
     * @param pass Contraseña en texto plano introducida por el usuario.
     * @param hashed Contraseña hasheada previamente almacenada.
     * @return {@code true} si la contraseña coincide con el hash; {@code false}
     * en caso contrario o si ocurre un error en la verificación.
     */
    public static boolean comprobarPassword(String pass, String hashed) {
        try {
            return BCrypt.checkpw(pass, hashed);
        } catch (Exception e) {
            String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);

            return false;
        }
    }

}
