package com.example.microservicioDeFactura.repository;

import com.example.microservicioDeFactura.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByRutEmpresa(String rutEmpresa);
}
