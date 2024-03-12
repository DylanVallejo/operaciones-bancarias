package com.operaciones.bancarias.SERVICE;


import com.operaciones.bancarias.ENTITY.Cliente;
import com.operaciones.bancarias.ENTITY.CuentaActual;
import com.operaciones.bancarias.ENTITY.CuentaAhorro;
import com.operaciones.bancarias.ENTITY.CuentaBancaria;
import com.operaciones.bancarias.EXCEPTIONS.BalanceInsuficienteException;
import com.operaciones.bancarias.EXCEPTIONS.ClienteNotFoundException;
import com.operaciones.bancarias.EXCEPTIONS.CuentaBancariaNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service   // esto q=creo que no vva aqui ****************////////////////////////****************
public interface CuentaBancariaService {


    Cliente saveCliente(Cliente cliente);

    CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    List<Cliente> listarClientes();

    CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;

    void transfer(String cuentaIdPropetario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    List<CuentaBancaria> listarCuentasBancarias();
}
