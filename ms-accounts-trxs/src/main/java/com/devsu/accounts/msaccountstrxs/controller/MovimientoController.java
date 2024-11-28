package com.devsu.accounts.msaccountstrxs.controller;

import com.devsu.accounts.msaccountstrxs.model.Movimiento;
import com.devsu.accounts.msaccountstrxs.service.MovimientoService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @GetMapping("/cuenta/{numeroCuenta}")
    public List<Movimiento> obtenerMovimientosPorCuenta(@PathVariable Long numeroCuenta) {
        return movimientoService.obtenerMovimientosPorCuenta(numeroCuenta);
    }

    @PostMapping
    public ResponseEntity<Movimiento> guardarMovimiento(@RequestBody Movimiento movimiento) {
        return ResponseEntity.ok(movimientoService.guardarMovimiento(movimiento));
    }
}
