package com.example.microservicioDeFactura.assemblers;

import com.example.microservicioDeFactura.controller.controllerFacturaV2;
import com.example.microservicioDeFactura.model.Factura;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FacturaModelAssembler implements RepresentationModelAssembler<Factura, EntityModel<Factura>> {

    @Override
    public EntityModel<Factura> toModel(Factura carrera) {
        return EntityModel.of(carrera,
                linkTo(methodOn(controllerFacturaV2.class).buscarPorId(carrera.getId())).withSelfRel(),
                linkTo(methodOn(controllerFacturaV2.class).listarFacturas()).withRel("facturas")
        );
    }
}
