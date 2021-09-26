package com.prueba.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseGetPedido implements Serializable {

	private static final long serialVersionUID = 4953406800778164220L;

	@JsonProperty("Inventario")
	private List<DataPedidoDto>inventario;

	public List<DataPedidoDto> getInventario() {
		return inventario;
	}

	public void setInventario(List<DataPedidoDto> inventario) {
		this.inventario = inventario;
	}

	
	
}
