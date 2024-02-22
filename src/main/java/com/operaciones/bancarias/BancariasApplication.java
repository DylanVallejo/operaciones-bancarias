package com.operaciones.bancarias;

import com.operaciones.bancarias.ENTITY.*;
import com.operaciones.bancarias.ENUMS.EstadoCuenta;
import com.operaciones.bancarias.ENUMS.TipoOperacion;
import com.operaciones.bancarias.REPOSITORY.ClienteRepository;
import com.operaciones.bancarias.REPOSITORY.CuentaBancariaRepository;
import com.operaciones.bancarias.REPOSITORY.OperacionCuentaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BancariasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancariasApplication.class, args);
	}

	@Bean
	CommandLineRunner start (ClienteRepository clienteRepository, CuentaBancariaRepository cuentaBancariaRepository, OperacionCuentaRepository operacionCuentaRepository){
//		llenamos datos
		return args -> {
			Stream.of("Christian", "Julen",  "Dylan", "Lanudo").forEach(nombre -> {
				Cliente cliente = new Cliente();
				cliente.setNombre(nombre);;
				cliente.setEmail(nombre+"@gmail.com");
				clienteRepository.save(cliente);
			});

			//		asiganmos cuentas bancarias
			clienteRepository.findAll().forEach(cliente -> {
				CuentaActual cuentaActual = new CuentaActual();
				cuentaActual.setId(UUID.randomUUID().toString());
				cuentaActual.setBalance(Math.random()* 90000);;
				cuentaActual.setFechaCreacion(new Date());
				cuentaActual.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaActual.setCliente(cliente);
				cuentaActual.setSobreGiro(9000);
				cuentaBancariaRepository.save(cuentaActual);

				CuentaAhorro cuentaAhorro = new CuentaAhorro();
				cuentaAhorro.setId(UUID.randomUUID().toString());
				cuentaAhorro.setBalance(Math.random()* 90000);;
				cuentaAhorro.setFechaCreacion(new Date());
				cuentaAhorro.setEstadoCuenta(EstadoCuenta.CREADA);
				cuentaAhorro.setCliente(cliente);
				cuentaAhorro.setTasaDeInteres(5.5);
				cuentaBancariaRepository.save(cuentaAhorro);
			});

			//agregamos las operaciones

			cuentaBancariaRepository.findAll().forEach(cuentaBancaria -> {
				for(int i = 0;  i < 10 ; i++){
					OperacionCuenta operacionCuenta = new OperacionCuenta();
					operacionCuenta.setFechaOperacion(new Date());
					operacionCuenta.setMonto(Math.random() * 12000);
					operacionCuenta.setTipoOperacion(Math.random() > 05 ? TipoOperacion.DEBITO : TipoOperacion.CREDITO);
					operacionCuenta.setCuentaBancaria(cuentaBancaria);
					operacionCuentaRepository.save(operacionCuenta);
				}
			});

		};
	}
}


