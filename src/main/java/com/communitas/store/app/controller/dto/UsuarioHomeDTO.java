package com.communitas.store.app.controller.dto;

import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioHomeDTO {
    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 9,max = 9)
    private String celular;
    @NotNull
    @Size(min = 5)
    private String contraseña;

    @NotNull
    private String direccion;

    @NotNull
    private Distrito distrito;
}
