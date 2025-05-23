package com.example.microservicioDeFactura.model;

import lombok.*;
import jakarta.persistence.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String nombreEmpresa;

    @Column(nullable=false, unique=true, length=50)
    private String rutEmpresa;

    @Column(nullable=false, length=100)
    private String contacto;

    @Column(nullable=false, length=100)
    private String correo;

    @Column(length=200)
    private String direccion;
}
