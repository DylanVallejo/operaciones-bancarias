package com.operaciones.bancarias.SERVICE;



import com.operaciones.bancarias.ENTITY.CuentaActual;
import com.operaciones.bancarias.ENTITY.CuentaAhorro;
import com.operaciones.bancarias.ENTITY.CuentaBancaria;
import com.operaciones.bancarias.REPOSITORY.CuentaBancariaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;

@Service
@Transactional
public class BancoService {

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;


    public void consultar(){
        CuentaBancaria cuentaBancariaBBDD = cuentaBancariaRepository.findById("6e946d05-837c-4913-9651-44ebf8f90a92").orElse(null);
        
        if (cuentaBancariaBBDD != null){
            System.out.println("********************");
            System.out.println(" Id : "  + cuentaBancariaBBDD.getId());
            System.out.println(" Balance de la cuenta: " + cuentaBancariaBBDD.getBalance());
            System.out.println(" Estado: " +cuentaBancariaBBDD.getEstadoCuenta());
            System.out.println(" Fecha de creación:  "+cuentaBancariaBBDD.getFechaCreacion());
            System.out.println(" Cliente:  "+cuentaBancariaBBDD.getCliente().getNombre());
            System.out.println("Nombre de la clase: " + cuentaBancariaBBDD.getClass().getSimpleName());
            if(cuentaBancariaBBDD instanceof CuentaActual){
                System.out.println(" Sobregiro:  " + ( (CuentaActual) cuentaBancariaBBDD).getSobreGiro());

            } else if (cuentaBancariaBBDD instanceof CuentaAhorro) {
                System.out.println(" Tasa de interes: " + ((CuentaAhorro) cuentaBancariaBBDD).getTasaDeInteres());

            }


            cuentaBancariaBBDD.getOperacionesCuenta().forEach(operacionCuenta -> {
                System.out.println(" -----------------------------" );
                System.out.println(" Tipo de Operacion : " + operacionCuenta.getTipoOperacion() );
                System.out.println(" Fecha de operacion " + operacionCuenta.getFechaOperacion() );
                System.out.println(" Monto " + operacionCuenta.getMonto() );
            });
        }
    }
}
