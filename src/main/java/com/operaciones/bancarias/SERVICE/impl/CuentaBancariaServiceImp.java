package com.operaciones.bancarias.SERVICE.impl;


import com.operaciones.bancarias.ENTITY.*;
import com.operaciones.bancarias.ENUMS.TipoOperacion;
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

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        log.info("Guardando un cliente nuevo.");
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return clienteBBDD;
    }

    @Override
    public CuentaActual saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }
        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobreGiro(sobregiro);
        cuentaActual.setCliente(cliente);

        CuentaActual cuentaActualBBDD = cuentaBancariaRepository.save(cuentaActual);
        return cuentaActualBBDD;
    }

    @Override
    public CuentaAhorro saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if (cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }
        CuentaAhorro cuentaAhorroActual = new CuentaAhorro();
        cuentaAhorroActual.setId(UUID.randomUUID().toString());
        cuentaAhorroActual.setFechaCreacion(new Date());
        cuentaAhorroActual.setBalance(balanceInicial);
        cuentaAhorroActual.setTasaDeInteres(tasaInteres);
        cuentaAhorroActual.setCliente(cliente);

        CuentaAhorro cuentaAhorroActualBBDD = cuentaBancariaRepository.save(cuentaAhorroActual);
        return cuentaAhorroActualBBDD;
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public CuentaBancaria getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(()  -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        return cuentaBancaria;
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId);
        if (cuentaBancaria.getBalance() < monto){
            throw new BalanceInsuficienteException("Blance insuficiente");
        }
        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = getCuentaBancaria(cuentaId);


        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);

        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void transfer(String cuentaIdPropetario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {

        debit(cuentaIdPropetario, monto, "trasferencia a: "+ cuentaIdDestinatario);
        credit(cuentaIdDestinatario, monto, "Transferencia de : " + cuentaIdPropetario);

//        esoty repitiendo codigo :c
//        ya existe las funciones para agregar o quitar dinero
//        CuentaBancaria cuentaBancariaPropietario = getCuentaBancaria(cuentaIdPropetario);
//
//
//        if (cuentaBancariaPropietario.getBalance() < monto){
//            throw new BalanceInsuficienteException("Blance insuficiente");
//        }
//// realizamos la operacion en la cuenta propetaria ******************
//
//
//        OperacionCuenta operacionCuentaPropietario = new OperacionCuenta();
//        operacionCuentaPropietario.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
//        operacionCuentaPropietario.setMonto(monto);
//        //        operacionCuenta.setDescripcion();
//        operacionCuentaPropietario.setFechaOperacion(new Date());
//        operacionCuentaPropietario.setCuentaBancaria(cuentaBancariaPropietario);
//        operacionCuentaRepository.save(operacionCuentaPropietario);
//        cuentaBancariaPropietario.setBalance(cuentaBancariaPropietario.getBalance() - monto);
//
////        realizamos la operacion en la cuenta destinataria  ******************
//
//        CuentaBancaria cuentaBancariaDestiantario = getCuentaBancaria(cuentaIdDestinatario);
//
//        OperacionCuenta operacionCuentaDestiantario = new OperacionCuenta();
//        operacionCuentaDestiantario.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
//        operacionCuentaDestiantario.setMonto(monto);
//        //        operacionCuenta.setDescripcion();
//        operacionCuentaDestiantario.setFechaOperacion(new Date());
//        operacionCuentaDestiantario.setCuentaBancaria(cuentaBancariaDestiantario);
//        operacionCuentaRepository.save(operacionCuentaDestiantario);
//        cuentaBancariaDestiantario.setBalance(cuentaBancariaPropietario.getBalance() + monto);

    }

    @Override
    public List<CuentaBancaria> listarCuentasBancarias() {
        return cuentaBancariaRepository.findAll();
    }
}
