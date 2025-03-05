package gm.Clientesi18NSecurity.DAOrepositorio;

import gm.Clientesi18NSecurity.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

// Esta clase implementa clases ya creadas que inyectan seguridad en nuestra app
public interface UsuarioDAO extends JpaRepository<Usuario, Integer> {

    // El nombre tiene que ser exactamente este porque Spring Security lo solicita:
    Usuario findByUsername(String username);
}
