package com.carrito.Microservicio_carrito.model;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nomUsuario;
    private String correo;
    private String estado;
}
