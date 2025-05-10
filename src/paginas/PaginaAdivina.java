package paginas;

/**
 * Clase que representa la página HTML del juego "Adivina el Número".
 * 
 * <p>Esta clase genera una página HTML simple y estilizada con un formulario
 * para que el usuario introduzca un número del 1 al 100. El formulario envía
 * los datos mediante el método POST a la ruta {@code /./adivina}.</p>
 * 
 * <p>La página está preformateada y contiene un marcador {@code <!--RESULTADO-->}
 * que puede ser sustituido dinámicamente para mostrar mensajes personalizados
 * como aciertos o errores.</p>
 * 
 * @author Antonio Álvarez Cárdenas
 */
public class PaginaAdivina {

    /**
     * HTML predefinido de la página "Adivina el Número".
     * 
     * Contiene una estructura completa con formulario y estilo CSS
     * embebido. El marcador {@code <!--RESULTADO-->} se puede reemplazar
     * dinámicamente para mostrar mensajes al usuario.
     */
    private static final String ADIVINA = "<!DOCTYPE html>\n"
            + "<html lang=\"es\">\n"
            + "<head>\n"
            + "    <meta charset=\"UTF-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
            + "    <title>Adivina el Número</title>\n"
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
            + "        input {\n"
            + "            padding: 10px;\n"
            + "            font-size: 16px;\n"
            + "            width: 100px;\n"
            + "            margin: 10px 0;\n"
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
            + "    </style>\n"
            + "</head>\n"
            + "<body>\n"
            + "    <div class=\"container\">\n"
            + "        <h1>Adivina el Número</h1>\n<!--RESULTADO-->"
            + "        <form action=\"/./adivina\" method=\"POST\">\n"
            + "            <input type=\"number\" name=\"numero\" min=\"1\" max=\"100\" required>\n"
            + "            <br>\n"
            + "            <button type=\"submit\">Enviar</button>\n"
            + "        </form>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";

    /**
     * Devuelve el HTML completo de la página "Adivina el Número".
     *
     * @return una cadena con el contenido HTML de la página
     */
    public static String getPagina() {
        return PaginaAdivina.ADIVINA;
    }
}
