package com.prueba.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataPedidoDto implements Serializable{

	private static final long serialVersionUID = 339806027880215994L;
	
	@JsonProperty("Nombre")
	private String nombre;
	
	@JsonProperty("Marca")
	private String marca;
	
	@JsonProperty("Estado")
	private String estado;
	
	@JsonProperty("Precio")
	private Integer precio;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
		

	

}
