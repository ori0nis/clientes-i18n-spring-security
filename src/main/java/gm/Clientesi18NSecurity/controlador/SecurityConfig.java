package gm.Clientesi18NSecurity.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
// La configuración propuesta está deprecada, aplico la nueva y más recomendada
// La lógica del logout está en plantilla.html
// Comento los beans una vez pasamos a inyectar usuarios desde la BD
public class SecurityConfig {

    // Este nombre se utiliza en UsuarioService, por lo tanto Spring inyecta esta clase con ese nombre:
    @Autowired
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // He comentado la clase posterior a esta porque está deprecada. Los comentarios aplican:
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    // Este es el objeto que Spring va a buscar para manejar la autenticación. No es necesario hacer nada más,
    // ya que UsuarioService ya configura todo lo que tiene que ocurrir en la autenticación. Primero busca el usuario
    //  en la BD, luego recorre sus roles y los añade envueltos en la clase correcta, y luego crea el User de Spring
    //  con el nombre, el password y sus roles asociados.
//    @Autowired
//    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
//        // Establecemos que vamos a utilizar la implementación de JPA (el método loadUserByUsername):
//        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }


    // Autenticación
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("admin")
//                .password("{noop}123")  // {noop} significa que el password no está encriptado
//                .roles("ADMIN", "USER")
//                .build();
//
//        UserDetails user = User.withUsername("user")
//                .password("{noop}123")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    // Autorización
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                        // Restringimos el acceso a diversos paths (con sus subpaths) dependiendo del usuario:
//                        .requestMatchers("/editar/**", "/agregar/**", "/eliminar").hasRole("ADMIN")
//                        .requestMatchers("/").hasAnyRole("USER", "ADMIN")
//                )
//                .formLogin(withDefaults()) // Desplegamos el form de login
//                .logout(withDefaults()); // Y logout
//
//        return http.build();
//    }
}
