package com.operaciones.bancarias.WEB;

import com.operaciones.bancarias.DTOS.ClienteDTO;
import com.operaciones.bancarias.ENTITY.Cliente;
import com.operaciones.bancarias.ENTITY.CuentaBancaria;
import com.operaciones.bancarias.EXCEPTIONS.ClienteNotFoundException;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/clientes")
    public List<ClienteDTO> listarClientes(){
        return cuentaBancariaService.listarClientes();
    }


    @GetMapping("/clientes/{id}")
    public ClienteDTO listarDatosDelCliente(@PathVariable(name = "id") Long clienteId) throws ClienteNotFoundException{
        return cuentaBancariaService.getCliente(clienteId);
    }

    @PostMapping("/clientes")
    public ClienteDTO guardarCliente(@RequestBody ClienteDTO clienteDTO){
        return  cuentaBancariaService.saveCliente(clienteDTO);
    }


    @PutMapping("/clientes/{clienteId}")
    public ClienteDTO actualizarCliente(@PathVariable Long clienteId, @RequestBody ClienteDTO clienteDTO) throws ClienteNotFoundException {
        clienteDTO.setId(clienteId);
        return cuentaBancariaService.updateCliente(clienteDTO);

    }

    @DeleteMapping("/clientes/{clienteId}")
    public void eliminarCLiente(@PathVariable Long clienteId) throws ClienteNotFoundException{
        cuentaBancariaService.deleteCliente(clienteId);
    }

    @GetMapping("/clientes/search")
    public List<ClienteDTO> buscarClientes(@RequestParam(name = "keyword", defaultValue = "") String keyword){
            return cuentaBancariaService.searchClientes("%"+keyword+"%");
    }


}
