package com.operaciones.bancarias.DTOS;


import com.operaciones.bancarias.ENTITY.CuentaBancaria;
import com.operaciones.bancarias.ENUMS.EstadoCuenta;
import lombok.Data;

import java.util.Date;

@Data
public class CuentaAhorroDTO  extends CuentaBancariaDTO {

    private String id;
    private double balance;
    private Date fechaDeCreacion;
    private EstadoCuenta estadoCuenta;
    private ClienteDTO clienteDTO;
    private double tasaDeInteres;


}
