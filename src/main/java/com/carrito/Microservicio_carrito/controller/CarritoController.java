package com.carrito.Microservicio_carrito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carrito.Microservicio_carrito.DTO.CarritoDTO;
import com.carrito.Microservicio_carrito.model.Carrito;
import com.carrito.Microservicio_carrito.service.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;

    @PostMapping
    public ResponseEntity<?> crearCarrito(@Valid @RequestBody CarritoDTO dto) {
        try {
            Carrito carrito = carritoService.crearCarrito(dto);
            return new ResponseEntity<>(carrito, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<?> verCarrito(@PathVariable String idUsuario) {
        Carrito carrito = carritoService.verCarrito(idUsuario).orElse(null);
        if (carrito == null) {
            return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carrito, HttpStatus.OK);
    }

    @PostMapping("/{idUsuario}/items")
    public ResponseEntity<?> agregarItem(
            @PathVariable String idUsuario,
            @RequestParam String idProducto,
            @RequestParam int cantidad) {
        try {
            Carrito carrito = carritoService
                .agregarItem(idUsuario, idProducto, cantidad).orElse(null);
            if (carrito == null) {
                return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{idUsuario}/items/{idProducto}")
    public ResponseEntity<?> eliminarItem(
            @PathVariable String idUsuario,
            @PathVariable String idProducto) {
        try {
            Carrito carrito = carritoService
                .eliminarItem(idUsuario, idProducto).orElse(null);
            if (carrito == null) {
                return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{idUsuario}/items/{idProducto}")
    public ResponseEntity<?> actualizarCantidad(
            @PathVariable String idUsuario,
            @PathVariable String idProducto,
            @RequestParam int cantidad) {
        try {
            Carrito carrito = carritoService
                .actualizarCantidad(idUsuario, idProducto, cantidad).orElse(null);
            if (carrito == null) {
                return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

     @PutMapping("/{idUsuario}/cupon")
    public ResponseEntity<?> aplicarCupon(
            @PathVariable String idUsuario,
            @RequestParam String codigo) {
        try {
            Carrito carrito = carritoService
                .aplicarCupon(idUsuario, codigo).orElse(null);
            if (carrito == null) {
                return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

     @DeleteMapping("/{idUsuario}/vaciar")
    public ResponseEntity<?> vaciarCarrito(@PathVariable String idUsuario) {
        try {
            Carrito carrito = carritoService.vaciarCarrito(idUsuario).orElse(null);
            if (carrito == null) {
                return new ResponseEntity<>("Carrito no encontrado", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carrito, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
