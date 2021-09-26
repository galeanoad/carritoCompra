package com.prueba.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.batch.BatchConfiguration;
import com.prueba.dto.RequestAgregarCarrito;
import com.prueba.dto.RequestConsultaInventario;
import com.prueba.dto.RequestGetPedido;
import com.prueba.dto.ResponseConsultaInventario;
import com.prueba.dto.ResponseGetPedido;
import com.prueba.dto.Status;
import com.prueba.service.CarritoService;

/**
 * Esta clase es la implementacion de la Api se encarga de ejecutar el servicio
 * y crear la respuesta del mismo.
 * @author Diego Galeano
 *
 */

@RestController
public class CarritoController {
	
	private static final Logger logger = LoggerFactory.getLogger(CarritoController.class);

	@Autowired
	CarritoService carritoService;

	@Autowired
	BatchConfiguration runBatch;
	
	@GetMapping("/cargaArchivo")
	public ResponseEntity<Object> uploadFiles() {
		
		HttpHeaders headers = new HttpHeaders();
		Status status = new Status();
		try {			
			logger.info("ingreso a servicio");
			
			runBatch.lanzarBatch();
			status.setResponse("Proceso Terminado");

			return new ResponseEntity<>(status, headers, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error ejecutando batch Carga Archivo. {}",e);
			status.setResponse(e.getMessage());
			return new ResponseEntity<>(status, headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/consultaInventario")
	private ResponseEntity<?> consultaInv(@RequestBody RequestConsultaInventario consulta) {
		
		HttpHeaders headers = new HttpHeaders();
		Status status = new Status();
		try {
			logger.info("Inicio consulta Inventario");
			
			List<ResponseConsultaInventario> responseConsulta = carritoService.getInventario(consulta);
			return new ResponseEntity<>(responseConsulta, headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error consultando el inventario. {}",e);
			status.setResponse(e.getMessage());
			return new ResponseEntity<>(status, headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/agregarCarrito")
	private ResponseEntity<?> agregarCarrito(@RequestBody RequestAgregarCarrito producto) {
		
		HttpHeaders headers = new HttpHeaders();
		Status status = new Status();
		try {
			logger.info("Inicio agregar producto al carro de compras");
			
			String responseConsulta = carritoService.guardarProducto(producto);
			status.setResponse(responseConsulta);
		
			return new ResponseEntity<>(status, headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error agregando al carro de compras. {}",e);
			status.setResponse(e.getMessage());
			return new ResponseEntity<>(status, headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/getPedido")
	private ResponseEntity<?> getCarro(@RequestBody RequestGetPedido producto) {
		
		HttpHeaders headers = new HttpHeaders();
		Status status = new Status();
		try {
			logger.info("Inicio consulta pedido");
			
			List<ResponseGetPedido>  responseCarro = carritoService.getPedido(producto);		
			return new ResponseEntity<>(responseCarro, headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error consultando Pedido. {}",e);
			status.setResponse(e.getMessage());
			return new ResponseEntity<>(status, headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@DeleteMapping("/deletePedido/{numeroPedido}")
	private ResponseEntity<?> vaciarCarro(@PathVariable int numeroPedido) {
		
		HttpHeaders headers = new HttpHeaders();
		Status status = new Status();
		try {			
			String response = carritoService.vaciarCarro(numeroPedido);	
			status.setResponse(response);
			
			return new ResponseEntity<>(status, headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error eliminando pedido. {}",e);
			status.setResponse(e.getMessage());
			return new ResponseEntity<>(status, headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/finalizarPedido")
	private ResponseEntity<?> finalizarPedido(@RequestBody RequestGetPedido producto) {
		
		HttpHeaders headers = new HttpHeaders();
		Status status = new Status();
		try {
			logger.info("Finalizando compra");
			
			String response = carritoService.finalizarCompra(producto.getNumeroPedido());			
			status.setResponse(response);
			
			return new ResponseEntity<>(status, headers, HttpStatus.CREATED);
			
		} catch (Exception e) {
			logger.error("Error finalizando compra. {}",e);
			status.setResponse(e.getMessage());
			return new ResponseEntity<>(status, headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
