package com.devsu.accounts.msaccountstrxs.repository;


import com.devsu.accounts.msaccountstrxs.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaNumeroCuenta(Long numeroCuenta);

    List<Movimiento> findByCuentaNumeroCuentaAndFechaBetween(Long numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin);
}
