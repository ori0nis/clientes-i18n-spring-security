package gm.Clientesi18NSecurity.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    // Utilizamos List porque un usuario puede tener varios roles
    @OneToMany
    @JoinColumn(name = "id_usuario")
    private List<Rol> roles;
}
