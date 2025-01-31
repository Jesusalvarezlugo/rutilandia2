package com.jesuslg.rutilandia2.dtos;

public class UsuarioDto {
	 Long id;
	
	String nombre="aaaaa";
	String apellido1="aaaaa";
	String apellido2="aaaaa";
	String telefono="aaaaa";
	String email="aaaaa";
	String rol="aaaaa";
	String contrasenia="aaaaa";
	String repContrasenia="aaaaa";
	String apellidos="aaaaa";
	
	//Getters y Setters
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	
	
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	public String getRepContrasenia() {
		return repContrasenia;
	}
	public void setRepContrasenia(String repContrasenia) {
		this.repContrasenia = repContrasenia;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "UsuarioDto [nombre=" + nombre + ", telefono=" + telefono + ", email=" + email + ", rol=" + rol
				+ ", contrasenia=" + contrasenia + ", repContrasenia=" + repContrasenia + ", apellidos=" + apellidos
				+ "]";
	}

}
