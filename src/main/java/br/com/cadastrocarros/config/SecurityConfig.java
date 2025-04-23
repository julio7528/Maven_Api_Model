package br.com.cadastrocarros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// Imports do Password Encoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean // Define o Bean para codificar senhas (AINDA É NECESSÁRIO!)
    public PasswordEncoder passwordEncoder() {
        // Mesmo para usuários em memória, a senha precisa estar codificada
        // para o Spring Security comparar corretamente.
        return new BCryptPasswordEncoder();
    }

    // REMOVIDO: Bean InMemoryUserDetailsManager que estava causando conflito
    // com UserDetailsServiceImpl que busca usuários do banco.

    @Bean // Define a cadeia de filtros e regras de acesso
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                // Permite acesso irrestrito a recursos estáticos
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                // Permite acesso à página de login
                .requestMatchers("/", "/login").permitAll() // Permitido aqui
                // Permite acesso ao console H2 (se ainda quiser usá-lo para outras tabelas)
                .requestMatchers("/h2-console/**").permitAll()

                // Protege as rotas do seu CarroController (exemplo)
                .requestMatchers("/carros/**").hasRole("USER") // Exige pelo menos USER
                    // .requestMatchers("/carros/admin/**").hasRole("ADMIN") // Se tiver área admin

                // .requestMatchers("/login").permitAll() // <-- ESTA LINHA É REDUNDANTE, já permitido acima e pelo formLogin

                // Qualquer outra requisição precisa estar autenticada (ISSO INCLUIRÁ "/" e "/index" AGORA)
                .anyRequest().authenticated()
            )
            // Configura o formulário de login personalizado
            .formLogin(form -> form
                .loginPage("/login") // URL da página de login personalizada
                .defaultSuccessUrl("/carros/listar", true) // Redireciona para /carros/listar após sucesso
                .permitAll() // Permite acesso à página de login em si (importante!)
            )
            // Configura o logout padrão
            .logout(logout -> logout
                .logoutSuccessUrl("/?logout") // Redireciona para a raiz com parâmetro após logout
                .permitAll()
            )
            // Configurações para H2 Console (se mantiver habilitado)
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
    // ... existing code ...
}