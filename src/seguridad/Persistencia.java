package seguridad;

import config.Configuracion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que maneja la persistencia de los usuarios y sus contraseñas, 
 * incluyendo la verificación, almacenamiento y login de usuarios, 
 * además de la gestión de archivos cifrados.
 * <p>
 * La clase se asegura de que el archivo de usuarios esté correctamente cifrado
 * y descifrado según sea necesario, y realiza las operaciones de búsqueda, 
 * escritura y autenticación de manera segura en entornos multihilo.
 * </p>
 * 
 * @author Antonio Álvarez Cárdenas
 */
public class Persistencia {

    /**
     * Flag que indica si hay un proceso de escritura en curso.
     * Se utiliza para evitar que otros hilos escriban en el archivo de usuarios simultáneamente.
     */
    private static boolean escribiendo = false;

    /**
     * Objeto de bloqueo utilizado para sincronizar el acceso a los métodos que operan sobre el archivo de usuarios.
     */
    private static final Object lock = new Object();

    /**
     * Busca un usuario en el archivo de usuarios.
     * <p>
     * Si el archivo está cifrado, lo descifra antes de realizar la búsqueda.
     * </p>
     * 
     * @param usuario El nombre de usuario a buscar.
     * @return true si el usuario se encuentra en el archivo, false en caso contrario.
     * @throws Exception Si ocurre un error de entrada/salida o un error al descifrar el archivo.
     */
    private static boolean buscarUsuario(String usuario) throws Exception {

        boolean encontrado = false;
        String salida = "";
        String[] datos;

        if (Cifradora.isCifrado()) {
            Cifradora.descifrarArchivo();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(Configuracion.getRutaArchivoUsuarios()))) {

            while ((salida = reader.readLine()) != null && !encontrado) {
                datos = salida.split(":");
                if (datos[0].equals(usuario)) {
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
        } finally {
            if (!Cifradora.isCifrado()) {
                Cifradora.cifrarArchivo();
            }
        }
        return encontrado;
    }

    /**
     * Guarda un nuevo usuario y su contraseña en el archivo de usuarios.
     * <p>
     * Si el archivo está cifrado, se descifra antes de escribir y se vuelve a cifrar una vez completada la escritura.
     * </p>
     * 
     * @param usuario El nombre de usuario a guardar.
     * @param contrasena La contraseña asociada al usuario.
     * @return true si el usuario fue guardado exitosamente, false en caso contrario.
     * @throws Exception Si ocurre un error de entrada/salida o al manejar el cifrado del archivo.
     */
    public static boolean guardarUsuario(String usuario, String contrasena) throws Exception {
        synchronized (lock) {

            boolean guardado = false;

            if (!buscarUsuario(usuario)) {
                escribiendo = true;
                if (Cifradora.isCifrado()) {
                    Cifradora.descifrarArchivo();
                }
                try (PrintWriter writer = new PrintWriter(new FileWriter(Configuracion.getRutaArchivoUsuarios(), true))) {

                    writer.println(String.format("%s:%s", usuario, Cifradora.cifrarPassword(contrasena)));
                    guardado = true;

                } catch (IOException e) {
                    String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
                    int lineaError = new Exception().getStackTrace()[0].getLineNumber();
                    Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
                } finally {
                    if (!Cifradora.isCifrado()) {
                        Cifradora.cifrarArchivo();
                    }
                    escribiendo = false;
                    lock.notifyAll();
                }
            }
            return guardado;
        }
    }

    /**
     * Verifica el login de un usuario comprobando si su nombre de usuario y contraseña coinciden
     * con los almacenados en el archivo de usuarios.
     * <p>
     * Si el archivo está cifrado, se descifra antes de realizar la comprobación.
     * </p>
     * 
     * @param usuario El nombre de usuario a verificar.
     * @param contrasena La contraseña asociada al usuario.
     * @return true si las credenciales coinciden, false en caso contrario.
     * @throws Exception Si ocurre un error de entrada/salida o al manejar el cifrado del archivo.
     */
    public static boolean loginUsuario(String usuario, String contrasena) throws Exception {

            while (escribiendo) {
                Thread.sleep(1000); // espera hasta que escribiendo sea false
            }
            boolean encontrado = false;
            String salida = "";
            String[] datos;

            if (Cifradora.isCifrado()) {
                Cifradora.descifrarArchivo();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(Configuracion.getRutaArchivoUsuarios()))) {

                while ((salida = reader.readLine()) != null && !encontrado) {
                    datos = salida.split(":");
                    if (datos[0].equals(usuario)) {
                        if (Cifradora.comprobarPassword(contrasena, datos[1])) {
                            encontrado = true;
                        }
                    }
                }
            } catch (IOException e) {
                String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
                int lineaError = new Exception().getStackTrace()[0].getLineNumber();
                Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
            } finally {
                if (!Cifradora.isCifrado()) {
                    Cifradora.cifrarArchivo();
                }
            }
            return encontrado;
        
    }
}
