package com.jesuslg.rutilandia2.dtos;
/**
 * Clase de la entidad mesa
 */
public class MesaDto {
	
	//Atributos
	
		Long idMesa;
		
		String nombreMesa="aaaaa";
		String descripcionMesa="aaaaa";
		
		public String getNombreMesa() {
			return nombreMesa;
		}
		public void setNombreMesa(String nombreMesa) {
			this.nombreMesa = nombreMesa;
		}
		public String getDescripcionMesa() {
			return descripcionMesa;
		}
		public void setDescripcionMesa(String descripcionMesa) {
			this.descripcionMesa = descripcionMesa;
		}
		
		public Long getIdMesa() {
			return idMesa;
		}
		public void setIdMesa(Long id) {
			this.idMesa= id;
		}
		

}
