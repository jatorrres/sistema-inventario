package com.sena.sistema_inventario.model.repository;
import com.sena.sistema_inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
