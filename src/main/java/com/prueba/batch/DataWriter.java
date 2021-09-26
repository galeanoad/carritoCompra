package com.prueba.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.prueba.entity.Inventario;
import com.prueba.repository.InventarioRepository;

/**
 * Esta clase realiza la persistencia del inventario 
 * @author Diego Galeano
 *
 */

public class DataWriter implements ItemWriter<Inventario>  {
	
	private static final Logger logger = LoggerFactory.getLogger(DataWriter.class);
	
	@Autowired
	InventarioRepository inventarioRepository;

	@Override
	public void write(List<? extends Inventario> items) throws Exception {
		try {
			List<Inventario> listaInventario = (List<Inventario>) items;
			
			logger.info("Iniciando persistencia Batch");
			
			for (int i = 0; i < listaInventario.size(); i++) {
				inventarioRepository.save(listaInventario.get(i));
			} 
			logger.info("Persistencia Finalizada");
		} catch (Exception e) {
			logger.error("Error persistiendo los registros. {}",e);
		}	
		
	}

}
