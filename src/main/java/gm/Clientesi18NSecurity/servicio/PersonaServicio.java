package gm.Clientesi18NSecurity.servicio;

import gm.Clientesi18NSecurity.DAOrepositorio.IPersonaDAO;
import gm.Clientesi18NSecurity.modelo.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaServicio implements IPersonaServicio{

    @Autowired
    private IPersonaDAO personaDAO;

    // Estos métodos requieren @Transactional porque al trabajar con diferentes tablas, las transacciones pueden crear
    // guardados o modificaciones parciales. Tenemos que asegurar que los cambios se guarden o se rechacen de manera
    // sincronizada en todas las tablas.
    @Override
    @Transactional(readOnly = true) // Aquí no es necesario especificar nada más porque buscar no crea un commit
    public List<Persona> listarPersonas() {
        // Esta implementación requiere un cast porque se devuelve un Iterable, no una Lista:
        return (List<Persona>) personaDAO.findAll();
    }

    @Override
    @Transactional
    public Persona buscarPersona(Persona persona) {
        return personaDAO.findById(persona.getIdPersona()).orElse(null);
    }

    @Override
    @Transactional
    public Persona guardarPersona(Persona persona) {
        return personaDAO.save(persona);
    }

    @Override
    @Transactional
    public void eliminarPersona(Persona persona) {
        personaDAO.delete(persona);
    }
}
