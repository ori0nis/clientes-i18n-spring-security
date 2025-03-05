package gm.Clientesi18NSecurity.controlador;

import gm.Clientesi18NSecurity.modelo.Persona;
import gm.Clientesi18NSecurity.servicio.IPersonaServicio;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
// Anotación para el log:
@Slf4j
public class ControladorInicio {

    @Autowired
    private IPersonaServicio personaServicio;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user){
        // En este caso no podemos definir personas como una lista de Persona, ya que CrudRepository devuelve
        // un tipo Iterable y no un tipo Lista:
        var personas = personaServicio.listarPersonas();
        log.info(personas.toString());
        // Mandamos a consola qué usuario ha hecho login:
        log.info(user.toString());
        model.addAttribute("personas", personas);
        var saldoTotal = 0D;
        // Iteramos la lista de personas para obtener el saldo total:
        for(var p : personas){
            saldoTotal += p.getSaldo();
        }
        model.addAttribute("saldoTotal", saldoTotal);
        model.addAttribute("totalClientes", personas.size());
        return "index";
    }

    @GetMapping("/agregar")
    // Si metemos Persona como parámetro, Spring intentará encontrar el objeto y si no lo encuentra, lo creará (ya no
    // necesitamos hacer new Persona()).
    // Relacionamos este objeto Persona con la vista mediante el th:object. Accedemos a los atributos del objeto en los
    // inputs mediante th:field y el atributo que queremos mostrar
    public String agregar(Persona persona){
        // Esta vista va a servir para agregar y modificar. Este método solo define lo que ocurre cuando se clica en el
        // botón de crear persona
        return "modificar";
    }

    @PostMapping("/guardar")
    // A diferencia de con Angular/React, solo necesitamos pasar el objeto por parámetro para que Spring relacione los
    // valores del form con el objeto Persona de esta transacción. El único valor que no se sincroniza es el del id,
    // porque no está en el form
    public String guardar(@Valid Persona persona, Errors errors){
        if(errors.hasErrors()){
            return "modificar";
        }
        personaServicio.guardarPersona(persona);
        return "redirect:/";
    }

    @GetMapping("/editar/{idPersona}")
    public String editar(Persona persona, Model model){
        // El objeto que buscamos ya tiene asignado el id que está en el context path
        persona = personaServicio.buscarPersona(persona);
        // Esto es lo que hace que se puedan ver los valores de la persona en el form:
        model.addAttribute("persona", persona);
        // Lanzamos modificar de nuevo porque esta vista nos sirve tanto para guardar como para editar. Si Spring
        // no encuentra el objeto Persona, guardará los datos que introduzcamos como nuevos. Si lo encuentra, llenará
        // los campos con sus datos y posteriormente modificará el objeto en la BD.
        return "modificar";
    }

    @GetMapping("/eliminar/{idPersona}")
    public String eliminar(Persona persona){
        persona = personaServicio.buscarPersona(persona);
        // Suele ser mejor práctica eliminar un registro por id, pero en este proyecto lo hacemos así
        personaServicio.eliminarPersona(persona);
        return "redirect:/";
    }
}
