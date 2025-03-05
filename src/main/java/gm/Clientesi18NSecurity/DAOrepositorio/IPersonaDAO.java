package gm.Clientesi18NSecurity.DAOrepositorio;

import gm.Clientesi18NSecurity.modelo.Persona;
import org.springframework.data.repository.CrudRepository;

// JpaRepository es una implementación más compleja de CrudRepository, que solo contiene los métodos
// más simples
public interface IPersonaDAO extends CrudRepository<Persona, Integer> {
}
