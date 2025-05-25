package com.example.microservicioDeFactura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "residuo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Residuo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique= false, length= 30, nullable= false)
    private String nombre;

    @Column(unique= false, length= 30, nullable= false)
    private String tipo;

    @Column(unique= false, length= 10, nullable= false)
    private String peso;

    @Column(unique= false, length= 6, nullable= false)
    private String peligrosidad;

    @Column(unique= false, length= 20, nullable= false)
    private String empresaEmisora;

    @Column(unique= false, length= 8, nullable= false)
    private String volumen;

    @Column(unique= false, length= 10, nullable= false)
    private String clasificacion;
}
