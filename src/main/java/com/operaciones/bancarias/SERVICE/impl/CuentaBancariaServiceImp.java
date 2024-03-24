package com.operaciones.bancarias.SERVICE.impl;


import com.operaciones.bancarias.DTOS.*;
import com.operaciones.bancarias.ENTITY.*;
import com.operaciones.bancarias.ENUMS.TipoOperacion;
import com.operaciones.bancarias.EXCEPTIONS.BalanceInsuficienteException;
import com.operaciones.bancarias.EXCEPTIONS.ClienteNotFoundException;
import com.operaciones.bancarias.EXCEPTIONS.CuentaBancariaNotFoundException;
import com.operaciones.bancarias.MAPPERS.CuentaBancariaMapperImpl;
import com.operaciones.bancarias.REPOSITORY.ClienteRepository;
import com.operaciones.bancarias.REPOSITORY.CuentaBancariaRepository;
import com.operaciones.bancarias.REPOSITORY.OperacionCuentaRepository;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private CuentaBancariaMapperImpl cuentaBancariaMapper;

    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        log.info("Guardando un cliente nuevo.");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(()-> new ClienteNotFoundException("Cliente no encontrado"));
        return cuentaBancariaMapper.mapearDeCliente(cliente);

    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) throws ClienteNotFoundException {
        log.info("actualizando cliente");
//        ).orElseThrow(()-> new ClienteNotFoundException("Cliente no existe"));
        if(clienteRepository.findById(clienteDTO.getId()).isPresent()){
            Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
            Cliente clienteActualizado = clienteRepository.save(cliente);
            return cuentaBancariaMapper.mapearDeCliente(clienteActualizado);
        }else {
            throw new ClienteNotFoundException("Cliente no encontrado.");
        }
    }

    @Override
    public void deleteCliente(Long clienteId) throws ClienteNotFoundException {

        if(clienteRepository.findById(clienteId).isPresent()) {
            clienteRepository.deleteById(clienteId);
        }else {
            throw new ClienteNotFoundException(" El cliente con el id : " + clienteId + " no existe. ");
        }

    }

//    @Override
//    private ClienteDTO updateCliente(Long clienteId ) throws  ClienteNotFoundException{
//        Cliente cliente = clienteRepository.findById(clienteId)
//                .orElseThrow(()-> new ClienteNotFoundException("Cliente no ecnotrado"));
//        Cliente clienteActualizado = new Cliente();
//
//
//        return
//
//    }

    @Override
    public CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
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
        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActualBBDD);
    }

    @Override
    public CuentaAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
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
        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorroActualBBDD) ;
    }

    @Override
    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDtos = clientes.stream()
                .map(cliente-> cuentaBancariaMapper.mapearDeCliente(cliente))
                .collect(Collectors.toList());

        return clientesDtos;
    }

    @Override
    public CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(()  -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
        if(cuentaBancaria instanceof CuentaAhorro){
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
        }else {
            CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual) ;
        }


    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(()  -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));
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
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(()  -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));


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
    public List<CuentaBancariaDTO> listarCuentasBancarias() {
        List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findAll();
        List<CuentaBancariaDTO> cuentasBancariasDTOS = cuentasBancarias.stream().map(cuentaBancaria -> {
            if(cuentaBancaria instanceof CuentaAhorro){
                CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
                return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
            } else {
                CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
                return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual) ;
            }
        }).collect(Collectors.toList());
        return cuentasBancariasDTOS;
    }

    @Override
    public List<OperacionCuentaDTO> historialDeCuenta(String cuentaId) {
//        retornando todas las opereaciones que se han realizado en una cuenta bancaria
        List<OperacionCuenta> operacionesDeCuentas = operacionCuentaRepository.findByCuentaBancariaId(cuentaId);
        return operacionesDeCuentas.stream().map(operacionCuenta ->
                cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta)
        ).collect(Collectors.toList());
    }

    @Override
    public HistorialCuentaDTO getHistorialCuenta(String cuentaId, int page, int size) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElse(null);

        if(cuentaBancaria == null){
            throw new CuentaBancariaNotFoundException("Cuenta no encontrada");
        }
        Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancariaId(cuentaId, PageRequest.of(page, size));
        HistorialCuentaDTO historialCuentaDTO = new HistorialCuentaDTO();
        List<OperacionCuentaDTO> operacionesCuentaDTOS = operacionesCuenta.getContent().stream()
                .map(operacionCuenta -> cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta)).collect(Collectors.toList());
        historialCuentaDTO.setOperacionCuentaDTOS(operacionesCuentaDTOS);
        historialCuentaDTO.setCuentaId(cuentaBancaria.getId());
        historialCuentaDTO.setBalance(cuentaBancaria.getBalance());
        historialCuentaDTO.setCurrentPage(page);
        historialCuentaDTO.setPageSize(size);
        historialCuentaDTO.setTotalPage(operacionesCuenta.getTotalPages());

        return historialCuentaDTO;
    }


}
