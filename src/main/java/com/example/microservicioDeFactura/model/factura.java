package com.example.microservicioDeFactura.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "factura")
public class factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = false, length = 30, nullable = false)
    private int rutCliente;

    @Column(unique = false, length = 1, nullable = false)
    private String dvRut;

    @ManyToOne
    private Cliente cliente;

    @Column(unique = false, length = 10, nullable = false)
    private int valor;

    @Column(unique = false, length = 10, nullable = false)
    private float cantidadDesechos;

    @ManyToOne
    private Residuo residuo;

    @Column(unique = false, length = 20)
    private Date fechaEmision;

    @PrePersist
    protected void onCreate() {
        this.fechaEmision = new Date(); // Asigna la fecha actual al campo
    }
}
