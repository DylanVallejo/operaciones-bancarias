package com.operaciones.bancarias.ENTITY;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nombre;

    private String email;

//    un cliente puede tener varias cuentas bancarias
//    indicamos que sera mapeada por la tabla cliente
    @OneToMany(  mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<CuentaBancaria> cuentaBancarias;


}
