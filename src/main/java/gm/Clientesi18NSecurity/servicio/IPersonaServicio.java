package gm.Clientesi18NSecurity.servicio;

import gm.Clientesi18NSecurity.modelo.Persona;
import java.util.List;

public interface IPersonaServicio {

    public List<Persona> listarPersonas();

    public Persona buscarPersona(Persona persona);

    public Persona guardarPersona(Persona persona);

    public void eliminarPersona(Persona persona);
}
