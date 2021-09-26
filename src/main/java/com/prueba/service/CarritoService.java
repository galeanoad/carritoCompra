package com.prueba.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prueba.dto.DataInventarioDto;
import com.prueba.dto.DataPedidoDto;
import com.prueba.dto.IConsultaInventario;
import com.prueba.dto.IConsultaPedido;
import com.prueba.dto.RequestAgregarCarrito;
import com.prueba.dto.RequestConsultaInventario;
import com.prueba.dto.RequestGetPedido;
import com.prueba.dto.ResponseConsultaInventario;
import com.prueba.dto.ResponseGetPedido;
import com.prueba.entity.Inventario;
import com.prueba.entity.Pedido;
import com.prueba.repository.InventarioRepository;
import com.prueba.repository.PedidoRepository;

/**
 * Esta clase implementa el servicio del proyecto. 
 * @author Diego Galeano
 *
 */

@Service
public class CarritoService {
	
	private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);
	
	@Autowired
	InventarioRepository inventarioRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	/**
	 * Metodo que obtiene listado del inventario
	 * @param consulta
	 * @return
	 */
	public List<ResponseConsultaInventario> getInventario(RequestConsultaInventario consulta){
		
		ResponseConsultaInventario consultaInventario = new ResponseConsultaInventario();
		List<ResponseConsultaInventario> response = new ArrayList<>();			
		List<DataInventarioDto> listDataDto = new ArrayList<DataInventarioDto>();
		
		try {
			
			int minimo = consulta.getMontoMinimo() == null ? 0 : consulta.getMontoMinimo();
			int maximo = consulta.getMontoMaximo() == null ? 999999999 : consulta.getMontoMaximo() ;
			
			List<IConsultaInventario> listInventario = inventarioRepository.getInventario(consulta.getNombre(),
					minimo,maximo,validMarca(consulta.getMarca()));
			
			listInventario.forEach(lInventario ->{
					
				DataInventarioDto dto = new DataInventarioDto();
				dto.setId(lInventario.getId());
				dto.setNombre(lInventario.getNombre());
				dto.setPrecio(lInventario.getPrecio());
				dto.setStock(lInventario.getStock());
				dto.setEstado(lInventario.getEstado());
				dto.setDescuento(lInventario.getDescuento());
				dto.setMarca(lInventario.getMarca());

				listDataDto.add(dto);
				consultaInventario.setInventario(listDataDto);					
			});			
			response.add(consultaInventario);	

			return response;
		} catch (Exception e) {
			logger.error("Error obteniendo inventario. {}",e);
			return null;
		}
	}
	
	/**
	 * Metodo que valida la marca del producto
	 * @param marca
	 * @return
	 */
	public String validMarca(String marca) {
		String resultado = "0";
		try {
			switch (marca) {
			case "HTC":
				resultado = "1";
				break;
			case "Huawey":
				resultado = "2";
				break;
			case "Apple":
				resultado = "3";
				break;
			case "motorola":
				resultado = "4";
				break;
			case "Samsung":
				resultado = "5";
				break;
			case "LG":
				resultado = "6";
				break;
			case "":
				resultado = "";
				break;
			default:
				resultado = "7";
				break;
			}			
			
		} catch (Exception e) {
			logger.error("Error realizando la validacion de la marca. {}",e);
		}
		return resultado;
	}
		
	/**
	 * Metodo que persiste los productos seleccionados al carro de compras
	 * @param consulta
	 * @return
	 */
	public String guardarProducto(RequestAgregarCarrito consulta){
		
		Pedido pedido = new Pedido();
		String response = "";			
		
		try {
			IConsultaInventario stock =inventarioRepository.consultaStock(consulta.getIdInventario());
			
			if (stock.getStock() !=0) {
				pedido.setInventario_id(consulta.getIdInventario());
				pedido.setNumeroPedido(consulta.getNumeroPedido());
				pedido.setEstado(stock.getEstado());
				pedido.setPrecio(stock.getPrecio());
				pedido.setMarca(stock.getMarca());
				pedido.setNombre(stock.getNombre());				
				
				pedidoRepository.save(pedido);		
				
				response = "Producto Agregado";
			}else {
				logger.info("No hay Stock para el producto : {}",consulta.getIdInventario());
				response = "No hay Stock";
			}
		} catch (Exception e) {
			logger.error("Error obteniendo inventario. {}",e);
			response = "Ocurrio un Error";;
		}		
		return response;
	}
	
	/**
	 * Metodo que consulta el listado de articulos por pedido
	 * @param pedido
	 * @return
	 */
	public List<ResponseGetPedido> getPedido(RequestGetPedido pedido){
			
			ResponseGetPedido consultaInventario = new ResponseGetPedido();
			List<ResponseGetPedido> response = new ArrayList<>();			
			List<DataPedidoDto> listDataDto = new ArrayList<DataPedidoDto>();
			
			try {				
				List<IConsultaPedido> listInventario = pedidoRepository.getProductos(pedido.getNumeroPedido());
				
				listInventario.forEach(lInventario ->{
						
					DataPedidoDto dto = new DataPedidoDto();
					dto.setNombre(lInventario.getNombre());
					dto.setMarca(lInventario.getMarca());
					dto.setEstado(lInventario.getEstado());
					dto.setPrecio(lInventario.getPrecio());				
	
					listDataDto.add(dto);
					consultaInventario.setInventario(listDataDto);						
				});				
				response.add(consultaInventario);	
	
				return response;
			} catch (Exception e) {
				logger.error("Error obteniendo pedido. {}",e);
				return null;
			}
		}
	
	/**
	 * Metodo que elimina los productos del carro de compra
	 * @param numeroPedido
	 * @return
	 */
	public String vaciarCarro(int numeroPedido){		
		String response = "";	
			
		try {
			pedidoRepository.deleteByNumeroPedido(numeroPedido);	
			
			response = "Carro vacio";
		} catch (Exception e) {
			logger.error("Error eliminando pedido en el carro de compra. {}",e);
			response = "Ocurrio un Error";;
		}		
		return response;
	}
		
	/**
	 * Metodo que finaliza la compra 
	 * @param numeroPedido
	 * @return
	 */
	public String finalizarCompra(int numeroPedido){		
		String response = "";	
			
		try {			
			List<IConsultaPedido> listInventario = pedidoRepository.getProductos(numeroPedido);
			
			listInventario.forEach(lInventario ->{
				
				Optional<Inventario> producto = inventarioRepository.findById(lInventario.getIdInventario());
				
				if(producto.get().getStock() != 0) {
					int temp = producto.get().getStock() -1;
					inventarioRepository.updateStock(temp, lInventario.getIdInventario());
				}else {
					logger.info("Stock en cero");
				}				
			});
			
			pedidoRepository.deleteByNumeroPedido(numeroPedido);	
			
			response = "Compra finalizada";
		} catch (Exception e) {
			logger.error("Error finalizarCompra. {}",e);
			response = "Ocurrio un Error";;
		}		
		return response;
	}
	
}