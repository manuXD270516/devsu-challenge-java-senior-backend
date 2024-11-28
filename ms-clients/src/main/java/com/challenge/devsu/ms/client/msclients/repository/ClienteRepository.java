package com.challenge.devsu.ms.client.msclients.repository;

import com.challenge.devsu.ms.client.msclients.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


}
