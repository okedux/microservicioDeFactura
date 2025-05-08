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
    private int id;
    private String nombreReceptor;
    private int valor;
    private float cantidadDesechos;
}
