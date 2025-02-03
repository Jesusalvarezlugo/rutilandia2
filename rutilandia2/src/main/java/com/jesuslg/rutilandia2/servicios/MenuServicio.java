package com.jesuslg.rutilandia2.servicios;

import com.jesuslg.rutilandia2.Rutilandia2Application;

/**
 * Clase que  gestiona los menus
 */
public class MenuServicio {

	public int mostrarMenuYSeleccion() {
		int opcion;
		
		System.out.println("###############");
		System.out.println("1.Dar alta usuario");
		System.out.println("2.Modificar usuario");
		System.out.println("3.Eliminar usuario");
		System.out.println("4.Dar de alta una mesa");
		System.out.println("5.Inicio de sesion");
		System.out.println("6.Recuperar contraseña");
		System.out.println("0.Salir de la aplicación");
		System.out.println("###############");
		opcion=Rutilandia2Application.sc.nextInt();
		
		return opcion;
	}
	
	public int mostrarMenuYSeleccionModificar() {
		
		int opcion;
		
		System.out.println("###############");
		System.out.println("1.Modificar nombre");
		System.out.println("2.Modificar apellidos");
		System.out.println("3.Modificar telefono");
		System.out.println("4.Modificar el email");
		System.out.println("5.Modificar el rol");
		System.out.println("6.Modificar la contraseña");
		System.out.println("###############");
		opcion=Rutilandia2Application.sc.nextInt();
		
		return opcion;
	}
}
