package com.operaciones.bancarias.REPOSITORY;

import com.operaciones.bancarias.ENTITY.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, String> {


}
