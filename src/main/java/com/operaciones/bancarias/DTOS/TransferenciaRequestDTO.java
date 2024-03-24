package com.operaciones.bancarias.DTOS;


import lombok.Data;

@Data
public class TransferenciaRequestDTO {
    private String cuentaPropietario;
    private String cuentaDestiantario;
    private double monto;
    private String descripcion;
}
