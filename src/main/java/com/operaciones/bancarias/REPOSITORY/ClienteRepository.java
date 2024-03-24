package com.operaciones.bancarias.REPOSITORY;

import com.operaciones.bancarias.ENTITY.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
//creamos un query personalizado para la consulta
    @Query("SELECT C FROM Cliente C WHERE C.nombre LIKE :kw")
    List<Cliente> searchClientes(@Param(value = "kw") String  keyword);

}
