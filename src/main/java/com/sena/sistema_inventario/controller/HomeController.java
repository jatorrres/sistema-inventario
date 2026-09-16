package com.sena.sistema_inventario.controller;

import com.sena.sistema_inventario.model.Producto;
import com.sena.sistema_inventario.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final ProductoService service;

    public HomeController(ProductoService service) {
        this.service = service;
    }

    @GetMapping({"/", "/index", "/index.html"})
    public String verPaginaDeInicio(Model model) {
        List<Producto> listaProductos = service.listarProductos();

        long totalProductos = listaProductos.size();
        
        long disponibles = listaProductos.stream()
                .filter(p -> p.getCantidad() > 0)
                .count();
                
        long agotados = listaProductos.stream()
                .filter(p -> p.getCantidad() == 0)
                .count();

        List<Producto> ultimosProductos = listaProductos.size() > 3 
                ? listaProductos.subList(listaProductos.size() - 3, listaProductos.size()) 
                : listaProductos;

        model.addAttribute("totalProductos", totalProductos);
        model.addAttribute("disponibles", disponibles);
        model.addAttribute("agotados", agotados);
        model.addAttribute("ultimosProductos", ultimosProductos);

        return "index"; 
    }
}