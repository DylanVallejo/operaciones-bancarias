package com.operaciones.bancarias.DTOS;


import com.operaciones.bancarias.ENUMS.EstadoCuenta;
import lombok.Data;

import java.util.Date;

@Data
public class CuentaActualDTO extends  CuentaBancariaDTO{

    private String id;
    private double balance;
    private Date fechaCreacion;
    private EstadoCuenta estadoCuenta;
    private ClienteDTO clienteDTO;
    private double sobregiro;


}
