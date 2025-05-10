package seguridad;

import config.Configuracion;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Clase utilitaria encargada de configurar y gestionar el sistema de logging de
 * la aplicación.
 *
 * <p>
 * Este configurador se encarga de establecer el manejo de logs para la
 * aplicación, incluyendo la configuración de la ruta del archivo de logs, el
 * formato de los mensajes, y el filtro de los mensajes de log a un nivel de
 * severidad {@code WARNING} o superior. Además, desactiva la salida de los logs
 * por consola.
 * </p>
 *
 * <p>
 * El logger se obtiene a través del método
 * {@link config.Configuracion#getLog()} y la ruta del archivo de log se puede
 * configurar a través del método {@link config.Configuracion#getRutaLog()}.
 * </p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class Logueadora {

    /**
     * Configura el logger global de la aplicación para:
     * <ul>
     * <li>Guardar los mensajes de log en el archivo especificado en
     * {@code Configuracion.getRutaLog()}.</li>
     * <li>Aplicar un formato simple para los mensajes del log.</li>
     * <li>Desactivar la salida de log por consola.</li>
     * <li>Registrar únicamente mensajes de nivel {@code WARNING} o más
     * graves.</li>
     * </ul>
     *
     * @throws IOException Si ocurre un error al crear o acceder al archivo de
     * log.
     */
    public static void configurarLogger() throws IOException {
        // Obtener el logger a partir del nombre almacenado en Configuracion
        Logger logger = Configuracion.getLog();

        // Crear un handler para guardar los logs en un archivo
        FileHandler fh = new FileHandler(Configuracion.getRutaLog(), true);

        // Establecer un formato simple para los mensajes del log
        fh.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return String.format("%s%n", record.getMessage());
            }
        });

        // Configurar el logger: agregar handler, desactivar salida por consola, y establecer nivel
        logger.addHandler(fh);
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.WARNING);
    }

    /**
     * Registra un error en el archivo de log con un nivel de severidad
     * determinado. El tipo de error se determina por el parámetro
     * {@code nivel}.
     *
     * <p>
     * Dependiendo del nivel, el mensaje de log variará. Los niveles disponibles
     * son:</p>
     * <ul>
     * <li>0: Registro de inicio de sesión de un usuario.</li>
     * <li>1: Registro de un nuevo usuario registrado en el servidor.</li>
     * <li>4: Error en el juego (advertencia).</li>
     * <li>5: Error crítico en el juego (grave).</li>
     * </ul>
     *
     *
     * @param nivel El nivel de gravedad del mensaje de log. Los valores válidos
     * son 0, 1, 4, y 5.
     * @param ahora La fecha y hora actual en la que se genera el log.
     * @param juego El nombre del juego en caso de que haya un error relacionado
     * con el juego.
     * @param linea El número de línea donde ocurrió el error, si aplica.
     * @param valorRecibido El valor recibido o el dato relacionado con el
     * evento o error.
     */
    public static void grabarError(int nivel, String ahora, String juego, int linea, String valorRecibido) {
        String mensaje;

        switch (nivel) {
            case (0):
                mensaje = String.format("%s - El usuario %s ha logueado.\n", ahora, valorRecibido);
                Configuracion.getLog().log(Level.CONFIG, mensaje);
                break;
            case (1):
                mensaje = String.format("%s - El usuario %s se ha registrado en el Servidor.\n", ahora, valorRecibido);
                Configuracion.getLog().log(Level.INFO, mensaje);
                break;
            case (4):
                mensaje = String.format("%s - Error en el juego %s en la línea %d: El valor introducido de \"%s\" no es correcto.\n", ahora, juego, linea, valorRecibido);
                Configuracion.getLog().log(Level.WARNING, mensaje);
                break;
            case (5):
                mensaje = String.format("%s - ¡Error crítico en el %s!. Ha fallado el %s en la linea %d\n", ahora, juego, valorRecibido, linea);
                Configuracion.getLog().log(Level.SEVERE, mensaje);
                break;
        }
    }
}
