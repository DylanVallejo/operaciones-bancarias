package com.operaciones.bancarias.REPOSITORY;

import com.operaciones.bancarias.ENTITY.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long>{
}
