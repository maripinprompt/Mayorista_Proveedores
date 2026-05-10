package mayorista.proveedores.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // indica que esta clase tiene configuraciones de Spring
@EnableWebSecurity // activa Spring Security en la aplicación
public class SecurityConfig {

    @Bean // Spring gestiona este objeto automáticamente
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // este método define las REGLAS de seguridad
                                                    // HttpSecurity es el objeto con el que configuramos qué está permitido y qué no
        http
            .csrf(csrf -> csrf.disable()) //desactivamos CSRF para APIs REST
                                        //CSRF es un tipo de ataque web
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // swagger sin autenticación
                 // estas rutas de Swagger las dejamos libres para que cualquiera pueda verlas,sin necesidad de usuario y contraseña
                .anyRequest().authenticated() // cualquier otro endpoint requiere autenticación
            )
            .httpBasic(basic -> {}); // autenticación básica con usuario y contraseña
        return http.build();
    }
}