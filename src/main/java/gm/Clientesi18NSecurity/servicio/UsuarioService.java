package gm.Clientesi18NSecurity.servicio;

import gm.Clientesi18NSecurity.DAOrepositorio.UsuarioDAO;
import gm.Clientesi18NSecurity.modelo.Rol;
import gm.Clientesi18NSecurity.modelo.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

// Este nombre también es importante para que Spring Security reconozca la configuración:
@Service("userDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO; // Con esta clase interactuaremos con las dos tablas, usuario y rol

    @Override
    // Necesitamos esta notación para que Spring pueda abrir una transacción y acceder a la lista de roles incluso sin
    // una sesión iniciada:
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByUsername(username);

        if(usuario == null){
            throw new UsernameNotFoundException(username);
        }

        // Para que los roles funcionen en Spring Security, tenemos que envolverlos con esta clase:
        var roles = new ArrayList<GrantedAuthority>();

        for(Rol rol : usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
