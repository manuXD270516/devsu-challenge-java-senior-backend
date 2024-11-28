package com.devsu.accounts.msaccountstrxs.controller;

import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import com.devsu.accounts.msaccountstrxs.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/cliente/{clienteId}")
    public List<Cuenta> obtenerCuentasPorCliente(@PathVariable Long clienteId) {
        return cuentaService.obtenerCuentasPorCliente(clienteId);
    }

    @PostMapping
    public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
        return cuentaService.guardarCuenta(cuenta);
    }
}
