package com.operaciones.bancarias.ENTITY;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@DiscriminatorValue("SA")  // SA = SAVING ACCOUNT
@NoArgsConstructor
@AllArgsConstructor
public class CuentaAhorro extends CuentaBancaria{


    private double tasaDeInteres;


}
