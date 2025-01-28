package com.jesuslg.rutilandia2.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Utilidades {
	
	/**
	 * Método para enciptar la contraseña
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
	
	
	public static boolean verificarEncriptacion(String contrasenia,String contraseniaEncriptada) {
		
		
		//Si la contraseña que introduce el usuario es igual que la contraseña que hay en la base de datos devolvera true,
		//si no lo es, devolvera false
		return encoder.matches(contrasenia, contraseniaEncriptada);
	}

}
