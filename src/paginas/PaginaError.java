package paginas;

/**
 * Clase que representa una página HTML para errores de tipo 400 o 404.
 * 
 * <p>Esta página se muestra cuando el usuario accede a una ruta no válida o realiza
 * una solicitud incorrecta. Presenta un mensaje amigable con estilos definidos,
 * informando al usuario que la página no fue encontrada.</p>
 * 
 * <p>El contenido HTML está embebido como una constante estática, e incluye: </p>
 * <ul>
 *   <li>Título descriptivo de error.</li>
 *   <li>Mensaje informativo.</li>
 *   <li>Estilos CSS para presentación agradable.</li>
 *   <li>Un enlace para regresar a la página principal.</li>
 * </ul>
 *
 * 
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaError {

    /**
     * HTML de la página de error.
     * 
     * Representa una estructura visual clara para indicar al usuario que la
     * página solicitada no fue encontrada o que hubo un error de solicitud.
     */
    private static final String PAGINA_ERROR = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <title>Error 404 - Página No Encontrada</title>\n"
            + "    <style>\n"
            + "        body {\n"
            + "            font-family: Arial, sans-serif;\n"
            + "            text-align: center;\n"
            + "            background-color: #f8d7da;\n"
            + "            color: #721c24;\n"
            + "            padding: 50px;\n"
            + "        }\n"
            + "        .container {\n"
            + "            max-width: 600px;\n"
            + "            margin: auto;\n"
            + "            background: white;\n"
            + "            padding: 20px;\n"
            + "            border-radius: 10px;\n"
            + "            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);\n"
            + "        }\n"
            + "        h1 {\n"
            + "            font-size: 50px;\n"
            + "        }\n"
            + "        p {\n"
            + "            font-size: 18px;\n"
            + "        }\n"
            + "        a {\n"
            + "            color: #721c24;\n"
            + "            text-decoration: none;\n"
            + "            font-weight: bold;\n"
            + "        }\n"
            + "        a:hover {\n"
            + "            text-decoration: underline;\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"container\">\n"
            + "        <h1>400</h1>\n"
            + "        <h2>¡Oops! Página no encontrada</h2>\n"
            + "        <p>La página que estás buscando no existe o la solicitud es incorrecta.</p>\n"
            + "        <p><a href=\"/\">Volver a la página principal</a></p>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve la página HTML de error.
     * 
     * @return el contenido HTML para mostrar un error 400 o 404
     */
    public static String getPagina(){
        return PaginaError.PAGINA_ERROR;
    }

}
