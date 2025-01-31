package com.jesuslg.rutilandia2.servicios;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jesuslg.rutilandia2.Rutilandia2Application;
import com.jesuslg.rutilandia2.dtos.UsuarioDto;
import com.jesuslg.rutilandia2.util.Utilidades;

import jakarta.servlet.http.HttpSession;

public class LoginServicio {

	private ApiServicio apiServicio = new ApiServicio();
	public LoginServicio() {
		
	}
	public void iniciarSesion(HttpSession sesion) throws URISyntaxException, IOException {
	    UsuarioDto usuarioLogin = new UsuarioDto();

	    System.out.println("=== Iniciar Sesión ===");
	    
	    System.out.print("Ingrese su email: ");
	    usuarioLogin.setEmail(Rutilandia2Application.sc.next());

	    System.out.print("Ingrese su contraseña: ");
	    usuarioLogin.setContrasenia(Rutilandia2Application.sc.next());

	    
	    

	    // Enviar los datos a la API
	    String token = apiServicio.enviarLoginUsuario(usuarioLogin);

	    if (token != null) {
	        System.out.println("¡Inicio de sesión exitoso!");
	        System.out.println("Token: " + token);
	        sesion.setAttribute("token", token);
	    } else {
	        System.out.println("[ERROR] Credenciales incorrectas o fallo en la API.");
	    }
	}
}
