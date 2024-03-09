package com.operaciones.bancarias.SERVICE.impl;


import com.operaciones.bancarias.ENTITY.*;
import com.operaciones.bancarias.EXCEPTIONS.BalanceInsuficienteException;
import com.operaciones.bancarias.EXCEPTIONS.ClienteNotFoundException;
import com.operaciones.bancarias.EXCEPTIONS.CuentaBancariaNotFoundException;
import com.operaciones.bancarias.REPOSITORY.ClienteRepository;
import com.operaciones.bancarias.REPOSITORY.CuentaBancariaRepository;
import com.operaciones.bancarias.REPOSITORY.OperacionCuentaRepository;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CuentaBancariaServiceImp implements CuentaBancariaService {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return null;
    }

    @Override
    public CuentaActual saveCuentaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        return null;
    }

    @Override
    public CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        return null;
    }

    @Override
    public List<Cliente> listarClientes() {
        return null;
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        return null;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {

    }

    @Override
    public void transfer(String cuentaIdPropetario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

    }

    @Override
    public List<CuentaBancaria> listarCuentasBancarias() {
        return null;
    }
}
