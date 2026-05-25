package com.carrito.Microservicio_carrito.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carrito.Microservicio_carrito.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByIdUsuarioAndEstado(
        String idUsuario, Carrito.EstadoCarrito estado);
}
