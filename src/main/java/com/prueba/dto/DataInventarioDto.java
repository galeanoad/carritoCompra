package com.prueba.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataInventarioDto  implements Serializable {

	private static final long serialVersionUID = 6604780778347613668L;

	@JsonProperty("Id")
	private Integer id;
	
	@JsonProperty("Nombre")
	private String nombre;
	
	@JsonProperty("Marca")
	private String marca;
	
	@JsonProperty("Precio")
	private Integer precio;
	
	@JsonProperty("Stock")
	private Integer stock;
	
	@JsonProperty("Estado")
	private String estado;
	
	@JsonProperty("Descuento")
	private Integer descuento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}
	
	
}
