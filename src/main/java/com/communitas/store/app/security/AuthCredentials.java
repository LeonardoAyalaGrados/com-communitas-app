package com.communitas.store.app.security;

import lombok.Data;

@Data
public class AuthCredentials {
	private String correo;
	private String contraseña;
}
