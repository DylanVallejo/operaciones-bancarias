package com.operaciones.bancarias.ENTITY;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.bytecode.enhance.spi.EnhancementInfo;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("CA") //CA = CURRENT ACOUNT CUENTA ACTUAL
public class CuentaActual extends CuentaBancaria {


    private double sobreGiro;
}
