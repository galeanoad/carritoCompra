package com.prueba.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestGetPedido implements Serializable {

	private static final long serialVersionUID = 7601934725393686245L;

	@JsonProperty("NumeroPedido")
	private Integer numeroPedido;

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	
	
}
