package com.sena.sistema_inventario.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.sena.sistema_inventario.model.Producto;
import com.sena.sistema_inventario.model.repository.ProductoRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service 
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    } 

    public Producto guardarProducto(Producto producto) {
        return repository.save(producto);
    } 

    public Producto registrarProducto(Producto producto) {
        return repository.save(producto);
    }
    
    public Producto modificarProducto(Long id, Producto producto) {
        Producto productoExistente = repository.findById(id).orElseThrow();
        productoExistente.setCodigo(producto.getCodigo());
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setCategoria(producto.getCategoria());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCantidad(producto.getCantidad());
        return repository.save(productoExistente);
    }

    public List<Producto> listarProductos() {
        return repository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void eliminarProducto(Long id) {
        repository.deleteById(id);
    }

    public ByteArrayInputStream generarReportePdf() {
        List<Producto> productos = repository.findAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Título del reporte
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph titulo = new Paragraph("Reporte de Inventario de Productos", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n")); // Espacio

            // Tabla con 5 columnas (Código, Nombre, Categoría, Precio, Cantidad)
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            // Cabeceras de la tabla
            String[] headers = {"Código", "Nombre", "Categoría", "Precio", "Cantidad"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Agregar los productos a la tabla
            for (Producto producto : productos) {
                table.addCell(producto.getCodigo());
                table.addCell(producto.getNombre());
                table.addCell(producto.getCategoria());
                table.addCell("$" + producto.getPrecio());
                table.addCell(String.valueOf(producto.getCantidad()));
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}