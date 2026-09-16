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

    // Ruta para la página principal
    @GetMapping({"/", "/index", "/index.html"})
    public String verPaginaDeInicio(Model model) {
        List<Producto> listaProductos = service.listarProductos();

        model.addAttribute("totalProductos", listaProductos.size());
        model.addAttribute("disponibles", listaProductos.stream().filter(p -> p.getCantidad() > 0).count());
        model.addAttribute("agotados", listaProductos.stream().filter(p -> p.getCantidad() == 0).count());
        model.addAttribute("ultimosProductos", listaProductos);

        return "index"; 
    }

    // Ruta para la vista de productos (Productos.html)
    @GetMapping({"/productos-vista", "/Productos.html"})
    public String verProductosVista() {
        return "Productos"; // Busca Productos.html en la carpeta templates
    }

    // Ruta para el formulario de registro (registrar.html)
    @GetMapping({"/registrar", "/registrar.html"})
    public String verRegistrarVista() {
        return "registrar"; // Busca registrar.html en la carpeta templates
    }
}