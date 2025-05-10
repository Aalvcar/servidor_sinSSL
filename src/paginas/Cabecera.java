package paginas;

/**
 * Clase utilitaria para generar cabeceras HTTP para respuestas HTML.
 * <p>
 * Genera cabeceras HTTP comunes para respuestas 200 OK, 302 Found (redirección)
 * y 404 Not Found, incluyendo los encabezados necesarios para el contenido HTML
 * con codificación UTF-8 y cookies de sesión.
 * </p>
 *
 * <p>
 * Esta clase está diseñada para ser utilizada en un servidor web básico que
 * genera respuestas manualmente.
 * </p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class Cabecera {

    // Cabecera para indicar tipo de contenido HTML con codificación UTF-8
    private static final String PRIMERA_CABECERA = "Content-Type:text/html;charset=UTF-8";

    // Línea de estado para respuesta exitosa HTTP
    private static final String PETICION_OK = "HTTP/1.1 200 OK";

    // Línea de estado para respuesta HTTP no encontrada
    private static final String PETICION_NOT_FOUND = "HTTP/1.1 404 Not Found";

    // Línea de estado para respuesta HTTP de redirección
    private static final String PETICION_REDIRECCION = "HTTP/1.1 302 Found\nLocation: /\n";

    /**
     * Genera una cabecera HTTP completa adecuada para una respuesta HTML.
     * <p>
     * La cabecera incluirá el código de estado HTTP correspondiente (200, 302 o
     * 404), tipo de contenido como HTML en UTF-8, longitud del contenido
     * (excepto si es redirección), y una cookie de sesión con el ID
     * proporcionado.
     * </p>
     *
     * @param longitudHtml la longitud en bytes del contenido HTML que se va a
     * enviar. Se ignora si el código de estado es 302 (redirección).
     * @param tipoRespuesta el código de estado HTTP: 200 para éxito, 300 para
     * redirección, 400 para recurso no encontrado.
     * @param sessionID el identificador de sesión que se establecerá como
     * cookie.
     * @return una cadena de texto que representa la cabecera HTTP generada.
     */
    public static String generarCabecera(int longitudHtml, int tipoRespuesta, String sessionID) {
        StringBuilder salida = new StringBuilder();
        String cabecera = "";
        String cookie = "";
        int contentLength = longitudHtml;

        switch (tipoRespuesta) {
            case 200:
                cabecera = PETICION_OK;
                break;
            case 300:
                cabecera = PETICION_REDIRECCION;
                contentLength = 0;
                break;
            case 400:
                cabecera = PETICION_NOT_FOUND;
                break;
            default:
                cabecera = PETICION_NOT_FOUND; // Valor por defecto
                break;
        }

        if (sessionID.isEmpty()) {
            cookie = "Set-Cookie: sessionID=; Path=/; Max-Age=0;\n";
        } else {
            cookie = "Set-Cookie: sessionID=" + sessionID + "; Path=/;\n";
        }

        salida.append(cabecera).append("\n");
        salida.append(PRIMERA_CABECERA).append("\n");
        salida.append("Content-Length: ").append(contentLength).append("\n");
        salida.append(cookie);
        salida.append("\n"); // Línea vacía que separa cabecera del cuerpo

        return salida.toString();
    }
}
