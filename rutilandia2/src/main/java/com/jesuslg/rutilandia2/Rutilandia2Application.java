package com.jesuslg.rutilandia2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jesuslg.rutilandia2.dtos.MesaDto;
import com.jesuslg.rutilandia2.dtos.UsuarioDto;
import com.jesuslg.rutilandia2.servicios.MenuServicio;
import com.jesuslg.rutilandia2.servicios.MesaServicio;
import com.jesuslg.rutilandia2.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;
/**
 * Clase por la que se inicia la aplicación.
 */
@SpringBootApplication
public class Rutilandia2Application  {
	
	public static UsuarioServicio usuarioServicio = new UsuarioServicio();
	public static MesaServicio mesaServicio = new MesaServicio();
	public static List<UsuarioDto> listaUsuarios = new ArrayList<UsuarioDto>();
	public static List<MesaDto> listaMesas = new ArrayList<MesaDto>();
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws URISyntaxException, IOException {
    	 int opcionS;
    	 boolean cerrarMenu=false;
    	 MenuServicio ms = new MenuServicio();
    	 HttpSession sesion = null;
    	 
    	 
    	 //ApiServicio apiServicio;
    	 //Carga los usuarios desde la api
    	 //apiServicio.cargarUsuariosDesdeApi();
        while(!cerrarMenu) {
        	
        	try {
        		opcionS=ms.mostrarMenuYSeleccion();
            	
            	switch(opcionS) {
            	
            	case 0:
            		System.out.println("Se cerrara la aplicación");
            		listaUsuarios.forEach(usuario -> System.out.println("Usuario: " + usuario));
            		cerrarMenu=true;
            		break;
            		
            	case 1:
            		System.out.println("Se dara de alta un usuario");
            		usuarioServicio.nuevoUsuario(sesion);
            		break;
            		
            	case 2:
            		System.out.println("Se modificara un usuario");
            		usuarioServicio.modificarUsuario();
            		break;
            		
            	case 3:
            		System.out.println("Se eliminara un usuario");
            		usuarioServicio.eliminarUsuario();
            		break;
            		
            	case 4:
            		System.out.println("Se dara de alta una nueva mesa");
            		mesaServicio.nuevaMesa(sesion);
            		break;
            		
            	default:
            		System.out.println("[ERROR] Opcion seleccionada no valida.");
            		break;
            	}
        	}catch(Exception e) {
        		System.out.println("escribir en el log el error");
        		System.out.println(e.getLocalizedMessage());
        	}
        }
        
        
    	
    }

    
}
