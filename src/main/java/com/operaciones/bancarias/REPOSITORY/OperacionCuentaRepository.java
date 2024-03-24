package com.operaciones.bancarias.REPOSITORY;

import com.operaciones.bancarias.ENTITY.OperacionCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperacionCuentaRepository extends JpaRepository<OperacionCuenta, Long>{

    List<OperacionCuenta> findByCuentaBancariaId(String cuentaId);
}
