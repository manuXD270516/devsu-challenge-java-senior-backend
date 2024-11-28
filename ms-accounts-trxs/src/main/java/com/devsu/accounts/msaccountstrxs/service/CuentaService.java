package com.devsu.accounts.msaccountstrxs.service;

import com.devsu.accounts.msaccountstrxs.model.Cuenta;
import com.devsu.accounts.msaccountstrxs.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> obtenerCuentasPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void updateClientAccounts(Long clientId, boolean estado) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clientId);
        for (Cuenta cuenta : cuentas) {
            cuenta.setEstado(estado);
            cuentaRepository.save(cuenta);
        }
    }
}
