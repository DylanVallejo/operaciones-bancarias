package com.operaciones.bancarias.WEB;

import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;



}
