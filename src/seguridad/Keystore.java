package seguridad;

import config.Configuracion;
import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Clase encargada de gestionar el almacenamiento y configuración del
 * certificado SSL para establecer una conexión segura en el servidor.
 *
 * <p>
 * Esta clase se encarga de cargar un almacén de claves (Keystore) y configurar
 * el contexto SSL para crear un {@link SSLServerSocket} que utilizará el
 * servidor para establecer conexiones seguras con los clientes.
 * </p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class Keystore {

    /**
     * Obtiene y configura un {@link SSLServerSocket} utilizando un almacén de
     * claves (Keystore) previamente cargado y configurado.
     *
     * <p>
     * Este método carga el archivo del almacén de claves en formato JKS,
     * utiliza la contraseña proporcionada para acceder al mismo y configurar el
     * {@link SSLContext} para permitir conexiones seguras mediante SSL. El
     * socket resultante estará configurado para escuchar en el puerto definido
     * por {@link config.Configuracion#getPuerto()}.
     * </p>
     *
     * @return Un {@link SSLServerSocket} configurado para aceptar conexiones
     * seguras.
     * @throws Exception Si ocurre algún error durante la carga del almacén de
     * claves o la inicialización del contexto SSL.
     */
    public static SSLServerSocket getServerSocketSSL() throws Exception {
        // Crear un KeyStore de tipo JKS
        KeyStore keyStore = KeyStore.getInstance(Configuracion.getTipoAlmacen());

        // Cargar el archivo del almacén de claves (Keystore)
        try (FileInputStream keyFile = new FileInputStream(Configuracion.getRutaAlmacenSSL())) {
            keyStore.load(keyFile, Configuracion.getPasswordAlmacenSSL().toCharArray());
        }

        // Inicializar el KeyManagerFactory con el almacén de claves y la contraseña
        KeyManagerFactory keyManagerFact = KeyManagerFactory.getInstance(Configuracion.getAlgoritmoAlmacenSSL());
        keyManagerFact.init(keyStore, Configuracion.getPasswordAlmacenSSL().toCharArray());

        // Inicializar el contexto SSL
        SSLContext sslContext = SSLContext.getInstance(Configuracion.getProtocoloAlmacenSSL());
        sslContext.init(keyManagerFact.getKeyManagers(), null, null);

        // Crear un SSLServerSocketFactory con el contexto SSL configurado
        SSLServerSocketFactory factory = sslContext.getServerSocketFactory();

        // Crear un SSLServerSocket en el puerto configurado
        SSLServerSocket sslServerSocket = (SSLServerSocket) factory.createServerSocket(Configuracion.getPuerto());
        return sslServerSocket;
    }
}
