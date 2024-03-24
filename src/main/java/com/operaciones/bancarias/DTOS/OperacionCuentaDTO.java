package com.operaciones.bancarias.DTOS;


import com.operaciones.bancarias.ENUMS.TipoOperacion;
import lombok.Data;

import java.util.Date;

@Data
public class OperacionCuentaDTO {

    private Long id;
    private Date fechaOperacion;
    private double monto;
    private TipoOperacion tipoOperacion;
    private String descripcion;

}
