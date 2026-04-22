package nicolagraziani.U5_W3_D1_Spring_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity

@EnableMethodSecurity //IMPORTANTE PER USARE LE AUTORIZZAZIONI
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());
        httpSecurity.sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.formLogin(formLogin -> formLogin.disable());
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.cors(Customizer.withDefaults()); // OBBLIGATORIO PER USARE CONFIGURAZIONE CORS
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder getBCrypt() {
        return new BCryptPasswordEncoder(12);
    }


    //    PER POTER FAR GIRARE IL BACKEND SU UN FRONTEND
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "https://mywonderfulfe.com"/* QUI VANNO INSERITI I DOMINI DA INSERIRE NELLA 'WHITELIST' */));
//    Nel caso di API pubbliche si potrebbe mettere configuration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
