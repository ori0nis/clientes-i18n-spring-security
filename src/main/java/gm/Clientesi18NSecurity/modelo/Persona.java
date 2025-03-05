package gm.Clientesi18NSecurity.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idPersona;

    // Agregamos validaciones para mejorar el funcionamiento del form:
    // NotNull también se puede utilizar, pero no valida cadenas vacías
    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    private String telefono;

    @NotEmpty
    @Email
    private String email;

    @NotNull // Utilizamos NotNull porque es el tipo adecuado para números
    private Double saldo;
}
