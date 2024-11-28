package com.devsu.accounts.msaccountstrxs.repository;



import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByClienteId(Long clienteId);

    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);

}
