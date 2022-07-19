package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Compuesto;

@Repository
public interface CompuestoRepositorio extends CrudRepository<Compuesto, String> {

	
	@Query("SELECT c from Compuesto c WHERE c.nombre=:nombre")
	public Compuesto buscarPorNombre(@Param("nombre") String nombre);
}
