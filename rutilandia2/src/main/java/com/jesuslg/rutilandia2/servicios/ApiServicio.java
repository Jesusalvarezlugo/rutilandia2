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
import java.util.Map;

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
		System.out.println("Codigo:"+codigoRespuesta);
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
	    
	    /**
	     * Método para enviar los datos a la api para hacer el login
	     * @param usuario
	     * @return
	     * @throws URISyntaxException
	     * @throws IOException
	     */
	    public String enviarLoginUsuario(UsuarioDto usuario) throws URISyntaxException, IOException {
	        URI uri = new URI("http://localhost:8082/api/usuarios/login");
	        URL url = uri.toURL();

	        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
	        conexion.setRequestMethod("POST");
	        conexion.setRequestProperty("Content-Type", "application/json");
	        conexion.setDoOutput(true);

	        // Convertir el objeto usuario a JSON para enviarlo a la API
	        ObjectMapper mapper = new ObjectMapper();
	        String usuarioJson = mapper.writeValueAsString(usuario);
	        System.out.println("Enviando JSON al backend: " + usuarioJson);
	        // Enviar los datos al servidor
	        OutputStream os = conexion.getOutputStream();
	        os.write(usuarioJson.getBytes());
	        os.flush();

	        // Leer la respuesta del servidor
	        int codigoRespuesta = conexion.getResponseCode();
	        System.out.println("Código de respuesta: " + codigoRespuesta);

	        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
	            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
	            StringBuilder response = new StringBuilder();
	            String inputLine;

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            System.out.println("Respuesta de la API: " + response.toString());
	            // Extraer el token de la respuesta
	            Map<String, Object> responseMap = mapper.readValue(response.toString(), Map.class);
	            if (responseMap.containsKey("token")) {
	                return (String) responseMap.get("token");
	            }
	        }

	        return null; // Si la API no devuelve un token o hay un error
	    }
	 
	
	    /**
	     * Metodo para enviar a la api la mesa creada
	     * @param nuevaMesa
	     * @param session
	     * @return
	     * @throws URISyntaxException
	     * @throws IOException
	     */
	    public String enviarRegistroMesa(MesaDto nuevaMesa, HttpSession session) throws URISyntaxException, IOException {
	    	URI uri = new URI("http://localhost:8082/api/mesas/crearMesa");
			URL url = uri.toURL();
			
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type", "application/json");
			conexion.setDoOutput(true);
			
			// Pasar el dto a json para enviarlo a la api

			ObjectMapper mapper = new ObjectMapper();

			String dtoAJson = mapper.writeValueAsString(nuevaMesa);
			System.out.println(dtoAJson);
			
			//se envian los datos al servidor
			
			OutputStream os = conexion.getOutputStream();
			
			os.write(dtoAJson.getBytes());
			os.flush();

			// Leer la respuesta del servidor
			int codigoRespuesta = conexion.getResponseCode();
			System.out.println("Codigo:"+codigoRespuesta);
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
	    
	    
	    
	    public String eliminarMesaPorId(Long idMesa) throws URISyntaxException, IOException {
	        // Definir la URL de la API para eliminar usuario por ID
	        URI uri = new URI("http://localhost:8083/api/usuarios/eliminar/" + idMesa);
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

	            System.out.println("Mesa eliminada correctamente: " + response.toString());
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





}
