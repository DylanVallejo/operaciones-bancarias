package com.operaciones.bancarias.ENTITY;


import com.operaciones.bancarias.ENUMS.EstadoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  //nos permite tener herencia en la clase para poder crear otro tipo de cuenta
@DiscriminatorColumn(name = "TIPO", length = 4) //
public class CuentaBancaria {


    @Id
    private String id;


    private double balance;

    private Date fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;


    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria")
    private List<OperacionCuenta> operacionesCuenta;


}
