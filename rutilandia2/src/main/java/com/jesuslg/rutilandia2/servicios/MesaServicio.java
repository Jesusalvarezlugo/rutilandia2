package com.jesuslg.rutilandia2.servicios;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.jesuslg.rutilandia2.Rutilandia2Application;
import com.jesuslg.rutilandia2.dtos.MesaDto;
import com.jesuslg.rutilandia2.dtos.UsuarioDto;

import jakarta.servlet.http.HttpSession;



@Service
public class MesaServicio {
	
	 private ApiServicio apiServicio = new ApiServicio();
	 public MesaServicio() {
			
		}
	 
	 public void nuevaMesa(HttpSession sesion) throws URISyntaxException, IOException {
		   MesaDto mesaNueva = new MesaDto();
		   
		   System.out.println("Introduzca el nombre de la mesa:");
		   mesaNueva.setNombreMesa(Rutilandia2Application.sc.next());
		   Rutilandia2Application.sc.nextLine();
		   System.out.println("Introduca la descripcion de la mesa: ");
		   mesaNueva.setDescripcionMesa(Rutilandia2Application.sc.nextLine());
		   apiServicio.enviarRegistroMesa(mesaNueva, sesion);
		}
	 
	 
	 
	 


}
