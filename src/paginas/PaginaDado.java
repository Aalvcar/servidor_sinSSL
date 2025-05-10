package paginas;

/**
 * Clase que representa la página HTML del juego "Tira los Dados".
 * 
 * <p>Esta clase genera una página HTML con estilo que permite a un usuario
 * lanzar dados contra la banca. La página incluye un botón de envío y espacio
 * reservado para mostrar resultados y puntuaciones.</p>
 * 
 * <p>El HTML contiene varios marcadores que pueden ser sustituidos
 * dinámicamente por resultados del juego:</p>
 * <ul>
 *   <li>{@code <!--RESULTADO_JUG-->}: Resultado del lanzamiento del jugador.</li>
 *   <li>{@code <!--RESULTADO_BANCA-->}: Resultado del lanzamiento de la banca.</li>
 *   <li>{@code <!--RESULTADO-->}: Resultado final (ganador, empate, etc.).</li>
 *   <li>{@code <!--puntosJug-->}: Puntos acumulados del jugador.</li>
 *   <li>{@code <!--puntosBanca-->}: Puntos acumulados de la banca.</li>
 * </ul>
 * 
 * <p>El formulario se envía mediante método {@code POST} a la ruta
 * {@code /./dados}.</p>
 * 
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaDado {

    /**
     * Página HTML base del juego "Tira los Dados".
     * 
     * Incluye estructura básica con estilos embebidos, un formulario con un botón 
     * de envío y contenedores donde se mostrarán los resultados de las tiradas 
     * y el marcador de puntos de jugador y banca.
     */
    private static final String DADOS = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <title>Tira los Dados</title>\n"
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
            + "        <h1>Tira los Dados</h1>\n"
            + "        <form action=\"/./dados\" method=\"POST\">\n"
            + "            <button type=\"submit\">Lanzar</button>\n"
            + "        </form>\n"
            + "        <div class=\"result\">\n"
            + "            <!--RESULTADO_JUG-->\n"
            + "            <!--RESULTADO_BANCA-->\n"
            + "            <!--RESULTADO-->\n"
            + "        </div>\n"
            + "        <div class=\"scoreboard\">\n"
            + "            <div>Jugador: <!--puntosJug--></div>\n"
            + "            <div>Banca: <!--puntosBanca--></div>\n"
            + "        </div>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve el HTML base de la página "Tira los Dados".
     * 
     * @return una cadena que representa la estructura HTML de la página
     */
    public static String getPagina() {
        return PaginaDado.DADOS;
    }
}
