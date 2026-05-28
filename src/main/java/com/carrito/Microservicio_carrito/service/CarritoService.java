package com.carrito.Microservicio_carrito.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.carrito.Microservicio_carrito.DTO.CarritoDTO;
import com.carrito.Microservicio_carrito.model.Carrito;
import com.carrito.Microservicio_carrito.model.UsuarioDTO;
import com.carrito.Microservicio_carrito.repository.CarritoRepository;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Carrito crearCarrito(CarritoDTO dto) {
        try {
            // Le preguntamos a ms_usuarios si el usuario existe
            String url = "http://localhost:8089/api/usuarios/" + dto.getIdUsuario();
            UsuarioDTO usuario = restTemplate.getForObject(url, UsuarioDTO.class);

            // Si el usuario NO existe, no creamos el carrito
            if (usuario == null) {
                throw new RuntimeException("El usuario no existe");
            }

            Carrito carrito = new Carrito();
            carrito.setIdUsuario(dto.getIdUsuario());
            carrito.setItems(new ArrayList<>());
            carrito.setSubtotal(BigDecimal.ZERO);
            carrito.setDescuento(BigDecimal.ZERO);
            carrito.setEstado(Carrito.EstadoCarrito.ACTIVO);
            return carritoRepository.save(carrito);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Optional<Carrito> verCarrito(String idUsuario) {
        return carritoRepository.findByIdUsuarioAndEstado(
            idUsuario, Carrito.EstadoCarrito.ACTIVO);
    }

    public Optional<Carrito> agregarItem(String idUsuario,
            String idProducto, int cantidad) {
        try {
            Optional<Carrito> carritoOpt = carritoRepository
                .findByIdUsuarioAndEstado(idUsuario, Carrito.EstadoCarrito.ACTIVO);
            carritoOpt.ifPresent(c -> {
                c.getItems().add(idProducto + ":" + cantidad);
                carritoRepository.save(c);
            });
            return carritoOpt;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Optional<Carrito> eliminarItem(String idUsuario, String idProducto) {
        try {
            Optional<Carrito> carritoOpt = carritoRepository
                .findByIdUsuarioAndEstado(idUsuario, Carrito.EstadoCarrito.ACTIVO);
            carritoOpt.ifPresent(c -> {
                c.getItems().removeIf(item -> item.startsWith(idProducto + ":"));
                carritoRepository.save(c);
            });
            return carritoOpt;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Optional<Carrito> actualizarCantidad(String idUsuario,
            String idProducto, int cantidad) {
        try {
            Optional<Carrito> carritoOpt = carritoRepository
                .findByIdUsuarioAndEstado(idUsuario, Carrito.EstadoCarrito.ACTIVO);
            carritoOpt.ifPresent(c -> {
                c.getItems().removeIf(item -> item.startsWith(idProducto + ":"));
                c.getItems().add(idProducto + ":" + cantidad);
                carritoRepository.save(c);
            });
            return carritoOpt;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Optional<Carrito> aplicarCupon(String idUsuario, String codigo) {
        try {
            Optional<Carrito> carritoOpt = carritoRepository
                .findByIdUsuarioAndEstado(idUsuario, Carrito.EstadoCarrito.ACTIVO);
            carritoOpt.ifPresent(c -> {
                c.setCuponAplicado(codigo);
                c.setDescuento(new BigDecimal("0.10"));
                carritoRepository.save(c);
            });
            return carritoOpt;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    public Optional<Carrito> vaciarCarrito(String idUsuario) {
        try {
            Optional<Carrito> carritoOpt = carritoRepository
                .findByIdUsuarioAndEstado(idUsuario, Carrito.EstadoCarrito.ACTIVO);
            carritoOpt.ifPresent(c -> {
                c.getItems().clear();
                c.setEstado(Carrito.EstadoCarrito.VACIADO);
                c.setSubtotal(BigDecimal.ZERO);
                c.setDescuento(BigDecimal.ZERO);
                carritoRepository.save(c);
            });
            return carritoOpt;
        } catch (RuntimeException e) {
            throw e;
        }
    }

}
