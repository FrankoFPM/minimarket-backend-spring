package org.minimarket.minimarketbackendspring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlantillaController {

    @GetMapping("/plantilla")
    public String plantilla() {
        return "{\"message\": \"Este es un backend RESTful\"}";
    }
}
