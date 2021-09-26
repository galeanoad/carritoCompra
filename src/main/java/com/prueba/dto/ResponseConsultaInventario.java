package com.prueba.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseConsultaInventario implements Serializable {

	private static final long serialVersionUID = -9186168603457937728L;
	
	@JsonProperty("Inventario")
	private List<DataInventarioDto>inventario;

	public List<DataInventarioDto> getInventario() {
		return inventario;
	}

	public void setInventario(List<DataInventarioDto> inventario) {
		this.inventario = inventario;
	}

}
