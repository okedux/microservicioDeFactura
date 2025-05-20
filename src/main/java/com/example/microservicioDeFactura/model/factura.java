package com.example.microservicioDeFactura.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

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

    @Column(unique = false, length = 30, nullable = false)
    private String nombreReceptor;

    @Column(unique = false, length = 10, nullable = false)
    private int valor;

    @Column(unique = false, length = 10, nullable = false)
    private float cantidadDesechos;

    @Column(unique = false, length = 20, nullable = false)
    private String tipoDeResiduos;

    @Column(unique = false, length = 20)
    private Date fechaEmision;

    @PrePersist
    protected void onCreate() {
        this.fechaEmision = new Date(); // Asigna la fecha actual al campo
    }
}
