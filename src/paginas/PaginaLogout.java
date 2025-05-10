package paginas;

/**
 * Clase que representa la página HTML del juego "Logout".
 *
 * <p>
 * Esta clase genera una página HTML simple y estilizada con un boton para que
 * el usuario pueda volver a la página principal.</p>
 *
 * <p>
 * La página está preformateada y contiene un boton para volver a login, y un
 * mensaje para informar que el logout se ha realizado correctamente.</p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaLogout {

    /**
     * HTML predefinido de la página "Logout".
     *
     * Contiene una estructura completa con un boton y estilo CSS embebido.
     */
    private static final String LOGOUT = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>Sesión cerrada</title>\n"
            + "    <style>\n"
            + "        body {\n"
            + "            font-family: Arial, sans-serif;\n"
            + "            background-color: #f2f2f2;\n"
            + "            display: flex;\n"
            + "            justify-content: center;\n"
            + "            align-items: center;\n"
            + "            height: 100vh;\n"
            + "            margin: 0;\n"
            + "        }\n"
            + "\n"
            + "        .mensaje {\n"
            + "            background-color: white;\n"
            + "            padding: 30px 50px;\n"
            + "            border-radius: 10px;\n"
            + "            box-shadow: 0 4px 8px rgba(0,0,0,0.1);\n"
            + "            text-align: center;\n"
            + "        }\n"
            + "\n"
            + "        .mensaje h1 {\n"
            + "            color: #333;\n"
            + "        }\n"
            + "\n"
            + "        .mensaje p {\n"
            + "            margin: 15px 0;\n"
            + "        }\n"
            + "\n"
            + "        .mensaje a {\n"
            + "            display: inline-block;\n"
            + "            margin-top: 10px;\n"
            + "            text-decoration: none;\n"
            + "            color: white;\n"
            + "            background-color: #007BFF;\n"
            + "            padding: 10px 20px;\n"
            + "            border-radius: 5px;\n"
            + "        }\n"
            + "\n"
            + "        .mensaje a:hover {\n"
            + "            background-color: #0056b3;\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"mensaje\">\n"
            + "        <h1>Has cerrado sesión correctamente</h1>\n"
            + "        <p>Gracias por visitar el casino Aguadulce, esperamos verte pronto.</p>\n"
            + "        <a href=\"/\">Volver al inicio</a>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve el HTML completo de la página "Logout", para volver a la pagina
     * de inicio e informar de logout exitoso.
     *
     * @return una cadena con el contenido HTML de la página
     */
    public static String getPagina() {
        return PaginaLogout.LOGOUT;
    }
}
