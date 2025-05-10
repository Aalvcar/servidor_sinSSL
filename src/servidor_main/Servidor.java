package servidor_main;

import config.Configuracion;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import paginas.*;
import seguridad.Cifradora;
import seguridad.Logueadora;
import utilidades.Comprobaciones;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import seguridad.Keystore;
import seguridad.Persistencia;

/**
 * Clase principal del servidor de juegos.
 *
 *
 * @author Antonio Álvarez Cárdenas
 * @version 2.0
 */
public class Servidor extends Thread {

    // private SSLSocket serverSocketSSL;
    private Socket s;

    private static int adivinaNumeroSecreto = 0;
    private static int adivinaIntentos = 0;

    private static int dadosIntentos = 1;
    private static int dadosPuntosJugador = 0;
    private static int dadosPuntosBanca = 0;
    private static boolean dadosAcabada = false;

    private static int pptOpcionElegida;
    private static int pptOpcionBanca;
    private static final String[] PTT_OPCIONES_DISPONIBLES = {"Piedra", "Papel", "Tijera"};
    private static int pptPuntosJugador = 0;
    private static int pptPuntosBanca = 0;
    private static int pptIntentos = 1;

    private static final ConcurrentHashMap<String, String> sesiones = new ConcurrentHashMap<>();
    private static String accion = "";
    private static String user = "";
    private static String pass = "";

    public Servidor(Socket socket) {
        this.s = socket;
    }

    // public Servidor(SSLSocket socket) {
    //   this.serverSocketSSL = socket;
    // }
    /**
     * Método principal que inicia el servidor de juegos y configura la
     * comunicación segura mediante SSL.
     *
     * Este método realiza las siguientes operaciones:
     * <ul>
     * <li>Inicia un servidor SSL utilizando un {@link SSLServerSocket} y
     * configura la conexión segura.</li>
     * <li>Configura el archivo de logs utilizando
     * {@link Logueadora#configurarLogger()}.</li>
     * <li>Verifica la existencia de un archivo de base de datos de usuarios y
     * lo crea si no existe, cifrando la información si es necesario.</li>
     * <li>Imprime información sobre el estado del servidor y el puerto donde
     * está escuchando.</li>
     * <li>Acepta conexiones de clientes mediante {@link SSLSocket} y crea un
     * hilo para cada conexión utilizando {@link Servidor}.</li>
     * </ul>
     *
     * En caso de que ocurra una excepción, el método captura el error y lo
     * registra en el archivo de logs.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este
     * método).
     * @throws Exception Si ocurre un error durante la configuración o ejecución
     * del servidor.
     */
    public static void main(String[] args) throws Exception {
        try {
            // Se obtiene el socket SSL para el servidor.
            ServerSocket serverSocket = new ServerSocket(Configuracion.getPuerto());
            System.out.println("Iniciando servidor de juegos...");

            // Configuración del archivo log.
            System.out.println("Configurando el archivo log...");
            Logueadora.configurarLogger(); // Inicializar logger al arrancar, todos tienen la misma instancia, sobre el mismo archivo, así nada más se crea un .lck

            // Si el archivo de datos no existe, se crea y cifra. Si existe, estará ya cifrado
            File datos = new File(Configuracion.getRutaArchivoUsuarios());
            if (!datos.exists()) {
                System.out.println("Creando y cifrando el archivo de datos..." +  Configuracion.getRutaArchivoUsuarios());
                datos.createNewFile();
                Cifradora.cifrarArchivo();
            }

            // Se informa sobre el estado del servidor.
            System.out.println("Servidor online: activo en el puerto " + Configuracion.getPuerto());
            //System.out.println("Visita https://localhost:" + Configuracion.getPuerto());

            // Se aceptan conexiones entrantes y se gestionan en hilos.
            while (true) {
                Socket s = serverSocket.accept();
                System.out.println("Cliente conectado desde: " + s.getInetAddress());

                // Aquí debes pasar el socket al hilo o clase que maneja la conexión
                new Thread(new Servidor(s)).start();
            }
        } catch (IOException e) {
            // En caso de error, se registra en el log con nivel 5.
            String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Servidor", lineaError, nombreMetodo);
        }
    }

    /**
     * Método que se ejecuta cuando el hilo del servidor comienza a atender a un
     * cliente.
     *
     * <p>
     * Este método contiene la lógica que gestiona la interacción con el
     * cliente.
     * </p>
     */
    @Override
    public void run() {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);

            String linea;
            String metodo = "";
            String url = "";
            String html = "";
            String mensaje = "";

            // Leer la primera línea para obtener el método (GET o POST) y la URL
            if ((linea = br.readLine()) != null) {
                System.out.println(linea);
                String[] metodoSplit = linea.split(" ");
                if (metodoSplit.length >= 0) {
                    metodo = metodoSplit[0]; // "GET" o "POST"
                    url = metodoSplit[1];    // Ruta de la petición
                }
            }

            int contentLength = 0; // Longitud del contenido en POST
            String sessionID = "";
            int tipoRespuesta = 400; // Por defecto es 404

            // Leer cabeceras
            while ((linea = br.readLine()) != null && !linea.isEmpty()) {
                System.out.println(linea); // Depuración
                if (linea.startsWith("Content-Length:")) {
                    contentLength = Integer.parseInt(linea.split(": ")[1]);
                } else if (linea.startsWith("Cookie: ")) {
                    String[] cookie = linea.substring(8).split("; ");
                    for (String estaCookie : cookie) {
                        if (estaCookie.startsWith("sessionID=")) {
                            sessionID = estaCookie.substring(10);
                            //System.out.println("***Ya tiene COOKIE");
                        }
                    }
                }
            }

            // **Manejo de peticiones GET**
            //Si esta la cookie en sesiones guardada, busco por usuario y si es igual que la que está guardada se sirve la página, sino se da una redireccion a la principal '/'
            if (metodo.equals("GET")) {
                switch (url) {
                    case "/":
                        tipoRespuesta = 200;
                        html = PaginaLogin.getPagina();
                        break;
                    case "/index":
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;
                        html = PaginaIndex.getPagina();
                        break;
                    case "/logout":
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;
                        sessionID = ""; // Dejamos la sesionID vacia para que la cabecera la borre en el método
                        html = PaginaLogout.getPagina();
                        break;
                    case "/adivina":
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;
                        html = PaginaAdivina.getPagina();
                        break;
                    case "/dados":
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;
                        html = PaginaDado.getPagina();
                        break;
                    case "/ppt":
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;
                        html = PaginaPPT.getPagina();
                        break;
                    default:
                        html = PaginaError.getPagina();
                        break;
                }
                pw.println(Cabecera.generarCabecera(html.length(), tipoRespuesta, sessionID));
                pw.println(html);
            } // **Manejo de peticiones POST (Formulario)**
            else if (metodo.equals("POST")) {

                switch (url) {
                    case "/": {
                        String[] arrayDatos;

                        // Leer exactamente `Content-Length` bytes del cuerpo
                        if (contentLength > 0) {
                            char[] buffer = new char[contentLength];
                            br.read(buffer, 0, contentLength);
                            String cuerpo = new String(buffer);
                            System.out.println("Cuerpo recibido: " + cuerpo); // Depuración

                            if (cuerpo.contains("user=") && cuerpo.contains("pass=")) {

                                arrayDatos = cuerpo.split("&");
                                user = URLDecoder.decode(arrayDatos[0].substring(5), StandardCharsets.UTF_8);
                                pass = URLDecoder.decode(arrayDatos[1].substring(5), StandardCharsets.UTF_8);
                                accion = arrayDatos[2].substring(7);

                                String texto = Comprobaciones.comprobarDatos(user, pass);

                                if (texto.equals("OK")) {
                                  System.out.println("********comprobaciones ok");

                                    if (accion.equals("login")) {
                                           System.out.println("********hay logueo ok");

                                        if (Persistencia.loginUsuario(user, pass)) {
                                            System.out.println("********se ha logueado ok");
                                            // Si sesionID esta vacia (osea si es "" aun), o la key no existe o no coincide el token porque se haya cambiado, pues se hace una cookie nueva y se guarda
                                            if (sessionID.isEmpty() || !sesiones.containsKey(user) || !sesiones.get(user).equals(sessionID)) {
                                                System.out.println("Cookie modificada en el usuario: " + user);
                                                sessionID = UUID.randomUUID().toString();    // Actualiza la cookie por si no tiene o por si está corrupta, para no seguir metiendo más.
                                                sesiones.put(user, sessionID);       // Solo acepta un par:  1 usuario -> 1 cookie, si se cambia o modifica se borra y se guarda una nueva.      
                                                System.out.println("*********Creando cookie " + sessionID);
                                            }
                                            // Guardamos la cookie en sesiones
                                            html = PaginaIndex.getPagina();

                                        } else {
                                            html = PaginaLogin.getPagina().replaceAll("<!--ERRORES-->", "Error: usuario o contraseña incorrectos.");
                                        }
                                    } else if (accion.equals("crear")) {
                                        if (Persistencia.guardarUsuario(user, pass)) {
                                            html = PaginaLogin.getPagina().replaceAll("<!--ERRORES-->", "¡Felicidades! Se ha creado la cuenta correctamente.");
                                            html = html.replaceAll("style=\"color: red;", "style=\"color: green;");
                                        } else {
                                            html = PaginaLogin.getPagina().replaceAll("<!--ERRORES-->", "Error: el usuario ya existe.");
                                        }
                                    }

                                } else {
                                    html = PaginaLogin.getPagina().replaceAll("<!--ERRORES-->", texto);
                                }
                            }
                        }
                        pw.println(Cabecera.generarCabecera(html.length(), 200, sessionID));
                        pw.println(html);
                        break;
                    }

                    //--------------------------------- JUEGO ADIVINA -------------------------------//
                    case "/adivina": {
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;

                        if (adivinaNumeroSecreto == 0) {
                            adivinaNumeroSecreto = (int) (Math.random() * 100) + 1;
                            adivinaIntentos = 0;
                        }
                        String[] arrayDatos;
                        String numeroAdivinado = "";
                        // Leer exactamente `Content-Length` bytes del cuerpo
                        if (contentLength > 0) {
                            char[] buffer = new char[contentLength];
                            br.read(buffer, 0, contentLength);
                            String cuerpo = new String(buffer);
                            System.out.println("Cuerpo recibido: " + cuerpo); // Depuración

                            if (cuerpo.contains("numero=")) {

                                arrayDatos = cuerpo.split("=");
                                if (arrayDatos.length == 2) {
                                    numeroAdivinado = cuerpo.split("=")[1];
                                }

                            }
                        }
                        if (!numeroAdivinado.equals("")) {
                            int numero = 0;

                            try {
                                numero = Integer.parseInt(numeroAdivinado);
                                adivinaIntentos++;

                                if (numero == adivinaNumeroSecreto) {
                                    mensaje = "¡Felicidades! Adivinaste el número en " + adivinaIntentos + " intentos <br>";
                                    mensaje += "El número era : " + adivinaNumeroSecreto;
                                    adivinaNumeroSecreto = 0; // Reiniciar el juego

                                } else if (adivinaIntentos >= 10) {
                                    mensaje = "Lo siento, agotaste tus 10 intentos. El número era " + adivinaNumeroSecreto;
                                    adivinaNumeroSecreto = 0; // Reiniciar el juego

                                } else if (numero < adivinaNumeroSecreto) {
                                    mensaje = "El número es MAYOR <br>";
                                    mensaje += "Intentos restantes: " + (10 - adivinaIntentos);
                                } else {
                                    mensaje = "El número es MENOR <br>";
                                    mensaje += "Intentos restantes: " + (10 - adivinaIntentos);

                                }
                            } catch (NumberFormatException e) {
                                StackTraceElement elemento = e.getStackTrace()[0];
                                Logueadora.grabarError(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), url.substring(1), elemento.getLineNumber(), numeroAdivinado);
                            }

                        } else {
                            mensaje = "No se ha enviado ningún número, no te hagas el listillo... <br>";
                            mensaje += "Intentos restantes: " + (10 - adivinaIntentos);

                            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
                            Logueadora.grabarError(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), url.substring(1), lineaError, numeroAdivinado);
                        }       // Reemplaza el marcador "<p>resultado</p>" en la página HTML con el mensaje del juego
                        html = PaginaAdivina.getPagina().replaceAll("<!--RESULTADO-->", mensaje);
                        if (adivinaIntentos >= 10) {
                            html = html.replaceAll("Enviar", "Volver a intentar");
                            html = html.replaceAll(" <input type=\"number\" name=\"numero\" min=\"1\" max=\"100\" required>\n", "");
                        }
                        pw.println(Cabecera.generarCabecera(html.length(), tipoRespuesta, sessionID));
                        pw.println(html);
                        break;
                    }

                    //--------------------------------- JUEGO DADOS -------------------------------//
                    case "/dados":
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;

                        if (dadosIntentos == 1) {
                            dadosPuntosJugador = 0;
                            dadosPuntosBanca = 0;
                            dadosAcabada = false;
                        }
                        if (contentLength > 0) {
                            char[] buffer = new char[contentLength];
                            br.read(buffer, 0, contentLength);
                            String cuerpo = new String(buffer);
                            System.out.println("Cuerpo recibido: " + cuerpo); // Depuración
                        }
                        int randomJugador = (int) ((Math.random() * 6) + 1);
                        int randombanca = (int) ((Math.random() * 6) + 1);
                        if (randomJugador > randombanca) {
                            dadosPuntosJugador++;
                            mensaje = "¡Punto para el Jugador!<br>";
                            mensaje += "Ronda: " + dadosIntentos;
                            if (dadosIntentos != 5) {
                                dadosIntentos++;
                            }

                        } else if (randomJugador < randombanca) {
                            dadosPuntosBanca++;
                            mensaje = "¡Punto para la banca!<br>";
                            mensaje += "Ronda: " + dadosIntentos;
                            if (dadosIntentos != 5) {
                                dadosIntentos++;
                            }

                        } else {
                            mensaje = "¡Empate!<br>";
                            mensaje += "Ronda: " + dadosIntentos;

                        }
                        if (dadosIntentos == 5) {
                            if (dadosPuntosJugador != dadosPuntosBanca) {
                                mensaje += dadosPuntosJugador > dadosPuntosBanca ? "<br>¡Felicidades! Has ganado a la banca" : "<br>Lo siento, has perdido";
                                dadosAcabada = true;
                                dadosIntentos = 1;
                            }
                        }
                        html = PaginaDado.getPagina().replaceAll("<!--RESULTADO-->", mensaje + "<br>");
                        html = html.replaceAll("<!--RESULTADO_JUG-->", "Has sacado un: " + randomJugador + "<br><br>");
                        html = html.replaceAll("<!--RESULTADO_BANCA-->", "La banca saca un: " + randombanca + "<br><br>");
                        html = html.replaceAll("<!--puntosJug-->", "" + dadosPuntosJugador);
                        html = html.replaceAll("<!--puntosBanca-->", "" + dadosPuntosBanca);
                        if (dadosAcabada) {
                            html = html.replaceAll("Lanzar", "Nueva partida");
                        }
                        pw.println(Cabecera.generarCabecera(html.length(), tipoRespuesta, sessionID));
                        pw.println(html);
                        break;

                    //--------------------------------- JUEGO PPT -------------------------------//
                    case "/ppt": {
                        tipoRespuesta = sesiones.get(user).equals(sessionID) ? 200 : 300;

                        if (pptIntentos == 1) {
                            pptPuntosJugador = 0;
                            pptPuntosBanca = 0;
                        }
                        String[] arrayDatos;
                        String opcionElegida = "";
                        if (contentLength > 0) {
                            char[] buffer = new char[contentLength];
                            br.read(buffer, 0, contentLength);
                            String cuerpo = new String(buffer);
                            System.out.println("Cuerpo recibido: " + cuerpo); // Depuración

                            if (cuerpo.contains("opcion=")) {
                                arrayDatos = cuerpo.split("=");
                                if (arrayDatos.length == 2) {
                                    opcionElegida = arrayDatos[1];
                                }
                            }
                        }
                        if (opcionElegida.equals("0") || opcionElegida.equals("1") || opcionElegida.equals("2")) {
                            pptOpcionBanca = (int) (Math.random() * 3);
                            try {

                                pptOpcionElegida = Integer.parseInt(opcionElegida);
                                switch (pptOpcionElegida) {

                                    case (0):
                                        if (pptOpcionBanca == 1) {
                                            pptPuntosBanca++;
                                            mensaje = "¡Punto para la banca!<br>";
                                            mensaje += "Ronda: " + pptIntentos;
                                            pptIntentos++;

                                        } else if (pptOpcionBanca == 2) {
                                            pptPuntosJugador++;
                                            mensaje = "¡Punto para el Jugador!<br>";
                                            mensaje += "Ronda: " + pptIntentos;
                                            pptIntentos++;
                                        }
                                        break;
                                    case (1):
                                        if (pptOpcionBanca == 0) {
                                            pptPuntosJugador++;
                                            mensaje = "¡Punto para el Jugador!<br>";
                                            mensaje += "Ronda: " + pptIntentos;
                                            pptIntentos++;
                                        } else if (pptOpcionBanca == 2) {
                                            pptPuntosBanca++;
                                            mensaje = "¡Punto para la banca!<br>";
                                            mensaje += "Ronda: " + pptIntentos;
                                            pptIntentos++;
                                        }
                                        break;
                                    case (2):
                                        if (pptOpcionBanca == 0) {
                                            pptPuntosBanca++;
                                            mensaje = "¡Punto para la banca!<br>";
                                            mensaje += "Ronda: " + pptIntentos;
                                            pptIntentos++;
                                        } else if (pptOpcionBanca == 1) {
                                            pptPuntosJugador++;
                                            mensaje = "¡Punto para el Jugador!<br>";
                                            mensaje += "Ronda: " + pptIntentos;
                                            pptIntentos++;
                                        }
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                // Este error está en several porque no debería de saltar nunca, a no ser que haya un fallo en el casteo.
                                StackTraceElement elemento = e.getStackTrace()[0];
                                Logueadora.grabarError(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), url.substring(1), elemento.getLineNumber(), opcionElegida);
                            }
                            if (pptOpcionElegida == pptOpcionBanca) {
                                mensaje = "¡Empate!<br>";
                                mensaje += "Ronda: " + pptIntentos;
                            }

                            html = PaginaPPT.getPagina();
                            if (pptIntentos >= 5) {
                                if (pptPuntosJugador != pptPuntosBanca) {
                                    html = html.replaceAll("<button type=\"submit\" name=\"opcion\" value=\"0\">Piedra</button><button type=\"submit\" name=\"opcion\" value=\"1\">Papel</button>", pptPuntosJugador > pptPuntosBanca ? "<br><h3 style=\"color: green;\">¡Felicidades! Has ganado a la banca</h3>" : "<br><h3 style=\"color: red;\">Lo siento, has perdido</h3>");
                                    html = html.replaceAll("<button type=\"submit\" name=\"opcion\" value=\"2\">Tijera</button>", "<button type=\"submit\" name=\"opcion\" value=\"4\">Volver a intentar</button>");
                                    pptIntentos = 1;
                                }
                            }

                            html = html.replaceAll("<!--RESULTADO_JUG-->", PTT_OPCIONES_DISPONIBLES[pptOpcionElegida]);
                            html = html.replaceAll("<!--RESULTADO_BANCA-->", PTT_OPCIONES_DISPONIBLES[pptOpcionBanca]);

                            html = html.replaceAll("<!--RESULTADO-->", mensaje + "<br>");
                            html = html.replaceAll("<!--puntosJug-->", "" + pptPuntosJugador);
                            html = html.replaceAll("<!--puntosBanca-->", "" + pptPuntosBanca);

                            pw.println(Cabecera.generarCabecera(html.length(), tipoRespuesta, sessionID));
                            pw.println(html);
                        } else {
                            html = PaginaPPT.getPagina();
                            pw.println(Cabecera.generarCabecera(html.length(), tipoRespuesta, sessionID));
                            pw.println(html);

                            int lineaError = new Exception().getStackTrace()[0].getLineNumber();
                            Logueadora.grabarError(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), url.substring(1), lineaError, opcionElegida);

                        }
                        break;
                    }
                    default:
                        break;
                }
            }

            // Cierro flujos y socketSSL
            br.close();
            pw.close();
            s.close();

        } catch (Exception e) {
            System.out.println("Error en el sistema: " + e.getMessage());
            // int lineaError = new Exception().getStackTrace()[0].getLineNumber();
            // Logueadora.grabarError(5, LocalDateTime.now().format(DateTimeFormatter.ofPattern(Configuracion.getFormatoFechaHoraLog())), "Sistema", lineaError, e.getMessage());
        }
    }
}
