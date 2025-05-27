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

    @Column(nullable= false, length= 30)
    private String nombre;

    @Column(nullable= false, length= 30)
    private String tipo;

    @Column(nullable= false)
    private Double peso;

    @Column(nullable= false, length= 10)
    private String unidadMedida;
    
    @Column(nullable= false, length= 6)
    private String peligrosidad;

    @Column(nullable= false, length= 20)
    private String empresaEmisora;

    @Column(nullable= false)
    private Double volumen;

    @Column(nullable= false, length= 10)
    private String clasificacion;

}

