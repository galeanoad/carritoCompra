package com.prueba.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prueba.dto.IConsultaInventario;
import com.prueba.entity.Inventario;

/**
 * Interfaz de la entidad Inventario
 * @author Diego Galeano
 *
 */

@Transactional
@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Integer> {
	
    @Query(value="SELECT i.id AS id,i.nombre AS nombre,m.marca AS marca, i.precio AS precio,"
    		+ " i.stock AS stock, e.estado AS estado, i.porcentaje_descuento AS descuento"
    		+ " FROM inventario i "
    		+ " INNER JOIN marca m ON i.marca_id = m.id "
    		+ " INNER JOIN estado e ON i.estado_id = e.id "
    		+ " WHERE nombre like %?1%"
    		+ " AND precio BETWEEN ?2 AND ?3 "
    		+ " AND marca_id like %?4",nativeQuery=true)
    public List<IConsultaInventario>  getInventario(String nombre, int montoMinimo, int montoMaximo, String marca);

    @Query(value="SELECT i.id AS id,i.nombre AS nombre,m.marca AS marca, i.precio AS precio,"
    		+ " i.stock AS stock, e.estado AS estado, i.porcentaje_descuento AS descuento"
    		+ " FROM inventario i "
    		+ " INNER JOIN marca m ON i.marca_id = m.id "
    		+ " INNER JOIN estado e ON i.estado_id = e.id "
    		+ " WHERE i.id = ?1",nativeQuery=true)
	public IConsultaInventario consultaStock(int id);
	
	@Modifying
    @Query(value="UPDATE inventario i "
    		+ " SET stock = ?1"
    		+ " WHERE i.id = ?2",nativeQuery=true)
	public int updateStock(int stock, int id);
}
