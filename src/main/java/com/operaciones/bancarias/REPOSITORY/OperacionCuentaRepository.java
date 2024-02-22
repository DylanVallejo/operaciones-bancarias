package com.operaciones.bancarias.REPOSITORY;

import com.operaciones.bancarias.ENTITY.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long>{
}
