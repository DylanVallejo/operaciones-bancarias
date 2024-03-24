package com.operaciones.bancarias.WEB;

import com.operaciones.bancarias.DTOS.CuentaBancariaDTO;
import com.operaciones.bancarias.DTOS.OperacionCuentaDTO;
import com.operaciones.bancarias.ENTITY.OperacionCuenta;
import com.operaciones.bancarias.EXCEPTIONS.CuentaBancariaNotFoundException;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;


    @GetMapping("/cuentas/{cuentaId}")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBnacaria(@PathVariable String cuentaId) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getCuentaBancaria(cuentaId);
    }

    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias(){
        return cuentaBancariaService.listarCuentasBancarias();
    }

    @GetMapping("/cuentas/historial/{cuentaId}")
    public List<OperacionCuentaDTO> listarHistorialCuentas(@PathVariable String cuentaId){
        return cuentaBancariaService.historialDeCuenta(cuentaId);
    }



}
