package com.operaciones.bancarias.SERVICE;


import com.operaciones.bancarias.DTOS.ClienteDTO;
import com.operaciones.bancarias.DTOS.CuentaActualDTO;
import com.operaciones.bancarias.DTOS.CuentaAhorroDTO;
import com.operaciones.bancarias.DTOS.CuentaBancariaDTO;
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


    ClienteDTO saveCliente(ClienteDTO clienteDTO);

    ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException;

    ClienteDTO updateCliente(ClienteDTO clienteDTO) throws  ClienteNotFoundException;

    void deleteCliente(Long clienteId) throws  ClienteNotFoundException;

    CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    CuentaAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    List<ClienteDTO> listarClientes();

    CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException;

    void transfer(String cuentaIdPropetario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    List<CuentaBancariaDTO> listarCuentasBancarias();
}
