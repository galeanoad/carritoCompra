package com.prueba.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prueba.dto.IConsultaPedido;
import com.prueba.entity.Pedido;

/**
 * Interfaz de la entidad Pedido
 * @author Diego Galeano
 *
 */

@Transactional
@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Integer>{
		
    @Query(value="SELECT p.nombre AS nombre,p.marca AS marca, p.precio AS precio,p.estado AS estado, p.inventario_id AS idInventario"
    		+ " FROM pedido p "
    		+ " WHERE numero_pedido = ?1",nativeQuery=true)
    public List<IConsultaPedido>  getProductos(int numeroPedido);

	@Modifying
    @Query(value="DELETE FROM pedido p "
    		+ " WHERE numero_pedido = ?1",nativeQuery=true)
	public int deleteByNumeroPedido(int numeroPedido);

}
