package com.sena.sistema_inventario.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.sena.sistema_inventario.model.Producto;
import com.sena.sistema_inventario.service.ProductoService;

import java.util.List;
import java.io.ByteArrayInputStream;

@RestController
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        return service.listarProductos();
    }

    @PostMapping("/productos")
    public Producto registrarProducto(@RequestBody Producto producto) {
        return service.registrarProducto(producto); 
    }

    @GetMapping("/productos/{id}")
    public Producto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @DeleteMapping("/productos/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        service.eliminarProducto(id);
    }
@GetMapping("/pdf")
public ResponseEntity<InputStreamResource> exportarPdf() {
    ByteArrayInputStream bis = service.generarReportePdf();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=reporte_productos.pdf");

    return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
}

  @PutMapping("/productos/{id}")
public Producto modificarProducto(
@PathVariable Long id,
@RequestBody Producto producto) {
return service.modificarProducto(id, producto);
}
}