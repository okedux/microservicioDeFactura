
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
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
