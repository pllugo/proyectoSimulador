package com.egg.mypkg.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.egg.mypkg.entidades.Acidificacion;
import com.egg.mypkg.entidades.Compuesto;

@Repository
public interface AcidificacionRepositorio extends JpaRepository<Acidificacion, String> {

	@Query("SELECT a.pesoMolecular from Acidificacion a WHERE a.id = :id")
	public Double buscarPesoMolecular(@Param("id") String id);
	
	
	
}
