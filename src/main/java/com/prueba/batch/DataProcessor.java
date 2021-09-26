package com.prueba.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.prueba.dto.DataDto;
import com.prueba.entity.Inventario;

/**
 * Esta clase valida la estructura del archivo
 * @author Diego Galeano
 *
 */

@Service
@StepScope
public class DataProcessor implements ItemProcessor<DataDto, Inventario>{

	private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
	
	@Override
	public Inventario process(DataDto item) throws Exception {
	
		Inventario detailRow = new Inventario();
		
		try {
			if(item.getNombre().isEmpty() ||
			item.getMarca().isEmpty() ||
			item.getPrecio() == null ||
			item.getStock() == null ||
			item.getEstado().isEmpty() ||
			item.getDescuento() == null) {
				logger.info("Faltan parametros para la carga del producto: {}",item.getNombre());
				return null;
			}
			
			detailRow.setNombre(item.getNombre());
			detailRow.setMarca_id(validMarca(item.getMarca()));
			detailRow.setPrecio(item.getPrecio());
			detailRow.setStock(item.getStock());
			detailRow.setEstado_id(validEstado(item.getEstado()));
			detailRow.setPorcentaje_descuento(item.getDescuento());
						
		} catch (Exception e) {
			logger.error("Error procesando el item: {}", item);
		}

		return detailRow;
	}
	
	/**
	 * Metodo que valida la marca del producto
	 * @param marca
	 * @return
	 */
	public int validMarca(String marca) {
		int resultado = 0;
		try {
			switch (marca) {
			case "HTC":
				resultado = 1;
				break;
			case "Huawey":
				resultado = 2;
				break;
			case "Apple":
				resultado = 3;
				break;
			case "motorola":
				resultado = 4;
				break;
			case "Samsung":
				resultado = 5;
				break;
			case "LG":
				resultado = 6;
				break;
			default:
				resultado = 7;
				break;
			}			
			
		} catch (Exception e) {
			logger.error("Error realizando la validacion de la marca. {}",e);
		}
		return resultado;
	}
	
	/**
	 * Metodo que valida el estado del producto
	 * @param estado
	 * @return
	 */
	public int validEstado(String estado) {
		int resultado = 0;
		try {
			switch (estado) {
			case "Nuevo":
				resultado = 1;
				break;
			case "Usado":
				resultado = 2;
				break;
			default:
				resultado = 3;
				break;
			}			
			
		} catch (Exception e) {
			logger.error("Error realizando la validacion del estado. {}",e);
		}
		return resultado;
	}

}
