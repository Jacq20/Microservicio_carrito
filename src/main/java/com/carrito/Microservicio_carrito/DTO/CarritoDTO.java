package com.carrito.Microservicio_carrito.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {
    
    @NotBlank(message = "El id de usuario es obligatorio")
    private String idUsuario;
}
