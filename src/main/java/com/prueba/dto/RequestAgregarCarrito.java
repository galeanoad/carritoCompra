package com.prueba.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAgregarCarrito implements Serializable{

	private static final long serialVersionUID = 1163851910888590800L;

	@JsonProperty("IdInventario")
	private Integer idInventario;
	
	@JsonProperty("NumeroPedido")
	private Integer numeroPedido;

	public Integer getIdInventario() {
		return idInventario;
	}

	public void setIdInventario(Integer idInventario) {
		this.idInventario = idInventario;
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	
	
}
