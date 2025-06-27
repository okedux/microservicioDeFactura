package com.example.microservicioDeFactura.assemblers;

import com.example.microservicioDeFactura.controller.ResiduoController;
import com.example.microservicioDeFactura.model.Residuo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ResiduoModelAssembler implements RepresentationModelAssembler<Residuo, EntityModel<Residuo>> {

    @Override
    public EntityModel<Residuo> toModel(Residuo residuo) {
        return EntityModel.of(residuo,
                linkTo(methodOn(ResiduoController.class).buscarPorId(residuo.getId())).withSelfRel(),
                linkTo(methodOn(ResiduoController.class).listar()).withRel("residuos")
        );
    }

}
