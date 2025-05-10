package paginas;

import config.Configuracion;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase que genera la página de inicio del sitio web "Gran Casino Aguadulce".
 *
 * <p>
 * Contiene una plantilla HTML embebida que actúa como punto de entrada
 * principal a los juegos disponibles del casino. Está diseñada con estilos CSS
 * integrados para una presentación atractiva e incluye enlaces a los distintos
 * juegos del sistema.</p>
 *
 * <p>
 * Componentes destacados del HTML:</p>
 * <ul>
 * <li>Título y subtítulo llamativos con efectos visuales.</li>
 * <li>Pestañas para seleccionar entre juegos como "Adivinar el número", "Tira
 * Dados" y "Piedra, Papel o Tijera".</li>
 * <li>Eslogan promocional en una sección destacada.</li>
 * <li>Pie de página con información de contacto del autor, institución
 * educativa, fecha y hora generadas dinámicamente y mensajes de juego
 * responsable.</li>
 * </ul>
 *
 *
 * <p>
 * La fecha y hora se obtienen dinámicamente mediante {@link LocalDateTime} y
 * formateadas según el formato definido en
 * {@code config.Configuracion.getFormatoFechaHoraServidor()}.</p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaIndex {

    /**
     * Contenido HTML de la página principal del casino. Se construye como una
     * constante de cadena multilínea y se inserta la hora actual al pie de
     * página.
     */
    private static final String INDEX = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <title>Gran Casino Aguadulce</title>\n"
            + "    <style>\n"
            + "        html, body {\n"
            + "            height: 100%;\n"
            + "            margin: 0;\n"
            + "            padding: 0;\n"
            + "            display: flex;\n"
            + "            flex-direction: column;\n"
            + "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n"
            + "            background: linear-gradient(to bottom, #e8e8f0, #fefefe);\n"
            + "            color: #333;\n"
            + "        }\n"
            + "\n"
            + "        .main-content {\n"
            + "            flex: 1;\n"
            + "        }\n"
            + "\n"
            + "        .header {\n"
            + "            background-color: #3f368d;\n"
            + "            padding: 20px;\n"
            + "            display: flex;\n"
            + "            justify-content: space-between;\n"
            + "            align-items: center;\n"
            + "        }\n"
            + "\n"
            + "        .header h1 {\n"
            + "            color: #FFD700;\n"
            + "            font-size: 28px;\n"
            + "            margin: 0;\n"
            + "            text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);\n"
            + "        }\n"
            + "\n"
            + "        .logout-btn {\n"
            + "            background-color: #28a745;\n"
            + "            color: white;\n"
            + "            padding: 10px 16px;\n"
            + "            text-decoration: none;\n"
            + "            border-radius: 5px;\n"
            + "            font-weight: bold;\n"
            + "            transition: background-color 0.3s;\n"
            + "        }\n"
            + "\n"
            + "        .logout-btn:hover {\n"
            + "            background-color: #218838;\n"
            + "        }\n"
            + "\n"
            + "        h2 {\n"
            + "            text-align: center;\n"
            + "            margin-top: 30px;\n"
            + "        }\n"
            + "\n"
            + "        .tabs {\n"
            + "            display: flex;\n"
            + "            justify-content: center;\n"
            + "            margin: 30px 0;\n"
            + "            flex-wrap: wrap;\n"
            + "            gap: 15px;\n"
            + "        }\n"
            + "\n"
            + "        .tab {\n"
            + "            background-color: #3f368d;\n"
            + "            color: white;\n"
            + "            padding: 12px 24px;\n"
            + "            border-radius: 8px;\n"
            + "            transition: background-color 0.3s;\n"
            + "        }\n"
            + "\n"
            + "        .tab a {\n"
            + "            text-decoration: none;\n"
            + "            color: inherit;\n"
            + "            font-size: 16px;\n"
            + "            font-weight: bold;\n"
            + "        }\n"
            + "\n"
            + "        .tab:hover {\n"
            + "            background-color: #FFA500;\n"
            + "        }\n"
            + "\n"
            + "        .eslogan {\n"
            + "            text-align: center;\n"
            + "            background-color: #af4c4c;\n"
            + "            color: white;\n"
            + "            padding: 40px 20px;\n"
            + "        }\n"
            + "\n"
            + "        .eslogan h3 {\n"
            + "            margin: 0;\n"
            + "            font-size: 22px;\n"
            + "        }\n"
            + "\n"
            + "        .eslogan h2 {\n"
            + "            font-size: 30px;\n"
            + "            margin: 10px 0 0;\n"
            + "        }\n"
            + "\n"
            + "        footer {\n"
            + "            background-color: #333;\n"
            + "            color: #fff;\n"
            + "            padding: 20px 30px;\n"
            + "            text-align: center;\n"
            + "            font-size: 14px;\n"
            + "        }\n"
            + "\n"
            + "        footer a {\n"
            + "            color: #FFA500;\n"
            + "            text-decoration: none;\n"
            + "        }\n"
            + "\n"
            + "        footer a:hover {\n"
            + "            color: #af4c4c;\n"
            + "            text-decoration: underline;\n"
            + "        }\n"
            + "\n"
            + "        footer p, footer h6 {\n"
            + "            margin: 4px 0;\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"main-content\">\n"
            + "        <div class=\"header\">\n"
            + "            <h1>¡Bienvenido al Gran Casino Aguadulce!</h1>\n"
            + "            <a class=\"logout-btn\" href=\"./logout\">Cerrar sesión</a>\n"
            + "        </div>\n"
            + "\n"
            + "        <h2>Selecciona el juego:</h2>\n"
            + "\n"
            + "        <div class=\"tabs\">\n"
            + "            <div class=\"tab\"><a href='./adivina'>Adivinar el número</a></div>\n"
            + "            <div class=\"tab\"><a href='./dados'>Tira Dados</a></div>\n"
            + "            <div class=\"tab\"><a href='./ppt'>Piedra, Papel o Tijera</a></div>\n"
            + "        </div>\n"
            + "\n"
            + "        <div class=\"eslogan\">\n"
            + "            <h3>Entra en el mejor casino de Aguadulce</h3>\n"
            + "            <h2><strong>¡Siempre toca!</strong></h2>\n"
            + "        </div>\n"
            + "    </div>\n"
            + "\n"
            + "    <footer>\n"
            + "        <p><a href=\"https://www.linkedin.com/in/antonio-%C3%A1lvarez-c%C3%A1rdenas-4652a5275/\">Antonio Álvarez Cárdenas</a></p>\n"
            + "        <p>Correo: <a href=\"mailto:neo_alvarez@hotmail.com\">neo_alvarez@hotmail.com</a></p>\n"
            + "        <p>IES. Aguadulce 2024/2025</p>\n"
            + "        <h6>*La comisión de juegos y deportes aconseja jugar con moderación.</h6>\n"
            + "        <h6>**Necesitas ser mayor de 18 años para acceder.</h6>\n"
            + "    </footer>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve la página de inicio del casino en formato HTML.
     *
     * @return el HTML como una cadena que representa la página principal
     */
    public static String getPagina() {
        return PaginaIndex.INDEX;
    }

}
