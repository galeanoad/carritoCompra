package com.prueba.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestConsultaInventario implements Serializable {

	private static final long serialVersionUID = -5429231704404056814L;
	
	@JsonProperty("Nombre")
	private String nombre;
	
	@JsonProperty("MontoMinimo")
	private Integer montoMinimo;
	
	@JsonProperty("MontoMaximo")
	private Integer montoMaximo;

	@JsonProperty("Marca")
	private String marca;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(Integer montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	public Integer getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(Integer montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
}
