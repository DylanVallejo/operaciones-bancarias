package com.operaciones.bancarias.ENTITY;


import com.operaciones.bancarias.ENUMS.TipoOperacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OperacionCuenta {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaOperacion;


    private double monto;

    @Enumerated(EnumType.STRING)
    private TipoOperacion tipoOperacion;

    @ManyToOne
    private CuentaBancaria cuentaBancaria;


}
