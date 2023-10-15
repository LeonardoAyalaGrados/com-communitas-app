package com.communitas.store.app.security;

import com.communitas.store.app.entity.Usuario;
import com.communitas.store.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Usuario usuario= usuarioRepository.findByEmail(correo)
		.orElseThrow(()->new UsernameNotFoundException("El email "+correo+"no se encuentra registrada"));
		
		return new UserDetailsImpl(usuario);
	}

}
