package config;

import java.util.logging.Logger;

/**
 * Clase de configuración centralizada para parámetros del servidor.
 *
 * <p>
 * Esta clase proporciona acceso a constantes utilizadas en la configuración de
 * un servidor, como el puerto de conexión, el algoritmo de cifrado, las rutas
 * de archivos, configuraciones SSL, y el formato de fecha y hora.
 * </p>
 *
 * <p>
 * Todos los valores están definidos como constantes privadas y son accesibles a
 * través de métodos públicos estáticos, lo que permite mantener un punto único
 * y seguro para modificar parámetros clave del sistema.
 * </p>
 *
 * <p>
 * También proporciona una instancia global de {@link Logger} para el registro
 * de eventos del sistema, así como detalles sobre el algoritmo y clave de
 * cifrado utilizados en operaciones de seguridad.
 * </p>
 *
 * <p>
 * Esta clase no puede ser instanciada.
 * </p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class Configuracion {

// Parámetros del servidor
    private static final int PUERTO = Integer.parseInt(System.getenv("PORT"));;

// Parámetros del logger
    private static final String LOGGER_NOMBRE = "MiLog";
    private static final String RUTA_LOG = "logErrores.log";
    private static final Logger LOGGER = Logger.getLogger(LOGGER_NOMBRE);

// Parámetros de usuarios y datos
    private static final String ARCHIVO_DE_DATOS_USUARIOS = "usuarios.txt";

// Parámetros de cifrado
    private static final String ALGORITMO = "AES";
    private static final String KEY = "1234567890123456";

// Formatos de fecha y hora
    private static final String FORMATO_LOG = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMATO_SERVIDOR = "dd-MM-yyyy HH:mm";

// Parámetros del almacén SSL (Keystore)
    private static final String RUTA_ALMACEN_SSL = "AlmacenSSL";
    private static final String PASS_ALMACEN_SSL = "123456";
    private static final String TIPO_ALMACEN = "JKS";
    private static final String ALGORITMO_ALMACEN_SSL = "SunX509";
    private static final String PROTOCOLO_ALMACEN_SSL = "TLS";

    /**
     * Obtiene el puerto TCP en el que escucha el servidor.
     *
     * @return número de puerto del servidor
     */
    public static int getPuerto() {
        return Configuracion.PUERTO;
    }

    /**
     * Devuelve la instancia de {@link Logger} utilizada para el sistema.
     *
     * @return instancia global de Logger
     */
    public static Logger getLog() {
        return Configuracion.LOGGER;
    }

    /**
     * Devuelve la ruta al archivo de texto donde se almacenan los datos de
     * usuarios.
     *
     * @return ruta del archivo de usuarios
     */
    public static String getRutaArchivoUsuarios() {
        return Configuracion.ARCHIVO_DE_DATOS_USUARIOS;
    }

    /**
     * Devuelve el nombre del algoritmo de cifrado utilizado para proteger los
     * datos.
     *
     * @return nombre del algoritmo de cifrado (por ejemplo, "AES")
     */
    public static String getAlgoritmo() {
        return Configuracion.ALGORITMO;
    }

    /**
     * Devuelve la clave utilizada para las operaciones de cifrado y descifrado.
     *
     * @return clave de cifrado
     */
    public static String getClaveCifrado() {
        return Configuracion.KEY;
    }

    /**
     * Devuelve la ruta del archivo de log donde se registran errores y eventos.
     *
     * @return ruta del archivo de log
     */
    public static String getRutaLog() {
        return Configuracion.RUTA_LOG;
    }

    /**
     * Devuelve el formato de fecha y hora utilizado en los registros de log.
     *
     * @return patrón de formato de fecha y hora para logs
     */
    public static String getFormatoFechaHoraLog() {
        return Configuracion.FORMATO_LOG;
    }

    /**
     * Devuelve el formato de fecha y hora utilizado por el servidor para
     * mostrar información.
     *
     * @return patrón de formato de fecha y hora para el servidor
     */
    public static String getFormatoFechaHoraServidor() {
        return Configuracion.FORMATO_SERVIDOR;
    }

    /**
     * Devuelve la ruta del almacén SSL (keystore) usado para conexiones
     * seguras.
     *
     * @return ruta del archivo del almacén SSL
     */
    public static String getRutaAlmacenSSL() {
        return Configuracion.RUTA_ALMACEN_SSL;
    }

    /**
     * Devuelve la contraseña del almacén SSL.
     *
     * @return contraseña del keystore SSL
     */
    public static String getPasswordAlmacenSSL() {
        return Configuracion.PASS_ALMACEN_SSL;
    }

    /**
     * Devuelve el tipo del almacén SSL utilizado.
     *
     * @return tipo de almacén SSL (por ejemplo, "JKS")
     */
    public static String getTipoAlmacen() {
        return Configuracion.TIPO_ALMACEN;
    }

    /**
     * Devuelve el algoritmo del gestor de claves utilizado por el almacén SSL.
     *
     * @return algoritmo del almacén SSL (por ejemplo, "SunX509")
     */
    public static String getAlgoritmoAlmacenSSL() {
        return Configuracion.ALGORITMO_ALMACEN_SSL;
    }

    /**
     * Devuelve el protocolo SSL utilizado para las conexiones seguras.
     *
     * @return protocolo SSL (por ejemplo, "TLS")
     */
    public static String getProtocoloAlmacenSSL() {
        return Configuracion.PROTOCOLO_ALMACEN_SSL;
    }

}
