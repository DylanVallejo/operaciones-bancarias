package com.operaciones.bancarias;

import com.operaciones.bancarias.DTOS.ClienteDTO;
import com.operaciones.bancarias.ENTITY.*;
import com.operaciones.bancarias.ENUMS.EstadoCuenta;
import com.operaciones.bancarias.ENUMS.TipoOperacion;
import com.operaciones.bancarias.EXCEPTIONS.ClienteNotFoundException;
import com.operaciones.bancarias.REPOSITORY.ClienteRepository;
import com.operaciones.bancarias.REPOSITORY.CuentaBancariaRepository;
import com.operaciones.bancarias.REPOSITORY.OperacionCuentaRepository;
import com.operaciones.bancarias.SERVICE.BancoService;
import com.operaciones.bancarias.SERVICE.CuentaBancariaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BancariasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancariasApplication.class, args);
	}

//	@Bean
	CommandLineRunner commandLineRunner(BancoService bancoService){
		return args -> {
			bancoService.consultar();
		};
	}

//	@Bean
	CommandLineRunner start (CuentaBancariaService cuentaBancariaService){
//		llenamos datos
		return args -> {
			Stream.of("Christian", "Julen",  "Dylan", "Lanudo").forEach(nombre -> {
				ClienteDTO cliente = new ClienteDTO();
				cliente.setNombre(nombre);
				cliente.setEmail(nombre+"@gmail.com");
				cuentaBancariaService.saveCliente(cliente);


			});
			cuentaBancariaService.listarClientes().forEach(cliente -> 	{
				try{
					cuentaBancariaService.saveCuentaBancariaActual(Math.random() * 90000, 90000, cliente.getId());
					cuentaBancariaService.saveCuentaBancariaAhorro(120000,5.5, cliente.getId());

					List<CuentaBancaria> cuentasBancarias = cuentaBancariaService.listarCuentasBancarias();
					for(CuentaBancaria cuentaBancaria : cuentasBancarias){
						for(int i =0; i < 10; i++){
							cuentaBancariaService.credit(cuentaBancaria.getId(), 10000+Math.random()*120000,"Credito");
							cuentaBancariaService.debit(cuentaBancaria.getId(), 1000+Math.random()*9000,"Debito");
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			});

			//		asiganmos cuentas bancarias
//			clienteRepository.findAll().forEach(cliente -> {
//				CuentaActual cuentaActual = new CuentaActual();
//				cuentaActual.setId(UUID.randomUUID().toString());
//				cuentaActual.setBalance(Math.random() * 90000);;
//				cuentaActual.setFechaCreacion(new Date());
//				cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
//				cuentaActual.setCliente(cliente);
//				cuentaActual.setSobreGiro(9000);
//				cuentaBancariaRepository.save(cuentaActual);
//
//				CuentaAhorro cuentaAhorro = new CuentaAhorro();
//				cuentaAhorro.setId(UUID.randomUUID().toString());
//				cuentaAhorro.setBalance(Math.random() * 90000);;
//				cuentaAhorro.setFechaCreacion(new Date());
//				cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
//				cuentaAhorro.setCliente(cliente);
//				cuentaAhorro.setTasaDeInteres(5.5);
//				cuentaBancariaRepository.save(cuentaAhorro);
//			});

			//agregamos las operaciones

//			cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
//				for(int i = 0;  i < 10 ; i++){
//					OperacionCuenta operacionCuenta = new OperacionCuenta();
//					operacionCuenta.setFechaOperacion(new Date());
//					operacionCuenta.setMonto(Math.random() * 12000);
////					operacionCuenta.setTipoOperacion(Math.random() > 05 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);
//					operacionCuenta.setTipoOperacion( (int) ( 5 + Math.random() ) * (10 - 5 + 1) > 5 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);
////					(min + Math.random() * (max - min + 1)
//					operacionCuenta.setCuentaBancaria(cuentaBancaria);
//					operacionCuentaRepository.save(operacionCuenta);
//				}
//			});

		};
	}
}


