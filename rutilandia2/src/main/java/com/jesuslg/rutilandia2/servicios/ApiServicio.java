package com.jesuslg.rutilandia2.servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jesuslg.rutilandia2.Rutilandia2Application;
import com.jesuslg.rutilandia2.dtos.MesaDto;
import com.jesuslg.rutilandia2.dtos.UsuarioDto;

import jakarta.servlet.http.HttpSession;
import reactor.core.publisher.Mono;
/**
 * Clase que gestiona los metodos para comunicar con la api
 */
@Service
public class ApiServicio {

	/**
	 * Método para enviar un usuario a la api.
	 * @param nuevoUsuario
	 * @param session
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	
	
public String enviarRegistroUsuario(UsuarioDto nuevoUsuario,HttpSession session) throws URISyntaxException, IOException {
		
		URI uri = new URI("http://localhost:8082/api/usuarios/registroUsuario");
		URL url = uri.toURL();
		
		HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
		conexion.setRequestMethod("POST");
		conexion.setRequestProperty("Content-Type", "application/json");
		conexion.setDoOutput(true);
		
		// Pasar el dto a json para enviarlo a la api

		ObjectMapper mapper = new ObjectMapper();

		String dtoAJson = mapper.writeValueAsString(nuevoUsuario);
		System.out.println(dtoAJson);
		
		//se envian los datos al servidor
		
		OutputStream os = conexion.getOutputStream();
		
		os.write(dtoAJson.getBytes());
		os.flush();

		// Leer la respuesta del servidor
		int codigoRespuesta = conexion.getResponseCode();
		
		if (codigoRespuesta == HttpURLConnection.HTTP_CREATED) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			StringBuilder response = new StringBuilder();
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			UsuarioDto usuario = mapper.readValue(response.toString(), UsuarioDto.class);

			if (usuario != null) {
				// Guardar el objeto UsuarioDto en la sesión

				session.setAttribute("usuario", usuario);

				return "success";
			} else {
				System.out.println("Error: La respuesta de la API no contiene un usuario válido.");
				return "error";
			}
				
		}
		
		return "error";
	}

	 
	 public ApiServicio() {
		 super();	
	}
	 //WebClient webClient = new WebClient();
	 //WebClient webClient = WebClient.builder().build();
	 WebClient webClient = WebClient.create("http://localhost:8082");
	 //public ApiServicio(WebClient.Builder webClientBuilder) {
	   //     this.webClient = webClientBuilder.baseUrl("http://localhost:8082").build();
	    //}
	 /**
	  * Método para traer los usuarios que haya en el base de datos.
	  * @return
	  */
	 public void cargarUsuariosDesdeApi() {
	        Mono<UsuarioDto[]> response = webClient.get()
	                .uri("/api/usuarios/obtenerUsuarios")
	                .retrieve()
	                .bodyToMono(UsuarioDto[].class);

	        UsuarioDto[] usuarios = response.block();  // Bloquear para obtener la respuesta síncrona

	        if (usuarios != null) {
	        	Rutilandia2Application.listaUsuarios = Arrays.asList(usuarios);
	            System.out.println("Usuarios cargados: " + Rutilandia2Application.listaUsuarios.size());
	            for(UsuarioDto usuario:Rutilandia2Application.listaUsuarios) {
	       		 usuario.toString();
	       	 }
	        }
	    }

	    public List<UsuarioDto> obtenerUsuarios() {
	        return Rutilandia2Application.listaUsuarios;
	    }
	    
	    /**
	     * Método para actualizar usuarios
	     * @param usuarioActualizado
	     * @return
	     * @throws URISyntaxException
	     * @throws IOException
	     */
	    public String actualizarUsuario(UsuarioDto usuarioActualizado) throws URISyntaxException, IOException {
	        URI uri = new URI("http://localhost:8082/api/usuarios/actualizarUsuario");
	        URL url = uri.toURL();

	        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
	        conexion.setRequestMethod("PUT");
	        conexion.setRequestProperty("Content-Type", "application/json");
	        conexion.setDoOutput(true);

	        // Convertir el objeto usuario actualizado a JSON
	        ObjectMapper mapper = new ObjectMapper();
	        String usuarioJson = mapper.writeValueAsString(usuarioActualizado);
	        System.out.println(usuarioJson);

	        // Enviar datos al servidor
	        OutputStream os = conexion.getOutputStream();
	        os.write(usuarioJson.getBytes());
	        os.flush();
	        os.close();

	        // Leer la respuesta del servidor
	        int codigoRespuesta = conexion.getResponseCode();
	        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String inputLine;

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            System.out.println("Respuesta de la API: " + response.toString());
	            return "success";
	        } else {
	            // Si la respuesta no es OK, procesamos el error
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
	            StringBuilder errorResponse = new StringBuilder();
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                errorResponse.append(inputLine);
	            }
	            in.close();
	            System.out.println("Error al actualizar el usuario: " + errorResponse.toString());
	            return "error";
	        }
	    }
	    
	    public String eliminarUsuarioPorId(Long idUsuario) throws URISyntaxException, IOException {
	        // Definir la URL de la API para eliminar usuario por ID
	        URI uri = new URI("http://localhost:8082/api/usuarios/eliminar/" + idUsuario);
	        URL url = uri.toURL();

	        // Configurar la conexión HTTP
	        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
	        conexion.setRequestMethod("DELETE");
	        conexion.setRequestProperty("Content-Type", "application/json");
	        conexion.setDoOutput(true);

	        // Leer la respuesta del servidor
	        int codigoRespuesta = conexion.getResponseCode();

	        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
	            // Leer la respuesta exitosa
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String inputLine;

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            System.out.println("Usuario eliminado correctamente: " + response.toString());
	            return "success";
	        } else {
	            // Leer la respuesta de error
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
	            StringBuilder errorResponse = new StringBuilder();
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                errorResponse.append(inputLine);
	            }
	            in.close();

	            System.out.println("Error al eliminar usuario: " + errorResponse.toString());
	            return "error";
	        }
	    }
	 
	
	    public String enviarRegistroMesa(MesaDto nuevaMesa, HttpSession session) throws URISyntaxException, IOException {
	        // Verificar que los datos sean correctos antes de convertir a JSON
	        System.out.println("Nombre de la mesa antes de enviar: " + nuevaMesa.getNombreMesa());
	        System.out.println("Descripción de la mesa antes de enviar: " + nuevaMesa.getDescripcionMesa());

	        // Definir la URI de la API
	        URI uri = new URI("http://localhost:8082/api/mesas/crearMesa");
	        URL url = uri.toURL();

	        // Abrir la conexión HTTP
	        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
	        conexion.setRequestMethod("POST");
	        conexion.setRequestProperty("Content-Type", "application/json");
	        conexion.setDoOutput(true);

	        // Convertir el DTO de Mesa a JSON
	        ObjectMapper mapper = new ObjectMapper();
	        String dtoAJson = mapper.writeValueAsString(nuevaMesa);
	        System.out.println("JSON enviado: " + dtoAJson);  // Imprimir el JSON para verificar que los datos estén correctos

	        // Enviar los datos al servidor
	        OutputStream os = conexion.getOutputStream();
	        os.write(dtoAJson.getBytes());
	        os.flush();

	        // Leer la respuesta del servidor
	        int codigoRespuesta = conexion.getResponseCode();

	        if (codigoRespuesta == HttpURLConnection.HTTP_CREATED) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String inputLine;

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            // Convertir la respuesta en un objeto MesaDto
	            MesaDto mesa = mapper.readValue(response.toString(), MesaDto.class);

	            if (mesa != null) {
	                // Guardar el objeto MesaDto en la sesión
	                session.setAttribute("mesa", mesa);
	                return "success";
	            } else {
	                System.out.println("Error: La respuesta de la API no contiene una mesa válida.");
	                return "error";
	            }
	        }

	        return "error";
	    }
	    
	    public void cargarMesasDesdeApi() {
	        // Realizamos una solicitud GET al endpoint para obtener todas las mesas
	        Mono<MesaDto[]> response = webClient.get()
	                .uri("/api/mesas/obtenerMesas")  // Asegúrate de que el endpoint exista en tu controlador de mesas
	                .retrieve()
	                .bodyToMono(MesaDto[].class);

	        // Bloqueamos la respuesta para obtenerla de manera síncrona
	        MesaDto[] mesas = response.block();

	        // Si la respuesta contiene mesas, las procesamos
	        if (mesas != null) {
	            // Guardamos las mesas en la lista estática de la aplicación
	            Rutilandia2Application.listaMesas = Arrays.asList(mesas);
	            System.out.println("Mesas cargadas: " + Rutilandia2Application.listaMesas.size());

	            // Imprimimos la información de cada mesa cargada (esto es opcional)
	            for (MesaDto mesa : Rutilandia2Application.listaMesas) {
	                System.out.println(mesa.toString());
	            }
	        }
	    }





}
