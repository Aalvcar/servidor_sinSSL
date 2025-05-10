package paginas;

/**
 * Clase que representa la página HTML del juego "Piedra, Papel o Tijera".
 *
 * <p>Esta clase genera dinámicamente el contenido HTML necesario para que los usuarios puedan jugar
 * una partida interactiva de Piedra, Papel o Tijera contra la banca. Está diseñada como una página web
 * simple, con estilos CSS incluidos y botones para realizar las jugadas.</p>
 *
 * <p>Características principales del HTML generado: </p>
 * <ul>
 *   <li>Diseño responsivo y centrado en pantalla, con estilo moderno y minimalista.</li>
 *   <li>Botones que permiten al usuario seleccionar una opción: Piedra, Papel o Tijera.</li>
 *   <li>Sección para mostrar el resultado del turno actual (elección del jugador y de la banca).</li>
 *   <li>Marcador de puntuación actualizado dinámicamente con los puntos del jugador y la banca.</li>
 * </ul>
 *
 *
 * <p>Los valores dinámicos como el resultado de las jugadas y el marcador se insertan en los comentarios
 * HTML como marcadores (ej. <code>&lt;!--RESULTADO_JUG--&gt;</code>) para ser reemplazados en tiempo de ejecución
 * por el servidor o controlador correspondiente.</p>
 *
 * <p>La clase es estática y no requiere ser instanciada.</p>
 * 
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaPPT {

    /**
     * Cadena HTML que representa la interfaz del juego "Piedra, Papel o Tijera".
     * Incluye estilos, botones, marcador y campos de resultado.
     */
    private static final String PPT = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <title>Piedra, Papel o Tijera</title>\n"
            + "    <style>\n"
            + "        body {\n"
            + "            font-family: Arial, sans-serif;\n"
            + "            text-align: center;\n"
            + "            background-color: #f4f4f4;\n"
            + "            padding: 20px;\n"
            + "        }\n"
            + "        .container {\n"
            + "            background: white;\n"
            + "            padding: 20px;\n"
            + "            border-radius: 10px;\n"
            + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n"
            + "            display: inline-block;\n"
            + "        }\n"
            + "        button {\n"
            + "            padding: 10px 20px;\n"
            + "            font-size: 16px;\n"
            + "            background-color: #007bff;\n"
            + "            color: white;\n"
            + "            border: none;\n"
            + "            cursor: pointer;\n"
            + "            border-radius: 5px;\n"
            + "            margin: 5px;\n"
            + "        }\n"
            + "        button:hover {\n"
            + "            background-color: #0056b3;\n"
            + "        }\n"
            + "        .result {\n"
            + "            margin-top: 20px;\n"
            + "            font-size: 18px;\n"
            + "        }\n"
            + "        .scoreboard {\n"
            + "            margin-top: 20px;\n"
            + "            display: flex;\n"
            + "            justify-content: space-around;\n"
            + "            font-size: 18px;\n"
            + "        }\n"
            + "        .scoreboard div {\n"
            + "            background: #fff;\n"
            + "            padding: 10px;\n"
            + "            border-radius: 5px;\n"
            + "            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);\n"
            + "        }\n"
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"container\">\n"
            + "        <h1>Piedra, Papel o Tijera</h1>\n"
            + "        <form action=\"/./ppt\" method=\"POST\">\n"
            + "            <button type=\"submit\" name=\"opcion\" value=\"0\">Piedra</button><button type=\"submit\" name=\"opcion\" value=\"1\">Papel</button><button type=\"submit\" name=\"opcion\" value=\"2\">Tijera</button>"
            + "        </form>\n"
            + "        <div class=\"result\">\n"
            + "            <p>Has elegido: <!--RESULTADO_JUG--></p>\n"
            + "            <p>La banca elige: <!--RESULTADO_BANCA--></p>\n<!--RESULTADO-->"
            + "        </div>\n"
            + "        <div class=\"scoreboard\">\n"
            + "            <div>Jugador: <!--puntosJug--></div>\n"
            + "            <div>Banca: <!--puntosBanca--></div>\n"
            + "        </div>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve la página HTML del juego "Piedra, Papel o Tijera".
     *
     * @return el HTML como una cadena de texto que representa la interfaz del juego
     */
    public static String getPagina(){
        return PaginaPPT.PPT;
    }
}
