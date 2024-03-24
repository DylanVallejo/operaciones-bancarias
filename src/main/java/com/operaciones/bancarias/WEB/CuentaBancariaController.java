package com.operaciones.bancarias.WEB;

import com.operaciones.bancarias.DTOS.*;
import com.operaciones.bancarias.ENTITY.OperacionCuenta;
import com.operaciones.bancarias.EXCEPTIONS.BalanceInsuficienteException;
import com.operaciones.bancarias.EXCEPTIONS.CuentaBancariaNotFoundException;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/cuentas/pageOperaciones/{cuentaId}")
    public HistorialCuentaDTO listarHistorialCuentasPaginadas(@PathVariable String cuentaId, @RequestParam(name= "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "5") int size) throws  CuentaBancariaNotFoundException{
        return cuentaBancariaService.getHistorialCuenta(cuentaId, page, size);
    }
    @PostMapping("/cuentas/debito")
    public DebitoDTO realizarDebito(@RequestBody DebitoDTO debitoDTO)  throws BalanceInsuficienteException, CuentaBancariaNotFoundException{
        cuentaBancariaService.debit(debitoDTO.getCuentaId(), debitoDTO.getMonto(), debitoDTO.getDescripcion());
        return debitoDTO;

    }

    @PostMapping("/cuentas/credito")
    public CreditoDTO realizarCredito(@RequestBody CreditoDTO creditoDTO) throws CuentaBancariaNotFoundException {
        cuentaBancariaService.credit(creditoDTO.getCuentaId(), creditoDTO.getMonto(), creditoDTO.getDescripcion());
        return creditoDTO;

    }


    @PostMapping("/cuentas/transferencia")
    public ResponseEntity<?> realizarTransferencia(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) throws BalanceInsuficienteException, CuentaBancariaNotFoundException {
        TransferenciaRequestDTO transferenciaRequestDTOOptional =  cuentaBancariaService.transfer(transferenciaRequestDTO.getCuentaPropietario(), transferenciaRequestDTO.getCuentaDestiantario(), transferenciaRequestDTO.getMonto(), transferenciaRequestDTO.getDescripcion());

//       todo retornar un mensaje de transferencia exitosa
        return new ResponseEntity<>(transferenciaRequestDTOOptional, HttpStatus.OK);
//        return new ResponseEntity<>(transferenciaRequestDTOOptional, HttpStatus.OK,"transfrencia exitosa");
    }


}
