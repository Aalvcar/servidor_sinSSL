package utilidades;

/**
 * Clase de utilidades para validación de datos de entrada.
 *
 * <p>
 * Contiene métodos estáticos diseñados para verificar la validez de información
 * ingresada por el usuario, tales como nombre de usuario y contraseña,
 * típicamente durante el proceso de registro o inicio de sesión.</p>
 *
 * <p>
 * Esta clase no está diseñada para ser instanciada.</p>
 *
 * @author Antonio Álvarez Cárdenas
 */
public class Comprobaciones {

    /**
     * Verifica si los datos ingresados por el usuario cumplen con las reglas
     * básicas de formato y seguridad.
     *
     * <p>
     * Los criterios de validación incluyen:</p>
     * <ul>
     * <li>El campo de usuario no debe estar vacío y debe tener formato de
     * correo electrónico.</li>
     * <li>La contraseña debe tener entre 6 y 12 caracteres.</li>
     * <li>La contraseña debe contener al menos una letra mayúscula y un
     * número.</li>
     * </ul>
     *
     * @param usuario dirección de correo electrónico del usuario a registrar
     * @param password contraseña elegida por el usuario
     * @return una cadena con el resultado de la validación: si es válida
     * devuelve "OK", de lo contrario, un mensaje de error indicando el problema
     * específico
     */
    public static String comprobarDatos(String usuario, String password) {
        String resultado = "";

        if (!usuario.isEmpty() && !password.isEmpty()) {
            if (!usuario.toLowerCase().matches("(\\w+)@(\\w+)\\.([a-z]{2,3})")) {
                resultado = "El correo de usuario no es un correo válido.";
            } else if (password.length() < 6 || password.length() > 12) {
                resultado = "La contraseña debe tener entre 6 y 12 carácteres";
            } else if (!password.matches(".*[A-Z].*") || !password.matches(".*\\d.*")) {
                resultado = "La contraseña debe contener al menos un número y una mayúscula";
            } else {
                resultado = "OK";
            }
        } else {
            resultado = "El usuario y la contraseña no pueden estar vacíos";
        }
        return resultado;
    }

}
