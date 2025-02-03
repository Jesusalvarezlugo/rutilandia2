package com.jesuslg.rutilandia2.util;

import java.sql.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jesuslg.rutilandia2.dtos.UsuarioDto;
/**
 * Clase para diferentes métodos que se utilizaran en varios sitios del código.
 */
public class Utilidades {
	
	/**
	 * Método para enciptar la contraseña.
	 * @param password
	 * @return contraseña encriptada
	 */
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	public static String encriptarContrasenia(String password) {
        try {
            // Encriptar la contraseña con BCrypt
            return encoder.encode(password);
        } catch (Exception e) {
            // Manejo de excepción en caso de error durante la encriptación
            System.out.println("Error al encriptar la contraseña: " + e.getMessage());
            return null;  // Retorna null si ocurre un error
        }
    }
	
	/**
	 * Método para verificar contraseña.
	 * @param contrasenia
	 * @param contraseniaEncriptada
	 * @return true si son iguales, false si no lo son
	 */
	public static boolean verificarEncriptacion(String contrasenia,String contraseniaEncriptada) {
		
		
		//Si la contraseña que introduce el usuario es igual que la contraseña que hay en la base de datos devolvera true,
		//si no lo es, devolvera false
		return encoder.matches(contrasenia, contraseniaEncriptada);
	}
	
	/**
	 * Método para generar un token.
	 * @param usuario
	 * @return
	 */
	public static String generateToken(UsuarioDto usuario) {
		 final String SECRET_KEY = "altair_006!";  // 
	     final long EXPIRATION_TIME = 600000;//10 minutos
        // Obtener la fecha y hora de expiración del token
        long currentTimeMillis = System.currentTimeMillis();
        Date expirationDate = new Date(currentTimeMillis + EXPIRATION_TIME);

        // Generar el token JWT
        return JWT.create()
                .withSubject(usuario.getEmail())  // Aquí puedes poner el ID o el email del usuario
                //.withClaim("role", usuario.getRole())  // Puedes agregar más información del usuario como el rol
                .withIssuedAt(new Date(currentTimeMillis))  // Fecha de creación
                .withExpiresAt(expirationDate)  // Fecha de expiración
                .sign(Algorithm.HMAC256(SECRET_KEY));  // Firma con la clave secreta
    }

}
