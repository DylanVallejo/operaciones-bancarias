package com.operaciones.bancarias.WEB;

import com.operaciones.bancarias.ENTITY.Cliente;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
//@CrossOrigin("*")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return cuentaBancariaService.listarClientes();
    }

}
