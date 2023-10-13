package com.communitas.store.app.response;

import com.communitas.store.app.entity.Distrito;
import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.entity.VentaOrden;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioResponse {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String fullName;
    private String celular;
    private String email;
    private String contraseña;
    private Usuario.Rol rol;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private String direccion;
    private Integer distrito;
    private List<VentaOrden> ordenes;
}
