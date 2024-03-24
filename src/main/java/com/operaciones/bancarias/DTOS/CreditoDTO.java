package com.operaciones.bancarias.DTOS;

import lombok.Data;

@Data
public class CreditoDTO {
    private String cuentaId;
    private double  monto;
    private String descripcion;
}
