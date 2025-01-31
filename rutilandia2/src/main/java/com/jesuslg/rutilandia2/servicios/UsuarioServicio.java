package com.jesuslg.rutilandia2.servicios;

import java.io.IOException;
import java.net.URISyntaxException;
import org.springframework.stereotype.Service;
import com.jesuslg.rutilandia2.Rutilandia2Application;
import com.jesuslg.rutilandia2.dtos.UsuarioDto;
import com.jesuslg.rutilandia2.util.Utilidades;

import jakarta.servlet.http.HttpSession;
@Service
public class UsuarioServicio {
	
	 
    private ApiServicio apiServicio = new ApiServicio();
	public UsuarioServicio() {
		
	}
public void nuevoUsuario(HttpSession sesion) throws URISyntaxException, IOException {
		apiServicio.cargarUsuariosDesdeApi();
		UsuarioDto usuarioNuevo = new UsuarioDto();
		
		System.out.println("Introduzca su nombre: ");
		usuarioNuevo.setNombre(Rutilandia2Application.sc.next());
		System.out.println("Introduzca su primer apellido: ");
		usuarioNuevo.setApellido1(Rutilandia2Application.sc.next());
		System.out.println("Introduzca su segundo apellido: ");
		usuarioNuevo.setApellido2(Rutilandia2Application.sc.next());
		usuarioNuevo.setApellidos(usuarioNuevo.getApellido1()+" "+usuarioNuevo.getApellido2());
		System.out.println("Introduzca su teléfono: ");
		usuarioNuevo.setTelefono(Rutilandia2Application.sc.next());
		System.out.println("Introduzca su email: ");
	    usuarioNuevo.setEmail(Rutilandia2Application.sc.next());
		System.out.println("Introduzca su rol: ");
		usuarioNuevo.setRol(Rutilandia2Application.sc.next());
		System.out.println("Introduzca su contraseña: ");
		usuarioNuevo.setContrasenia(Rutilandia2Application.sc.next());
		do {
			
			System.out.println("Repita Su contraseña: ");
			usuarioNuevo.setRepContrasenia(Rutilandia2Application.sc.next());
			
			if (!usuarioNuevo.getContrasenia().equalsIgnoreCase(usuarioNuevo.getRepContrasenia())) {
		        System.out.println("Las contraseñas no coinciden. Inténtelo de nuevo.");
		    }
			
		}while(!usuarioNuevo.getContrasenia().equalsIgnoreCase(usuarioNuevo.getRepContrasenia()));
		//Aqui encriptamos la contraseña
		usuarioNuevo.setContrasenia(Utilidades.encriptarContrasenia(usuarioNuevo.getContrasenia()));
		System.out.println(usuarioNuevo);
	
		apiServicio.enviarRegistroUsuario(usuarioNuevo,sesion);
	}

public void modificarUsuario() throws Exception {
    // Carga los usuarios desde la API
    apiServicio.cargarUsuariosDesdeApi();

    System.out.println("Introduzca su email:");
    String emailAComprobar = Rutilandia2Application.sc.next();
    
    UsuarioDto usuarioEncontrado = null;
    for (UsuarioDto usuario : Rutilandia2Application.listaUsuarios) {
        if (emailAComprobar.equalsIgnoreCase(usuario.getEmail())) {
            usuarioEncontrado = usuario;
            break;
        }
    }
    
    if (usuarioEncontrado == null) {
        System.out.println("Usuario no encontrado");
        return;
    }

    String respuesta;
    int opcionS;
    MenuServicio ms = new MenuServicio();

    do {
        opcionS = ms.mostrarMenuYSeleccionModificar();

        switch (opcionS) {
            case 1:
                System.out.println("----- Se modificará el nombre -----");
                System.out.println("Introduzca el nuevo nombre: ");
                usuarioEncontrado.setNombre(Rutilandia2Application.sc.next());
                break;

            case 2:
                System.out.println("----- Se modificarán los apellidos -----");
                System.out.println("Introduzca el primer apellido: ");
                usuarioEncontrado.setApellido1(Rutilandia2Application.sc.next());
                System.out.println("Introduzca el segundo apellido: ");
                usuarioEncontrado.setApellido2(Rutilandia2Application.sc.next());
                usuarioEncontrado.setApellidos(usuarioEncontrado.getApellido1() + " " + usuarioEncontrado.getApellido2());
                break;

            case 3:
                System.out.println("----- Se modificará el teléfono -----");
                System.out.println("Introduzca el nuevo teléfono: ");
                usuarioEncontrado.setTelefono(Rutilandia2Application.sc.next());
                break;

            case 4:
                System.out.println("----- Se modificará el email -----");
                System.out.println("Introduzca el nuevo email: ");
                usuarioEncontrado.setEmail(Rutilandia2Application.sc.next());
                break;

            case 5:
                System.out.println("----- Se modificará el rol -----");
                System.out.println("Introduzca el nuevo rol: ");
                usuarioEncontrado.setRol(Rutilandia2Application.sc.next());
                break;

            case 6:
                System.out.println("----- Se modificará la contraseña -----");
                System.out.println("Para modificar la contraseña, introduzca la actual:");
                String repetirContrasenia;
                // La contraseña ingresada por el usuario (en texto claro)
                String contraseniaAComprobar = Rutilandia2Application.sc.next();
                System.out.println("Contraseña almacenada en la base de datos (encriptada): " + usuarioEncontrado.getContrasenia());
                System.out.println("Contraseña ingresada para comprobar: " + contraseniaAComprobar);
                // Verificamos si la contraseña ingresada (texto claro) coincide con la almacenada encriptada
                if (Utilidades.verificarEncriptacion(contraseniaAComprobar, usuarioEncontrado.getContrasenia())) {
                    System.out.println("Introduzca la nueva contraseña: ");
                    String nuevaContrasenia = Rutilandia2Application.sc.next();

                    do {
                        System.out.println("Repita la contraseña: ");
                         repetirContrasenia = Rutilandia2Application.sc.next();

                        // Verificamos si ambas contraseñas coinciden
                        if (!nuevaContrasenia.equals(repetirContrasenia)) {
                            System.out.println("Las contraseñas no coinciden. Inténtelo de nuevo.");
                        } else {
                            // Si las contraseñas coinciden, encriptamos la nueva contraseña
                            usuarioEncontrado.setContrasenia(Utilidades.encriptarContrasenia(nuevaContrasenia));
                            System.out.println("Contraseña modificada correctamente.");
                        }
                    } while (!nuevaContrasenia.equals(repetirContrasenia));
                } else {
                    System.out.println("La contraseña actual no es correcta.");
                }
                break;
        }

        System.out.println("¿Desea modificar algún campo más? (si/no)");
        respuesta = Rutilandia2Application.sc.next();
    } while (!respuesta.equalsIgnoreCase("no"));

    // Enviar usuario modificado a la API
    String resultado = apiServicio.actualizarUsuario(usuarioEncontrado);
    if ("success".equals(resultado)) {
        System.out.println("Usuario actualizado correctamente.");
    } else {
        System.out.println("Hubo un error al actualizar el usuario.");
    }
}

public void eliminarUsuario() throws Exception {
    // Cargar usuarios desde la API
    apiServicio.cargarUsuariosDesdeApi();

    System.out.println("Introduzca el email del usuario que desea eliminar:");
    String emailAEliminar = Rutilandia2Application.sc.next();

    UsuarioDto usuarioEncontrado = null;
    
    // Buscar el usuario por email
    for (UsuarioDto usuario : Rutilandia2Application.listaUsuarios) {
        if (emailAEliminar.equalsIgnoreCase(usuario.getEmail())) {
            usuarioEncontrado = usuario;
            break;
        }
    }

    if (usuarioEncontrado == null) {
        System.out.println("Usuario no encontrado.");
        return;
    }

    // Obtener el ID del usuario encontrado
    Long idUsuario = usuarioEncontrado.getId();

    System.out.println("Está a punto de eliminar al usuario con ID: " + idUsuario + " y email: " + usuarioEncontrado.getEmail());
    System.out.println("Para confirmar la eliminación, escriba 'CONFIRMAR':");
    String confirmacion = Rutilandia2Application.sc.next();

    if (confirmacion.equalsIgnoreCase("CONFIRMAR")) {
        // Llamar al servicio API para eliminar por ID
        String resultado = apiServicio.eliminarUsuarioPorId(idUsuario);

        if ("success".equals(resultado)) {
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("Hubo un error al eliminar el usuario.");
        }
    } else {
        System.out.println("Eliminación cancelada. No se realizó ninguna acción.");
    }
}

}
