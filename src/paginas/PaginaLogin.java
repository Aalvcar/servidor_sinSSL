package paginas;

/**
 * Clase encargada de generar la página HTML del formulario de inicio de sesión.
 *
 * <p>Esta clase proporciona una interfaz visual para que los usuarios puedan iniciar sesión o crear una cuenta
 * dentro del sistema del "Gran Casino Aguadulce". Contiene estilos CSS integrados para una presentación clara y moderna,
 * y un formulario con campos para usuario y contraseña.</p>
 *
 * <p>Características principales del HTML generado:</p>
 * <ul>
 *   <li>Diseño centrado en pantalla con estilo limpio y profesional.</li>
 *   <li>Campos para introducir nombre de usuario y contraseña.</li>
 *   <li>Dos botones con acciones diferenciadas: "Entrar" e "Crear cuenta".</li>
 *   <li>Espacio reservado para mostrar mensajes de error centrados y en rojo.</li>
 * </ul>
 * 
 * 
 * <p>La clase es estática y no requiere instanciación. Su único método público devuelve la página como una cadena HTML.</p>
 * 
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaLogin {

    /**
     * Cadena HTML que representa la página de inicio de sesión.
     * Contiene un formulario con campos de entrada, botones y estilos integrados.
     */
    private static final String LOGIN = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <title>Inicio de Sesión</title>\n"
            + "    <style>\n"
            + "        body {\n"
            + "            font-family: Arial, sans-serif;\n"
            + "            background: #f2f2f2;\n"
            + "            display: flex;\n"
            + "            justify-content: center;\n"
            + "            align-items: center;\n"
            + "            height: 100vh;\n"
            + "        }\n"
            + "        .login-container {\n"
            + "            background: white;\n"
            + "            padding: 30px;\n"
            + "            border-radius: 10px;\n"
            + "            box-shadow: 0 0 10px rgba(0,0,0,0.1);\n"
            + "            width: 350px;\n"
            + "        }\n"
            + "        h2 {\n"
            + "            text-align: center;\n"
            + "            margin-bottom: 20px;\n"
            + "        }\n"
            + "        input[type=\"text\"],\n"
            + "        input[type=\"password\"] {\n"
            + "            width: 100%;\n"
            + "            margin: 10px 0 20px 0;\n"
            + "            height: 30px;\n"
            + "            border: 1px solid #ccc;\n"
            + "            border-radius: 5px;\n"
            + "        }\n"
            + "        .botones {\n"
            + "            display: flex;\n"
            + "            justify-content: space-between;\n"
            + "            gap: 10px;\n"
            + "        }\n"
            + "        .botones button {\n"
            + "            flex: 1;\n"
            + "            padding: 10px;\n"
            + "            background: #3f368d;\n"
            + "            color: white;\n"
            + "            border: none;\n"
            + "            border-radius: 5px;\n"
            + "            font-weight: bold;\n"
            + "            cursor: pointer;\n"
            + "        }\n"
            + "        .botones button:hover {\n"
            + "            background: #2e2b6b;\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"login-container\">\n"
            + "        <h2>Iniciar Sesión</h2>\n"
            + "        <form method=\"POST\" action=\"\">\n"
            + "            <input type=\"text\" name=\"user\" placeholder=\"Nombre de usuario\" required>\n"
            + "            <input type=\"password\" name=\"pass\" placeholder=\"Contraseña\" required>\n"
            + "            <div class=\"botones\">\n"
            + "                <button type=\"submit\" name=\"accion\" value=\"login\">Entrar</button>\n"
            + "                <button type=\"submit\" name=\"accion\" value=\"crear\">Crear cuenta</button>\n"
            + "            </div>\n"
            + "           <p style=\"color: red; text-align: center;\"><!--ERRORES--></p>\n"
            + "        </form>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve la página de inicio de sesión en formato HTML.
     *
     * @return el HTML como una cadena que representa el formulario de login
     */
    public static String getPagina() {
        return PaginaLogin.LOGIN;
    }
}
